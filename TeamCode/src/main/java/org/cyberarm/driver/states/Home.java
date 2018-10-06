package org.cyberarm.driver.states;

import org.cyberarm.driver.Driver;
import org.cyberarm.engine.CyberarmState;
import org.cyberarm.greece.statues.AbstractMotor;
import org.cyberarm.greece.statues.EndStop;
import org.cyberarm.greece.statues.Motor;

public class Home extends CyberarmState {
  private AbstractMotor xAxis, yAxis;
  private EndStop xAxisEndStop, yAxisEndStop;
  private boolean xAxisHomed = false, yAxisHomed = false;

  public Home() {
    if (((Driver) Driver.instance).offlineDebugging) {
      xAxis = (((Driver) Driver.instance).xAxisV);
      yAxis = (((Driver) Driver.instance).yAxisV);
    } else {
      xAxis = new Motor(cyberarmEngine.hardwareMap.dcMotor.get("xAxis"));
      yAxis = new Motor(cyberarmEngine.hardwareMap.dcMotor.get("yAxis"));
    }
    xAxisEndStop = new EndStop(cyberarmEngine.hardwareMap.touchSensor.get("xAxisEndStop"));
    yAxisEndStop = new EndStop(cyberarmEngine.hardwareMap.touchSensor.get("yAxisEndStop"));
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
    cyberarmEngine.telemetry.addLine("HOME");

    cyberarmEngine.telemetry.addData("xAxis", xAxis.position());
    cyberarmEngine.telemetry.addData("yAxis", yAxis.position());

    cyberarmEngine.telemetry.addData("xAxis Stalled", xAxis.stalled());
    cyberarmEngine.telemetry.addData("yAxis Stalled", yAxis.stalled());

    cyberarmEngine.telemetry.addData("xAxis Endstop", xAxisEndStop.triggered());
    cyberarmEngine.telemetry.addData("yAxis Endstop", yAxisEndStop.triggered());
  }
}
