package org.timecrafters.gfp.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.HardWareConfig;
import org.timecrafters.gfp.state.TestState;
import org.timecrafters.gfp.state.cam.ReadCam;
import org.timecrafters.gfp.state.color.ReadColor;
import org.timecrafters.gfp.state.jewelBump.Beam;
import org.timecrafters.gfp.state.jewelBump.Flipper;
import org.timecrafters.gfp.state.util.Sleep;
import org.timecrafters.gfp.subEngine.TestCamCenter;
import org.timecrafters.gfp.subEngine.TestCamLeft;
import org.timecrafters.gfp.subEngine.TestCamRight;
import org.timecrafters.gfp.subEngine.TestSubEngine;


/**
 * Created by t420 on 9/14/2017.
 */
@TeleOp(name = "Test")
public class TestEngine extends Engine {


    @Override
    public void setProcesses(){
        hardWareConfig = new HardWareConfig(this);
        addState(hardWareConfig);

        addState(new TestState("state 1"));
        addState(new TestState("state 2"));

        addSubEngine(new TestSubEngine("state 3"));
        addSubEngine(new TestSubEngine("state 3.5"));

        addState(new TestState("state 4"));
        addState(new TestState("state 5"));

        addSubEngine(new TestSubEngine("state 6"));

        addState(new TestState("state 7"));
        addSubEngine(new TestSubEngine("state 8"));
    }

}
