/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team3044.RobotComponents;

import edu.wpi.first.wpilibj.Encoder;
import com.team3044.robot.Components;
import com.team3044.robot.Utilities;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Joey
 */
public class Drive {

    Jaguar Jagleft = Components.leftdrive;
    Jaguar Jagright = Components.rightdrive;
    double leftY = Components.leftdriveY;
    double rightY = Components.rightdriveY;
    Encoder encoderleft = Components.encoderleftdrive;
    Encoder encoderright = Components.encoderrightdrive;
    double encoderleftvalue = 0;
    double encoderrightvalue = 0;
    double autospeed = 0;
    boolean move = false;
    boolean distancetraveled = false;
    final double maxdrivevalue = .85;
    double scaler = 1;
    
    boolean leftDriveForward = false;
    boolean rightDriveForward = false;
    
    private final int FORWARD_RIGHT_SPEED = 831;
    private final int BACKWARD_RIGHT_SPEED = 777;
    private final int FORWARD_LEFT_SPEED = 775;
    private final int BACKWARD_LEFT_SPEED = 795;
    private final double SLOWEST_SPEED = FORWARD_LEFT_SPEED;
    //Test 2
    
    private final double FORWARD_RIGHT = 1.0;/*SLOWEST_SPEED/FORWARD_RIGHT_SPEED;*/
    private final double BACKWARD_RIGHT = 1.0;/*SLOWEST_SPEED/BACKWARD_RIGHT_SPEED;*/
    private final double FORWARD_LEFT = 1.0;/*SLOWEST_SPEED/FORWARD_LEFT_SPEED;*/
    private final double BACKWARD_LEFT = 1.0;/*SLOWEST_SPEED/BACKWARD_LEFT_SPEED;*/

    public Drive() {

    }

    public void DriveAuto() {
        if (move) {

            if (Math.abs(encoderleft.getDistance()) > encoderleftvalue) {
                Jagleft.set(0);
            } else {
                Jagleft.set(autospeed);

            }
            if (Math.abs(encoderright.getDistance()) > encoderrightvalue) {
                Jagright.set(0);
            } else {
                Jagright.set(-autospeed);
            }
            if (Math.abs(encoderright.getDistance()) > encoderrightvalue && Math.abs(encoderleft.getDistance()) > encoderleftvalue) {
                distancetraveled = true;
                move = false;
            }
            
        }
    }

    public void setDistanceToTravel(double left, double right, double speed) {
        encoderleftvalue = left;
        encoderrightvalue = right;
        autospeed = speed;

        //in inches
    }

    public boolean hasTraveledSetDistance() {

        return distancetraveled;
    }

    public void startdriving(boolean reset) {

        move = true;

        if (reset) {
            distancetraveled = false;
            encoderleft.reset();
            encoderright.reset();
            encoderleft.start();
            encoderright.start();
        }
    }

    public void robotInit() {
        Jagleft.set(0);
        Jagright.set(0);
        encoderleft.setDistancePerPulse(((4*Math.PI)/250)/**1.22917 * (13.0/15.0)*/);
        encoderright.setDistancePerPulse(((4*Math.PI)/250)/**1.22917 * (13.0/15.0)*/);
        encoderleft.setReverseDirection(true);
        encoderleft.reset();
        encoderright.reset();
        encoderleft.start();
        encoderright.start();

    }

    ;
    
    public void autoInit() {
    }

    ;
    
    public void teleopInit() {
    }

    ;
    
    public void Drivemain() {
        leftDriveForward = Components.leftdriveY < 0;
        rightDriveForward = Components.rightdriveY > 0;
        
        if(Components.driveStraight){
            if(leftDriveForward){
                Jagleft.set(-Utilities.deadband(Components.rightdriveY * scaler,.15) * this.FORWARD_LEFT);
            }else{
                Jagleft.set(-Utilities.deadband(Components.rightdriveY * scaler,.15) * this.BACKWARD_LEFT);
            }
            if(leftDriveForward){
                Jagright.set(Utilities.deadband(Components.leftdriveY * scaler, .15) * this.FORWARD_RIGHT);
            }else{
                Jagright.set(Utilities.deadband(Components.leftdriveY * scaler, .15) * this.BACKWARD_RIGHT);
            }
        }
        
        if (Components.rightdriveY < -maxdrivevalue && Components.leftdriveY < -maxdrivevalue) {
            Jagleft.set(-1 * scaler * FORWARD_LEFT);
            Jagright.set(1 * scaler * BACKWARD_RIGHT);
        } else if (Components.rightdriveY > maxdrivevalue && Components.leftdriveY > maxdrivevalue) {
            Jagleft.set(1 * scaler * BACKWARD_LEFT);
            Jagright.set(-1 * scaler * BACKWARD_RIGHT);
            
        } else {
            Jagleft.set(Utilities.deadband(Components.leftdriveY, 0.15) * scaler);
            Jagright.set(-Utilities.deadband(Components.rightdriveY, 0.15) * scaler);
            
        }
        
        if (Math.abs(Components.gamePadDriveTriggers) > .7) {
            scaler = .5;
        } else {
            scaler = 1.0;      
        }
    }

    public void stop() {
        move = false;

    }

}
