package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.engine.CyberarmEngine;
import org.cyberarm.greece.states.RevRovingRobotControl;

@TeleOp(name = "RevRovingRobot")
public class RevRovingRobot extends CyberarmEngine {
  @Override
  public void setup() {
    addState(new RevRovingRobotControl());
  }
}
