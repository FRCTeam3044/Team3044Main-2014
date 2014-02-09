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
    static Talon Roller= Components.PickupRollers;
    static Relay PickArm= Components.LiftingPickup;
    
    public static boolean rollerfoward=Components.rollerfoward;
    public static boolean rollerreverse=Components.rollerreverse;
    public static boolean rollerstop=Components.rollerstop;
    public static boolean pickupdown=Components.pickupdown;
    public static boolean pickuptop=Components.pickuptop;
    public static boolean pickupmiddle=Components.pickupmiddle;
    public static boolean pickupstop=Components.pickupstop;
    

    AnalogChannel Potentiometer= Components.RollerPot;
    DigitalInput limitDown=Components.UpPickupLimit;
    DigitalInput limitUp= Components.DownPickupLimit;
    
    private double speed1=1.0;
    private double speed2=1.0;
    private int n;
    private int k;
    final int STOPPED=0;
    final int MOVING_FORWARD=1;
    final int MOVING_BACKWARD=2;
    
    int MID_LOCATION=90;
    final int STOPPED_MID=0;
    final int STOPPED_DOWN=1;
    final int MOVE_DOWN=2;
    final int MOVE_PARTIAL_DOWN=3;
    final int STOPPED_UP=4;
    final int MOVE_UP=5;
    final int MOVE_PARTIAL_UP=6;
    
    private double initpot=0;
    
    public Pickup(){
    n=STOPPED_UP;//problem need to be fix
    k=STOPPED;// problem need to be fix
        }
    public void robotInit(){
        Roller.set(0);
        PickArm.set(Relay.Value.kOff);
        
        initpot =Potentiometer.getAverageVoltage();
        MID_LOCATION+=initpot;
        
    }
    public void teleopInit(){
        
    }
    public void teleop(){ //function 
       Arm();
       roller();
       
    }
    private void roller(){
       
        switch(k){
            case STOPPED:{// Roller stopped
                if (rollerfoward){
                    k=MOVING_FORWARD;
                    Roller.set(1.0);
                } else if (rollerreverse){
                    k=MOVING_BACKWARD;
                    Roller.set(-1.0);
                }
             }
            case MOVING_FORWARD:{// roller is moving to pick the ball
                if (rollerstop||rollerreverse){
                    k=STOPPED;
                    Roller.set(0.0);
                } 
            }
            case MOVING_BACKWARD:{ //roller is moving to throu the ball out
                if (rollerfoward||rollerstop)
                    k=STOPPED;
                    Roller.set(0);
                }
                
        }
    }
    private void Arm(){
        
        
        switch(n){
           case STOPPED_MID:{
               if(pickuptop){
                   n=MOVE_UP;
                   //PickArm.set(-speed1);
                   PickArm.set(Relay.Value.kReverse);
               } else if(pickupdown){
                   n=MOVE_DOWN;
                   PickArm.set(Relay.Value.kForward);
               }
           }
           case STOPPED_DOWN:{
               if(pickuptop){
                   n=MOVE_UP;
                  // PickArm.set(-speed1);
                   PickArm.set(Relay.Value.kReverse);
               } else if (pickupmiddle){
                   n=MOVE_PARTIAL_UP;
                   //PickArm.set(-speed2);
                   PickArm.set(Relay.Value.kReverse);
                  }
               }
           case MOVE_DOWN:{
                if (limitDown.get()==false){
                    n=STOPPED_DOWN;
                    PickArm.set(Relay.Value.kOff);
                } else if(pickuptop||pickupstop){
                    n=STOPPED_MID;
                    PickArm.set(Relay.Value.kOff);
                }
           }
           case MOVE_PARTIAL_DOWN:{
                if(pickupstop){
                    n=STOPPED_MID;
                    PickArm.set(Relay.Value.kOff);
                 } else if(Potentiometer.getValue()<=MID_LOCATION){
                    n=STOPPED_MID;
                    PickArm.set(Relay.Value.kOff);    
                    }
          }
           
           case STOPPED_UP:{
               if(pickupdown){
                   n=MOVE_DOWN;
                   PickArm.set(Relay.Value.kForward);
                } else if( pickupstop){
                   n=MOVE_PARTIAL_DOWN;
                   PickArm.set(Relay.Value.kForward);
                }
            }
           case MOVE_UP:{
                if (limitUp.get()==true){
                    n=STOPPED_UP;
                    PickArm.set(Relay.Value.kOff);
                } else if(pickupdown||pickupstop){
                    n=STOPPED_MID;
                    PickArm.set(Relay.Value.kOff);
                    }
            }
           case MOVE_PARTIAL_UP:{
               if(pickupstop){
                    n=STOPPED_MID;
                    PickArm.set(Relay.Value.kOff);
                 } else if(Potentiometer.getValue()>=MID_LOCATION){
                    n=STOPPED_MID;
                    PickArm.set(Relay.Value.kOff);   
                    }
                }
           }    
        }
                
    } 
    