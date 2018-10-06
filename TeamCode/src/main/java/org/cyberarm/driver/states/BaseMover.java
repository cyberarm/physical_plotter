package org.cyberarm.driver.states;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.cyberarm.engine.CyberarmState;
import org.cyberarm.engine.Support;

import java.util.HashMap;

public class BaseMover extends CyberarmState {
  DcMotor xAxis, yAxis;
  TouchSensor xAxisEndStop, yAxisEndStop;

  int targetX, targetY, localX, localY;
  int multiplier = 40, fuzz;
  double speed = 0.1;
  int faultThreshold = 1000;
  boolean setupCompleted = false, checkEndStops = false;
  HashMap encoderData = new HashMap<>();

  // What to do when a encoder malfunction or motor stall is detected
  int ABORT = 0,
          SKIP = 1,
          IGNORE = 2,
          HALT_MOTOR = 3;

  public BaseMover(int x, int y) {
    encoderData.put("x_axis_endstop_activated", false);
    encoderData.put("y_axis_endstop_activated", false);

    targetX = x;
    targetY = y;

    xAxis = cyberarmEngine.hardwareMap.dcMotor.get("xAxis");
    yAxis = cyberarmEngine.hardwareMap.dcMotor.get("yAxis");

    xAxisEndStop = cyberarmEngine.hardwareMap.touchSensor.get("xAxisEndStop");
    yAxisEndStop = cyberarmEngine.hardwareMap.touchSensor.get("yAxisEndStop");

    xAxis.setPower(0);
    yAxis.setPower(0);

    localX = adjustedTarget(targetX);
    localY = adjustedTarget(targetY);
    fuzz = 10;
  }

  @Override
  public void init() {
    xAxis.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    yAxis.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }

  @Override
  public void exec() {
    cyberarmEngine.telemetry.addData("Event", this.getClass());
    cyberarmEngine.telemetry.addData("yAxis", "" + yAxis.getCurrentPosition());

    if (!setupCompleted) {
      Support.puts("BaseMover", "Moving pen to " + localX + ":" + localY + "...");

      setupCompleted = true;
    } // else { Support.puts("BaseMover", ""+xAxis.getCurrentPosition()+":"+yAxis.getCurrentPosition()); }

    if (checkEndStops) { // Used for Home state
      checkEncoder("xAxis", xAxis, targetX, faultThreshold, HALT_MOTOR);
      checkEncoder("yAxis", yAxis, targetY, faultThreshold, HALT_MOTOR);

      if (checkEndStop("xAxisEndStop", xAxisEndStop)) {
        xAxis.setPower(0.0);
        encoderData.put("x_axis_endstop_activated", true);
        cyberarmEngine.telemetry.addData("End Stop", "X Axis endstop triggered.");
      } else {
        if ((boolean) encoderData.get("x_axis_endstop_activated") != true) {
          xAxis.setPower(-0.1);
        }
      }

      if (checkEndStop("yAxisEndStop", yAxisEndStop)) {
        yAxis.setPower(0.0);
        encoderData.put("y_axis_endstop_activated", true);
        cyberarmEngine.telemetry.addData("End Stop", "Y Axis endstop triggered.");
      } else {
        if ((boolean) encoderData.get("y_axis_endstop_activated") != true) {
          yAxis.setPower(-0.1); // CAUTION: THIS IS A BUG RISK. (Might be resolved.)
        }
      }

//            if (((boolean) encoderData.get("x_axis_endstop_activated") || xAxis.getPower() == 0.0) && ((boolean) encoderData.get("y_axis_endstop_activated") || yAxis.getPower() == 0.0)) {
//      if ((boolean) encoderData.get("y_axis_endstop_activated")) {
//        setFinished(true);
//        return;
//      }

    } else {
      checkEncoder("xAxis", xAxis, targetX, faultThreshold, ABORT);
      checkEncoder("yAxis", yAxis, targetY, faultThreshold, ABORT);

      if (!between(yAxis.getCurrentPosition(), adjustedTarget(targetY), fuzz)) {
        if (yAxis.getCurrentPosition() < localY + fuzz) {
          yAxis.setPower(-speed);
        } else if (yAxis.getCurrentPosition() > localY - fuzz) {
          yAxis.setPower(speed);

        } else {
          yAxis.setPower(0);
        }
      } else {
        if (xAxis.getCurrentPosition() < localX + fuzz) {
          xAxis.setPower(-speed);
        } else if (xAxis.getCurrentPosition() > localX + fuzz) {
          xAxis.setPower(speed);

        } else {
          xAxis.setPower(0);
//          setFinished(true);
        }
      }
    }

    cyberarmEngine.telemetry.update();
  }

  @Override
  public void stop() {
    xAxis.setPower(0);
    yAxis.setPower(0);
  }

  protected void useEndStops() {
    checkEndStops = true;
  }

  private boolean checkEndStop(String friendlyName, TouchSensor touchSensor) {
    if (touchSensor.isPressed()) {
      Support.puts("BaseMover", "" + friendlyName + " is pressed.");
      return true;
    } else {
      return false;
    }
  }

  private void checkEncoder(String friendlyName, DcMotor motor, int target, int threshold, int resolver) {
    if (between(motor.getCurrentPosition(), target, fuzz)) {
      // All clear
      if (encoderData.get(friendlyName) != null) {
        encoderData.put(friendlyName, "" + motor.getCurrentPosition() + ":" + 0);
      }
    } else {
      if (encoderData.get(friendlyName) != null) {
        // last_position:same_position_count
        String[] data = ((String) encoderData.get(friendlyName)).split(":");
        int last_position = Integer.valueOf(data[0]);
        int same_position_count = Integer.valueOf(data[1]);

        if (between(motor.getCurrentPosition(), last_position, fuzz)) {
          if (threshold >= same_position_count) {
            resolve(friendlyName, resolver); // Explode.
          }

          same_position_count++;
          Log.i("BaseMover", ""+friendlyName+" -> "+same_position_count+" fault count");
          encoderData.put(friendlyName, "" + last_position + ":" + same_position_count);
        }
      } else {
        encoderData.put(friendlyName, "" + motor.getCurrentPosition() + ":" + 0);
      }
    }
  }

  private void resolve(String name, int resolver) {
    if (resolver == ABORT) {
//            throw new RuntimeException("Encoder on "+name+" is broken or the motor is stalled!");
      cyberarmEngine.telemetry.addData("Error", "Encoder on " + name + " is broken or the motor is stalled!" + cyberarmEngine.getRuntime());
//      setFinished(true);
//      cyberarmEngine.stop();

    } else if (resolver == HALT_MOTOR) {
      cyberarmEngine.telemetry.addData("Error", "Encoder or motor error on " + name + "! Halting Motor.");
      cyberarmEngine.hardwareMap.dcMotor.get(name).setPower(0.0);

    } else if (resolver == SKIP) {
      cyberarmEngine.telemetry.addData("Error", "Encoder or motor error on " + name + "! Skipping.");
      Support.puts("BaseMover", "Encoder or motor error on " + name + "! Skipping.");
      setFinished(true);

    } else if (resolver == IGNORE) {
      cyberarmEngine.telemetry.addData("Error", "Encoder or motor error on " + name + "! Ignoring.");
      // Do nothing but watch the world burn.
    } else {
      Support.puts("BaseMover", "Unknown resolver: " + resolver);
    }
  }

    /*
        position.between?(target-fuzz, target+fuzz)
     */

  private boolean between(int position, int target, int fuzz) {
    if ((position > target - fuzz) && (position < target + fuzz)) {
      return true;
    } else {
      return false;
    }
  }

  private int adjustedTarget(int target) {
    return (target * multiplier);
  }
}
