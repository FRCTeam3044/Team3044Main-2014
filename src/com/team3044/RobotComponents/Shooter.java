package com.team3044.RobotComponents;

import com.team3044.robot.Components;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.DriverStationLCD;
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
    Components Components;
    private boolean islimitshooteruptrigger;
    private boolean islimitshooterdowntrigger;
    //private DigitalInput upshooterlimit = Components.UpShooterLimit;
    //private DigitalInput downshooterlimit = Components.DownShooterLimit;
    //private AnalogChannel shootpot = Components.ShooterPot;
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
     
     //ugj
    public double singlespeed=.1;
    
    private double initialpot=0;
    private double shootpotdown = 1.75;
    private double shootpotlow =2.4;
    private double shootpotmiddle = 2.6;
    private double shootpothigh = 3;
    
    public boolean tempbutton=false;
    public boolean templimit=true;
    DriverStationLCD ds = DriverStationLCD.getInstance();
  
    
    
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
            shootermotor2.configEncoderCodesPerRev(250);
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
    initialpot =Components.ShooterPot.getAverageVoltage();
    //shootpotdown +=initialpot;
    //shootpotlow+= initialpot;
    //shootpotmiddle+= initialpot;
    //shootpothigh+=initialpot;
    

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
        
        islimitshooteruptrigger = Components.UpShooterLimit.get();
        //limit normally closed
        return !islimitshooteruptrigger;
    }
    
    
    public boolean islimitshooterdown(){
        
        islimitshooterdowntrigger = Components.DownShooterLimit.get();
        //limit normally closed
        return !islimitshooterdowntrigger;
    }
    
    
    public double setshootspeedone(){
        
        if(Components.shoot == true){
            shootspeedone= 1;
        }
        else if (Components.pass == true){
            shootspeedone =.5;
        }
        else if (Components.truss ==true){
            shootspeedone = .6;
        }
        
        return shootspeedone;
    }
    
    
    public double setshootspeedtwo(){
        if(Components.shoot == true){
            shootspeedtwo= .75;
        }
        else if (Components.pass == true){
            shootspeedtwo = .3;
        }
        else if (Components.truss ==true){
            shootspeedtwo = .4;
        }
        return shootspeedtwo;
    }
    
    
    public double setshootspeedthree(){
        
        if(Components.shoot == true){
            shootspeedthree= .4;
        }
        else if (Components.pass == true){
            shootspeedthree = .2;
        }
        else if (Components.truss ==true){
            shootspeedthree = .2;
        }
        
        return shootspeedthree;
    }
    
    
    public void shoot(){   
   boolean shootbutton = Components.shoot;
     boolean shootdownbutton = Components.shooterdown;
     //boolean shootpass = Components.pass;
    //boolean shoottruss = Components.truss;
    // boolean shootsinglebutton = Components.shootsinglespeed;
     //Components.GamePadshoot.getRawButton(1);
   //  Components.GamePadshoot.getRawButton(5);
     
     
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
                tempbutton=Components.shootsinglespeed;
                templimit=islimitshooterup();
                /*if(islimitshooterdowntrigger==true || shootpotposition ==1)
                {
                    shootstate =down;
                    shootermotor.set(0);
                    shootermotorneg.set(0);
                }*/
               if(Components.shoot==true &&  islimitshooterup() ==true)
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
               else if(Components.shootsinglespeed==true &&  islimitshooterup()==true){
                     try {
                shootermotor.setX(singlespeed);
                shootermotor2.setX(singlespeed);
                ds.println(DriverStationLCD.Line.kUser3, 1, "I set the motors");
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
                    ds.println(DriverStationLCD.Line.kUser5, 1, "after moving up ");
                    ds.updateLCD();
                       }
               
              break;
                
           case movingup:
                if(islimitshooterup()==false || Components.potvalue>=shootpothigh)
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
               if(Components.shooterdown == true)
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
               if(Components.potvalue>=shootpothigh || islimitshooterup() == false){
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
                else if(Components.potvalue>= shootpotmiddle){
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
                else if(Components.potvalue>=shootpotlow)
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
                 if(Components.shooterdown ==true){
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
                 else if(Components.potvalue >=shootpothigh || islimitshooterup() ==false){
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
                 else if(Components.potvalue>=shootpotmiddle)
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
                
                if(Components.shooterdown ==true){
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
                else if(Components.potvalue >=shootpothigh || islimitshooterup() ==false){
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
                
                if(Components.shooterdown && islimitshooterdown() ==true)
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
                 if((islimitshooterdown()==false) || (Components.potvalue <shootpotdown))
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
