package org.driver.states;

import org.driver.Driver;
import org.engine.State;

public class Wait extends State {
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
    engine.telemetry.addLine("Waiting...");
    engine.telemetry.addData("Looped While Waiting", loopedWhileWaiting);
  }
}
