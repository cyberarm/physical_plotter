package org.driver.states;

import org.engine.State;
import org.greece.statues.EndStop;
import org.greece.statues.Motor;

public class Move extends State {
  private Motor xAxis, yAxis;
  private EndStop xAxisEndStop, yAxisEndStop;
  private int xTarget, yTarget;
  private boolean xAxisMoved = false;
  private boolean yAxisMoved = false;

  public Move(int x, int y) {
    xTarget = x;
    yTarget = y;

    xAxis = new Motor(engine.hardwareMap.dcMotor.get("xAxis"));
    yAxis = new Motor(engine.hardwareMap.dcMotor.get("yAxis"));
    xAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("xAxisEndStop"));
    yAxisEndStop = new EndStop(engine.hardwareMap.touchSensor.get("yAxisEndStop"));
  }

  @Override
  public void exec() {
    xAxis.update();
    yAxis.update();

    if (!xAxisMoved)
      if (xTarget > xAxis.position()) {
        xAxis.getMotor().setPower(0.1);

      } else if (xTarget < xAxis.position()) {
        xAxis.getMotor().setPower(-0.1);
      } else {
        xAxis.stop();
        xAxisMoved = true;
      }

    if (!yAxisMoved && xAxisMoved){
      if (yTarget > yAxis.position()) {
        yAxis.getMotor().setPower(0.1);

      } else if (yTarget < yAxis.position()) {
        yAxis.getMotor().setPower(-0.1);
      } else {
        yAxisMoved = true;
        yAxis.stop();
      }

      if (xAxisMoved && yAxisMoved){
        xAxis.stop();
        yAxis.stop();
        setFinished(true);
      }

    }
  }

  @Override
  public void telemetry() {
    engine.telemetry.addLine("MOVE: X: "+xTarget+" Y: "+yTarget);
    engine.telemetry.addData("xAxis", "Y: "+xAxis.position()+" Target X: "+xTarget);
    engine.telemetry.addData("yAxis", "Y: "+yAxis.position()+" Target Y: "+yTarget);

    engine.telemetry.addData("xAxis Stalled", xAxis.stalled());
    engine.telemetry.addData("yAxis Stalled", yAxis.stalled());

    engine.telemetry.addData("xAxis Endstop", xAxisEndStop.triggered());
    engine.telemetry.addData("yAxis Endstop", yAxisEndStop.triggered());
  }
}
