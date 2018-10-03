package org.timecrafters.gfp.state.grabber;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 11/5/2017.
 */

public class LeftGrabber extends Config {

    double power;
    int position;

    boolean firstRun = true;

    public LeftGrabber(Engine engine, double power, int position){
        super(engine);
        this.power = power;
        this.position = position;
    }

    public void exec(){
        if(firstRun){
            dcLeftGrabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcLeftGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            firstRun = false;
        }

        dcLeftGrabber.setPower(-power);

        if(Math.abs(dcLeftGrabber.getCurrentPosition()) >= position){
            dcLeftGrabber.setPower(0.0);
            setFinished(true);
        }
    }

}
