package org.cyberarm.driver.states;

import com.qualcomm.robotcore.hardware.CRServo;

import org.cyberarm.engine.CyberarmState;
import org.cyberarm.engine.Support;

public class PenDown extends CyberarmState {
  CRServo pen;

  public PenDown() {
    pen = cyberarmEngine.hardwareMap.crservo.get("svPen");
  }

  @Override
  public void exec() {
    Support.puts("PenDown", "Lowering pen...");
    pen.setPower(0);
    sleep(500);
    setFinished(true);
  }
}