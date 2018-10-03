package org.cyberarm.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
//@TeleOp(name = "CyberarmRampedMotor")
public class RampedDrive extends OpMode {
    private DcMotor motor;
    private int encoderTravel = 5000;
    private int encoderOffset = 0;
    private double rampMultiplier = 0.1;
    private long lastUpdateTime;
    private long updateInterval = 250;
    private boolean hasRampedUp, hasTravelled = false;
    private double maxPower = 0.5;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("leftMotor");
        motor.setPower(0.0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void start() {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        if (System.currentTimeMillis()-lastUpdateTime >= updateInterval) {
            if (!hasRampedUp) {
                // Ramp up
                if (motor.getPower() >= maxPower) {
                    motor.setPower(maxPower);
                    encoderOffset = motor.getCurrentPosition();
                    hasRampedUp = true;
                } else {
                    motor.setPower(motor.getPower()+rampMultiplier);
                }

            } else if (!hasTravelled) {
                // Travel
                if (motor.getCurrentPosition()-encoderOffset >= encoderTravel) {
                    hasTravelled = true;
                }

            } else if(hasTravelled && hasRampedUp) {
                // Ramp down
                if (motor.getPower() <= 0.01 || motor.getPower()-rampMultiplier*2 <= 0.01) {
                    motor.setPower(0.0);
                    stop();
                } else {
                    motor.setPower(motor.getPower()-rampMultiplier*2);
                }
            }

            telemetry.addLine("HELO");
            telemetry.addData("Motor Power", motor.getPower());
            telemetry.addData("hasRampedUp", hasRampedUp);
            telemetry.addData("hasTravelled", hasTravelled);
            telemetry.addData("motor position", motor.getCurrentPosition());
            telemetry.addData("realitie position", motor.getCurrentPosition()-encoderOffset);
            telemetry.addData("encoderOffset", encoderOffset);
            telemetry.addData("encoderTravel target", encoderTravel);

            lastUpdateTime = System.currentTimeMillis();
        }

    }
}
