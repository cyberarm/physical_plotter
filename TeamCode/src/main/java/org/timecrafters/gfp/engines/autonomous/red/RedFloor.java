package org.timecrafters.gfp.engines.autonomous.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.state.drive.DriveStraightBackward;
import org.timecrafters.gfp.state.drive.DriveStraightForward;
import org.timecrafters.gfp.state.drive.TurnRight;

/**
 * Created by t420 on 11/2/2017.
 */


@Autonomous(name = "Red Floor")
public class    RedFloor extends Engine {

    public void setProcesses(){

        addState(new DriveStraightForward(this,0.5,1500));
        addState(new TurnRight(this, 0.5, 300));
        addState(new DriveStraightBackward(this, -0.5,300));
        /*addState(new ExtendArm(this,1.0, 1550));
        addState(new RaiseArm(this, 1.0, 1950));
        addState(new ExtendArm(this,1.0, 1550));
        addState(new RaiseArm(this,-0.5,1950));
        addState(new LeftGrabber(this,0.5,500));*/


    }

}
