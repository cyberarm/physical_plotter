package org.timecrafters.gfp.state.color;

import android.util.Log;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

/**
 * Created by goldfishpi on 12/9/17.
 */

public class ReadColor extends Config {

    int reads;
    int currentReads = 0;
    int colorIntensity;

    double blueAverage = 0;
    double redAverage = 0;
    int incrament;

    int sleepms;

    public ReadColor(Engine engine,int reads,int colorIntensity,int sleepms) {
        super(engine);
        this.reads = reads;
        this.colorIntensity = colorIntensity;
        this.sleepms = sleepms;
    }

    public void init(){
        super.init();
        colorSensor.enableLed(true);
    }

    public void exec(){
        engine.telemetry.addData("ColorSensor", "Blue "+getBlueAverage()+" Red: "+getRedAverage());
        if(currentReads <= reads){

            blueAverage += colorSensor.blue();
            redAverage += colorSensor.red();
            currentReads++;

            sleep(sleepms);

        }else{
            blueAverage/=reads;
            redAverage/=reads;

            setFinished(true);
        }
    }

    public double getBlueAverage() {
        return blueAverage;

    }

    public double getRedAverage() {
        Log.i(TAG,Double.toString(redAverage));
        return redAverage;
    }
}
