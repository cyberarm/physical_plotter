package org.greece.statues;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;

public class VirtualDCMotor extends AbstractMotor {

  private boolean simulateStall = false;
  private int stallPosition = 0;

  public VirtualDCMotor(String name) {
    this.name = name;
    this.motor = this;
    Log.i("MOTOR", getMotor().toString());
    this.lastUpdateMs = 0;
    this.lastPosition = 0;
    this.lastVelocity = 0;

    simulatedStall(4000);
  }

  public void update() {
    if (hasUpdatedBefore) {
      currentVelocity = (getMotor().getCurrentPosition() - lastPosition) / ((System.currentTimeMillis() - lastUpdateMs) / 1000.0);
      simulateMotor();
      faultCheck();
    } else {
      hasUpdatedBefore = true;
    }


    lastVelocity = currentVelocity;
    lastPosition = getMotor().getCurrentPosition();
    lastUpdateMs = System.currentTimeMillis();
  }

  private void simulateMotor() {
    long time = System.currentTimeMillis() - lastUpdateMs;

    if (shouldStall()) {
      playErrorTone();
    } else {
      double i = (5 * Math.abs(power)) / time;
      if (getPower() < 0) { // Moving BACKWARD
        position -= i;
      } else if (getPower() > 0) { // Moving FORWARD
        position += i;
      } else { // Not Moving

      }
    }
  }

  public void simulatedStall(int stallPosition) {
    this.simulateStall = true;
    this.stallPosition = stallPosition;
  }

  private boolean shouldStall() {
    if (between(getCurrentPosition(), stallPosition, fuzz)) {
      return true;
    } else {
      return false;
    }
  }

  protected void faultCheck() {}

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
    return getMotor().getCurrentPosition();
  }

  public int lastPosition() {
    return lastPosition;
  }

  public VirtualDCMotor getMotor() {
    return (VirtualDCMotor) motor;
  }

  public double lastVelocity() {
    return lastVelocity;
  }

  public boolean stalled() {
    return stalled;
  }

  public void stop() {
    getMotor().setPower(0.0);
  }

  public void setPower(double power) {
    if (power > 1.0) {
      this.power = 1.0;
    } else if (power < -1.0) {
      this.power = -1.0;
    } else {
      this.power = power;
    }
  }

  public double getPower() {
    return this.power;
  }

  public int getCurrentPosition() {
    return (int) this.position;
  }

  public String getDeviceName() {
    return this.name;
  }

  public void resetEncoder() {
    this.position = 0;
  }
}