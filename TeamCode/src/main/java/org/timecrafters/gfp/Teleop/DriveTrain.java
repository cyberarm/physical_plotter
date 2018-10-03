package org.timecrafters.gfp.Teleop;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 9/30/2017.
 */

public class DriveTrain extends Config {

    public DriveTrain(Engine engine){
        super(engine);
    }

    @Override
    public void exec(){


        double subtract = 0.5;

        double rightPower = engine.gamepad1.right_stick_y;
        double leftPower = engine.gamepad1.left_stick_y;

        if(Math.abs(rightPower) == rightPower && rightPower > subtract){
            rightPower-=subtract;
        }else if(Math.abs(rightPower) != rightPower && rightPower < -subtract){
            rightPower +=subtract;
        }

        if(Math.abs(leftPower) == leftPower && leftPower > subtract){

            leftPower-=subtract;
        }else if(Math.abs(rightPower) != leftPower && leftPower < -subtract){
            leftPower +=subtract;
        }

        dcFrontRight.setPower(rightPower);
        dcBackRight.setPower(rightPower);

        dcFrontLeft.setPower(leftPower);
        dcBackLeft.setPower(leftPower);
    }
}