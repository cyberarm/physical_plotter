package org.timecrafters.Liv.state;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by Liv on 11/28/2017.
 */

public class VeerLeft extends Config {


    public VeerLeft(Engine engine) {
        super(engine);

    }
    public void exec(){
        dcFrontRight.setPower(0.25);
        dcFrontLeft.setPower(0.75);
        dcBackRight.setPower(0.25);
        dcBackLeft.setPower(0.75);


        if(dcFrontRight.getCurrentPosition()>=700)

        dcFrontRight.setPower(0.0);
        dcFrontLeft.setPower(0.0);
        dcBackRight.setPower(0.0);
        dcBackLeft.setPower(0.0);


    }

}
