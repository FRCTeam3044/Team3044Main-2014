package com.team3044.RobotComponents;

import com.team3044.robot.Components;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogChannel;
/**
 * dsaf
 * 
 * @author Joey
 */
public class Shooter {
    private boolean islimitshooteruptrigger = Components.islimitshooteruptriggerd;
    private boolean islimitshooterdowntrigger = Components.islimitshooterdowntriggerd;
    private DigitalInput upshooterlimit = Components.UpShooterLimit;
    private DigitalInput downshooterlimit = Components.DownShooterLimit;
    private AnalogChannel shootpot = Components.ShooterPot;
    private Jaguar shootermotor = Components.shootermotorleft;
    private Jaguar shootermotorneg = Components.shootermotorright;
    private boolean shootbutton = Components.shoot;
    private boolean shootdownbutton = Components.shooterdown;
    private double shootspeed = Components.shootspeed;
    private double shootdownspeed = Components.shootdownspeed;
    private double shootpotvalue = Components.potvalue;
    private double shootpotposition = Components.shooterpotpostion;
        private  int shootstate;
    private final int down =0;
    private  final int movingup= 1;
    private  final int stopped =3;
    private  final int movingdown = 4;
 
    
    
    public Shooter(){
    
    }
    
    public void robotInit(){
    shootstate = down;
    };
    public void autoInit(){};
    public void teleopInit(){};
    
    public boolean islimitshooterup(){
        
        islimitshooteruptrigger = upshooterlimit.get();
        
        return islimitshooteruptrigger;
    }
    public boolean islimitshooterdown(){
        
        islimitshooterdowntrigger = downshooterlimit.get();
        
        return islimitshooterdowntrigger;
    }
    
    public double shootpotvalue(){
        
        if(shootpotvalue ==0){
            shootpotposition =1;
        }
        else if(shootpotvalue<.25 && shootpotvalue>0){
            shootpotposition =2;
        }
        else if (shootpotvalue==.25){
            shootpotposition =3;
        }

        return shootpotposition;
    }
    
    
    public void shoot(){
        /*
        if(buttonone&& islimitshooteruptrigger == false && shootpotposition ==2){//moveup
            shootermotor.set(shootspeed);
            shootermotorneg.set(-shootspeed);
        }
        else if(buttonone && islimitshooteruptrigger == true && shootpotposition ==3){//stopped
            shootermotor.set(0);
            shootermotorneg.set(0);
         }
        else if(buttontwo && islimitshooterdowntrigger == false&& shootpotposition ==2){//movedown
            shootermotor.set(shootdownspeed);
            shootermotor.set(-shootdownspeed);
        }
        else if (buttontwo && islimitshooterdowntrigger == true && shootpotposition ==1){//down
            shootermotor.set(0);
            shootermotorneg.set(0);
        }
     */
        switch(shootstate){
            case down:
                /*if(islimitshooterdowntrigger==true || shootpotposition ==1)
                {
                    shootstate =down;
                    shootermotor.set(0);
                    shootermotorneg.set(0);
                }*/
               if(shootbutton==true &&  islimitshooteruptrigger ==false)
                {
                    shootstate = movingup;
                    shootermotor.set(shootspeed);
                    shootermotorneg.set(-shootspeed);
                }
               
              break;
            case movingup:
                
                if(islimitshooteruptrigger==true || shootpotposition==3)
                {
                    shootstate=stopped;
                    shootermotor.set(0);
                    shootermotorneg.set(0);
                }
                
                break;
            case stopped:
                
                if(shootdownbutton&& islimitshooterdowntrigger ==false)
                {
                    shootstate= movingdown;
                     shootermotor.set(shootdownspeed);
                    shootermotor.set(-shootdownspeed);
                }
                break;
            case movingdown:
                 if(islimitshooterdowntrigger==false || shootpotposition ==1)
                {
                    shootstate=down;
                    shootermotor.set(0);
                    shootermotorneg.set(0);
                }
        }
        
                
        
        
    }
}
