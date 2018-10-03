package org.timecrafters.Darcshadowz.State;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 11/5/2017.
 */

public class RightGrabber extends Config {

    double power;
    int position;

    boolean firstRun = true;

    public RightGrabber(Engine engine, double power, int position){
        super(engine);
        this.power = power;
        this.position = position;
    }

    public void exec(){
        if(firstRun == true){
            dcRightGrabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcRightGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            firstRun = false;
        }

        dcRightGrabber.setPower(-power);

        if(Math.abs(dcRightGrabber.getCurrentPosition()) >= position){
            dcRightGrabber.setPower(0.0);
            setFinished(true);
        }
    }

}

