package org.timecrafters;

import org.timecrafters.engine.Engine;

/**
 * Created by t420 on 9/7/2017.
 */

public class BlankEngine extends Engine {

    @Override
    public void setProcesses() {
        //Adds in state
        addState(new BlankState(this));
        //Adds a linear process to the state
        addThreadedState(new BlankState(this));
    }
}
