package org.greece.states;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.container.InputChecker;
import org.engine.State;
import org.greece.statues.RevTOFDistanceSensor;

public class BlinkinLedDriverState extends State {
  ServoImplEx pwm_output;
//  RevTOFDistanceSensor TOFSensor;


  InputChecker inputChecker;
  int pwm_target = 1005;
  int max_pwm_target = 1995;
  int min_pwm_target = 1005;
  int pwm_step_size = 10;
  private long next_time;

  @Override
  public void init() {
    pwm_output = (ServoImplEx) engine.hardwareMap.servo.get("pwm");
//    TOFSensor = engine.hardwareMap.get(RevTOFDistanceSensor.class, "distance");

    pwm_output.setPwmRange(new PwmControl.PwmRange(1000.0, 2000.0));
    inputChecker = new InputChecker(engine.gamepad1);
  }

  /***
   * do math is a function that translates microseconds to a servo power.
   * @param target
   * */
  public double do_math(int target) {
    return ((target-1000.0) / 1000.0);
  }

  @Override
  public void exec() {
    if ((System.currentTimeMillis()) - engine.gamepad1.timestamp > 10_000) {
      if (System.currentTimeMillis() > next_time) {
        pwm_target += pwm_step_size;
        if (pwm_target > max_pwm_target) {
          pwm_target = min_pwm_target;
        }
        next_time = System.currentTimeMillis() + 4_000;
      }
    }
    
    
    if (inputChecker.check("y")) {
      pwm_target+=pwm_step_size;
    } else if (inputChecker.check("a")) {
      pwm_target-=pwm_step_size;
    }
    if (pwm_target < min_pwm_target) {
      pwm_target = min_pwm_target;
    } else if (pwm_target > max_pwm_target) {
      pwm_target = max_pwm_target;
    }

    pwm_output.setPosition(do_math(pwm_target));

    inputChecker.update();
  }

  @Override
  public void telemetry() {
    engine.telemetry.addData("PWM target", pwm_target);
    engine.telemetry.addData("PWM position/power", do_math(pwm_target));
    engine.telemetry.addData("Next change", ""+(next_time-System.currentTimeMillis())+" ms");
//    engine.telemetry.addData("Distance Sensor WHO_AM_I", ""+TOFSensor.getWhoAmIRAW());
    engine.telemetry.addData("Gamepad1 Timestamp", engine.gamepad1.timestamp);
  }
}
