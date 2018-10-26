package org.cyberarm.engine.V2;

import java.util.ArrayList;

/**
 * Created by t420 on 9/29/2016.
 */


public abstract class CyberarmStateV2 implements Runnable {

  private volatile boolean hasFinished, isRunning;
  public static String TAG = "PROGRAM.STATE";
  public org.cyberarm.engine.V2.CyberarmEngineV2 cyberarmEngine = CyberarmEngineV2.instance;
  public ArrayList<CyberarmStateV2> children;

  protected CyberarmStateV2() {
    hasFinished = false;
    isRunning  = false;

    children   = new ArrayList<>();
  }


  // Called when INIT button on Driver Station is pushed
  public void init() {
  }

  // Called when state has begin to run
  public void start() {
  }

  public abstract void exec();

  @Override
  public void run() {
    while (!hasFinished) {
      exec();
    }
  }

  public void telemetry() {
  }

  public void stop() {
    /*
     * Override this and put your ending stuff in here
     * */
  }

  // Add a state which runs in parallel to this one
  public void addParallelState(CyberarmStateV2 state) {
    children.add(state);
  }

  public boolean hasChildren() {
    return children.size() > 0;
  }

  public boolean childrenHaveFinished() {
    return childrenHaveFinished(children);
  }

  public boolean childrenHaveFinished(ArrayList<CyberarmStateV2> kids) {
    boolean allDone = true;

      for (CyberarmStateV2 state : kids) {
        if (!state.hasFinished) {
          allDone = false;
          break;
        } else {
          if (!state.childrenHaveFinished()) {
            allDone = false;
            break;
          }
        }
      }

    return allDone;
  }

  public void setHasFinished(boolean value) {
    hasFinished = value;
  }

  public boolean getHasFinished() {
    return hasFinished;
  }

  public void setIsRunning(boolean value) {
    isRunning = value;
  }

  public boolean getIsRunning() {
    return isRunning;
  }

  public void sleep(long timems) {
    try {
      Thread.sleep(timems);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public String progressBar(int width, double percentCompleted, String bar, String padding) {
    String percentCompletedString = "" + Math.round(percentCompleted) + "%";
    double activeWidth = (width - 2) - percentCompletedString.length();

    String string = "[";
    double completed = (percentCompleted / 100.0) * activeWidth;

    for (int i = 0; i <= ((int) activeWidth); i++) {
      if (i == ((int) activeWidth) / 2) {
        string += percentCompletedString;
      } else {
        if (i <= (int) completed && (int) completed > 0) {
          string += bar;
        } else {
          string += padding;
        }
      }
    }

    string += "]";
    return string;
  }

  public String progressBar(int width, double percentCompleted) {
    return progressBar(width, percentCompleted, "=", "  ");
  }
}