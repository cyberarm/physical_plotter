package org.timecrafters;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by t420 on 9/7/2017.
 */

public class BlankState extends State {

    public BlankState(Engine engine){
        //If you have a config
        //super(engine);

        //If you dont have a config
        this.engine = engine;
    }

    public void init(){
        //Put code in that starts when init is pressed
    }

    public void exec(){
        //Put in code which runs durring main loop
        setFinished(true);
    }

}
