package org.driver.states;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.engine.State;
import org.engine.Support;

public class PenUp extends State {
  CRServo pen;

  public PenUp() {
    pen = engine.hardwareMap.crservo.get("svPen");
  }

  @Override
  public void exec() {
    Support.puts("PenUp", "Raising pen...");
    pen.setPower(-0.5);
    sleep(300);
    setFinished(true);
  }
}
