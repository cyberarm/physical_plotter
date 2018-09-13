package org.engine;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.driver.Driver;
import org.driver.states.Wait;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by t420 on 9/29/2016.
 * First successful test was 5:00 6 thur oct 2016
 */

public abstract class Engine extends OpMode {

    public static Engine instance;
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
        telemetry.addData("No state", "State at "+activeStateIndex+" is OutOfBounds");
      }

      if (state != null) {
        if ( !state.getIsFinished()) {
          telemetry.addData("Running State", ""+state.getClass());

          state.exec();
          state.telemetry();
        } else {
          telemetry.addData("Next", "State");
          activeStateIndex++;
        }
      } else {
        telemetry.addData("NOTICE", "No active state!");
      }
//      for (int i = 0; i < states.size(); i++) {
//        if (states.get(i) != null && !states.get(i).getIsFinished()) {
//          try {
//            Driver driver = (Driver) Engine.instance;
//            if (states.get(i).getClass() == Wait.class) {
//              states.get(i).exec();
//              states.get(i).telemetry();
//
//
//              try {
//                if (states.get(i+1) != null && states.get(i+1).getClass() != Wait.class) {
//                  driver.pendingWork = true;
//                }
//              } catch (IndexOutOfBoundsException e) {}
//
//              break;
//            } else {
//              states.get(i).exec();
//              states.get(i).telemetry();
//              break;
//            }
//          } catch(ClassCastException err) {
//            // Behave Like A Normal State
//            states.get(i).exec();
//            states.get(i).telemetry();
//            break;
//          }
//
//        } else {
//          try {
//            if (Driver.instance != null) {
//              ((Driver) Driver.instance).pendingWork = false;
//              addState(new Wait((Driver) Driver.instance));
//            } else {
//              stop();
//            }
//          } catch (ClassCastException e) {
//          }
//        }
//      }
    }

    //kills all states running when program endes
    @Override
    public void stop() {
      for(int i = 0; i < states.size(); i++) {
        states.get(i).stop();
      }

      // Dump server shutdown into a thread because the FTC stop() times out REALLY fast.
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            Driver driver = (Driver) Engine.instance;
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
          } catch(ClassCastException err) {
              // Engine is not a Driver
          }
        }
      }).start();

    }

    //set states in extended classes
    public abstract void setup();

    //For adding states when setup is called
    public void addState(State state) {
      states.add(state);
    }
}
