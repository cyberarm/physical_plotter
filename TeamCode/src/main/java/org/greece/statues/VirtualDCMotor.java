package org.greece.statues;

import android.util.Log;

public class VirtualDCMotor extends AbstractMotor{

  public VirtualDCMotor(String name) {
    this.name = name;
    this.motor = this;
    Log.i("MOTOR", getMotor().toString());
    this.lastUpdateMs = 0;
    this.lastPosition = 0;
    this.lastVelocity = 0;
  }

  public void update() {
    if (hasUpdatedBefore) {
      currentVelocity = (getMotor().getCurrentPosition() - lastPosition) / ((System.currentTimeMillis() - lastUpdateMs)/1000.0);
      simulateMotor();
      faultCheck();
    } else { hasUpdatedBefore = true; }


    lastVelocity = currentVelocity;
    lastPosition = getMotor().getCurrentPosition();
    lastUpdateMs = System.currentTimeMillis();
  }

  private void simulateMotor() {
    long time = System.currentTimeMillis()-lastUpdateMs;

    double i = (5*Math.abs(power))/time;
    if (getPower() < 0) { // Moving BACKWARD
      position-=i; // TODO: Maths
    } else if (getPower() > 0) { // Moving FORWARD
      position+=i; // TODO: Maths
    } else { // Not Moving

    }
  }

  protected void faultCheck() {
    if (Math.abs(getMotor().getPower()) >= 0.1) {
      if (getMotor().getPower() < 0.0) {
        if (getMotor().getCurrentPosition() >= lastPosition) {
          fault+=1;
          if (fault >= faultThreshold) {
            stalled = true;
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
