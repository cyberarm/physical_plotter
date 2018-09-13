package org.driver.states;

import org.driver.Driver;
import org.engine.State;
import org.greece.statues.AbstractMotor;
import org.greece.statues.EndStop;
import org.greece.statues.Motor;
import org.greece.statues.VirtualDCMotor;

public class Move extends State {
  private Motor xAxis, yAxis;
  private VirtualDCMotor xAxisV, yAxisV;
  private EndStop xAxisEndStop, yAxisEndStop;
  private int xTarget, yTarget;
  private boolean xAxisMoved = false;
  private boolean yAxisMoved = false;
  private int fuzz = 10;
  private int maxX = 4000;
  private  int maxY = 3000;

  public Move(int x, int y) {
    xTarget = x;
    yTarget = y;

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
    processor();
  }

  private void processor() {
    xAxis.update();
    yAxis.update();

    if (!xAxisMoved) {
      if (!between(xAxis.position(), xTarget)) {
        if (xAxis.position() > xTarget + fuzz)
          xAxis.getMotor().setPower(-0.1);
        else if (xAxis.position() < xTarget + fuzz) {
          xAxis.getMotor().setPower(0.1);
        } else {
          xAxis.stop();
          xAxisMoved = true;
        }
      }
    }


    if (!yAxisMoved && xAxisMoved){
      if (!between(yAxis.position(), yTarget)) {
        if (yAxis.position() > yTarget + fuzz)
          yAxis.getMotor().setPower(-0.1);
        else if (yAxis.position() < yTarget + fuzz) {
          yAxis.getMotor().setPower(0.1);
        } else {
          yAxis.stop();
          yAxisMoved = true;
        }
      }

      if (xAxisMoved && yAxisMoved){
        xAxis.stop();
        yAxis.stop();
        setFinished(true);
      }

    }
  }

  /*
    position.between?(target-fuzz, target+fuzz)
  */

  private boolean between(int position, int target) {
    return between(position, target, fuzz);
  }
  private boolean between(int position, int target, int fuzz){
    if ((position > target - fuzz) && (position < target + fuzz)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void telemetry() {
    if (((Driver) Driver.instance).offlineDebugging) {
      engine.telemetry.addLine("MOVE: X: " + xTarget + " Y: " + yTarget);
      engine.telemetry.addData("xAxisV", "X: " + xAxisV.position() + " Target X: " + xTarget);
      engine.telemetry.addData("yAxisV", "Y: " + yAxisV.position() + " Target Y: " + yTarget);

      engine.telemetry.addData("xAxis Stalled", xAxisV.stalled());
      engine.telemetry.addData("yAxis Stalled", yAxisV.stalled());

      engine.telemetry.addData("xAxis Endstop", xAxisEndStop.triggered());
      engine.telemetry.addData("yAxis Endstop", yAxisEndStop.triggered());
    } else {
      engine.telemetry.addLine("MOVE: X: " + xTarget + " Y: " + yTarget);
      engine.telemetry.addData("xAxis", "X: " + xAxis.position() + " Target X: " + xTarget);
      engine.telemetry.addData("yAxis", "Y: " + yAxis.position() + " Target Y: " + yTarget);

      engine.telemetry.addData("xAxis Stalled", xAxis.stalled());
      engine.telemetry.addData("yAxis Stalled", yAxis.stalled());

      engine.telemetry.addData("xAxis Endstop", xAxisEndStop.triggered());
      engine.telemetry.addData("yAxis Endstop", yAxisEndStop.triggered());
    }
  }
}
