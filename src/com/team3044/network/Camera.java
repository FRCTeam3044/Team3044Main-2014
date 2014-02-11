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
public class Camera {
    
    NetTable t;
            
    public Camera(){
        t = new NetTable("CAMERA");
    }
    
    public Rectangle getRectangle(int id){
        return t.getRectangle(id);
    }
    
    public Rectangle[] getRectangles(){
        Rectangle rects[] = new Rectangle[10];
        boolean atEnd = false;
        double done = t.getDouble("MAXRECTS");
        int index = 0;
        while(!atEnd){
            if(index != done){
                rects[index] = getRectangle(index);
                index ++;
            }else{
                atEnd = true;
            }
        }
        return rects;
    }
    
    public Target getTarget(){
        
        return null;
    }
    
    public int getNumTargets(){
        //0 no targets
        return -1;
    }
    
}
