package org.driver.states;

import com.qualcomm.robotcore.hardware.Servo;

import org.engine.State;
import org.engine.Support;

public class PenDown extends State {
  Servo pen;

  public PenDown() {
    pen = engine.hardwareMap.servo.get("svPen");
  }

  @Override
  public void exec() {
    Support.puts("PenDown", "Lowering pen...");
    pen.setPosition(-1.0);
    sleep(100);
    setFinished(true);
  }
}