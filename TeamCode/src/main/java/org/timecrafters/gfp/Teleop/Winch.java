package org.timecrafters.gfp.Teleop;

import android.util.Log;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 10/15/2017.
 */

public class Winch extends Config {

    double power;
    public Winch(Engine engine,double power) {
        super(engine);
        this.power = power;
    }

    public void exec() {

        if (engine.gamepad2.y){
            dcWinch.setPower(power);
        }else if(engine.gamepad2.a){
            dcWinch.setPower(-power);
        }else{
            dcWinch.setPower(0.0);
        }

        Log.i(TAG, Integer.toString(dcWinch.getCurrentPosition()));
    }
}