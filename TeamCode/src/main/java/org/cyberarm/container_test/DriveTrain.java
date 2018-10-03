package org.cyberarm.container_test;
import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.cyberarm.container.Actor;

class DriveTrain extends Actor {
    DcMotor leftDrive;
    DcMotor rightDrive;
    boolean aToggle = true;

    @Override
    public void setup() {
        leftDrive = motor("leftDrive");
        rightDrive = motor("rightDrive");
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void update() {
        delay(127); // Halt execution for 127 milliseconds
        if (aToggle) {
            leftDrive.setPower(container.gamepad1.left_stick_y);
            rightDrive.setPower(container.gamepad1.right_stick_x);
        } else {
            leftDrive.setPower(0.0);
            rightDrive.setPower(0.0);

        }

        container.telemetry.addLine("DriveTrain");
        container.telemetry.addData("leftDrive", leftDrive.getPower());
        container.telemetry.addData("rightDrive", rightDrive.getPower());
        sleep(127); // Stop Collection from calling update on me for 127~ milliseconds
    }

    @Override
    public void buttonUp(Gamepad gamepad, String button) {
        if (gamepad == container.gamepad1) {
            if (button.equals("a")) {
                aToggle = !aToggle;
            }
        }

        if (gamepad == container.gamepad2) {
            Log.d("DriveTrain","button \""+button+"\" has been released.");
        }
    }
}
