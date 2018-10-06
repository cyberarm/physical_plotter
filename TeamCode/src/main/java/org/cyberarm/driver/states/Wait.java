package org.cyberarm.driver.states;

import org.cyberarm.driver.Driver;
import org.cyberarm.engine.CyberarmState;

public class Wait extends CyberarmState {
  private final Driver driver;
  private long loopedWhileWaiting = 0;

  public Wait(Driver driver) {
    this.driver = driver;
  }
  @Override
  public void exec() {
    if (!driver.pendingWork) {
      loopedWhileWaiting++;
    } else {
      setFinished(true);
    }
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addLine("Waiting...");
    cyberarmEngine.telemetry.addData("Looped While Waiting", loopedWhileWaiting);
  }
}
