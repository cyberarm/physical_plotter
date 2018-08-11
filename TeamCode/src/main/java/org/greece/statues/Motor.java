package org.greece.statues;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Motor {
    private long lastUpdateMs;
    private int lastPosition;
    private double lastVelocity, currentVelocity;
    private DcMotor motor;
    private boolean hasUpdatedBefore = false;
    private int fault = 0;
    private int faultThreshold = 100;
    private boolean stalled = false;

  public Motor(DcMotor motor) {
        this.motor = motor;
         Log.i("MOTOR", motor.toString());
        this.lastUpdateMs = 0;
        this.lastPosition = 0;
        this.lastVelocity = 0;
    }

    public void update() {
        if (hasUpdatedBefore) {
          currentVelocity = (motor.getCurrentPosition() - lastPosition) / ((System.currentTimeMillis() - lastUpdateMs)/1000.0);
          faultCheck();
        } else { hasUpdatedBefore = true; }

        lastVelocity = currentVelocity;
        lastPosition = motor.getCurrentPosition();
        lastUpdateMs = System.currentTimeMillis();
    }

  private void faultCheck() {
    if (Math.abs(motor.getPower()) >= 0.1) {
      if (motor.getPower() < 0.0) {
        if (motor.getCurrentPosition() <= lastPosition) {
          fault+=1;
          if (fault >= faultThreshold) {stalled = true;
          }
        } else {
          fault = 0;
        }

      } else if (motor.getPower() > 0.0) {
        if (motor.getCurrentPosition() <= lastPosition) {
          fault+=1;
          if (fault >= faultThreshold) {
            stalled = true;
          }
        } else {
          fault = 0;
        }
      }
    }
  }

  public double velocity() {
        return currentVelocity;
    }

    public String name() {
       return motor.getDeviceName();
    }

    public double power() {
       return motor.getPower();
    }

    public int position() {
        return motor.getCurrentPosition();
    }

    public int lastPosition() {
        return lastPosition;
    }

    public DcMotor getMotor() {
        return motor;
    }

    public double lastVelocity() {
        return lastVelocity;
    }

    public boolean stalled() {
      return stalled;
    }

  public void stop() {
      motor.setPower(0.0);
  }

  public void resetEncoder() {
      motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }
}
