package org.timecrafters.temptingreality.states;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by t420-1 on 7/11/2018.
 */

public class PlotterDriveMotor2 extends State {
    private int ticks;
    public TouchSensor pushButton2;
    public boolean firstRun2,targetPosition2;
    public DcMotor plotterMotor2;
    public PlotterDriveMotor2(Engine engine, int ticks)


    {
        this.engine = engine;
        this.ticks = ticks;
    }

    @Override
    public void init() {
        super.init();
        plotterMotor2 = engine.hardwareMap.dcMotor.get("motor2");
        plotterMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pushButton2 = engine.hardwareMap.touchSensor.get("pushbutton2");
        firstRun2 = true;
    }

    @Override
    public void exec() {
    }
}


