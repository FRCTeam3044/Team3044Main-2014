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
import com.team3044.network.Camera;
import com.team3044.network.NetTable;
import com.team3044.vision.targets.Target;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    boolean inShootingRange = false;
    double calculatedShootVoltage = 0.0;
    double calculatedShootDistance = 0.0;
    double calculatedShootAngle = 0.0;
    double voltagescale = (1024 / 5);
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

    Camera camera = new Camera();

    public Utilities getUtilities() {
        return utils;
    }

    public Components getComponents() {
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

    public void testPeriodic() {
        drive.DriveAuto();

        System.out.println("Left drive encoder: " + String.valueOf(Components.encoderleftdrive.getDistance()));
        System.out.println("Right Drive Encoder: " + String.valueOf(Components.encoderrightdrive.getDistance()));

        if (drive.hasTraveledSetDistance()) {
            drive.stop();

        }
    }

    public void autonomousInit() {
        drive.teleopInit();
        drive.autoInit();
        pickup.teleopInit();
        shooter.teleopInit();

        autoIndex = 0;
        autoType = 0;

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        shooter.teleop();
        pickup.teleop();
        drive.DriveAuto();
        //if(table.getDouble("ISHOT", 0) == 1 && ds.getMatchTime() < 5){ 
        //    autoType = SHOOT_THEN_MOVE;
        //}

        //
        //autoLowGoal();
        /*switch(autoType){
         case MOVE_THEN_SHOOT:
         autoMoveAndShoot();
         break;
            
         case SHOOT_THEN_MOVE:
         autoShootAndMove();
         break;
                
         }*/
    }

    public void testInit() {
        drive.setDistanceToTravel(180, 180, .25);
        drive.startdriving(true);
        pickup.teleopInit();
        drive.teleopInit();
        shooter.teleopInit();
    }

    public void teleopInit() {
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
        try {
            teleopTime = 0;

            //try {
            //try {
            //SmartDashboard.putNumber("Can voltage Left", Components.shootermotorleft.getOutputVoltage());
            //SmartDashboard.putNumber("Can voltage Right", Components.shootermotorright.getOutputVoltage());
            //SmartDashboard.putNumber("Can voltage Right 2", Components.shootermotorright2.getOutputVoltage());
            //SmartDashboard.putNumber("Can voltage Left 2", Components.shootermotorleft2.getOutputVoltage());
            //} catch (CANTimeoutException ex) {
            //    ex.printStackTrace();
            //}
            lcd.println(DriverStationLCD.Line.kUser1, 1, "Shooter Up: " + Components.UpShooterLimit.get());
            lcd.println(DriverStationLCD.Line.kUser2, 1, "Shooter Down: " + Components.DownShooterLimit.get());
            lcd.println(DriverStationLCD.Line.kUser3, 1, "Pickup Up: " + Components.UpPickupLimit.get() + "    ");
            lcd.println(DriverStationLCD.Line.kUser4, 1, "Pickup Down: " + Components.DownPickupLimit.get() + "     ");
            lcd.println(DriverStationLCD.Line.kUser5, 1, "Ultrasonic: " + Components.ultrasonic.getVoltage() * voltagescale + "     ");
            lcd.println(DriverStationLCD.Line.kUser6, 1, "Pot: " + Components.ShooterPot.getVoltage() + "      ");
            lcd.updateLCD();

            switch (teleopState) {

                case PRE_OPERATOR_MOVE:

                    Components.leftdriveY = -.75;
                    Components.rightdriveY = -.75;
                    drive.Drivemain();
                    if (Math.abs(Utilities.deadband(components.GamePaddrive.getRawAxis(2), .1)) > 0
                            || Math.abs(Utilities.deadband(components.GamePaddrive.getRawAxis(5), .1)) > 0) {

                        teleopState = STANDARD_TELEOP;
                    }

                    break;

                case STANDARD_TELEOP:

                    components.upDateVals();
                    components.updatedrivevals();
                    pickup.teleop();
                    shooter.teleop();
                    drive.Drivemain();
                    SmartDashboard.putNumber("LEFT FRONT VOLTAGE", Components.shootermotorleft.getOutputVoltage());
                    SmartDashboard.putNumber("RIGHT FRONT VOLTAGE", Components.shootermotorright.getOutputVoltage());
                    SmartDashboard.putNumber("LEFT BACK VOLTAGE", Components.shootermotorleft2.getOutputVoltage());
                    SmartDashboard.putNumber("RIGHT BACK VOLTAGE", Components.shootermotorright2.getOutputVoltage());
                    System.out.println("Encoder Distance Left: " + Components.encoderleftdrive.getDistance());
                    System.out.println("Encoder Distance Right: " + Components.encoderrightdrive.getDistance());
                    //Take average between two targets?
                    /*if(camera.getNumTargets() > 1){
                     leftTarget = camera.getTarget(true);
                     rightTarget = camera.getTarget(false);
                     calculatedShootDistance = (leftTarget.getCalculatedDistance() + rightTarget.getCalculatedDistance())/2.0;
                     calculatedShootAngle = (leftTarget.getAngle() + rightTarget.getAngle()) / 2.0;
                     calculatedShootVoltage = Utilities.getCalculatedShootVoltage(calculatedShootDistance);
                     }else if(camera.getNumTargets() == 1){
                     if(camera.getTarget(false).getId() == -1){
                        
                     }else{
                    
                     }
                     }*/

                    break;

            }
            if (teleopTime != oldTeleopTime) {
                oldTeleopTime = teleopTime;
            }
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }

    }

    public double getLastUpdateTime() {
        return table.getDouble("TIME");
    }

    public void autoShootTwo() {
        switch (autoIndex) {
            case 0:
                Components.pickupdown = true;
                Components.rollerfoward = true;
                autoIndex++;
                break;

            case 1:
                if (pickup.getPickarm() == pickup.STOPPED_DOWN) {
                    Components.rollerfoward = false;
                    Components.rollerstop = true;
                    autoIndex++;
                }
                break;
            case 2:
                if (ds.getMatchTime() > 3) {
                    Components.rollerstop = false;
                    autoIndex++;
                }
                break;
            case 3:
                Components.shootsinglespeed = true;
                autoIndex++;
                break;
            case 4:

                Components.shootsinglespeed = false;
                autoIndex++;

                break;
            case 5:
                if (shooter.getshooterstate() == shooter.down) {
                    Components.rollerfoward = true;
                    autoIndex++;
                }
                break;
            case 6:
                if (ds.getMatchTime() > 7) {
                    Components.rollerfoward = false;
                    Components.rollerstop = true;
                    autoIndex++;
                }
                break;
            case 7:
                Components.rollerstop = false;
                Components.shootsinglespeed = true;
                autoIndex++;
                break;
            case 8:

                Components.shootsinglespeed = false;
                autoIndex++;

                break;
            case 9:
                break;
        }
    }

    public void autoShoot() {
        switch (autoIndex) {
            case 0:
                Components.pickupdown = true;
                Components.rollerfoward = true;
                autoIndex++;
                break;

            case 1:
                if (pickup.getPickarm() == pickup.STOPPED_DOWN) {
                    Components.rollerfoward = false;
                    Components.rollerstop = true;
                    autoIndex++;
                }
                break;
            case 2:
                if (ds.getMatchTime() > 3) {
                    Components.rollerstop = false;
                    autoIndex++;
                }
                break;
            case 3:
                Components.shootsinglespeed = true;
                autoIndex++;
                break;
            case 4:

                Components.shootsinglespeed = false;
                autoIndex++;

                break;
            case 5:
                if (shooter.getshooterstate() == shooter.down) {
                    Components.pickuptop = true;
                    autoIndex++;
                }
                break;
            case 6:
                Components.pickuptop = false;
                break;
        }
    }
}
