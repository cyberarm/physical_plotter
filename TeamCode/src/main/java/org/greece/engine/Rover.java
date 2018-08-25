package org.greece.engine;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.container.InputChecker;
import org.greece.statues.Motor;

@TeleOp(name = "RoverSimple")
public class Rover extends OpMode {
  Motor leftDrive, rightDrive;
  double speedRegulator = 0.0;
  double maxSpeed = 1.0;
  double stepSize = 0.05;
  InputChecker inputChecker; // checks for button up events
  @Override
  public void init() {
    leftDrive = new Motor(hardwareMap.dcMotor.get("leftDrive"));
    rightDrive = new Motor(hardwareMap.dcMotor.get("rightDrive"));
    inputChecker = new InputChecker(gamepad1);
  }

  @Override
  public void loop() {
    if (inputChecker.check("dpad_up")) {
      speedRegulator+=stepSize;
      if (speedRegulator > maxSpeed) {speedRegulator = maxSpeed;}
    } else if (inputChecker.check("dpad_down")) {
      speedRegulator-=stepSize;
      if (speedRegulator > maxSpeed) {speedRegulator = maxSpeed;}
    }

    leftDrive.getMotor().setPower(speedRegulator);
    rightDrive.getMotor().setPower(speedRegulator);

    inputChecker.update();

    telemetry.addData("leftDrive", leftDrive.getMotor().getPower());
    telemetry.addData("rightDrive", rightDrive.getMotor().getPower());
    telemetry.addData("speedRegulator", speedRegulator);
  }
}
