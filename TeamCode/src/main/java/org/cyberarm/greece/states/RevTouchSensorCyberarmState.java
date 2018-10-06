package org.cyberarm.greece.states;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.cyberarm.engine.CyberarmState;

public class RevTouchSensorCyberarmState extends CyberarmState {
    private DigitalChannel touchSensor;

    public RevTouchSensorCyberarmState(String sensor) {
        this.touchSensor = cyberarmEngine.hardwareMap.digitalChannel.get(sensor);
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
        cyberarmEngine.telemetry.addData("Button is", touchSensor.getState());
    }
}
