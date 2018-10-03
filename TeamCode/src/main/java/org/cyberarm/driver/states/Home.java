package org.cyberarm.driver.states;

import org.cyberarm.driver.Driver;
import org.cyberarm.engine.State;
import org.cyberarm.greece.statues.AbstractMotor;
import org.cyberarm.greece.statues.EndStop;
import org.cyberarm.greece.statues.Motor;

public class Home extends State {
  private AbstractMotor xAxis, yAxis;
  private EndStop xAxisEndStop, yAxisEndStop;
  private boolean xAxisHomed = false, yAxisHomed = false;

  public Home() {
    if (((Driver) Driver.instance).offlineDebugging) {
      xAxis = (((Driver) Driver.instance).xAxisV);
      yAxis = (((Driver) Driver.instance).yAxisV);
    } else {
      xAxis = new Motor(engine.hardwareMap.dcMotor.get("xAxis"));
      yAxis = new Motor(engine.hardwareMap.dcMotor.get("yAxis"));
    }
    xAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("xAxisEndStop"));
    yAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("yAxisEndStop"));
  }

  @Override
  public void exec() {
    xAxis.update();
    yAxis.update();

    if (xAxis.stalled()) {xAxisHomed = true;}
    if (yAxis.stalled()) {yAxisHomed = true;}

    moveChecker();
  }

  private void moveChecker() {
    if (!xAxisHomed) {
      if (!xAxisEndStop.triggered()) {
        xAxis.setPower(-0.1);
      } else {
        xAxisHomed = true;
        xAxis.stop();
      }
    }

    if (!yAxisHomed && xAxisHomed) {
      if (!yAxisEndStop.triggered()) {
        yAxis.setPower(-0.1);
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
