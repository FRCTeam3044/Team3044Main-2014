/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team3044.RobotComponents;

import com.team3044.robot.Components;
import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author Joey
 */
public class Drive { 
    Jaguar Jagleft=Components.leftdrive;
    Jaguar Jagright=Components.rightdrive;
    double leftY = Components.leftdriveY;
    double rightY = Components.rightdriveY;
    
    //Test 2
   
   
       
    public Drive(){
    
   }
    public void setDistanceToTravel(double left, double right){
        //in inches
    }
    
    public boolean hasTraveledSetDistance(){
        return true;
    }
    
    public void resetDistance(boolean set){
    
    }
    
    public void robotInit(){
    Jagleft.set(0);
    Jagright.set(0);
    };
    public void autoInit(){};
    public void teleopInit(){};
    public void Drivemain (){
    
    Jagleft.set(-leftY);
    Jagright.set(rightY);
    
    //leftY.set();
    //rightY.set(Joyleft(Components.));        
    
 }

    public void stop() {
        
    }
   
    
    


}