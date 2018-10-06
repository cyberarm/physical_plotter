package org.cyberarm.driver.states;

import com.qualcomm.robotcore.hardware.CRServo;

import org.cyberarm.engine.CyberarmState;
import org.cyberarm.engine.Support;

public class PenUp extends CyberarmState {
  CRServo pen;

  public PenUp() {
    pen = cyberarmEngine.hardwareMap.crservo.get("svPen");
  }

  @Override
  public void exec() {
    Support.puts("PenUp", "Raising pen...");
    pen.setPower(-0.5);
    sleep(300);
    setFinished(true);
  }
}
