package org.greece.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.container.InputChecker;
import org.engine.State;

public class RevRovingRobotControl extends State {
  private final DcMotor leftDrive, rightDrive;
  private final Servo steering;
  private final InputChecker inputChecker;
  private double speedKP = 1.0;
  private double steeringKP = 0.5;
  private double drivePower;

  public RevRovingRobotControl() {
    leftDrive  = engine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
    rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

    steering   = engine.hardwareMap.servo.get("svSteering");
    inputChecker = new InputChecker(engine.gamepad1);
  }


  @Override
  public void exec() {
    inputChecker.update();
    drivePower = engine.gamepad1.left_stick_y * speedKP;

    leftDrive.setPower(drivePower);
    rightDrive.setPower(drivePower);

    steering.setPosition((engine.gamepad1.right_stick_x + steeringKP) * -1);

    if (inputChecker.check("a")) {
      speedKP += 0.1;
    } else if (inputChecker.check("b")) {
      speedKP -=0.1;
    }

    speedKP = Range.clip(speedKP, 0.1, 1.0);
  }

  @Override
  public void telemetry() {
    engine.telemetry.addLine("Rev Roving Robot");
    engine.telemetry.addData("Speed", drivePower);
    engine.telemetry.addData("Steering", (engine.gamepad1.right_stick_x + steeringKP) * -1);
  }

  @Override
  public void stop() {
    leftDrive.setPower(0);
    rightDrive.setPower(0);
  }
}
