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
    //Test 2

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
        encoderleft.setDistancePerPulse((4*Math.PI)/250/* * (168.62812/150.59538) * (190.0/180.0)*/);
        encoderright.setDistancePerPulse((4*Math.PI)/250/* * (190.0/180.0) * (179.0/180.0)*/);
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

        if (Components.rightdriveY < -maxdrivevalue && Components.leftdriveY < -maxdrivevalue) {
            Jagleft.set(-1 * scaler);
            Jagright.set(1 * scaler);
        } else if (Components.rightdriveY > maxdrivevalue && Components.leftdriveY > maxdrivevalue) {

            Jagleft.set(1 * scaler);
            Jagright.set(-1 * scaler);

        } else {
            Jagleft.set(Utilities.deadband(Components.leftdriveY, 0.1) * scaler);
            Jagright.set(-Utilities.deadband(Components.rightdriveY, 0.1) * scaler);
            SmartDashboard.putNumber("Encoder left Rate: ", Components.encoderleftdrive.getRate());
            SmartDashboard.putNumber("Encoder right Rate: ", Components.encoderrightdrive.getRate());
            SmartDashboard.putNumber("Encoder left smooth", Utilities.lowpass(Components.encoderleftdrive.getRate(), 3));
        }
        if (Math.abs(Components.gamePadDriveTriggers) > .7) {
            scaler = .5;
        } else {

            scaler = 1.0;        //leftY.set();

            //rightY.set(Joyleft(Components.));        
        }
    }

    public void stop() {
        move = false;

    }

}
