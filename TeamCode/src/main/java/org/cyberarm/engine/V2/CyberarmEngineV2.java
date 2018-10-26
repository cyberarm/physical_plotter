package org.cyberarm.engine.V2;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

public abstract class CyberarmEngineV2 extends OpMode {

  public static CyberarmEngineV2 instance;
  //Array To Hold States
  public ArrayList<CyberarmStateV2> cyberarmStates = new ArrayList<>();
  public int activeStateIndex = 0;

  private static String TAG = "PROGRAM.ENGINE: ";

  //sets cyberarmStates
  public void init() {
    CyberarmEngineV2.instance = this;

    setup();

    for (CyberarmStateV2 state: cyberarmStates) {
      initState(state);
    }
  }

  // Called when
  public void start() {
    if (cyberarmStates.size() > 0) {
      runState(cyberarmStates.get(0));
    }
  }

  public void loop() {
    CyberarmStateV2 state;

    try {
       state = cyberarmStates.get(activeStateIndex);
    } catch(IndexOutOfBoundsException e) {
      // The engine is now out of states.
      stop();

      telemetry.addLine("" + this.getClass() + " is out of states to run!");
      telemetry.addLine();
      return;
    }
    telemetry.addLine("Running state: " + activeStateIndex + " of " + (cyberarmStates.size()-1));
    telemetry.addLine();

    if (state.getHasFinished() && state.childrenHaveFinished()) {
      runState(state);
      activeStateIndex++;
    } else {
      stateTelemetry(state);
    }
  }

  private void stateTelemetry(CyberarmStateV2 state) {
    if (!state.getHasFinished()) {
      state.telemetry();
    }

    for(CyberarmStateV2 childState : state.children) {
      if (!childState.getHasFinished()) {
        stateTelemetry(childState);
      }
    }
  }

  // Called when INIT button on Driver Station is pressed
  // Recursively initiates states
  private void initState(CyberarmStateV2 state) {
    state.init();

    for(CyberarmStateV2 childState : state.children) {
      initState(childState);
    }
  }

  // Called when programs ends or STOP button on Driver Station is pressed
  // Recursively stop states
  private void stopState(CyberarmStateV2 state) {
    state.stop();

    for(CyberarmStateV2 childState : state.children) {
      stopState(childState);
    }
  }

  // Recursively start up states
  private void runState(CyberarmStateV2 state) {
    final CyberarmStateV2 finalState = state;
    state.startTime = System.currentTimeMillis();

    new Thread(new Runnable() {
      @Override
      public void run() {
        finalState.run();
      }
    }).start();

    state.start();

    for (CyberarmStateV2 kid : state.children) {
      runState(kid);
    }
  }

  //kills all cyberarmStates running when program ends
  @Override
  public void stop() {
    for (CyberarmStateV2 state: cyberarmStates) {
      stopState(state);
    }
  }


  //set cyberarmStates in extended classes
  public abstract void setup();

  //For adding cyberarmStates when setup is called
  public void addState(CyberarmStateV2 state) {
    Log.i(TAG, "Adding cyberarmState "+ state.getClass());
    cyberarmStates.add(state);
  }

  // For dynamically adding states after main loop has started (Robot is active)
  public void addStateAtRuntime(CyberarmStateV2 state) {
    Log.i(TAG, "Adding cyberarmState (AT RUNTIME) "+ state.getClass());

    cyberarmStates.add(state);
    state.init();
  }
}
