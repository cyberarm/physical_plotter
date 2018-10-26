package org.cyberarm.engineV2Test;

import org.cyberarm.engine.V2.CyberarmStateV2;

public class TopLevelState extends CyberarmStateV2 {
  int updatesSinceLastTelemetryRead = 0;
  @Override
  public void init() {

    addParallelState(new ChildState());
  }

  @Override
  public void exec() {
    if (runTime() > 5_000) {
      setHasFinished(true);
    }

    updatesSinceLastTelemetryRead++;
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addLine();
    cyberarmEngine.telemetry.addLine("Hello From TopLevelState");
    cyberarmEngine.telemetry.addData("Runtime", runTime());
    cyberarmEngine.telemetry.addData("Updates", updatesSinceLastTelemetryRead);
    cyberarmEngine.telemetry.addData("Finished?", getHasFinished());

    double progress = (runTime() / 5_000.0) * 100.0;
    cyberarmEngine.telemetry.addLine(progressBar(50, progress));

    updatesSinceLastTelemetryRead = 0;
  }
}
