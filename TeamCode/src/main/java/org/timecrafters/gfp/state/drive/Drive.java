package org.timecrafters.gfp.state.drive;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.gfp.config.Config;

/**
 * Created by GoldfishPi on 11/2/2017.
 */

public abstract class Drive extends Config {

    int distance;
    public double power;

    private int frontRight;
    private int frontLeft;
    private int backRight;
    private int backLeft;

    private boolean firstRun = true;
    private DcMotor[] motors;

    private double powerInc = 0.05;

    private int rampDistance;
    private int rampChangeDistances;

    private double currentPower;
    private int lastChangePosition = 0;

    private boolean runUntillStateFinished = false;
    private volatile State finishedState;

    private boolean[] finished = new boolean[4];

    public boolean haltOnComplete = true;


    Drive(Engine engine){
        super(engine);
    }

    public void init(){
        super.init();
        rampDistance = (int)(distance *.20);
        int powerChanges = (int) (power / powerInc);
        rampChangeDistances = rampDistance/ powerChanges;
    }

    public void exec(){
        if(firstRun){

            dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            currentPower = powerInc;
            firstRun = false;
        }

        //Average necersary motors
        int motorTickSubtotal = 0;
        int motorTickAverage = 0;
        for(int i = 0; i < motors.length; i ++){
            motorTickSubtotal += Math.abs(motors[i].getCurrentPosition());
            motorTickAverage = motorTickSubtotal/(i+1);
        }

        //check if ramping up
        if(motorTickAverage < rampDistance ){
            if(motorTickAverage >= lastChangePosition+rampChangeDistances){
                lastChangePosition = motorTickAverage;
                currentPower += powerInc;
            }
        }
        //check if ramping down
        else if(motorTickAverage >= distance - rampDistance){
            if(motorTickAverage >= lastChangePosition + rampChangeDistances){
                lastChangePosition = motorTickAverage;
                currentPower -= powerInc;
            }
        }
        //if not ramping up or down set power to absolute
        else{
            currentPower = power;
            lastChangePosition = motorTickAverage;
        }

        int dcFrontRightEncoder =   Math.abs(dcFrontRight.getCurrentPosition());
        int dcFrontLeftEncoder = Math.abs(dcFrontLeft.getCurrentPosition());
        int dcBackRightEncoder = Math.abs(dcBackRight.getCurrentPosition());
        int dcBackLeftEncoder = Math.abs(dcBackLeft.getCurrentPosition());
        //Chack if running untill state is finished
        //TODO make this a switch statement to account for other runmodes you lazy bum
        if(runUntillStateFinished){

            if(finishedState.getIsFinished()){
//                if (haltOnComplete) {
                    dcFrontRight.setPower(0);
                    dcFrontLeft.setPower(0);
                    dcBackRight.setPower(0);
                    dcBackLeft.setPower(0);
//                }

                for(int i = 0; i < finished.length; i ++){
                    finished[i] = true;
                }
            }else{
                dcFrontRight.setPower(currentPower*frontRight);
                dcFrontLeft.setPower(currentPower*frontLeft);

                dcBackLeft.setPower(currentPower*backLeft);
                dcBackRight.setPower(currentPower*backRight);

            }

        }else {

            dcFrontRight.setPower(power*frontRight);
            dcFrontLeft.setPower(power*frontLeft);
            dcBackRight.setPower(power*backRight);
            dcBackLeft.setPower(power*backLeft);

            if(motorTickAverage >= distance){
//                if (haltOnComplete) {
                    dcFrontRight.setPower(0);
                    dcFrontLeft.setPower(0);
                    dcBackRight.setPower(0);
                    dcBackLeft.setPower(0);
//                }
                setFinished(true);
            }
        }



        Log.i(TAG+".DRIVEMOTORS", "Front Right :" +  Integer.toString(dcFrontRightEncoder));
        Log.i(TAG+".DRIVEMOTORS", "Front Left :" + Integer.toString(dcFrontLeftEncoder));

        Log.i(TAG+".DRIVEMOTORS", "Back Right :" + Integer.toString(dcBackRightEncoder));
        Log.i(TAG+".DRIVEMOTORS", "Back Left :" + Integer.toString(dcBackLeftEncoder));

        Log.i(TAG+".DRIVEMOTORS","---------");

        /*boolean finishedReturn = true;
        for (boolean aFinished : finished) {
            if (!aFinished) {
                finishedReturn = false;
                break;
            }
        }
        setFinished(finishedReturn);*/

    }

    public void runUntillStateFinished(State state){
        runUntillStateFinished = true;
        finishedState = state;
    }

    void setMotors(int frontLeft, int backLeft, int frontRight, int backRight){
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;

        this.backRight = backRight;
        this.backLeft = backLeft;

    }

    void setReadMotors(DcMotor[] motors){
        this.motors = motors;

    }

    public void stop(){
        dcFrontLeft.setPower(0);
        dcFrontRight.setPower(0);
        dcBackLeft.setPower(0);
        dcBackRight.setPower(0);
    }



}
