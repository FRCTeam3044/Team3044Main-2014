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
 * Has CAN Limit Switches are set to normally closed ie will send a normally
 * true signal
 *
 *
 * @author Joey
 */
public class Shooter {

    Components Components;
    private boolean islimitshooteruptrigger;
    private boolean islimitshooterdowntrigger;

    CANJaguar shootermotor = Components.shootermotorleft;
    CANJaguar shootermotor2 = Components.shootermotorleft2;
    CANJaguar shootermotorneg = Components.shootermotorright;
    CANJaguar shootermotorneg2 = Components.shootermotorright2;

    private boolean shootdownbutton = Components.shooterdown;
    private boolean shootpass = Components.pass;
    private boolean shoottruss = Components.truss;
    private boolean shootsinglebutton = Components.shootsinglespeed;

    //  private double shootspeedone = Components.shootspeedone;
    //private double shootspeedtwo = Components.shootspeedtwo;
    //  private double shootspeedthree = Components.shootspeedthree;
    private double shootdownspeed = Components.shootdownspeed;
    private double trussspeed = Components.trussspeed;
    private double passspeed = Components.passspeed;
    public double singlespeed = .1;

    //joey heres your speed that you wanted
    public double shooterspeed = .5;

    private double shootpotvalue = Components.potvalue;
    private double initialpot = 0;
    private double shootpotdown = 1.75;
    // private double shootpotlow =2.4;
    // private double shootpotmiddle = 2.6;
    public double shootpothigh = 3;
    private double shootsinglepot = 3;
    private double trusspothigh = 2.2;
    private double passpot = 3;
    private double shootpot;

    private int shootstate;

    public final int down = 0;
    public final int movingup = 1;
    public final int speedone = 2;
    public final int speedtwo = 3;
    public final int speedthree = 4;
    public final int stopped = 5;
    public final int movingdown = 6;

    public boolean tempbutton = false;
    public boolean templimit = true;

    DriverStationLCD ds = DriverStationLCD.getInstance();
    DriverStation DS = DriverStation.getInstance();

    //private double p;
    //private double i;
    //private double d;
    public Shooter() {

    }

    public void robotInit() {
        try {
            shootermotor = new CANJaguar(1, CANJaguar.ControlMode.kPercentVbus);
            shootermotor.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shootermotor.configEncoderCodesPerRev(250);
            shootermotorneg = new CANJaguar(3, CANJaguar.ControlMode.kPercentVbus);
            shootermotorneg.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shootermotorneg.configEncoderCodesPerRev(250);
            shootermotor2 = new CANJaguar(2, CANJaguar.ControlMode.kPercentVbus);
            shootermotor2.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            shootermotor2.configEncoderCodesPerRev(250);
            shootermotorneg2 = new CANJaguar(4, CANJaguar.ControlMode.kPercentVbus);
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
        initialpot = Components.ShooterPot.getAverageVoltage();
        //shootpotdown +=initialpot;
        //shootpotlow+= initialpot;
        //shootpotmiddle+= initialpot;
        //shootpothigh+=initialpot;

    }

    ;
    
    public void autoInit() {
    }

    ;
    
    public void teleopInit() {
        //  p= DS.getAnalogIn(2);
        //i= DS.getAnalogIn(3);
        //d= DS.getAnalogIn(4);
        // try {
        //       shootermotor.enableControl();
        //     shootermotorneg.enableControl();
        //   shootermotor2.enableControl();
        //          shootermotorneg2.enableControl();
//        } 
        //  catch (CANTimeoutException ex) {
        //        ex.printStackTrace();
        //  }

    }

    ;
    
    public void disabledInit() {
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

    public void teleop() {
        singlespeed = DS.getAnalogIn(1);
        shoot();
    }

    public boolean islimitshooterup() {

        islimitshooteruptrigger = Components.UpShooterLimit.get();
        //limit normally closed
        return !islimitshooteruptrigger;
    }

    public boolean islimitshooterdown() {

        islimitshooterdowntrigger = Components.DownShooterLimit.get();
        //limit normally closed
        return !islimitshooterdowntrigger;
    }

    public double setshootspeed() {

        return shooterspeed;
    }

    public double setshootpot() {
        return shootpot;
    }

    public void shoot() {
        //boolean shootbutton = Components.shoot;
        //boolean shootdownbutton = Components.shooterdown;

        switch (shootstate) {
            case down:
                tempbutton = Components.shootsinglespeed;
                templimit = islimitshooterup();

                if (Components.shootsinglespeed == true && islimitshooterup() == true) {
                    try {
                        shootermotor.setX(singlespeed);
                        shootermotor2.setX(singlespeed);
                        shootermotorneg.setX(-singlespeed);
                        shootermotorneg2.setX(-singlespeed);

                    } catch (CANTimeoutException ex) {
                        ex.printStackTrace();

                    }
                    shootpothigh = shootsinglepot;
                    shootstate = movingup;
                } else if (Components.truss == true && islimitshooterup() == true) {
                    try {
                        shootermotor.setX(trussspeed);
                        shootermotor2.setX(trussspeed);
                        shootermotorneg.setX(-trussspeed);
                        shootermotorneg2.setX(-trussspeed);
                    } catch (CANTimeoutException ex) {
                        ex.printStackTrace();
                    }
                    shootpothigh = trusspothigh;
                    shootstate = movingup;
                } else if (Components.pass == true && islimitshooterup() == true) {
                    try {
                        shootermotor.setX(passspeed);
                        shootermotor2.setX(passspeed);
                        shootermotorneg.setX(-passspeed);
                        shootermotorneg2.setX(-passspeed);
                    } catch (CANTimeoutException ex) {
                        ex.printStackTrace();
                    }
                    shootpothigh = passpot;
                    shootstate = movingup;
                } else if (Components.shoot == true && islimitshooterup() == true) {
                    try {
                        shootermotor.setX(shooterspeed);
                        shootermotor2.setX(shooterspeed);
                        shootermotorneg.setX(-shooterspeed);
                        shootermotorneg2.setX(-shooterspeed);
                    } catch (CANTimeoutException ex) {
                        ex.printStackTrace();
                    }
                    shootpothigh = shootpot;
                    shootstate = movingup;
                }
                break;

            case movingup:
                if (islimitshooterup() == false || Components.potvalue >= shootpothigh) {
                    shootstate = stopped;
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

            case stopped:
                try {
                    if (/*Components.shooterdown && */islimitshooterdown() == true) {

                        shootermotor.setX(shootdownspeed);
                        shootermotor2.setX(shootdownspeed);

                        shootermotorneg.setX(-shootdownspeed);
                        shootermotorneg2.setX(-shootdownspeed);

                        shootstate = movingdown;
                    }
                } catch (CANTimeoutException ex) {
                    ex.printStackTrace();
                }
                break;

            case movingdown:
                if ((islimitshooterdown() == false) || (Components.potvalue < shootpotdown)) {

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
                    shootstate = down;
                }
        }
    }

    public int getshooterstate() {
        return shootstate;
    }

}
