package org.timecrafters.Darcshadowz.SubEngines.Red;

import android.support.annotation.RequiresPermission;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.timecrafters.engine.Engine;
import org.timecrafters.engine.SubEngine;
import org.timecrafters.gfp.state.arm.ExtendArm;
import org.timecrafters.gfp.state.arm.RaiseArm;
import org.timecrafters.gfp.state.cam.ReadCam;
import org.timecrafters.gfp.state.drive.DriveStraightBackward;
import org.timecrafters.gfp.state.drive.DriveStraightForward;
import org.timecrafters.gfp.state.drive.TurnLeft;
import org.timecrafters.gfp.state.drive.TurnRight;
import org.timecrafters.gfp.state.grabber.LeftGrabber;

/**
 * Created by Dylan on 12/9/2017.
 */

public class RedFrontRightColumn extends SubEngine {
    Engine engine;
    ReadCam readCam;

    public RedFrontRightColumn(Engine engine, ReadCam readCam) {
        this.engine = engine;
        this.readCam = readCam;
    }

    @Override
    public void setProcesses() {

        addState(new TurnRight(engine,0.3,1750));
        addState(new RaiseArm(engine, 1, 500));
        addThreadedState(new DriveStraightForward(engine, 0.5, 1300));
        addThreadedState(new ExtendArm(engine, 1, 2800));
        addState(new RaiseArm(engine, -1, 500));
        addState(new LeftGrabber(engine, 0.5, 500));
        addState(new RaiseArm(engine, 1, 1950));
        addState(new DriveStraightBackward(engine, 0.5, 700));
        addState(new TurnRight(engine, 0.3, 1750));
        addState(new DriveStraightBackward(engine, 0.5, 700));
        addState(new DriveStraightForward(engine, 0.5, 100));
        addThreadedState(new RaiseArm(engine,-1, 1950));

    }

    @Override
    public void evaluate() {
    //    setPreInit(true);
        if (readCam.getVuMark() == RelicRecoveryVuMark.RIGHT) {
            setRunable(true);
        }
    }
}
