package org.timecrafters.temptingreality.engines;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.timecrafters.engine.Engine;
import org.timecrafters.temptingreality.states.PlotterDriveGamepadControls;
import org.timecrafters.temptingreality.states.PlotterDriveMotor1;
import org.timecrafters.temptingreality.states.PlotterDriveMotor2;

/**
 * Created by t420-1 on 5/5/2018.
 */
@TeleOp(name = "plotter test")
public class PlotterEngine extends Engine {

    @Override
    public void setProcesses() {
        addState(new PlotterDriveMotor1(this,1));
        addThreadedState(new PlotterDriveMotor2(this,10));
        addThreadedState(new PlotterDriveGamepadControls(this));
    }
}
