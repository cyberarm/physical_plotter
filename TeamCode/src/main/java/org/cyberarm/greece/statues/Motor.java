package org.cyberarm.greece.statues;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.cyberarm.engine.CyberarmEngine;

public class Motor extends AbstractMotor {
  private DcMotor motor;
  public Motor(DcMotor motor) {
    this.motor = motor;
    Log.i("MOTOR", getMotor().toString());
    this.lastUpdateMs = 0;
    this.lastPosition = 0;
    this.lastVelocity = 0;
  }

  public void update() {
    if (hasUpdatedBefore) {
      currentVelocity = (getMotor().getCurrentPosition() - lastPosition) / ((System.currentTimeMillis() - lastUpdateMs) / 1000.0);
      faultCheck();
    } else {
      hasUpdatedBefore = true;
    }

    lastVelocity = currentVelocity;
    lastPosition = getMotor().getCurrentPosition();
    lastUpdateMs = System.currentTimeMillis();
  }

//  protected void faultCheck() {
//    if (Math.abs(getMotor().getPower()) > 0) { // Should be moving?
//      if (getMotor().getPower() < 0.0) { // Is moving backward?
//        if (getMotor().getCurrentPosition() >= lastPosition) {
//          fault += 1;
//          if (fault >= faultThreshold) {
//            stalled = true;
//            playErrorTone();
//            getMotor().setPower(0);
//          }
//        } else {
//          fault = 0;
//        }
//
//      } else if (getMotor().getPower() > 0.0) { // Is moving Forward?
//        if (getMotor().getCurrentPosition() <= lastPosition) {
//          fault += 1;
//          if (fault >= faultThreshold) {
//            stalled = true;
//            playErrorTone();
//            getMotor().setPower(0);
//          }
//        } else {
//          fault = 0;
//        }
//      }
//    }
//
//    CyberarmEngine.instance.telemetry.addData("Motor "+getDeviceName()+" Faults", fault);
//    CyberarmEngine.instance.telemetry.addData("Motor "+getDeviceName()+" Fault Threshold", faultThreshold);
//  }

  protected void faultCheck() {
    if (Math.abs(getMotor().getPower()) > 0) { // Should be moving?
      if (getMotor().getPower() < 0) { // Is moving backward?
        if (getMotor().getCurrentPosition() >= lastPosition) {

          if (fault >= faultThreshold) {
            stalled = true;
            playErrorTone();
            getMotor().setPower(0);
          } else if ((System.currentTimeMillis()-lastFault) >= faultTimeOut) {
            fault += 1;
            lastFault = System.currentTimeMillis();
          }
        } else {
          fault = 0;
        }

      } else if (getMotor().getPower() > 0) { // Is moving Forward?
        if (getMotor().getCurrentPosition() <= lastPosition) {

          if (fault >= faultThreshold) {
            stalled = true;
            playErrorTone();
            getMotor().setPower(0);
          } else if ((System.currentTimeMillis()-lastFault) >= faultTimeOut) {
            fault += 1;
            lastFault = System.currentTimeMillis();
          }
        } else {
          fault = 0;
        }
      }
    }

    CyberarmEngine.instance.telemetry.addData(""+this.getClass()+" "+getDeviceName()+" Faults", fault);
    CyberarmEngine.instance.telemetry.addData(""+this.getClass()+" "+getDeviceName()+" Fault Threshold", faultThreshold);
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

  public DcMotor getMotor() {
    return (DcMotor) motor;
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
  public void setPower(double power) {
    getMotor().setPower(power);
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
