package org.timecrafters.Darcshadowz.SubEngines.Logic.Blue;

import android.support.annotation.RequiresPermission;

import org.timecrafters.Liv.SubEngines.BlueBackCenter;
import org.timecrafters.Liv.SubEngines.BlueBackLeft;
import org.timecrafters.Liv.SubEngines.BlueBackRight;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;
import org.timecrafters.gfp.state.cam.ReadCam;

/**
 * Created by Liv on 1/15/2018.
 */

public class BlueBackLogic extends SubEngine {

    ReadCam readCam;
    Engine engine;
    public BlueBackLogic(Engine engine, ReadCam readCam){
        this.readCam = readCam;
        this.engine = engine;
    }

    @Override
    public void setProcesses() {

    }

    @Override
    public void evaluate() {
        setRunable(true);
    }
}
