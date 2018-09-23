package org.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.engine.Engine;
import org.greece.states.RevRovingRobotControl;

@TeleOp(name = "RevRovingRobot")
public class RevRovingRobot extends Engine {
  @Override
  public void setup() {
    addState(new RevRovingRobotControl());
  }
}
