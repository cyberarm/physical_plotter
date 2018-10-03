package org.timecrafters.gfp.state;

import android.graphics.Path;

import com.qualcomm.robotcore.hardware.CompassSensor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.gfp.config.Config;

/**
 * Created by goldfishpi on 12/5/17.
 */

public class Compass extends Config {

    CompassSensor sensor;

    double direction;

    public Compass(Engine engine) {
        super(engine);
    }

    @Override
    public void exec() {

            direction = sensor.getDirection();

    }

    public double getDirection() {
        return direction;
    }
}
