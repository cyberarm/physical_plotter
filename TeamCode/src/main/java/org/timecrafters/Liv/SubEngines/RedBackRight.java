package org.timecrafters.Liv.SubEngines;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.timecrafters.Darcshadowz.State.RightGrabber;
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
 * Created by Liv on 12/21/2017.
 */

public class RedBackRight  extends SubEngine{
    Engine engine;
    ReadCam readCam;

    public RedBackRight (Engine engine, ReadCam readCam) {
        this.engine=engine;
        this.readCam = readCam;
    }

    @Override
    public void setProcesses() {
        //Right glyph goal
        addState(new TurnRight(engine, 0.3, 900));
        addState(new RaiseArm(engine, 1, 500));
        addThreadedState(new DriveStraightBackward(engine, 0.5, 800));
        addState(new ExtendArm(engine, 0.5, 2000));
        addState(new DriveStraightForward(engine, 0.5, 300));
        addState(new RaiseArm(engine, -1, 500));
        addState(new LeftGrabber(engine, 0.5, 500));
        addState(new RaiseArm(engine, 1, 1950));
        addState(new DriveStraightBackward(engine,   0.5, 500));
        addState(new TurnRight(engine, 0.3, 1800));
        addState(new DriveStraightBackward(engine, 0.3, 1200));
        addState(new DriveStraightForward(engine, 0.3, 100));
        addState(new RaiseArm(engine, -1, 1950));

    }

    @Override
    public void evaluate() {
        if (readCam.getVuMark() == RelicRecoveryVuMark.RIGHT || readCam.getVuMark() == RelicRecoveryVuMark.UNKNOWN) {
            setRunable(true);
        }
    }}