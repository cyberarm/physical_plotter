package org.timecrafters.temptingreality.subEngine;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;

/**
 * Created by t420-1 on 8/4/2018.
 */

public class plotterDriveSubEngine extends SubEngine {
    Engine engine;

    public plotterDriveSubEngine(Engine engine) {
        this.engine = engine;

    }

    @Override
    public void setProcesses() {
        //addState(new PlotterDriveMotor1(engine,10));
        //addState(new PlotterDriveMotor2(engine,10));


    }

    @Override
    public void evaluate() {
        if (engine.gamepad1.a == true){
            setRunable(true);

        }else{
            setRunable(false);
        }


    }
}
