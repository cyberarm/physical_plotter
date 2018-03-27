package org.driver.states;

import com.qualcomm.robotcore.hardware.Servo;

import org.engine.Engine;
import org.engine.State;

public class PenUp extends State {
    Servo pen;
    public PenUp(Engine engine) {
        this.engine = engine;
        pen = engine.hardwareMap.servo.get("svPen");
    }
    @Override
    public void exec() {
        pen.setPosition(0.0);
        setFinished(true);
    }
}
