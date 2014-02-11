/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team3044.network;

/**
 *
 * @author Joey
 */
public class Target {
    private double angle, calculatedDistance;
    private int id;
    
    public Target(double angle, double calculatedDistance, int id){
        this.angle = angle;
        this.id = id;
        this. calculatedDistance = calculatedDistance;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(double calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
