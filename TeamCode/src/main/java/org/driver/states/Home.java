package org.driver.states;

import org.driver.Driver;
import org.engine.State;
import org.greece.statues.EndStop;
import org.greece.statues.Motor;

public class Home extends State {
  private Motor xAxis, yAxis;
  private EndStop xAxisEndStop, yAxisEndStop;

  @Override
  public void init() {
    xAxis = new Motor(engine.hardwareMap.dcMotor.get("xAxis"));
    yAxis = new Motor(engine.hardwareMap.dcMotor.get("yAxis"));
    xAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("xAxisEndStop"));
    yAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("yAxisEndStop"));
  }

  @Override
  public void exec() {
    xAxis.update();
    yAxis.update();

    if (!xAxisEndStop.triggered() && !xAxis.stalled()) {
      xAxis.getMotor().setPower(-0.1);

    } else if (!yAxisEndStop.triggered() && !yAxis.stalled()) {
        xAxis.stop();
        yAxis.getMotor().setPower(-0.1);

      } else {
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
