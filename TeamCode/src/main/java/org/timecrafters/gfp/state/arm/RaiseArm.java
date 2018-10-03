package org.timecrafters.gfp.state.arm;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 11/5/2017.
 */

public class RaiseArm extends Config{

    double power;
    int position;

    boolean firstRun = true;

    public RaiseArm(Engine engine, double power, int position){
        super(engine);
        this.power = power;
        this.position = position;
    }

    public void exec(){
        if(firstRun){
            dcArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            firstRun = false;
        }

        dcArm.setPower(power);

        if(Math.abs(dcArm.getCurrentPosition()) >= Math.abs(position)){
            dcArm.setPower(0.0);
            setFinished(true);
        }
    }
}
