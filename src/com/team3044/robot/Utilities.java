/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team3044.robot;

/**
 *
 * @author Joey
 */
public class Utilities {
    
    /**
     * 
     * @param val Value to test
     * @param cutoff Cutoff value around 0
     * @return the value of the val variable, or if it is within the cutoff, 0
     */
    public static double deadband(double val, double cutoff){
        if(val < Math.abs(cutoff)){
            val = 0;
        }
        return 0;
    }
    /**
     * 
     * @param around value to test around
     * @param cutoff Value around target to cutoff
     * @param value Value to test
     * @return similar to deadband, but instead of 0, returns variable
     */
    public static double deadbandAround(double around, double cutoff, double value){
        return 0;
    }
}
