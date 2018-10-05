package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.engine.CyberarmEngine;
import org.cyberarm.greece.states.RevTouchSensorCyberarmState;

@Disabled
//@TeleOp(name = "RevTouchSensor")
public class RevTouchSensor extends CyberarmEngine {
    @Override
    public void setup() {
        addState(new RevTouchSensorCyberarmState("touchSensor"));
    }
}
