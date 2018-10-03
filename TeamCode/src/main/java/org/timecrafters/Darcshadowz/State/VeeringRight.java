package org.timecrafters.Darcshadowz.State;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.gfp.config.Config;

/**
 * Created by Dylan on 11/28/2017.
 */

public class VeeringRight extends Config {
private boolean resetEncoders = true;
public VeeringRight(Engine engine) {
super(engine);
}

    @Override
    public void exec() {

        if (resetEncoders == true){
            dcFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            dcFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            dcBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            dcBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            dcBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            resetEncoders = false;
        }

        dcFrontLeft.setPower(.25);
        dcFrontRight.setPower(.75);
        dcBackLeft.setPower(.25);
        dcBackRight.setPower(.75);

        if(dcFrontRight.getCurrentPosition()>= 10000){

            dcFrontLeft.setPower(0);
            dcFrontRight.setPower(0);
            dcBackLeft.setPower(0);
            dcBackRight.setPower(0);
            setFinished(true);
        }


    }
}
