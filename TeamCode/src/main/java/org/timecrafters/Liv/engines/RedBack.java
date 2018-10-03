package org.timecrafters.Liv.engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.Liv.SubEngines.RedBackCenter;
import org.timecrafters.Liv.SubEngines.RedBackLeft;
import org.timecrafters.Liv.SubEngines.RedBackRight;
import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.HardWareConfig;
import org.timecrafters.gfp.engines.autonomous.jewelBump.Red.RedBumpLeft;
import org.timecrafters.gfp.engines.autonomous.jewelBump.Red.RedBumpRight;
import org.timecrafters.gfp.state.cam.ReadCam;
import org.timecrafters.gfp.state.color.ReadColor;
import org.timecrafters.gfp.state.drive.DriveStraightForward;
import org.timecrafters.gfp.state.jewelBump.Beam;
import org.timecrafters.gfp.state.jewelBump.Flipper;
import org.timecrafters.gfp.state.util.Sleep;

/**
 * Created by Liv on 12/5/2017.
 */


@Autonomous(name="Red Back")
public class RedBack extends Engine {




    public void setProcesses() {

        ReadCam readCam = new ReadCam(this);
        hardWareConfig = new HardWareConfig(this);
        addState(hardWareConfig);

        ReadColor readColor = new ReadColor(this,3,5,0);

        addState(readCam);

        addState(new Beam(this, -1, 1500));
        addThreadedState(new Flipper(this, 1, 725, true,500));
        addState(new Beam(this, -1, 2500));

        addState(readColor);

        addSubEngine(new RedBumpLeft(this, readColor));

        addState(new Beam(this, 1, 2500+1700));
        addThreadedState(new Flipper(this, -1, 750, false, 2500));
     //   addThreadedState(new Beam(this, 1, 1700));

        addThreadedState(new DriveStraightForward(this, 0.15, 2300, 250));

        addSubEngine(new RedBackLeft(this, readCam));
        addSubEngine(new RedBackCenter(this, readCam));
        addSubEngine(new RedBackRight(this, readCam));

        }
    }


