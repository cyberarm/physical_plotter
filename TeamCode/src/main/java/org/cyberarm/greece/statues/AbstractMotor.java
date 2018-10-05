package org.cyberarm.greece.statues;

import android.media.ToneGenerator;

import org.cyberarm.engine.CyberarmEngine;

public abstract class AbstractMotor {
  protected String name;
  protected AbstractMotor motor;

  protected long lastUpdateMs;
  protected int lastPosition;
  protected double lastVelocity, currentVelocity;
  public boolean hasUpdatedBefore = false;

  protected int fault = 0; // Number of detected faults (Fault = encoder not changed)
  protected int faultThreshold = 10; // Number of detected faults until Motor is declared stalled
  protected long lastFault = 0; // Time in milliseconds, default to 0 to ensure first fault is trigger promptly
  protected long faultTimeOut = 100; // milliseconds
  protected boolean stalled = false; // Whether motor is stuck or encoder is broken

  protected double power = 0.0;
  protected double position = 0;
  protected int fuzz = 10; // Used to pad encoder position checks (POSITION == TARGET_POSITION +/- fuzz)


  protected ToneGenerator toneGenerator = CyberarmEngine.driverStatic.toneGenerator;
  protected long toneGeneratorStartedAt;


  public abstract void update();

  public double velocity() {
    return currentVelocity;
  }

  protected boolean between(int position, int target, int fuzz) {
    if ((position > target - fuzz) && (position < target + fuzz)) {
      return true;
    } else {
      return false;
    }
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

  public void playErrorTone() {
    if (System.currentTimeMillis() - toneGeneratorStartedAt > 200) {
      toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
      toneGeneratorStartedAt = System.currentTimeMillis();
    }
  }

  public abstract double getPower();

  public abstract void setPower(double power);

  public abstract int getCurrentPosition();

  public abstract String getDeviceName();

  public abstract void stop();

  public abstract void resetEncoder();
}
