package org.driver.states;

import org.engine.State;
import org.greece.statues.EndStop;
import org.greece.statues.Motor;

public class Home extends State {
  private Motor xAxis, yAxis;
  private EndStop xAxisEndStop, yAxisEndStop;
  private boolean xAxisHomed = false, yAxisHomed = false;

  public Home() {
    xAxis = new Motor(engine.hardwareMap.dcMotor.get("xAxis"));
    yAxis = new Motor(engine.hardwareMap.dcMotor.get("yAxis"));
    xAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("xAxisEndStop"));
    yAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("yAxisEndStop"));
  }

  @Override
  public void exec() {
    xAxis.update();
    yAxis.update();

    if (!xAxisHomed) {
      if (!xAxisEndStop.triggered()) {
        xAxis.getMotor().setPower(-0.1);
      } else {
        xAxisHomed = true;
        xAxis.stop();
      }
    }

    if (!yAxisHomed && xAxisHomed) {
      if (!yAxisEndStop.triggered()) {
        yAxis.getMotor().setPower(-0.1);
      } else {
        yAxisHomed = true;
        yAxis.stop();
      }
    }

    if (xAxisHomed && yAxisHomed) {
      xAxis.stop();
      yAxis.stop();
      xAxis.resetEncoder();
      yAxis.resetEncoder();
      setFinished(true);
    }
  }

  @Override
  public void telemetry() {
    engine.telemetry.addLine("HOME");
    engine.telemetry.addData("xAxis", xAxis.position());
    engine.telemetry.addData("yAxis", yAxis.position());

    engine.telemetry.addData("xAxis Stalled", xAxis.stalled());
    engine.telemetry.addData("yAxis Stalled", yAxis.stalled());

    engine.telemetry.addData("xAxis Endstop", xAxisEndStop.triggered());
    engine.telemetry.addData("yAxis Endstop", yAxisEndStop.triggered());
  }
}
