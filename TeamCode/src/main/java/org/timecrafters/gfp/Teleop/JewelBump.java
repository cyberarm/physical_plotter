package org.timecrafters.gfp.Teleop;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by goldfishpi on 1/19/18.
 */

public class JewelBump extends Config {

    double beamPower;
    double flipperPower;

    public JewelBump(Engine engine, double beamPower,double flipperPower) {
        super(engine);
        this.beamPower = beamPower;
        this.flipperPower = flipperPower;
    }

    @Override
    public void exec(){
        if(engine.gamepad2.y){
            crBeam.setPower(beamPower);
        }else if (engine.gamepad2.a){
            crBeam.setPower(-beamPower);
        }else{
            crBeam.setPower(0.0);
        }

        if(engine.gamepad2.x){
            crFlipper.setPower(flipperPower);
        }else if(engine.gamepad2.b){
            crFlipper.setPower(-beamPower);
        }else{
            crFlipper.setPower(0.0);
        }
    }
}
