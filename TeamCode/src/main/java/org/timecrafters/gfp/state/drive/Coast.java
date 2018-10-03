package org.timecrafters.gfp.state.drive;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 9/23/2017.
 */

public class Coast extends Config {

    double expectedPower;
    double currentPower;

    int coastTicks;
    int distanceTicks;

    int changeTime;
    int lastChangeTime = 0;

    boolean firstRun = true;

    int initalEncoderPosition;

    int encoderPosition;

    double powerInc = 0.05;

    boolean decelerating = false;

    public Coast(Engine engine, double power, int distanceTicks, int coastTicks,double powerInc){
        super(engine);
        this.expectedPower = power;
        this.coastTicks = coastTicks;
        this.distanceTicks = distanceTicks;
        this.powerInc = powerInc;
        changeTime = coastTicks/(int)(expectedPower/powerInc);
    }

    public void init(){
        super.init();
        dcFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void exec(){
        if(firstRun){
            initalEncoderPosition = dcFrontRight.getCurrentPosition();
            currentPower +=powerInc;
            firstRun = false;
        }

        encoderPosition = dcFrontRight.getCurrentPosition() - initalEncoderPosition;

        if(encoderPosition *1.1 + coastTicks >= distanceTicks){
            decelerating = true;
            if (encoderPosition >= lastChangeTime + changeTime && currentPower >= 0.1) {
                lastChangeTime += changeTime;
                currentPower -= powerInc;
                Log.i(TAG,"Current Power : " + Double.toString(currentPower));
                Log.i(TAG,"Current Position : " + Integer.toString(encoderPosition));
            }
            if(encoderPosition > distanceTicks){
                dcFrontRight.setPower(0.0);
                setFinished(true);
            }
        }else if(encoderPosition < distanceTicks) {
            if (encoderPosition >= lastChangeTime + changeTime) {
                lastChangeTime += changeTime;
                if(currentPower < 1.0) {
                    currentPower += powerInc;
                    Log.i(TAG, "Current Power : " + Double.toString(currentPower));
                    Log.i(TAG, "Current Position : " + Integer.toString(encoderPosition));
                }
            }
        }




        if(encoderPosition < distanceTicks){
            dcFrontRight.setPower(currentPower);


        }else{
            //dcFrontRight.setPower(0.0);
            //setFinished(true);
        }



    }

}
