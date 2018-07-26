package org.driver.states;

import com.qualcomm.robotcore.hardware.Servo;

import org.engine.State;
import org.engine.Support;

public class PenUp extends State {
  Servo pen;

  public PenUp() {
    pen = engine.hardwareMap.servo.get("svPen");
  }

  @Override
  public void exec() {
    Support.puts("PenUp", "Raising pen...");
    pen.setPosition(1.0);
    sleep(100);
    setFinished(true);
  }
}
