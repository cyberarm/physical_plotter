package org.timecrafters.gfp.engines.autonomous.jewelBump.Red;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;
import org.timecrafters.gfp.state.color.ReadColor;
import org.timecrafters.gfp.state.jewelBump.Flipper;

/**
 * Created by goldfishpi on 1/14/18.
 */

public class RedBumpLeft extends SubEngine {
    ReadColor readColor;
    Engine engine;
    public RedBumpLeft(Engine engine, ReadColor readColor) {
        this.engine = engine;
        this.readColor = readColor;
    }

    @Override
    public void setProcesses() {
        if(readColor.getRedAverage() >= 2.0 && readColor.getBlueAverage() <= 5.0) {
            addState(new Flipper(engine, -0.5, 150, false,0));
        }else{
            addState(new Flipper(engine, 0.5, 150, false,0));
        }
    }

    @Override
    public void evaluate() {
            setRunable(true);

    }
}
