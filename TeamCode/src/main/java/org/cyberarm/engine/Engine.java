package org.cyberarm.engine;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.cyberarm.driver.Driver;
import org.cyberarm.driver.states.Wait;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by t420 on 9/29/2016.
 * First successful test was 5:00 6 thur oct 2016
 */

public abstract class Engine extends OpMode {

  public static Engine instance;
  public static Driver driverStatic = null;
  public Driver driver = null;
  //Array To Hold States
  public ArrayList<State> states = new ArrayList<>();
  public int activeStateIndex = 0;


  //Array For Holding Threads
  private ArrayList<Thread> threads = new ArrayList<>();

  private static String TAG = "PROGRAM.ENGINE: ";

  //sets states
  public void init() {
    Engine.instance = this;

    //Call Set Processes to fill arrays with states
    setup();

    //Loop through states array and initialize states
    for (int i = 0; i < states.size(); i++) {
      if (states.get(i) != null) {
        states.get(i).init();
      }
    }
  }

  public void start() {
    for (int i = 0; i < states.size(); i++) {
      if (states.get(i) != null) {
        states.get(i).start();
      }
    }
  }

  //checks if ops are finished
  public void loop() {
    State state = null;
    try {
      state = states.get(activeStateIndex);
    } catch (IndexOutOfBoundsException e) {
      if (driver == null) {
        telemetry.addData("[ENGINE] No state", "State at " + activeStateIndex + " is OutOfBounds");
        stop();
      } else {
        telemetry.addData("[ENGINE] Waiting...", "Awaiting state from server.");
      }
    }

    if (state != null) {
      if (!state.getIsFinished()) {
        telemetry.addData("[ENGINE] Running State", "" + state.getClass());
        telemetry.addLine("[ENGINE] State "+activeStateIndex+" of "+states.size());

        state.exec();
        state.telemetry();
      } else {
        telemetry.addData("[ENGINE] Next", "State");
        activeStateIndex++;
        setupNextState();
      }
    } else {
      telemetry.addData("[ENGINE] NOTICE", "No active state!");
    }

    try {
      if (states.get(activeStateIndex) != null && !states.get(activeStateIndex).getIsFinished()) {
        if (driver != null && driver.getClass() == Driver.class) {
          try {
            if (states.get(activeStateIndex + 1) != null && states.get(activeStateIndex + 1).getClass() != Wait.class) {
              driver.pendingWork = true;
            } else {

            }
          } catch (IndexOutOfBoundsException e) {
          }
        }

      }
    } catch (IndexOutOfBoundsException err) {
    }
  }


  private void setupNextState() {
    try {
      states.get(activeStateIndex).init();
    } catch (IndexOutOfBoundsException e) {
      Log.i(TAG, "No state at " + activeStateIndex);
    }
  }

  //kills all states running when program endes
  @Override
  public void stop() {
    for (int i = 0; i < states.size(); i++) {
      states.get(i).stop();
    }

    // Dump server shutdown into a thread because the FTC stop() times out REALLY fast.
    new Thread(new Runnable() {
      @Override
      public void run() {
        if (driver != null) {
          Support.puts("Driver", "Stopping server...");
          try {
            if (driver.server != null) {
              driver.server.stop();
              Support.puts("Driver", "Stopped server.");
            }
          } catch (IOException e) {
            Support.puts("Driver", "Failed to stop server!");
            e.printStackTrace();
          }
        }
      }
    }).start();

  }

  //set states in extended classes
  public abstract void setup();

  //For adding states when setup is called
  public void addState(State state) {
    Log.i(TAG, "Adding state "+state.getClass());
    states.add(state);
  }
}
