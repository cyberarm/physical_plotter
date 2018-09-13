package org.greece.statues;

import android.media.AudioManager;
import android.media.ToneGenerator;

public abstract class AbstractMotor {
  protected long lastUpdateMs;
  protected int lastPosition;
  protected double lastVelocity, currentVelocity;
  public boolean hasUpdatedBefore = false;
  protected AbstractMotor motor;
  protected int fault = 0;
  protected int faultThreshold = 100;
  protected boolean stalled = false;
  protected double power = 0.0;
  protected double position = 0;
  protected String name;
  protected int fuzz = 10;


  protected ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
  protected long toneGeneratorStartedAt;


  public abstract void update();

  protected abstract void faultCheck();

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
