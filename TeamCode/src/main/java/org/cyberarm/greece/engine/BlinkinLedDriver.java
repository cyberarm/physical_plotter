package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.cyberarm.engine.CyberarmEngine;
import org.cyberarm.greece.states.BlinkinLedDriverCyberarmState;

@Disabled
//@TeleOp(name = "Blinkin")
public class BlinkinLedDriver extends CyberarmEngine {
  @Override
  public void setup() {
    addState(new BlinkinLedDriverCyberarmState());
  }
}
