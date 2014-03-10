/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author minh
 */
package com.team3044.RobotComponents;

import com.team3044.robot.Components;
import edu.wpi.first.wpilibj.*;

public class Pickup {

    static Talon Roller = Components.PickupRollers;
    static Relay PickArm = Components.LiftingPickup;

    //public static boolean rollerfoward = Components.rollerfoward;
    //public static boolean rollerreverse = Components.rollerreverse;
    //public static boolean rollerstop = Components.rollerstop;
    //public static boolean pickupdown = Components.pickupdown;
    //public static boolean pickuptop = Components.pickuptop;
    //public static boolean pickupmiddle=Components.pickupmiddle;
    //public static boolean pickupstop = Components.pickupstop;

    DriverStation ds = DriverStation.getInstance();
    //AnalogChannel Potentiometer= Components.RollerPot;
    //DigitalInput limitDown = Components.DownPickupLimit;
    //DigitalInput limitUp = Components.UpPickupLimit;

    private int n;
    private int k;
    final int STOPPED = 0;
    final int MOVING_FORWARD = 1;
    final int MOVING_BACKWARD = 2;
    public final int STOPPED_MID = 0;
    public final int STOPPED_DOWN = 1;
    public final int MOVE_DOWN = 2;
    public final int STOPPED_UP = 3;
    public final int MOVE_UP = 4;
    public Pickup() {
       
    }

    public void robotInit() {
        n = STOPPED_MID;//problem need to be fix
        k = STOPPED;// problem need to be fix
        Roller.set(0.0);
        PickArm.set(Relay.Value.kOff);
    }

    public void teleopInit() {
        robotInit();
    }

    public void teleop() { //function 
        Arm();
        roller();
        

    }

    private void roller() {

        switch (k) {
            case STOPPED: {// Roller stopped
                if (Components.rollerfoward) {
                    k = MOVING_FORWARD;
                    Roller.set(-1/*1*/);
                } else if (Components.rollerreverse) {
                    k = MOVING_BACKWARD;
                    Roller.set(1/*-1*/);
                }

            }
            break;
            case MOVING_FORWARD: {// roller is moving to pick the ball
                if (Components.rollerstop || Components.rollerreverse || !Components.rollerfoward) {
                    k = STOPPED;
                    Roller.set(0.0);
                }

            }
            break;
            case MOVING_BACKWARD: { //roller is moving to throu the ball out
                if (Components.rollerfoward || Components.rollerstop || !Components.rollerreverse) {
                    k = STOPPED;
                    Roller.set(0.0);
                }
            }
            break;

        }
    }

    private void Arm() {

        switch (n) {
            case STOPPED_MID: {
                if (Components.pickuptop) {
                    n = MOVE_UP;
                    PickArm.set(Relay.Value.kForward);
                } else if (Components.pickupdown) {
                    n = MOVE_DOWN;
                    PickArm.set(Relay.Value.kReverse);
                }
            }
            break;
            case STOPPED_DOWN: {
                if (Components.pickuptop) {
                    n = MOVE_UP;
                    PickArm.set(Relay.Value.kForward);
                }

            }
            break;
            case MOVE_DOWN: {
                if (Components.DownPickupLimit.get()) {
                    n = STOPPED_DOWN;
                    PickArm.set(Relay.Value.kOff);
                } else if (Components.pickuptop) {
                    n = STOPPED_MID;
                    PickArm.set(Relay.Value.kOff);
                }

            }
            break;

            case STOPPED_UP: {
                if (Components.pickupdown) {
                    n = MOVE_DOWN;
                    PickArm.set(Relay.Value.kReverse);
                }

            }
            break;
            case MOVE_UP: {
                if (Components.UpPickupLimit.get()) {
                    n = STOPPED_UP;
                    PickArm.set(Relay.Value.kOff);
                } else if (Components.pickupdown) {
                    n = STOPPED_MID;
                    PickArm.set(Relay.Value.kOff);
                }

            }
            break;

        }
    }

    public int getRoller() {
        return k;
    }

    public int getPickarm() {
        return n;
    }
    public void DisableInit(){
        
    }
    public void Disable(){
        PickArm.set(Relay.Value.kOff);
        Roller.set(0.0);
        k=STOPPED;
        if (n==MOVE_UP||n==MOVE_DOWN){
            n=STOPPED;
        }
       
    }
}