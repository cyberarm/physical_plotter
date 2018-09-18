package org.driver.states;

import android.media.ToneGenerator;

import com.qualcomm.robotcore.hardware.CRServo;

import org.driver.Driver;
import org.engine.State;
import org.greece.statues.AbstractMotor;
import org.greece.statues.Motor;

public class Stop extends State {
  private AbstractMotor xAxis, yAxis;
  private CRServo svPen;
  private long lastToneMs = 0;

  public Stop() {
    if (engine.driver.offlineDebugging) {
      xAxis = (engine.driver.xAxisV);
      yAxis = (engine.driver.yAxisV);
    } else {
      xAxis = new Motor(engine.hardwareMap.dcMotor.get("xAxis"));
      yAxis = new Motor(engine.hardwareMap.dcMotor.get("yAxis"));
    }
    svPen = engine.hardwareMap.crservo.get("svPen");
  }

  @Override
  public void exec() {
    xAxis.stop();
    yAxis.stop();
    svPen.setPower(0);

    if (System.currentTimeMillis()-lastToneMs > 500) {
      engine.driver.toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD);
      lastToneMs = System.currentTimeMillis()+500;
    }

    setFinished(false);
  }

  @Override
  public void telemetry() {
    engine.telemetry.addLine("STOP STOP STOP");

    engine.telemetry.addLine();

    engine.telemetry.addLine("Plotter has been stopped");
    engine.telemetry.addData("xAxis", xAxis.getPower());
    engine.telemetry.addData("yAxis", yAxis.getPower());
    engine.telemetry.addData("svPen", svPen.getPower());

    engine.telemetry.addLine();

    engine.telemetry.addLine("STOP STOP STOP");
  }
}
