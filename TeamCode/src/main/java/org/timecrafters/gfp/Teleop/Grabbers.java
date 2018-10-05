package org.timecrafters.gfp.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 10/19/2017.
 */

public class Grabbers extends Config {


    double power;
    boolean b=false;
    boolean x=false;
    boolean joystick=true;
    int xTarget = 0;
    int bTarget = 0;
    public Grabbers(Engine engine,double power){
        super(engine);
        this.power = power;
    }

    @Override
    public void exec(){

        //Right Grabber
        /*if(cyberarmEngine.gamepad2.right_bumper) {
            dcRightGrabber.setPower(upPower);

        }else if (cyberarmEngine.gamepad2.right_trigger > 0){
            dcRightGrabber.setPower(-upPower);
        }else{
            dcRightGrabber.setPower(0);
        }

        if(cyberarmEngine.gamepad2.left_bumper){
            dcLeftGrabber.setPower(-upPower);
        }else if(cyberarmEngine.gamepad2.left_trigger > 0){
            dcLeftGrabber.setPower(upPower);
        }else{
            dcLeftGrabber.setPower(0);
        }
*/

        dcLeftGrabber.setPower(engine.gamepad2.left_stick_x);
        dcRightGrabber.setPower(engine.gamepad2.right_stick_x);

//        if (Math.abs(cyberarmEngine.gamepad2.right_stick_x) >= 0.1 || Math.abs(cyberarmEngine.gamepad2.left_stick_x) >= 0.1){
//            joystick=false;
//
//        }else{
//            joystick=true;
//        }
//
//        if(cyberarmEngine.gamepad2.b){
//            if(!b){
//                dcRightGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                bTarget = (dcRightGrabber.getCurrentPosition()+500);
//                dcRightGrabber.setPower(1.0);
//                b=true;
//            }
//        }else{
//
//            b=false;
//            if (joystick && dcLeftGrabber.getCurrentPosition() >= bTarget){
//                dcRightGrabber.setPower(0.0);
//            } else {
//                bTarget = dcRightGrabber.getTargetPosition();
//            }
//        }
//
//        if(cyberarmEngine.gamepad2.x){
//            if(!x){
//                dcLeftGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//                xTarget = (dcLeftGrabber.getCurrentPosition()-500);
//                dcLeftGrabber.setPower(-1.0);
//                x=true;
//            }
//        }else{
//
//            x=false;
//            if (joystick && dcLeftGrabber.getCurrentPosition() <= xTarget){
//                dcLeftGrabber.setPower(0.0);
//            } else {
//                xTarget = dcLeftGrabber.getCurrentPosition();
//            }
//        }
    }
}