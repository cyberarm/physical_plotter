package org.timecrafters.temptingreality.states;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by t420-1 on 8/4/2018.
 */

public class PlotterDriveGamepadControls extends State {
    private DcMotor motor1,motor2;
    private boolean firstRun;
    public PlotterDriveGamepadControls(Engine engine) {this.engine = engine;

    }

    @Override
    public void init() {
     motor1 = engine.hardwareMap.dcMotor.get("motor1");
     motor2 = engine.hardwareMap.dcMotor.get("motor2");
     firstRun = true;
    }

    @Override
    public void exec() throws InterruptedException {
        engine.telemetry.addData("first run",firstRun);
        engine.telemetry.update();

        if (firstRun == true){
            sleep(10000);
            firstRun = false;
        }
        motor1.setPower(engine.gamepad1.right_stick_x/5);
        motor2.setPower(engine.gamepad1.right_stick_y/5);
    }
}
