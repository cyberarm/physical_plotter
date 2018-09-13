package org.greece.statues;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Motor extends AbstractMotor {
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
         Log.i("MOTOR", getMotor().toString());
        this.lastUpdateMs = 0;
        this.lastPosition = 0;
        this.lastVelocity = 0;
    }

    public void update() {
        if (hasUpdatedBefore) {
          currentVelocity = (getMotor().getCurrentPosition() - lastPosition) / ((System.currentTimeMillis() - lastUpdateMs)/1000.0);
          faultCheck();
        } else { hasUpdatedBefore = true; }

        lastVelocity = currentVelocity;
        lastPosition = getMotor().getCurrentPosition();
        lastUpdateMs = System.currentTimeMillis();
    }

  protected void faultCheck() {
    if (Math.abs(getMotor().getPower()) >= 0.1) {
      if (getMotor().getPower() < 0.0) {
        if (getMotor().getCurrentPosition() <= lastPosition) {
          fault+=1;
          if (fault >= faultThreshold) {stalled = true;
          }
        } else {
          fault = 0;
        }

      } else if (getMotor().getPower() > 0.0) {
        if (getMotor().getCurrentPosition() <= lastPosition) {
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
       return getMotor().getDeviceName();
    }

    public double power() {
       return getMotor().getPower();
    }

    public int position() {
        return 0;//;getMotor().getCurrentPosition();
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

  @Override
  public double getPower() {
    return getMotor().getPower();
  }

  @Override
  public int getCurrentPosition() {
    return getMotor().getCurrentPosition();
  }

  @Override
  public String getDeviceName() {
    return getMotor().getDeviceName();
  }

  public void stop() {
    getMotor().setPower(0.0);
  }

  public void resetEncoder() {
    getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }
}
