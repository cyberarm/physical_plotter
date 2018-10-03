package org.timecrafters.Darcshadowz.Engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.Darcshadowz.SubEngines.Red.RedFrontCenterColumn;
import org.timecrafters.Darcshadowz.SubEngines.Red.RedFrontLeftColumn;
import org.timecrafters.Darcshadowz.SubEngines.Red.RedFrontRightColumn;
import org.timecrafters.Liv.SubEngines.BlueBumpRight;
import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.HardWareConfig;
import org.timecrafters.gfp.engines.autonomous.jewelBump.Red.RedBumpLeft;
import org.timecrafters.gfp.engines.autonomous.jewelBump.Red.RedBumpRight;
import org.timecrafters.gfp.state.arm.ExtendArm;
import org.timecrafters.gfp.state.arm.RaiseArm;
import org.timecrafters.gfp.state.cam.ReadCam;
import org.timecrafters.gfp.state.color.ReadColor;
import org.timecrafters.gfp.state.drive.DriveStraightBackward;
import org.timecrafters.gfp.state.drive.DriveStraightForward;
import org.timecrafters.gfp.state.drive.TurnLeft;
import org.timecrafters.gfp.state.drive.TurnRight;
import org.timecrafters.gfp.state.grabber.LeftGrabber;
import org.timecrafters.gfp.state.jewelBump.Beam;
import org.timecrafters.gfp.state.jewelBump.Flipper;
import org.timecrafters.gfp.state.util.Sleep;

/**
 * Created by Dylan on 11/14/2017.
 */


@Autonomous(name = "DYLAN RED FRONT")
public class DarcShadowzTestEngineRed extends Engine {

    private ReadCam readCam;

    public void setProcesses() {

        // Read Pictograph and determine left, center, right
        readCam = new ReadCam(this);
        hardWareConfig = new HardWareConfig(this);
        addState(hardWareConfig);
        addState(readCam);

        ReadColor readColor = new ReadColor(this, 3, 5, 0);

        // Beam down, flipper out, beam continues down
        addState(new Beam(this, -1, 1500));
        addThreadedState(new Flipper(this, 1, 725, true, 500));
        addState(new Beam(this, -1, 2500));

        // Color sensor reads red/blue, bump left or right
        addState(readColor);
        addSubEngine(new RedBumpLeft(this, readColor));

        // Beam up, small delay before flipper in and drive off balancing stone
        addState(new Beam(this, 1, 2500+1700));
        addThreadedState(new Flipper(this, -1, 750, false, 2500));
        addThreadedState(new DriveStraightForward(this, 0.15, 2300, 250));

        // Turn Left, Drive Forward
        addState(new TurnLeft(this, 0.3, 1000));
        addState(new DriveStraightForward(this, 0.50, 2900));

        // Run SubEngine left, center, right
        addSubEngine(new RedFrontCenterColumn(this, readCam));
        addSubEngine(new RedFrontRightColumn(this, readCam));
        addSubEngine(new RedFrontLeftColumn(this, readCam));

    }

}