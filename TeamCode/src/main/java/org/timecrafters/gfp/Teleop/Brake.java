package org.timecrafters.gfp.Teleop;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420-1 on 2/3/2018.
 */

public class Brake extends Config {
    public Brake(Engine engine) {
        super(engine);
    }

    @Override
    public void exec() {
        if(engine.gamepad2.left_bumper){
            crBrake.setPower(1.0);
        }
        if(engine.gamepad2.left_trigger >0.0){
            crBrake.setPower(-1.0);
        }
    }
}
