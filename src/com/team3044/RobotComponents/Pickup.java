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

    public static boolean rollerfoward = Components.rollerfoward;
    public static boolean rollerreverse = Components.rollerreverse;
    public static boolean rollerstop = Components.rollerstop;
    public static boolean pickupdown = Components.pickupdown;
    public static boolean pickuptop = Components.pickuptop;
    //public static boolean pickupmiddle=Components.pickupmiddle;
    public static boolean pickupstop = Components.pickupstop;

    DriverStation ds = DriverStation.getInstance();
    //AnalogChannel Potentiometer= Components.RollerPot;
    DigitalInput limitDown = Components.UpPickupLimit;
    DigitalInput limitUp = Components.DownPickupLimit;

    private int n;
    private int k;
    final int STOPPED = 0;
    final int MOVING_FORWARD = 1;
    final int MOVING_BACKWARD = 2;

    int MID_LOCATION = 90;
    public final int STOPPED_MID = 0;
    public final int STOPPED_DOWN = 1;
    public final int MOVE_DOWN = 2;
    public final int MOVE_PARTIAL_DOWN = 3;
    public final int STOPPED_UP = 4;
    public final int MOVE_UP = 5;
    public final int MOVE_PARTIAL_UP = 6;

    private double initpot = 0;

    public Pickup() {
        n = STOPPED_MID;//problem need to be fix
        k = STOPPED;// problem need to be fix
    }

    public void robotInit() {
        Roller.set(0);
        PickArm.set(Relay.Value.kOff);

        //initpot =Potentiometer.getAverageVoltage();
        MID_LOCATION += initpot;

    }

    public void teleopInit() {

    }

    public void teleop() { //function 
        Arm();
        roller();
        rollerfoward = Components.rollerfoward;
        rollerreverse = Components.rollerreverse;
        rollerstop = Components.rollerstop;
        pickupdown = Components.pickupdown;
        pickuptop = Components.pickuptop;
        //pickupmiddle=Components.pickupmiddle;
        pickupstop = Components.pickupstop;

    }

    private void roller() {

        switch (k) {
            case STOPPED: {// Roller stopped
                if (Components.rollerfoward) {
                    k = MOVING_FORWARD;
                    Roller.set(ds.getAnalogIn(3));
                } else if (Components.rollerreverse) {
                    k = MOVING_BACKWARD;
                    Roller.set(-ds.getAnalogIn(3));
                }

            }
            break;
            case MOVING_FORWARD: {// roller is moving to pick the ball
                if (Components.rollerstop || Components.rollerreverse) {
                    k = STOPPED;
                    Roller.set(0.0);
                }

            }
            break;
            case MOVING_BACKWARD: { //roller is moving to throu the ball out
                if (Components.rollerfoward || Components.rollerstop) {
                    k = STOPPED;
                    Roller.set(0);
                }
            }
            break;

        }
    }

    private void Arm() {

        switch (n) {
            case STOPPED_MID: {
                if (pickuptop) {
                    n = MOVE_UP;
                    //PickArm.set(-speed1);
                    PickArm.set(Relay.Value.kReverse);
                } else if (Components.pickupdown) {
                    n = MOVE_DOWN;
                    PickArm.set(Relay.Value.kForward);
                }
            }
            break;
            case STOPPED_DOWN: {
                if (pickuptop) {
                    n = MOVE_UP;
                    // PickArm.set(-speed1);
                    PickArm.set(Relay.Value.kReverse);
                }

            }
            break;
            case MOVE_DOWN: {
                if (limitDown.get()) {
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
                    PickArm.set(Relay.Value.kForward);
                }

            }
            break;
            case MOVE_UP: {
                if (limitUp.get()) {
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
}
