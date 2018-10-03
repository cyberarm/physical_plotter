package org.timecrafters.gfp.state.arm;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 11/2/2017.
 */

public class ExtendArm extends Config {

    int rotations;

    int rotationCount = 0;

    boolean pressed = false;

    boolean firstRun = true;

    double power;

    public ExtendArm(Engine engine,double power, int rotations){
        super(engine);
        this.rotations = rotations;
        this.power = power;
    }

    public void exec(){

        if(firstRun){
            dcWinch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcWinch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            firstRun = false;
        }

        dcWinch.setPower(power);

        if(Math.abs(dcWinch.getCurrentPosition()) >= rotations){
            dcWinch.setPower(0);
            setFinished(true);
        }


    }
}
