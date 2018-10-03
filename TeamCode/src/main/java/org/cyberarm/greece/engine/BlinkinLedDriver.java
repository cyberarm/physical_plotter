package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.Engine;
import org.cyberarm.greece.states.BlinkinLedDriverState;

@Disabled
//@TeleOp(name = "Blinkin")
public class BlinkinLedDriver extends Engine {
  @Override
  public void setup() {
    addState(new BlinkinLedDriverState());
  }
}
