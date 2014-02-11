/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.team3044.robot;


import com.team3044.RobotComponents.Drive;
import com.team3044.RobotComponents.Pickup;
import com.team3044.RobotComponents.Shooter;
import com.team3044.network.Camera;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
 //TEST
public class RobotMain extends IterativeRobot {
    private static Utilities utils;
    private static Components components = new Components(); // if this works as static... Check net tables
    Drive drive;
    Pickup pickup;
    Shooter shooter;
    
    DriverStation ds = DriverStation.getInstance();
    
    int autoType = 0;
    int autoIndex = 0;
    
    double autoStartTime = 0;
    double time = 0;
    
    final int MOVE_THEN_SHOOT = 0;
    final int SHOOT_THEN_MOVE = 1;
        
    Camera camera = new Camera();
    
    public static Utilities getUtilities(){
        return utils;
    }
    
    public static Components getComponents(){
        return components;
    }
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        utils = new Utilities();
        components = new Components();
        pickup = new Pickup();
        shooter = new Shooter();
        drive = new Drive();
        
        shooter.robotInit();
        pickup.robotInit();
        drive.robotInit();
        
        pickup.teleopInit();
        drive.teleopInit();
        shooter.teleopInit();
        
    
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        switch(autoType){
            case MOVE_THEN_SHOOT:
                autoMoveAndShoot();
            break;
            
            case SHOOT_THEN_MOVE:
                autoShootAndMove();
            break;
                
        }
    }
    
    public void testInit(){
        drive.teleopInit();
    }
    
    public void testPeriodic(){
        drive.Drivemain();
    }

    public void teleopInit(){

    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        pickup.teleop();
        shooter.teleop();
        drive.Drivemain();
        
        
    }
    
    public void autoInit(){
        
    }
    
    public void autoMoveAndShoot(){
        switch(autoIndex){
            case 0:
                //5 feet wheels are 12.25 in double check
                drive.setDistanceToTravel(60, 60);
                autoIndex += 1;
            break;
            case 1:
                if(drive.hasTraveledSetDistance()){
                    drive.resetDistance(false);
                    autoIndex++;
                    time = ds.getMatchTime();
                }
            break;
            case 2:
                //assuming it takes .5 seconds from shot to scoring
                if(ds.getMatchTime() > 4.5){
                    autoIndex ++;
                }
            break;
            case 3:
                if(/*catcher is down*/1==1){
                
                }
            break;
        }
    }
    
    public void autoShootAndMove(){
    
    }
    
}
