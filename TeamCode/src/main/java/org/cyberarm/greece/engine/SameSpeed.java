package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Disabled
//@TeleOp(name = "SameSpeed")
public class SameSpeed extends OpMode {
    private DcMotor leftMotor, rightMotor;
    private DigitalChannel button;
    private ColorSensor colorSensor;
    private long lastLoopTime;

    @Override
    public void init() {
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        button = hardwareMap.digitalChannel.get("button");
        colorSensor = hardwareMap.colorSensor.get("colorSensor");

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        button.setMode(DigitalChannel.Mode.INPUT);
    }

    @Override
    public void start() {
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftMotor.setPower(0.4);
        lastLoopTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        if (System.currentTimeMillis() - lastLoopTime >= 3000) {
            if (leftMotor.getCurrentPosition() > rightMotor.getCurrentPosition()) {
                rightMotor.setPower(rightMotor.getPower()+0.1);
            } else if (leftMotor.getCurrentPosition() < rightMotor.getCurrentPosition()) {
                rightMotor.setPower(rightMotor.getPower()-0.1);

            }

            lastLoopTime = System.currentTimeMillis();
        }

        telemetry.addData("leftMotor Power", leftMotor.getPower());
        telemetry.addData("rightMotor Power", rightMotor.getPower());
        telemetry.addData("leftMotor Encoder", leftMotor.getCurrentPosition());
        telemetry.addData("rightMotor Encoder", rightMotor.getCurrentPosition());
        telemetry.addData("Button", button.getState());
    }
}
