package org.cyberarm.greece.states;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.cyberarm.engine.State;
import org.cyberarm.greece.engine.RevTouchSensor;

public class RevTouchSensorState extends State {
    private DigitalChannel touchSensor;

    public RevTouchSensorState(String sensor) {
        this.touchSensor = engine.hardwareMap.digitalChannel.get(sensor);
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
    }

    @Override
    public void start() {

    }

    @Override
    public void exec() {
    }

    @Override
    public void telemetry() {
        engine.telemetry.addData("Button is", touchSensor.getState());
    }
}
