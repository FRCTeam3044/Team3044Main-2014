/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team3044.robot;


import com.team3044.RobotComponents.Drive;
import com.team3044.RobotComponents.TestShooter;
import com.team3044.RobotComponents.Pickup;
import com.team3044.RobotComponents.Shooter;
import com.team3044.network.NetTable;
import com.team3044.vision.targets.Target;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
/*
--------------------------------------
Allocated Analog inputs on ds:
--------------------------------------
Analog 1: SingleSpeed speed
Analog 2: Top shooter pot value
--------------------------------------
Digital Inputs:
--------------------------------------
None

*/
public class RobotMain extends IterativeRobot {
    
    private Utilities utils;
    private Components components = new Components(); // if this works as static... Check net tables
    Drive drive;
    Pickup pickup;
    Shooter shooter;
    NetTable table = NetTable.getInstance();
    DriverStationLCD lcd = DriverStationLCD.getInstance();
    DriverStation ds = DriverStation.getInstance();
    TestShooter testShooter = new TestShooter();
    
    Target leftTarget;
    Target rightTarget;
    
    
    final int PRE_OPERATOR_MOVE = 0;
    final int STANDARD_TELEOP = 1;
    int teleopState = STANDARD_TELEOP;
    
    int autoType = 0;
    int autoIndex = 0;
    
    double autoStartTime = 0;
    double time = 0;
    
    double teleopTime = 0;
    double oldTeleopTime = 0;
    
    final int MOVE_THEN_SHOOT = 0;
    final int SHOOT_THEN_MOVE = 1;
        
    //Camera camera = new Camera();
    
    public Utilities getUtilities(){
        return utils;
    }
    
    public Components getComponents(){
        return components;
    }
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        utils = new Utilities();
        components = new Components();
        components.robotInit();
        pickup = new Pickup();
        shooter = new Shooter();
        drive = new Drive();
        
        shooter.robotInit();
        pickup.robotInit();
        drive.robotInit();
    
    }

    
    public void testPeriodic(){
        drive.DriveAuto();

               
        System.out.println("Left drive encoder: " + String.valueOf(Components.encoderleftdrive.getDistance()));
        System.out.println("Right Drive Encoder: " + String.valueOf(Components.encoderrightdrive.getDistance()));
                
        if(drive.hasTraveledSetDistance()){
            drive.stop();
            
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        shooter.teleop();
        pickup.teleop();
        drive.DriveAuto();
        if(table.getDouble("ISHOT", 0) == 1 && ds.getMatchTime() < 5){ 
            autoType = SHOOT_THEN_MOVE;
        }
        System.out.println(autoIndex);
        autoShootAndMove();
        /*switch(autoType){
            case MOVE_THEN_SHOOT:
                autoMoveAndShoot();
            break;
            
            case SHOOT_THEN_MOVE:
                autoShootAndMove();
            break;
                
        }*/
    }
    
    public void testInit(){
        drive.setDistanceToTravel(72, 72, .25);
        drive.startdriving(true);
        pickup.teleopInit();
        drive.teleopInit();
        shooter.teleopInit();
    }
 

    public void teleopInit(){
        pickup.teleopInit();
        shooter.teleopInit();
        drive.teleopInit();

        Components.encoderleftdrive.reset();
        Components.encoderrightdrive.reset();
        
    }
    
    /**
     * This function is called periodically during operator control
     */
    
    public void teleopPeriodic() {
        teleopTime = 0;
        
        if(teleopTime - oldTeleopTime < .5/*seconds*/){
            //Updating and using values
        }
        
        switch(teleopState){
            
            case PRE_OPERATOR_MOVE:
            /*
                Components.leftdriveY = -.75;
                Components.rightdriveY = .75;
                drive.Drivemain();
                if(Math.abs(Utilities.deadband(components.GamePaddrive.getRawAxis(2), .1)) > 0
                    || Math.abs(Utilities.deadband(components.GamePaddrive.getRawAxis(5), .1)) > 0){
                        
                    teleopState = STANDARD_TELEOP;
                }
            */
                break;
            
            
            case STANDARD_TELEOP:
                             
                components.upDateVals();
                components.updatedrivevals();
                pickup.teleop();
                shooter.teleop();
                drive.Drivemain();
                System.out.println("Left drive encoder: " + String.valueOf(Components.encoderleftdrive.getDistance()));
                System.out.println("Right Drive Encoder: " + String.valueOf(Components.encoderrightdrive.getDistance()));
                lcd.println(DriverStationLCD.Line.kUser1, 1, "Left Drive Encoder: " + String.valueOf(Components.encoderleftdrive.getDistance()));
                lcd.println(DriverStationLCD.Line.kUser2, 1, "Right Drive Encoder: " + String.valueOf(Components.encoderrightdrive.getDistance()));
                lcd.println(DriverStationLCD.Line.kUser3, 1, "Shooter Up: " + Components.UpShooterLimit.get() + "    ");
                lcd.println(DriverStationLCD.Line.kUser4, 1, "Shooter Down: " + Components.DownShooterLimit.get() + "     ");
                lcd.println(DriverStationLCD.Line.kUser5, 1, "Target pot: " + shooter.shootpothigh + "     ");
                lcd.println(DriverStationLCD.Line.kUser6, 1, "Pot: " + Components.ShooterPot.getVoltage()+ "      ");
                lcd.updateLCD();
            break;
        
    }
        if(teleopTime != oldTeleopTime){
            oldTeleopTime = teleopTime;
        }

    }
    
    public void autoInit(){
        
    }
    
    public double getLastUpdateTime(){
        return table.getDouble("TIME");
    }
    
    public void autoMoveAndShoot(){
        switch(autoIndex){
            case 0:
                drive.setDistanceToTravel(60, 60,.5);
                
                drive.startdriving(true);
                autoIndex += 1;
            break;
            case 1:            
                if(drive.hasTraveledSetDistance() || ds.getMatchTime() >= 4){
                    
                    drive.stop();
                    autoIndex++;
                    time = ds.getMatchTime();
                }
            break;
            case 2:
                Components.pickupdown = true;
                //assuming it takes .5 seconds from shot to scoring
                if(ds.getMatchTime() > 4.5){
                    autoIndex ++;
                    Components.pickupdown = false;
                }
            break;
            case 3:
                if(pickup.getPickarm() == pickup.STOPPED_DOWN && shooter.getshooterstate() == shooter.down){
                   Components.shoot = true;
                   autoIndex ++;
                }
            break;
            case 4:
                Components.shoot = false;
                if(shooter.getshooterstate() == shooter.stopped){
                    Components.shooterdown = true;
                    autoIndex ++;
                }
            break;
            case 5:
                Components.shooterdown = false;
            break;
            
            
        }
    }
    
    public void autoLowGoal(){
        switch(autoIndex){
            case 0:
                drive.setDistanceToTravel(-180, -180,.5);
                drive.startdriving(true);
                autoIndex ++;
                break;
            case 1:
                if(drive.hasTraveledSetDistance()){
                    drive.stop();
                    autoIndex++;
                }
                break;
            case 2:
                if(ds.getMatchTime() > 7){
                    autoIndex++;
                }
                break;
            case 3:
                Components.pickupdown = true;
                autoIndex ++;
                break;
            case 4:
                Components.pickupdown = false;
                if(pickup.getPickarm() == pickup.STOPPED_DOWN){
                    Components.rollerreverse = true;
                    autoIndex ++;
                }
        }
    }
    
    public void autoShootAndMove(){
        switch(autoIndex){
            case 0:
                Components.pickupdown = true;
                if(pickup.getPickarm() == pickup.STOPPED_DOWN || ds.getMatchTime() > 3){
                    Components.pickupdown = false;
                    autoIndex ++;
                    
                }
                if(shooter.getshooterstate() == shooter.stopped){
                    //Components.shooterdown = true;
                }else if(shooter.getshooterstate() == shooter.down){
                    //Components.shooterdown = false;
                }
                
            break;
            case 1:
                Components.shoot = false;
                Components.shooterdown = false;
                if(shooter.getshooterstate() == shooter.down){
                    //Components.shoot = true;
                    autoIndex++;
                }
            break;
            case 2:
                //Components.shoot = false;
                
                if(shooter.getshooterstate() == shooter.stopped || ds.getMatchTime() > 4){
                    //Components.shooterdown = true;
                    autoIndex++;
                }
                
            break;
            case 3:
                drive.setDistanceToTravel(60, 60,.5);
                drive.startdriving(true);
                if(ds.getMatchTime() >= 4.5){
                    autoIndex++;
                }
            break;
            case 4:
                if(drive.hasTraveledSetDistance()){
                    drive.stop();
                    autoIndex ++;
                }
        }
    }
    
}
