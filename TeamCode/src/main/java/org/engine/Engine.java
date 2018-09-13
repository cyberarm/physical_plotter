package org.engine;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.driver.Driver;
import org.driver.states.Wait;

import java.io.IOException;

/**
 * Created by t420 on 9/29/2016.
 * First successful test was 5:00 6 thur oct 2016
 */
/*

::::::::: ::::::::::: :::        :::
:+:    :+:    :+:     :+:        :+:
+:+    +:+    +:+     +:+        +:+
+#++:++#+     +#+     +#+        +#+
+#+    +#+    +#+     +#+        +#+
#+#    #+#    #+#     #+#        #+#
######### ########### ########## ##########

 */


public abstract class Engine extends OpMode {

    public static Engine instance;
    //Array To Hold States
    public State[][] states = new State[100][100];

    //Array For Holding Threads
    private Thread[] threads = new Thread[100];

    //Keep Track of states X and Y
    private int processesX = 0;
    private int processesY = 0;

    private boolean checkingStates = true;

    private static String TAG = "PROGRAM.ENGINE: ";
    private int processIndex = 0;
    private boolean machineFinished = false;
    private boolean opFinished = true;

    //sets states
    public void init() {
        Engine.instance = this;
        //Call Set Processes to fill arrays with states
        setup();

        //Loop through to states array and initialize states
        for (int i = 0; i < states.length; i++) {
            for (int y = 0; y < states.length; y++) {
                if (states[i][y] != null) {
                    states[i][y].init();
                    Log.i(TAG, "INIT" + "[" + Integer.toString(i) + "]" + "[" + Integer.toString(y) + "]");
                }
            }
        }
    }

    public void start() {
        for (int i = 0; i < states.length; i++) {
            for (int y = 0; y < states.length; y++) {
                if (states[i][y] != null) {
                    states[i][y].start();
                }
            }
        }
    }

    //checks if ops are finished
    public void loop() {

        //check if we are checking states
        if(checkingStates) {
            checkStateFinished();
        }

        for (int x = 0; x < states.length; x++) {
            for (int y = 0; y < states.length; y++) {
                if (states[x][y] != null && !states[x][y].getIsFinished()){
                  try {
                    Driver driver = (Driver) Engine.instance;
                    if (states[x][y].getClass() != Wait.class) {
                      driver.pendingWork = true;
                    }
                  } catch(ClassCastException err) {
                    // Engine is not a Driver
                  }
                    states[x][y].telemetry();
                }
            }
        }
    }

    //kills all states running when program endes
    @Override
    public void stop() {
        //end all states
        for (int x = 0; x < states.length; x++) {
            for (int y = 0; y < states.length; y++) { // NOTE: states.length should probably be states[x].length
                if (states[x][y] != null) {
                    states[x][y].setFinished(true);
                    states[x][y].stop();
                    Log.i(TAG, "KILLED OP : " + "[" + Integer.toString(x) + "]" + "[" + Integer.toString(y) + "]");
                }
            }
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

    public void checkStateFinished(){

        //check to make sure the current state or whole machine isnt finished
        if (!opFinished && !machineFinished) {

            //Loop through to check if all sections of the current
            // state are finished, if so set opFinsished to true
            for (int y = 0; y < states.length; y++) {

                if (states[processIndex][y] != null) {
                    if (states[processIndex][y].getIsFinished()) {
                        opFinished = true;
                        Log.i(TAG, "FINISHED OP : " + "[" + Integer.toString(processIndex) + "]" + "[" + Integer.toString(y) + "]");
                    } else {
                        opFinished = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (opFinished) {
                processIndex++;
            }


        } else {
            //If opmode is finished than set up the next set of states or
            if (states[processIndex][0] != null) {
                //set next state
                for (int i = 0; i < states.length; i++) {
                    threads[i] = new Thread(states[processIndex][i]);
                    threads[i].start();
                }
                opFinished = false;
                Log.i(TAG, "Started State : " + Integer.toString(processIndex));
            }
            else if (states[processIndex][0] == null && !machineFinished) {
                if (Driver.instance != null) {
                    ((Driver) Driver.instance).pendingWork = false;
                    addState(new Wait((Driver) Driver.instance));
                } else {
                    Log.i(TAG, "MACHINE TERMINATED");
                    machineFinished = true;
                    stop();
                }
            }

        }
    }

    //set states in extended classes
    public abstract void setup();

    public int getProcessIndex() {
        return processIndex;
    }

    //adds the ability to add states inside states
    public void addInLineProcess(State state,boolean init) {
        for(int i = 0; i < states.length; i ++){
            if(states[processIndex][i] == null || states[processIndex][i].getIsFinished()){
                if(init){
                    state.init();
                }
                states[processIndex][i] = state;
                Thread thread = new Thread(states[processIndex][i]);
                thread.start();
                Log.i(TAG, "ADDED THREAD AT : " + "[" + Integer.toString(getProcessIndex()) + "]" + "[" + Integer.toString(i) + "]");
                break;
            }
        }

    }

    //For adding states when setup is called
    public void addState(State state){

        processesY = 0;

        states[processesX][processesY] = state;

        processesY++;
        processesX++;

        Log.i(TAG, "ADDED NEW STATE AT : " + Integer.toString(processesX) );

    }

    public void addThreadedState(State state){
        states[processesX -1][processesY] = state;
        processesY++;
    }
}
