package org.timecrafters.gfp.state;

import android.util.Log;

import org.timecrafters.engine.State;
import org.timecrafters.gfp.config.Config;

/**
 * Created by t420 on 11/4/2017.
 */

public class TestState extends State {

    int i = 0;

    private volatile boolean burger = false;

    String output;

    public TestState(String output){
        this.output = output;
    }
    public TestState(){
        this.output ="TestState";
    }

    public void init(){
        Log.i(TAG, "MADE IT TO INIT");
    }

    public void exec(){
        Log.i(TAG,output);
        setFinished(true);
    }

    public boolean isBurger() {
        return burger;
    }
}
