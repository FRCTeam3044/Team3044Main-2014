/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team3044.network;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Joey
 */
public class Dashboard {
    private static Dashboard dashboard;
    
    public Dashboard(){
        
    }
    
    public void putDouble(String tag, double value){
        tag = tag.toUpperCase();
        SmartDashboard.putNumber(tag, value);
    }
    
    public void putString(String tag, String value){
        tag = tag.toUpperCase();
        SmartDashboard.putString(tag, value);
    }
    
    public void putBoolean(String tag, boolean bool){
        tag = tag.toUpperCase();
        SmartDashboard.putBoolean(tag, bool);
    }
    
    public double getDouble(String tag, double defaultValue){
        tag = tag.toUpperCase();
        double val = SmartDashboard.getNumber(tag,defaultValue);
        return val;
    }
    
    public String getString(String tag){
        tag = tag.toUpperCase();
        return SmartDashboard.getString(tag, "");
    }
    
    public boolean getBoolean(String tag, boolean defaultValue){
        tag = tag.toUpperCase();
        return SmartDashboard.getBoolean(tag,defaultValue);
    }
    
    
    
    public static Dashboard getInstance(){
        if(dashboard == null){
            dashboard = new Dashboard();
        }
        return dashboard;
    }
    
}
