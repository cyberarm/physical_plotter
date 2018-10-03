package org.timecrafters.gfp.config;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by goldfishpi on 1/15/18.
 */

public class BlankConfig extends State {
    public BlankConfig(Engine engine){
        this.engine = engine;
    }
    @Override
    public void init(){

    }
    @Override
    public void exec() {
        setFinished(true);
    }
}
