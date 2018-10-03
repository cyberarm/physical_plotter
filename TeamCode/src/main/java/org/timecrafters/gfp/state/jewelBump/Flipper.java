package org.timecrafters.gfp.state.jewelBump;

import android.util.Log;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by goldfishpi on 1/9/18.
 */

public class Flipper extends Config {
    int delayBeforeStart;
    boolean usePushButton;
    double power;
    boolean runTime;
    int timems;
    boolean firstRun = true;
    long startTime;

    public Flipper(Engine engine, double power) {
        super(engine);
        this.power = power;
    }

    public Flipper(Engine engine, double power,int timems, boolean usePushButton, int delayBeforeStart) {
        super(engine);
        this.power = power;
        this.runTime = true;
        this.timems = timems;
        this.usePushButton = usePushButton;
        this.delayBeforeStart = delayBeforeStart;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void exec() {


        if (runTime){
            if(firstRun){
                startTime = System.currentTimeMillis();
                firstRun = false;
            }
            if(System.currentTimeMillis() - startTime >= delayBeforeStart){
                crFlipper.setPower(power);
            }
            if (usePushButton) {

                if (flipperTouch.isPressed()) {
                    engine.telemetry.addData("FlipperTime:", System.currentTimeMillis() - startTime+ "ms");
                    crFlipper.setPower(0);
                    setFinished(true);
                    firstRun = true;
                } else if (System.currentTimeMillis() - startTime >= 1500) {
                    engine.telemetry.addData("FlipperTime:", "ERROR!!!! TimedOut" );
                    crFlipper.setPower(0);
                    setFinished(true);
                    firstRun = true;
                }
            }
            else if(System.currentTimeMillis() - startTime >= delayBeforeStart){
                crFlipper.setPower(power);
                sleep(timems);
                crFlipper.setPower(0);
                setFinished(true);
                firstRun = true;
            }
        }
        else{
            //this is dead code
            crFlipper.setPower(power);
           // sleep(timems); //not sure why we are sleeping here?
            crFlipper.setPower(0);
            setFinished(true);
        }
    }
}
