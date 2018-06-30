package org.greece.states;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.engine.State;
import org.greece.engine.RevTouchSensor;

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
