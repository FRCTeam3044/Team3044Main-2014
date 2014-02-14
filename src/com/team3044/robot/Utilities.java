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
    double returnvalue=0;      
     double[] lowpassarray={0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    /*0=leftjag
      * 1=rightjag
      * 2,3,4,5,6,7 unalocated
      */

    /**
     * 
     * @param val Value to test
     * @param cutoff Cutoff value around 0
     * @return the value of the val variable, or if it is within the cutoff, 0
     */
    /*public static double deadband(double val, double cutoff){
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
    /*public static double deadbandAround(double around, double cutoff, double value){
        return 0;
       */ 
       public double lowpass(double targetspeed,int lowpassgroup)
     {     
        if (lowpassgroup>7||lowpassgroup<0)
        returnvalue=0;
        else{
    lowpassarray[lowpassgroup]=lowpassarray[lowpassgroup]*0.97+targetspeed*0.03;
    returnvalue=lowpassarray[lowpassgroup];
    
     }
    return returnvalue;
}    
         
   
     /* 
     * @param val Value to test
     * @param cutoff Cutoff value around 0
     * @return the value of the val variable, or if it is within the cutoff, 0
     */
    public static double deadband(double val, double cutoff){
        if(Math.abs(val) < Math.abs(cutoff)){
            val = 0;
        }
        return val;
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
