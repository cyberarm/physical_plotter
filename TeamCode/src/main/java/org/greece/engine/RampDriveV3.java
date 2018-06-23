package org.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "RampDriveV3")
public class RampDriveV3 extends OpMode {
    private DcMotor motor;
    private long timeToRampMS = 5000;
    private long startTimeMs;
    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("leftMotor");
        motor.setPower(0.0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void start() {
        startTimeMs = System.currentTimeMillis();
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        double power = ((double) (System.currentTimeMillis()-startTimeMs) / (double) timeToRampMS);
        motor.setPower(power);
    }
}
