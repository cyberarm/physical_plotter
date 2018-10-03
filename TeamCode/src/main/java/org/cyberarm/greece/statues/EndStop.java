package org.cyberarm.greece.statues;

import com.qualcomm.robotcore.hardware.TouchSensor;

public class EndStop {
  private TouchSensor endstop;
  public EndStop(TouchSensor endstop) {
    this.endstop = endstop;
  }

  public boolean triggered() {
    return endstop.isPressed();
  }
}
