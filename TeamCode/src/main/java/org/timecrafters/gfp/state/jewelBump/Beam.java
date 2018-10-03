package org.timecrafters.gfp.state.jewelBump;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by goldfishpi on 1/12/18.
 */

public class Beam extends Config {
    double power;
    boolean runTime = false;
    int timems;

    public Beam(Engine engine, double power) {
        super(engine);
        this.power = power;
    }

    public Beam(Engine engine, double power, int timems){
        super(engine);
        this.power=power;
        this.timems = timems;
        this.runTime = true;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void exec() {
        crBeam.setPower(power);
        if(runTime){
            sleep(timems);
            crBeam.setPower(0);
            setFinished(true);
        }else {
            if (beamTouch.isPressed()) {
                crBeam.setPower(0);
                setFinished(true);
            }
        }
    }

    public void setRunTime(int timems){
        runTime = true;
        this.timems = timems;
    }
}
