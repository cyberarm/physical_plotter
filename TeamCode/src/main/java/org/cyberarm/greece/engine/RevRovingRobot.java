package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.Engine;
import org.cyberarm.greece.states.RevRovingRobotControl;

@TeleOp(name = "RevRovingRobot")
public class RevRovingRobot extends Engine {
  @Override
  public void setup() {
    addState(new RevRovingRobotControl());
  }
}
