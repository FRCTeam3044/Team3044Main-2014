/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team3044.RobotComponents;

import com.team3044.robot.Components;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 * @author Joey
 */
public class TestShooter {
    int index = 0;
    final int DOWN = 0;
    final int MOVINGUP = 1;
    final int MOVINGDOWN = 2;
    final int UP = 3;
    DriverStation ds = DriverStation.getInstance();
    
    public void teleopPeriodic(){
        try {
            System.out.println("ID: " + index);
            switch(index){
                case DOWN:
                    if(Components.shoot){
                        try {
                            Components.shootermotorleft.setX(ds.getAnalogIn(1));
                            Components.shootermotorleft2.setX(ds.getAnalogIn(1));
                            Components.shootermotorright.setX(-ds.getAnalogIn(1));
                            Components.shootermotorright2.setX(-ds.getAnalogIn(1));
                            index = MOVINGUP;
                        }catch (CANTimeoutException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                    break;
                case MOVINGUP:
                    Components.shootermotorleft.setX(ds.getAnalogIn(1));
                    Components.shootermotorleft2.setX(ds.getAnalogIn(1));
                    Components.shootermotorright.setX(-ds.getAnalogIn(1));
                    Components.shootermotorright2.setX(-ds.getAnalogIn(1));
                    if(Components.islimitshooteruptriggerd){
                        try {
                            Components.shootermotorleft.setX(0);
                            Components.shootermotorleft2.setX(0);
                            Components.shootermotorright.setX(0);
                            Components.shootermotorright2.setX(0);
                            index = UP;
                        } catch (CANTimeoutException ex) {
                            ex.printStackTrace();
                        }
                    }
                    /*
                    if(Components.shooterdown){
                    try{
                    Components.shootermotorleft.setX(-ds.getAnalogIn(1));
                    Components.shootermotorleft2.setX(-ds.getAnalogIn(1));
                    Components.shootermotorright.setX(ds.getAnalogIn(1));
                    Components.shootermotorright2.setX(ds.getAnalogIn(1));
                    index = MOVINGDOWN;
                    }catch(CANTimeoutException ex){
                    
                    }
                    }*/
                    break;
                case MOVINGDOWN:
                    Components.shootermotorleft.setX(-ds.getAnalogIn(1));
                    Components.shootermotorleft2.setX(-ds.getAnalogIn(1));
                    Components.shootermotorright.setX(ds.getAnalogIn(1));
                    Components.shootermotorright2.setX(ds.getAnalogIn(1));
                    if(Components.islimitshooterdowntriggerd){
                        try {
                            Components.shootermotorleft.setX(0);
                            Components.shootermotorleft2.setX(0);
                            Components.shootermotorright.setX(0);
                            Components.shootermotorright2.setX(0);
                            index = DOWN;
                        } catch (CANTimeoutException ex) {
                            ex.printStackTrace();
                        }
                    }
                    /*
                    if(Components.shoot){
                    try{
                    Components.shootermotorleft.setX(ds.getAnalogIn(1));
                    Components.shootermotorleft2.setX(ds.getAnalogIn(1));
                    Components.shootermotorright.setX(-ds.getAnalogIn(1));
                    Components.shootermotorright2.setX(-ds.getAnalogIn(1));
                    index = MOVINGDOWN;
                    }catch(CANTimeoutException ex){
                    
                    }
                    
                    }*/
                    break;
                case UP:
                    if(Components.shooterdown){
                        try {
                            Components.shootermotorleft.setX(-ds.getAnalogIn(1));
                            Components.shootermotorleft2.setX(-ds.getAnalogIn(1));
                            Components.shootermotorright.setX(ds.getAnalogIn(1));
                            Components.shootermotorright2.setX(ds.getAnalogIn(1));
                            index = MOVINGDOWN;
                        }catch (CANTimeoutException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                
            }
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
}
