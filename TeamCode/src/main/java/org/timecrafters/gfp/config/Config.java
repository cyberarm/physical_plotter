package org.timecrafters.gfp.config;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by t420 on 9/14/2017.
 */

public class Config extends State {

    public Config(Engine engine){
        this.engine = engine;
    }

    public HardWareConfig hardWareConfig;

    public DcMotor dcRightGrabber;
    public DcMotor dcLeftGrabber;

    public DcMotor dcFrontRight;
    public DcMotor dcFrontLeft;
    public DcMotor dcBackRight;
    public DcMotor dcBackLeft;

    public DcMotor dcArm;

    public DcMotor dcWinch;

    public TouchSensor winchTouch;
    public TouchSensor flipperTouch;
    public TouchSensor beamTouch;

    public ModernRoboticsI2cRangeSensor frontRightDistanceSensor;
    public ModernRoboticsI2cRangeSensor backRightDistanceSensor;
    public ModernRoboticsI2cRangeSensor frontDistanceSensor;

    public CRServo crFlipper;
    public CRServo crBeam;
    public CRServo crGrabber;
    public CRServo crBrake;

    public ColorSensor colorSensor;

    long time = 100;


    public void init(){

        hardWareConfig = engine.hardWareConfig;
        dcRightGrabber = hardWareConfig.dcRightGrabber;
        dcLeftGrabber = hardWareConfig.dcLeftGrabber;

        dcFrontRight = hardWareConfig.dcFrontRight;
        dcFrontLeft = hardWareConfig.dcFrontLeft;
        dcBackRight = hardWareConfig.dcBackRight;
        dcBackLeft = hardWareConfig.dcBackLeft;

        dcArm = hardWareConfig.dcArm;
        dcWinch = hardWareConfig.dcWinch;

        winchTouch = hardWareConfig.winchTouch;
        flipperTouch = hardWareConfig.flipperTouch;
        beamTouch = hardWareConfig.beamTouch;

        crFlipper = hardWareConfig.crFlipper;
        crBeam = hardWareConfig.crBeam;
        crGrabber = hardWareConfig.crGrabber;
        crBrake =  hardWareConfig.crBrake;

        colorSensor = hardWareConfig.colorSensor;
        super.init();
    }

    public void exec(){
        setFinished(true);
    }

    public void setHardWareConfig(HardWareConfig hardWareConfig){
        this.hardWareConfig = hardWareConfig;
    }

}
