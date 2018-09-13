package org.driver.states;

import org.driver.Driver;
import org.engine.State;
import org.greece.statues.EndStop;
import org.greece.statues.Motor;
import org.greece.statues.VirtualDCMotor;

public class Home extends State {
  private Motor xAxis, yAxis;
  private VirtualDCMotor xAxisV, yAxisV;
  private EndStop xAxisEndStop, yAxisEndStop;
  private boolean xAxisHomed = false, yAxisHomed = false;

  public Home() {
    if (((Driver) Driver.instance).offlineDebugging) {
      xAxisV = (((Driver) Driver.instance).xAxisV);
      yAxisV = (((Driver) Driver.instance).yAxisV);
    } else {
      xAxis = new Motor(engine.hardwareMap.dcMotor.get("xAxis"));
      yAxis = new Motor(engine.hardwareMap.dcMotor.get("yAxis"));
    }
    xAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("xAxisEndStop"));
    yAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("yAxisEndStop"));
  }

  @Override
  public void exec() {
    if (((Driver) Driver.instance).offlineDebugging) {
      xAxisV.update();
      yAxisV.update();

      xAxisV.stop();
      yAxisV.stop();
      xAxisV.resetEncoder();
      yAxisV.resetEncoder();
      setFinished(true);
    } else {
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
  }

  @Override
  public void telemetry() {
    engine.telemetry.addLine("HOME");
    if (((Driver) Driver.instance).offlineDebugging) {
      engine.telemetry.addData("xAxisV", xAxisV.position());
      engine.telemetry.addData("yAxisV", yAxisV.position());

      engine.telemetry.addData("xAxisV Stalled", xAxisV.stalled());
      engine.telemetry.addData("yAxisV Stalled", yAxisV.stalled());

      engine.telemetry.addData("xAxis Endstop", xAxisEndStop.triggered());
      engine.telemetry.addData("yAxis Endstop", yAxisEndStop.triggered());
    } else {
      engine.telemetry.addData("xAxis", xAxisV.position());
      engine.telemetry.addData("yAxis", yAxisV.position());

      engine.telemetry.addData("xAxis Stalled", xAxisV.stalled());
      engine.telemetry.addData("yAxis Stalled", yAxisV.stalled());

      engine.telemetry.addData("xAxis Endstop", xAxisEndStop.triggered());
      engine.telemetry.addData("yAxis Endstop", yAxisEndStop.triggered());
    }
  }
}
