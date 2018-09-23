package org.greece.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.engine.State;

public class RevRovingRobotControl extends State {
  private final DcMotor leftDrive, rightDrive;
  private final Servo steering;
  private double speedKP = 1.0;
  private double steeringKP = 0.5;

  public RevRovingRobotControl() {
    leftDrive  = engine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
    rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

    steering   = engine.hardwareMap.servo.get("svSteering");
  }


  @Override
  public void exec() {
    double power = engine.gamepad1.left_stick_y * speedKP;

    leftDrive.setPower(power);
    rightDrive.setPower(power);

    steering.setPosition((engine.gamepad1.right_stick_x + steeringKP) * -1);
  }

  @Override
  public void stop() {
    leftDrive.setPower(0);
    rightDrive.setPower(0);
  }
}
