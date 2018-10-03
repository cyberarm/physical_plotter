package org.timecrafters.gfp.state.ultrasonic;

import org.timecrafters.engine.Engine;

/**
 * Created by goldfishpi on 11/30/17.
 */

public class ReadFrontRightRange extends UltraSonic {
    public ReadFrontRightRange(Engine engine, int readings) {
        super(engine, readings);
    }
    public void init(){
        super.init();
        setSensor(frontRightDistanceSensor);
    }
}
