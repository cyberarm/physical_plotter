package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.Engine;
import org.cyberarm.greece.states.RevTouchSensorState;

@Disabled
//@TeleOp(name = "RevTouchSensor")
public class RevTouchSensor extends Engine {
    @Override
    public void setup() {
        addState(new RevTouchSensorState("touchSensor"));
    }
}
