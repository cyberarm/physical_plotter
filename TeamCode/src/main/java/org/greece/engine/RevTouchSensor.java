package org.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.engine.Engine;
import org.greece.states.RevTouchSensorState;

@TeleOp(name = "RevTouchSensor")
public class RevTouchSensor extends Engine {
    @Override
    public void setup() {
        addState(new RevTouchSensorState("touchSensor"));
    }
}
