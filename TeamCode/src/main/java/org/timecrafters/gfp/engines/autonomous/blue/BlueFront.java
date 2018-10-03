package org.timecrafters.gfp.engines.autonomous.blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.state.drive.DriveStraightForward;
import org.timecrafters.gfp.state.ultrasonic.UltraSonic;

/**
 * Created by t420 on 11/10/2017.
 */

@Autonomous(name="Blue Front")
public class BlueFront extends Engine {

    public void setProcesses(){

        UltraSonic ultraSonic = new UltraSonic(this,6);
        DriveStraightForward driveStraightForward = new DriveStraightForward(this, 0.5, 800);
        driveStraightForward.runUntillStateFinished(ultraSonic);

        addState(ultraSonic);
        addThreadedState(driveStraightForward);

        addState(new DriveStraightForward(this, 0.5,800));


    }

}
