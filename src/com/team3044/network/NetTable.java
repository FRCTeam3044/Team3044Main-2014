/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team3044.network;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.io.IOException;

/**
 *
 * @author Joey
 */
public class NetTable {
    NetworkTable t;
    
    public NetTable(String name){
        try {
            NetworkTable.initialize();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        NetworkTable.getTable(name);
    }
    
}
