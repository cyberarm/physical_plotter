package org.timecrafters.Liv.SubEngines;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;
import org.timecrafters.gfp.state.color.ReadColor;
import org.timecrafters.gfp.state.jewelBump.Flipper;

/**
 * Created by Liv on 1/14/2018.
 */

public class BlueBumpLeft extends SubEngine {
    Engine engine;
    ReadColor readColor;

    public BlueBumpLeft(Engine engine, ReadColor readColor) {
        this.engine = engine;
        this.readColor = readColor;
    }

    @Override
    public void setProcesses() {
        if (readColor.getRedAverage() >=2.0 && readColor.getBlueAverage() <=5.0) {
            addState(new Flipper(engine, 1, 120, false, 0));
        } else {
            addState(new Flipper(engine, -1, 120, false, 0));
        }
    }

    @Override
    public void evaluate() {
//        if (readColor.getRedAverage() >=2.0 && readColor.getBlueAverage() <=5.0){
            setRunable(true);
//        }
    }
}
