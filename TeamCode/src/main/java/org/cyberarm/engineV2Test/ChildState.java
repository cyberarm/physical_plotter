package org.cyberarm.engineV2Test;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class ChildState extends CyberarmStateV2 {
  @Override
  public void exec() {
    if (runTime() > 2_000) {
      setHasFinished(true);
    }
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addLine();
    cyberarmEngine.telemetry.addData("Child", runTime());
    cyberarmEngine.telemetry.addData("Finished?", getHasFinished());
  }
}
