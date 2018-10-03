package org.timecrafters.gfp.state.grabber;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 9/14/2017.
 */

public class GrabberTeleop extends Config {

    private double power;
    private int position;
    private boolean firstStopOne;
    private boolean firstStopTwo;


    public GrabberTeleop(Engine engine, double power){
        super(engine);
        this.power = power;
    }

    @Override
    public void init(){
        super.init();

    }

    @Override
    public void exec(){



        if(engine.gamepad1.left_trigger > 0){
            position ++;
            dcRightGrabber.setPower(power);
            dcRightGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            firstStopOne = true;
        }else if (engine.gamepad1.right_trigger > 0){
            position --;
            dcRightGrabber.setPower(-power);
            dcRightGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            firstStopOne = true;
        }else{
            if(firstStopOne) {
                dcRightGrabber.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                dcRightGrabber.setPower(power);
                dcRightGrabber.setTargetPosition(dcRightGrabber.getCurrentPosition());
                firstStopOne = false;
            }

        }

        if(engine.gamepad1.left_bumper ){
            dcLeftGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcLeftGrabber.setPower(power);
            firstStopTwo = true;
        }else if(engine.gamepad1.right_bumper){
            dcLeftGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            dcLeftGrabber.setPower(-power);
            firstStopTwo = true;
        }else{
            if(firstStopTwo) {
                dcLeftGrabber.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                dcLeftGrabber.setTargetPosition(dcLeftGrabber.getCurrentPosition());
                firstStopTwo = false;

            }

        }





    }

    @Override
    public void stop(){
        dcRightGrabber.setTargetPosition(0);
    }

}
