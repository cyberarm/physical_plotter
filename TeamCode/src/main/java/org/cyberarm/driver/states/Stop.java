package org.cyberarm.driver.states;

import android.media.ToneGenerator;

import com.qualcomm.robotcore.hardware.CRServo;

import org.cyberarm.engine.CyberarmState;
import org.cyberarm.greece.statues.AbstractMotor;
import org.cyberarm.greece.statues.Motor;

public class Stop extends CyberarmState {
  private AbstractMotor xAxis, yAxis;
  private CRServo svPen;
  private long lastToneMs = 0;

  public Stop() {
    if (cyberarmEngine.driver.offlineDebugging) {
      xAxis = (cyberarmEngine.driver.xAxisV);
      yAxis = (cyberarmEngine.driver.yAxisV);
    } else {
      xAxis = new Motor(cyberarmEngine.hardwareMap.dcMotor.get("xAxis"));
      yAxis = new Motor(cyberarmEngine.hardwareMap.dcMotor.get("yAxis"));
    }
    svPen = cyberarmEngine.hardwareMap.crservo.get("svPen");
  }

  @Override
  public void exec() {
    xAxis.stop();
    yAxis.stop();
    svPen.setPower(0);

    if (System.currentTimeMillis()-lastToneMs > 500) {
      cyberarmEngine.driver.toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD);
      lastToneMs = System.currentTimeMillis()+500;
    }

    setFinished(false);
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addLine("STOP STOP STOP");

    cyberarmEngine.telemetry.addLine();

    cyberarmEngine.telemetry.addLine("Plotter has been stopped");
    cyberarmEngine.telemetry.addData("xAxis", xAxis.getPower());
    cyberarmEngine.telemetry.addData("yAxis", yAxis.getPower());
    cyberarmEngine.telemetry.addData("svPen", svPen.getPower());

    cyberarmEngine.telemetry.addLine();

    cyberarmEngine.telemetry.addLine("STOP STOP STOP");
  }
}
