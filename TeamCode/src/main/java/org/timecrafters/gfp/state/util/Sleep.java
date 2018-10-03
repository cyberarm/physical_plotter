package org.timecrafters.gfp.state.util;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;
import org.timecrafters.gfp.config.Config;

/**
 * Created by goldfishpi on 1/14/18.
 */

public class Sleep extends Config {
    int sleepms;
    public Sleep(Engine engine, int sleepms) {
        super(engine);
        this.sleepms = sleepms;
    }

    @Override
    public void exec() {
        sleep(sleepms);
        setFinished(true);
    }
}
