package org.timecrafters.Liv.engines;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.timecrafters.Darcshadowz.State.RightGrabber;
import org.timecrafters.Liv.SubEngines.BlueBackCenter;
import org.timecrafters.Liv.SubEngines.BlueBackLeft;
import org.timecrafters.Liv.SubEngines.BlueBackRight;
import org.timecrafters.Liv.SubEngines.BlueBumpLeft;
import org.timecrafters.Liv.SubEngines.BlueBumpRight;
import org.timecrafters.Liv.SubEngines.RedBackCenter;
import org.timecrafters.Liv.SubEngines.RedBackLeft;
import org.timecrafters.Liv.SubEngines.RedBackRight;
import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.HardWareConfig;
import org.timecrafters.gfp.state.arm.ExtendArm;
import org.timecrafters.gfp.state.arm.RaiseArm;
import org.timecrafters.gfp.state.cam.ReadCam;
import org.timecrafters.gfp.state.color.ReadColor;
import org.timecrafters.gfp.state.drive.DriveStraightBackward;
import org.timecrafters.gfp.state.drive.DriveStraightForward;
import org.timecrafters.gfp.state.drive.TurnLeft;
import org.timecrafters.gfp.state.drive.TurnRight;
import org.timecrafters.gfp.state.jewelBump.Beam;
import org.timecrafters.gfp.state.jewelBump.Flipper;
import org.timecrafters.gfp.state.util.Sleep;

/**
 * Created by Liv on 12/21/2017.
 */

  @Autonomous(name="Blue Back")
  public class BlueBack extends Engine {



    ReadCam readCam;

    public void setProcesses() {

        readCam = new ReadCam(this);
        hardWareConfig = new HardWareConfig(this);
        addState(hardWareConfig);

        ReadColor readColor = new ReadColor(this,3,5,0);

        addState(readCam);

        addState(new Beam(this, -1, 1500));
        addThreadedState(new Flipper(this, 1, 700, true,500)); //726 is to much
        addState(new Beam(this, -1, 2500));

        addState(readColor);

        addSubEngine(new BlueBumpLeft(this, readColor));

        addState(new Beam(this, 1, 2500+1700));
        addThreadedState(new Flipper(this, -1, 750, false, 2500));
      //  addThreadedState(new Beam(this, 1, 1700));

        addThreadedState(new DriveStraightForward(this, -0.3, 1700, 250));

        addSubEngine(new BlueBackCenter(this,readCam));
        addSubEngine(new BlueBackRight(this,readCam));
        addSubEngine(new BlueBackLeft(this,readCam));


    }

    }











