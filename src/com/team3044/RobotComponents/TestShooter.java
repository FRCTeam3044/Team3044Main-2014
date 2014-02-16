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
    private double upPotValue = 2.11;
    private double downPotValue = .37;
    private double trussPotValue = .57;
    private double lobPotValue = .9;
    private double upSpeed = .3;
    private double shootSpeed = .3;
    private double shootPotValue = 2.11;
    private double trussSpeed = .3;
    private double lobSpeed = .3;
    private double downSpeed = .1;
    private final double TRUSS_SPEED = .5, SHOOT_SPEED = .3, LOW_GOAL_SPEED = .1;
    
    //Down pot .37
    //Up pot    2.11
    public void teleopPeriodic(){
        if(Components.truss){
            upPotValue = trussPotValue;
            upSpeed = trussSpeed;
        }else if(Components.shoot){
            upPotValue = shootPotValue;
            upSpeed = shootSpeed;
        }else if(Components.shootsinglespeed){
            upPotValue = lobPotValue;
            upSpeed = lobSpeed;
        }
        try {
            System.out.println("ID: " + index);
            switch(index){
                case DOWN:
                    if(Components.shoot || Components.shootsinglespeed || Components.truss){
                        try {
                            Components.shootermotorleft.setX(upSpeed);
                            Components.shootermotorleft2.setX(upSpeed);
                            Components.shootermotorright.setX(-upSpeed);
                            Components.shootermotorright2.setX(-upSpeed);
                            index = MOVINGUP;
                        }catch (CANTimeoutException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                    break;
                case MOVINGUP:
                    Components.shootermotorleft.setX(upSpeed);
                    Components.shootermotorleft2.setX(upSpeed);
                    Components.shootermotorright.setX(-upSpeed);
                    Components.shootermotorright2.setX(-upSpeed);
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
   
                    break;
                case MOVINGDOWN:
                    Components.shootermotorleft.setX(-downSpeed);
                    Components.shootermotorleft2.setX(-downSpeed);
                    Components.shootermotorright.setX(downSpeed);
                    Components.shootermotorright2.setX(downSpeed);
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

                    break;
                case UP:
                    try {
                        Components.shootermotorleft.setX(-downSpeed);
                        Components.shootermotorleft2.setX(-downSpeed);
                        Components.shootermotorright.setX(downSpeed);
                        Components.shootermotorright2.setX(downSpeed);
                        index = MOVINGDOWN;
                    }catch (CANTimeoutException ex) {
                        ex.printStackTrace();
                    }   
            }
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
    
}
