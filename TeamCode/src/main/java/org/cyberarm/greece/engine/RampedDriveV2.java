package org.cyberarm.greece.engine;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "CyberarmRampedMotorV2")
public class RampedDriveV2 extends OpMode {
    private DcMotor motor;
    private int encoderTravel = 10_000;
    private int encoderAtRampUp = 0;
    private double rampPercentage = 0.1;
    private boolean hasRampedUp, hasTravelled = false;
    private double maxPower = 0.5;
    private double kp = 50;
    private long lastUpdated, startTime;
    private boolean hasFinished = false;
    private double magic = (encoderTravel * rampPercentage);
    private int ticks = 0;
    private double whiteMagic;
    private double grownMagic;

    private int updateMs   = 17;

    @Override
    public void init() {
        motor = hardwareMap.dcMotor.get("leftMotor");
        motor.setPower(0.0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void start() {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Log.d("LOGGER_MAX", "motor, time");
        startTime = System.currentTimeMillis();
        whiteMagic = 1/magic;
        grownMagic = whiteMagic/kp;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        double ratio = motor.getCurrentPosition() / (encoderTravel * rampPercentage);
        telemetry.addLine("503___305");
        telemetry.addData("position", motor.getCurrentPosition());
        telemetry.addData("power++", motor.getPower() + grownMagic);
        telemetry.addData("magic++", whiteMagic);
        telemetry.addData("ratio", ratio);

        if (System.currentTimeMillis() - startTime >= updateMs) {
            update();
            startTime = System.currentTimeMillis();
        }
    }

    public void update() {
        double ratio = motor.getCurrentPosition() / (encoderTravel * rampPercentage);

        if (motor.getPower() < 1 && !hasRampedUp) {
            double power = (motor.getPower() + grownMagic);
            grownMagic+=whiteMagic;
            motor.setPower(power);
            if ((!hasRampedUp && motor.getPower() >= 1.0) || !hasRampedUp && ratio >= 1.0) {
                hasRampedUp = true;
                encoderAtRampUp = motor.getCurrentPosition();
            }
            telemetry.addData("Power recommendation", grownMagic);
        } else if (hasRampedUp && !hasTravelled) {
            if (motor.getCurrentPosition() >= encoderTravel-encoderAtRampUp) {
                hasTravelled = true;
            }
            telemetry.addData("Target vs Current", ""+(encoderTravel-encoderAtRampUp)+" | "+motor.getCurrentPosition());
        } else {
            double power = motor.getPower() - grownMagic;
            grownMagic-=whiteMagic;
            if (motor.getCurrentPosition() >= encoderTravel) {
                motor.setPower(0.0);
            }
            if (motor.getPower() <= 0.01) {
                motor.setPower(0);
                hasFinished = true;
            } else {
                motor.setPower(power);
            }
        }

        if (!hasFinished) {
            Log.d("LOGGER_MAX", "" + motor.getPower() + "," + (System.currentTimeMillis() - startTime));
        }

        telemetry.addData("Power", motor.getPower());
        ticks++;

    }
}
