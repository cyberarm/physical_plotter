package org.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.engine.Engine;
import org.greece.states.DualMotorSync;

//@TeleOp(name = "SyncedMotors")
public class SyncedMotors extends Engine {
    public static SyncedMotors instance;
    public SyncedMotors() {
        instance = this;
    }
    @Override
    public void setProcesses() {
//        addState(new DualMotorSync(0.4, DualMotorSync.left));
    }
}
