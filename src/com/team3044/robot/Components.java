/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team3044.robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 * @author Joey
 */
public class Components {
      //doesn't have motors for shooter(can?), doesn't have anythign for arduino or encoder, no joysticks
 //Pickup
    public static DigitalInput UpPickupLimit = new DigitalInput(1,6);
    public static DigitalInput DownPickupLimit = new DigitalInput(1,5);
    
    public static Jaguar PickupRollers =new Jaguar(1,4); 
    public static Jaguar LiftingPickup=new Jaguar(1,3); 
    
    public static AnalogChannel RollerPot = new AnalogChannel (1,2);
    
 //Shooter need motors
   public static DigitalInput DownshooterLimit = new DigitalInput(1,8);
    public static DigitalInput UpShooterLimit = new DigitalInput(1,9);
    
    public static Jaguar shootermotorleft = new Jaguar (1,5);
    public static Jaguar shootermotorright = new Jaguar (1,6);
            
    public static AnalogChannel ShooterPot = new AnalogChannel (1,1);
     
 //drive
    public static Jaguar leftdrive = new Jaguar(1,1);
    public static Jaguar rightdrive = new Jaguar(1,2);
    
    public static Encoder encoderleftdrive = new Encoder(1,1,1,2);
    public static Encoder encoderrightdrive = new Encoder(1,3,1,4);
    
 //vision
    public static Servo servCameraPickupX=new Servo(1,8);    
    public static Servo servCameraPickupY=new Servo(1,7);
    
    public static Servo servCameraShooterX=new Servo(2,1);    
    public static Servo servCameraShooterY=new Servo(2,2);
    
 //driver station add buttons
    DriverStationLCD ds=DriverStationLCD.getInstance();
    DriverStation DS= DriverStation.getInstance();
    
   private static Joystick GamePaddrive=new Joystick(1);//check with xbox controllers
    private static Joystick GamePadshoot=new Joystick(2);
    
  //other
    public static AnalogChannel ultrasonic = new AnalogChannel(1,3); // going on arduino?
    
  //GamepadDrive
    public static double leftdriveY=0.0;//left yaxis
    public static double rightdriveY=0.0;
    public static boolean button1 = false;//A Buton
    public static boolean button2 = false;//b button
    public static boolean button3 = false;//X button
    public static boolean button4 = false;//Y button
    public static boolean button5 = false;//left bumper(top not trigger)
    public static boolean button6 = false;//right bumper
    
    
    //drive- drive+shoot 2-pickup+camera servos
    //rumble motors?
     
    
   public void upDateVals(){
            //drive?
            //button vals.
     leftdriveY = GamePaddrive.getRawAxis(2);
     rightdriveY = GamePaddrive.getRawAxis(5);
     button1=GamePaddrive.getRawButton(1);
     button2=GamePaddrive.getRawButton(2);
     button3=GamePaddrive.getRawButton(3);
     button4=GamePaddrive.getRawButton(4);
     button5=GamePaddrive.getRawButton(5);
     button6=GamePaddrive.getRawButton(6);
            
        }
    
    
    
    
}
