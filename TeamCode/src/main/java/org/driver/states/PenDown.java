package org.driver.states;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.engine.State;
import org.engine.Support;

public class PenDown extends State {
  CRServo pen;

  public PenDown() {
    pen = engine.hardwareMap.crservo.get("svPen");
  }

  @Override
  public void exec() {
    Support.puts("PenDown", "Lowering pen...");
    pen.setPower(0);
    sleep(500);
    setFinished(true);
  }
}