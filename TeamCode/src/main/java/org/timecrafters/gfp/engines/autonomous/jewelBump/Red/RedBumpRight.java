package org.timecrafters.gfp.engines.autonomous.jewelBump.Red;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;
import org.timecrafters.gfp.state.color.ReadColor;
import org.timecrafters.gfp.state.jewelBump.Flipper;

/**
 * Created by goldfishpi on 1/14/18.
 */

public class RedBumpRight extends SubEngine {
    Engine engine;
    ReadColor readColor;

    public RedBumpRight(Engine engine, ReadColor readColor) {
        this.engine = engine;
        this.readColor = readColor;

    }

    @Override
    public void setProcesses() {
        addState(new Flipper(engine,-0.5,120, false,0));
        //addState(new Flipper(engine, 0.5, 150));
    }

    @Override
    public void evaluate() {
        if(readColor.getRedAverage() >=2.0 && readColor.getBlueAverage() <=5.0){
            setRunable(true);
        }
    }
}
