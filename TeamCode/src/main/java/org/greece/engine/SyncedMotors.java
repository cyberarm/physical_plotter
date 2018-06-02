package org.greece.engine;

import org.engine.Engine;
import org.greece.states.DualMotorSync;

public class SyncedMotors extends Engine {
    @Override
    public void setProcesses() {
        addState(new DualMotorSync(1.0, DualMotorSync.left));
    }
}
