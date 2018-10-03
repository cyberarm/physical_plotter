package org.timecrafters.gfp.Teleop;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by luke on 2/3/18.
 */

public class RelicGrabber extends Config {
    double power;
    public RelicGrabber(Engine engine,double power) {
        super(engine);
        this.power = power;
    }

    public void exec(){
        if(engine.gamepad2.right_bumper){
            crGrabber.setPower(power);
        }else if(engine.gamepad2.right_trigger >0.0){
            crGrabber.setPower(-power);
        }else{
            crGrabber.setPower(0.0);
        }
    }
}
