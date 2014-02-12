package com.team3044.RobotComponents;

import com.team3044.robot.Components;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
/**
 * 
 * 
 * Has CAN
 * Limit Switches are set to normally closed ie will send a normally true signal
 * 
 * 
 * @author Joey
 */
public class Shooter {
    private boolean islimitshooteruptrigger = Components.islimitshooteruptriggerd;
    private boolean islimitshooterdowntrigger = Components.islimitshooterdowntriggerd;
    private DigitalInput upshooterlimit = Components.UpShooterLimit;
    private DigitalInput downshooterlimit = Components.DownShooterLimit;
    private AnalogChannel shootpot = Components.ShooterPot;
    CANJaguar shootermotor = Components.shootermotorleft;
    CANJaguar shootermotor2= Components.shootermotorleft2;
    CANJaguar shootermotorneg = Components.shootermotorright;
    CANJaguar shootermotorneg2 =Components.shootermotorright2;
    
    private boolean shootbutton = Components.shoot;
    private boolean shootdownbutton = Components.shooterdown;
    private boolean shootpass = Components.pass;
    private boolean shoottruss = Components.truss;
    private boolean shootsinglebutton = Components.shootsinglespeed;
    
    private double shootspeedone = Components.shootspeedone;
    private double shootspeedtwo = Components.shootspeedtwo;
    private double shootspeedthree = Components.shootspeedthree;
    private double shootdownspeed = Components.shootdownspeed;
    
    private double shootpotvalue = Components.potvalue;
    private double shootpotposition = Components.shooterpotpostion;
    
    private  int shootstate;
        
    public final int down =0;
    public final int movingup =1;
    public final int speedone= 2;
    public final int speedtwo = 3;
    public final int speedthree =4;
    public final int stopped =5;
    public final int movingdown = 6;
    
    
    public double singlespeed=0;
    
    private double initialpot=0;
    private double shootpotdown = .22;
    private double shootpotlow =1;
    private double shootpotmiddle = 1.5;
    private double shootpothigh = 1.9;
    
    
    
    public Shooter(){
    
    }
    
    public void robotInit(){
       try {
            shootermotor = new CANJaguar(1,CANJaguar.ControlMode.kPercentVbus);
            shootermotor.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shootermotor.configEncoderCodesPerRev(250);
            shootermotorneg = new CANJaguar(3,CANJaguar.ControlMode.kPercentVbus);
            shootermotorneg.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shootermotorneg.configEncoderCodesPerRev(250);
            shootermotor2 = new CANJaguar(2,CANJaguar.ControlMode.kPercentVbus);
            shootermotor2.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shootermotor.configEncoderCodesPerRev(250);
            shootermotorneg2 = new CANJaguar(4,CANJaguar.ControlMode.kPercentVbus);
            shootermotorneg2.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shootermotorneg2.configEncoderCodesPerRev(250);            
            //jag.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
            
            
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        shootstate = down;
        try {
            shootermotor.setX(0);
            shootermotor2.setX(0);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        try {
            shootermotorneg.setX(0);
            shootermotorneg2.setX(0);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    initialpot =shootpot.getAverageVoltage();
    shootpotdown +=initialpot;
    shootpotlow+= initialpot;
    shootpotmiddle+= initialpot;
    shootpothigh+=initialpot;
    

    };
    
    public void autoInit(){};
    
    public void teleopInit(){
    try {
            shootermotor.enableControl();
            shootermotorneg.enableControl();
            shootermotor2.enableControl();
            shootermotorneg2.enableControl();
        } 
    catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    
    };
    
    public void disabledInit(){
        try {
            shootermotor.setX(0);
            shootermotor2.setX(0);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        try {
            shootermotorneg.setX(0);
            shootermotorneg2.setX(0);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
   public void teleop(){
       shoot();
   }
    
    public boolean islimitshooterup(){
        
        islimitshooteruptrigger = upshooterlimit.get();
        //limit normally closed
        return islimitshooteruptrigger;
    }
    
    
    public boolean islimitshooterdown(){
        
        islimitshooterdowntrigger = downshooterlimit.get();
        //limit normally closed
        return islimitshooterdowntrigger;
    }
    
    
    public double setshootspeedone(){
        
        if(shootbutton == true){
            shootspeedone= 1;
        }
        else if (shootpass == true){
            shootspeedone =.5;
        }
        else if (shoottruss ==true){
            shootspeedone = .6;
        }
        
        return shootspeedone;
    }
    
    
    public double setshootspeedtwo(){
        if(shootbutton == true){
            shootspeedtwo= .75;
        }
        else if (shootpass == true){
            shootspeedtwo = .3;
        }
        else if (shoottruss ==true){
            shootspeedtwo = .4;
        }
        return shootspeedtwo;
    }
    
    
    public double setshootspeedthree(){
        
        if(shootbutton == true){
            shootspeedthree= .4;
        }
        else if (shootpass == true){
            shootspeedthree = .2;
        }
        else if (shoottruss ==true){
            shootspeedthree = .2;
        }
        
        return shootspeedthree;
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
               if(shootbutton==true &&  islimitshooteruptrigger ==true)
                {
                       try {
                shootermotor.setX(shootspeedone);
                shootermotor2.setX(shootspeedone);
                    } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                        }
                 try {
                shootermotorneg.setX(-shootspeedone);
                shootermotorneg2.setX(-shootspeedone);
                     } catch (CANTimeoutException ex) {
                        ex.printStackTrace();
                    }
                    shootstate = speedone;
                }
               else if(shootsinglebutton==true){
                     try {
                shootermotor.setX(singlespeed);
                shootermotor2.setX(singlespeed);
                     } catch (CANTimeoutException ex) {
                       ex.printStackTrace();
                      }
                      try {
                shootermotorneg.setX(-singlespeed);
                shootermotorneg2.setX(-singlespeed);
                     } catch (CANTimeoutException ex) {
                        ex.printStackTrace();
                   }
                    shootstate = movingup;
                       }
               
              break;
           case movingup:
                if(islimitshooteruptrigger==false || shootpot.getAverageVoltage()>=shootpothigh)
                {
                    shootstate=stopped;
            try {
                shootermotor.setX(0);
                shootermotor2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(0);
                shootermotorneg2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                }
                break;
            case speedone:
               if(shootdownbutton == true)
               {
            try {
                shootermotor.setX(shootdownspeed);
                shootermotor2.setX(shootdownspeed);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(-shootdownspeed);
                shootermotorneg2.setX(-shootdownspeed);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                   shootstate=movingdown;
               } 
               else if(shootpot.getAverageVoltage()>=shootpothigh || islimitshooteruptrigger == false){
            try {
                shootermotor.setX(0);
                shootermotor2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(0);
                shootermotorneg2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                    shootstate = stopped;
                }
                else if(shootpot.getAverageVoltage()>= shootpotmiddle){
            try {
                shootermotor.setX(shootspeedthree);
                shootermotor2.setX(shootspeedthree);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(-shootspeedthree);
                shootermotorneg2.setX(-shootspeedthree);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                    shootstate = speedthree;
                }
                else if(shootpot.getAverageVoltage()>=shootpotlow)
                 {
            try {
                shootermotor.setX(shootspeedtwo);
                shootermotor2.setX(shootspeedtwo);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try { 
                shootermotorneg.setX(-shootspeedtwo);
                shootermotorneg2.setX(-shootspeedtwo);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                   shootstate = speedtwo;
                 }
                 
                break;
                
            case speedtwo:
                 if(shootdownbutton ==true){
            try {
                shootermotor.setX(shootdownspeed);
                shootermotor2.setX(shootdownspeed);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(-shootdownspeed);
                shootermotorneg2.setX(-shootdownspeed);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                     shootstate =movingdown;
                 }
                 else if(shootpot.getAverageVoltage() >=shootpothigh || islimitshooteruptrigger ==false){
            try {
                shootermotor.setX(0);
                shootermotor2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(0);
                shootermotorneg2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                     shootstate =stopped;
                 }
                 else if(shootpot.getAverageVoltage()>=shootpotmiddle)
                 {
            try {
                shootermotor.setX(shootspeedthree);
                shootermotor2.setX(shootspeedthree);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotor.setX(-shootspeedthree);
                shootermotor2.setX(-shootspeedthree);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                     shootstate = speedthree;
                 }
                
                break;
                
            case speedthree:
                
                if(shootdownbutton ==true){
            try {
                shootermotor.setX(shootdownspeed);
                shootermotor2.setX(shootdownspeed);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(-shootdownspeed);
                shootermotorneg2.setX(-shootdownspeed);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                    shootstate =movingdown;
                }
                else if(shootpot.getAverageVoltage() >=shootpothigh || islimitshooteruptrigger ==false){
            try {
                shootermotor.setX(0);
                shootermotor2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(0);
                shootermotorneg2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                     shootstate =stopped;
                 }
                
                break;
                
            case stopped:
                
                if(shootdownbutton&& islimitshooterdowntrigger ==true)
                {
            try {
                shootermotor.setX(shootdownspeed);
                shootermotor2.setX(shootdownspeed);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(-shootdownspeed);
                shootermotorneg2.setX(-shootdownspeed);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                    shootstate= movingdown;
                }
                
                break;
                
            case movingdown:
                 if(islimitshooterdowntrigger==true || shootpot.getAverageVoltage()<=shootpotdown)
                {
                    
                    try {
                shootermotor.setX(0);
                shootermotor2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
            try {
                shootermotorneg.setX(0);
                shootermotorneg2.setX(0);
            } catch (CANTimeoutException ex) {
                ex.printStackTrace();
            }
                    shootstate=down;
                }
        } 
    }
    
    public int getshooterstate()
    {
        return shootstate;
    }
    
}
