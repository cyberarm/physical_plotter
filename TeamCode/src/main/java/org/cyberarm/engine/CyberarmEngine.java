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

public abstract class CyberarmEngine extends OpMode {

  public static CyberarmEngine instance;
  public static Driver driverStatic = null;
  public Driver driver = null;
  //Array To Hold States
  public ArrayList<CyberarmState> cyberarmStates = new ArrayList<>();
  public int activeStateIndex = 0;


  //Array For Holding Threads
  private ArrayList<Thread> threads = new ArrayList<>();

  private static String TAG = "PROGRAM.ENGINE: ";

  //sets cyberarmStates
  public void init() {
    CyberarmEngine.instance = this;

    //Call Set Processes to fill arrays with cyberarmStates
    setup();

    //Loop through cyberarmStates array and initialize cyberarmStates
    for (int i = 0; i < cyberarmStates.size(); i++) {
      if (cyberarmStates.get(i) != null) {
        cyberarmStates.get(i).init();
      }
    }
  }

  public void start() {
    for (int i = 0; i < cyberarmStates.size(); i++) {
      if (cyberarmStates.get(i) != null) {
        cyberarmStates.get(i).start();
      }
    }
  }

  //checks if ops are finished
  public void loop() {
    CyberarmState cyberarmState = null;
    try {
      cyberarmState = cyberarmStates.get(activeStateIndex);
    } catch (IndexOutOfBoundsException e) {
      if (driver == null) {
        telemetry.addData("[ENGINE] No cyberarmState", "CyberarmState at " + activeStateIndex + " is OutOfBounds");
        stop();
      } else {
        telemetry.addData("[ENGINE] Waiting...", "Awaiting cyberarmState from server.");
      }
    }

    if (cyberarmState != null) {
      if (!cyberarmState.getIsFinished()) {
        telemetry.addData("[ENGINE] Running CyberarmState", "" + cyberarmState.getClass());
        telemetry.addLine("[ENGINE] CyberarmState "+activeStateIndex+" of "+ cyberarmStates.size());

        cyberarmState.exec();
        cyberarmState.telemetry();
      } else {
        telemetry.addData("[ENGINE] Next", "CyberarmState");
        activeStateIndex++;
        setupNextState();
      }
    } else {
      telemetry.addData("[ENGINE] NOTICE", "No active cyberarmState!");
    }

    try {
      if (cyberarmStates.get(activeStateIndex) != null && !cyberarmStates.get(activeStateIndex).getIsFinished()) {
        if (driver != null && driver.getClass() == Driver.class) {
          try {
            if (cyberarmStates.get(activeStateIndex + 1) != null && cyberarmStates.get(activeStateIndex + 1).getClass() != Wait.class) {
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
      cyberarmStates.get(activeStateIndex).init();
    } catch (IndexOutOfBoundsException e) {
      Log.i(TAG, "No state at " + activeStateIndex);
    }
  }

  //kills all cyberarmStates running when program endes
  @Override
  public void stop() {
    for (int i = 0; i < cyberarmStates.size(); i++) {
      cyberarmStates.get(i).stop();
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

  //set cyberarmStates in extended classes
  public abstract void setup();

  //For adding cyberarmStates when setup is called
  public void addState(CyberarmState cyberarmState) {
    Log.i(TAG, "Adding cyberarmState "+ cyberarmState.getClass());
    cyberarmStates.add(cyberarmState);
  }
}
