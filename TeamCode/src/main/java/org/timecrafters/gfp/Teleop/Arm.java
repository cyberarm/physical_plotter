package org.timecrafters.gfp.Teleop;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 10/28/2017.
 */

public class Arm extends Config {

    double upPower;
    double downPower;

    public Arm(Engine engine, double upPower, double downPower){
        super(engine);
        this.upPower = upPower;
        this.downPower = downPower;
    }

    public void exec(){
        if(engine.gamepad2.dpad_down){
            dcArm.setPower(-downPower);

        }else if (engine.gamepad2.dpad_up){
            dcArm.setPower(upPower);

        }else{
            dcArm.setPower(0.0);
        }
    }
}
