package org.timecrafters.gfp.subEngine;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;
import org.timecrafters.gfp.state.cam.ReadCam;
import org.timecrafters.gfp.state.drive.DriveStraightForward;
import org.timecrafters.gfp.state.drive.TurnLeft;

/**
 * Created by goldfishpi on 1/15/18.
 */

public class TestCamLeft extends SubEngine {

    Engine engine;
    ReadCam readCam;

    public TestCamLeft(Engine engine, ReadCam readCam) {
        this.engine = engine;
        this.readCam = readCam;

    }

    @Override
    public void setProcesses() {
        addState(new TurnLeft(engine,1.0,1000));
    }

    @Override
    public void evaluate() {
        if(readCam.getVuMark() == RelicRecoveryVuMark.LEFT){
            setRunable(true);
        }
    }

}
