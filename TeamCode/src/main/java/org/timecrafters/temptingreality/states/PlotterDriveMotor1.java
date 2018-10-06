package org.timecrafters.temptingreality.states;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.timecrafters.engine.Engine;
import org.timecrafters.engine.State;

/**
 * Created by t420-1 on 7/11/2018.
 */

public class PlotterDriveMotor1 extends State {
    private int ticks;
    public TouchSensor pushButton1;
    public boolean firstRun,targetPosition;
    public DcMotor plotterMotor1;
    public PlotterDriveMotor1(Engine engine, int ticks)


    {
        this.engine = engine;
        this.ticks = ticks;
    }

    @Override
    public void init() {
        super.init();
        plotterMotor1 = engine.hardwareMap.dcMotor.get("motor1");
        plotterMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pushButton1 = engine.hardwareMap.touchSensor.get("pushbutton1");
        firstRun = true;
        targetPosition = false;

    }

    @Override
    public void exec() {
        engine.telemetry.addData("1 pressed", pushButton1.getValue());
        engine.telemetry.addData("first run one", firstRun);
        engine.telemetry.update();

        if (firstRun == true) {
            plotterMotor1.setPower(-0.2);
            if (pushButton1.isPressed()) {
                plotterMotor1.setPower(0);
                plotterMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                plotterMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                firstRun = false;
                targetPosition = true;
            }

         }
         if (targetPosition = true){
             plotterMotor1.setPower(.1);
             plotterMotor1.setTargetPosition(150);
             if (plotterMotor1.getCurrentPosition() == 150){
                 plotterMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                 plotterMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                 targetPosition = false;
             }
         }
    }
}