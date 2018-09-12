package org.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.engine.Engine;
import org.greece.states.BlinkinLedDriverState;

@TeleOp(name = "Blinkin")
public class BlinkinLedDriver extends Engine {
  @Override
  public void setup() {
    addState(new BlinkinLedDriverState());
  }
}
