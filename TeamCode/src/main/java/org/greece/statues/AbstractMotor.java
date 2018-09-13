package org.greece.statues;

import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class AbstractMotor {
  protected long lastUpdateMs;
  protected int lastPosition;
  protected double lastVelocity, currentVelocity;
  protected boolean hasUpdatedBefore = false;
  protected AbstractMotor motor;
  protected int fault = 0;
  protected int faultThreshold = 100;
  protected boolean stalled = false;
  protected double power = 0.0;
  protected double position = 0;
  protected String name;

  public abstract void update();

  protected abstract void faultCheck();

  public double velocity() {
    return currentVelocity;
  }

  public abstract String name();

  public abstract double power();

  public abstract int position();

  public int lastPosition() {
    return lastPosition;
  }

  public double lastVelocity() {
    return lastVelocity;
  }

  public boolean stalled() {
    return this.stalled;
  }

  public abstract double getPower();

  public abstract int getCurrentPosition();

  public abstract String getDeviceName();

  public abstract void stop();

  public abstract void resetEncoder();
}
