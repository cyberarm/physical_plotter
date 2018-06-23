package org.greece.engine;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.greece.statues.Motor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@TeleOp(name = "MatchSpeedv3")
public class MatchSpeedv3 extends OpMode {
    ArrayList<Motor> motors;
    int currentMotor = 0;
    private long lastUpdateMs;
    private int updateDelay = 500;
    private double initialPower = 0.4;
    private double kp = 1; // constant of proportionality
    private int loopCount = 0;
    private int loopCountReset = 25;
    private boolean invertMotor = false;

    @Override
    public void init() {
        motors = new ArrayList();
        motors.add(new Motor(getMotor("leftMotor")));
        motors.add(new Motor(getMotor("rightMotor")));
        getMotor("rightMotor").setDirection(DcMotorSimple.Direction.REVERSE);

        for (int i = 0; i < motors.size(); i++) {
            Motor motor = motors.get(i);
            motor.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    private DcMotor getMotor(String motor) {
        return hardwareMap.dcMotor.get(motor);
    }

    @Override
    public void start() {
        for (int i = 0; i < motors.size(); i++) {
            Motor motor = motors.get(i);
            motor.getMotor().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.getMotor().setPower(0.0);
        }

        motors.get(currentMotor).getMotor().setPower(initialPower);
        lastUpdateMs = System.currentTimeMillis();
        Log.d("TELEMETRY_LOGGER", "Time,Ratio");
    }

    @Override
    public void loop() {
        if (System.currentTimeMillis()-lastUpdateMs > updateDelay) {
            update();

            lastUpdateMs = System.currentTimeMillis();
            loopCount++;
        }
    }

    public void update() {
        for (int i = 0; i < motors.size(); i++) {
            Motor motor = motors.get(i);
            Motor targetMotor = motors.get(currentMotor);
            if (loopCount >= loopCountReset) {
                double speed = Range.clip(new Random().nextDouble(), -1, 1);

                if (invertMotor) {
                    speed*=-1;
                }
                invertMotor = !invertMotor;

                targetMotor.getMotor().setPower(speed);
                loopCount = 0;
            }
            motor.update();
            telemetry.addLine("Motor|"+motor.name());
            telemetry.addData("Power", motor.power());
            telemetry.addData("Encoder", motor.position());
            telemetry.addData("LastEncoder", motor.lastPosition());
            telemetry.addData("Velocity", motor.velocity());
            telemetry.addLine("______________________________");

            if (motor == targetMotor) { continue; }

            if (motor.getMotor().getPower() == 0.00) {
                if (targetMotor.position() > targetMotor.lastPosition()) {
                    motor.getMotor().setPower(0.1);
                } else if (targetMotor.position() == targetMotor.lastPosition()) {
                    motor.getMotor().setPower(0);
                } else {
                    motor.getMotor().setPower(0.1);
                }
            } else {

                // LOGIC
                double ratio = targetMotor.velocity()/motor.velocity();

                if (ratio == 1.000000000000000000000000000000000000000000000000) {
                    telemetry.addLine("+=+=+=+=+=+=+=+=+=+=+=+=+=+");
                    telemetry.addLine("Matching Velocity!");
                    telemetry.addLine("+=+=+=+=+=+=+=+=+=+=+=+=+=+");
                } else {
                    telemetry.addLine("|=|=|=|=|=|=|=|=|=|=|=|=|=|");
                    telemetry.addData("Ratio", ratio);
                    telemetry.addData("ratio/kp", ratio / kp);
                    telemetry.addLine("|=|=|=|=|=|=|=|=|=|=|=|=|=|");

                    double speed = motor.getMotor().getPower() * (ratio / kp);
                    motor.getMotor().setPower((speed));
//                    if (targetMotor.getMotor().getCurrentPosition() > targetMotor.lastPosition()) {
//                        motor.getMotor().setPower(speed);
//                    } else {
//                        motor.getMotor().setPower((speed) * -1);
//                    }
                }

                Log.d("TELEMETRY_LOGGER", new Date().toString()+ ","+ratio/kp);
            }
        }
    }
}
