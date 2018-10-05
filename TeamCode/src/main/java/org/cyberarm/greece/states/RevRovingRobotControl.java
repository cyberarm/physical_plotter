package org.cyberarm.greece.states;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.container.InputChecker;
import org.cyberarm.engine.CyberarmState;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RevRovingRobotControl extends CyberarmState {
  private final DcMotor leftDrive, rightDrive;
  private final Servo steering;
  private final InputChecker inputChecker;

  private double speedKP = 1.0;
  private double steeringKP = 0.5;
  private double drivePower;

  private DistanceSensor laserDistanceSensor0,
                         laserDistanceSensor1,
                         laserDistanceSensor2,
                         laserDistanceSensor3;
  private ColorSensor colorSensor;
  private DistanceSensor hybridDistanceSensor;

  public RevRovingRobotControl() {
    leftDrive  = cyberarmEngine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = cyberarmEngine.hardwareMap.dcMotor.get("rightDrive");
    rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

    steering   = cyberarmEngine.hardwareMap.servo.get("svSteering");
    inputChecker = new InputChecker(cyberarmEngine.gamepad1);

    laserDistanceSensor0 = cyberarmEngine.hardwareMap.get(DistanceSensor.class, "distance0");
    laserDistanceSensor1 = cyberarmEngine.hardwareMap.get(DistanceSensor.class, "distance1");
    laserDistanceSensor2 = cyberarmEngine.hardwareMap.get(DistanceSensor.class, "distance2");
    laserDistanceSensor3 = cyberarmEngine.hardwareMap.get(DistanceSensor.class, "distance3");

//    hybridDistanceSensor = cyberarmEngine.hardwareMap.get(DistanceSensor.class, "colorSensor");
//    colorSensor = cyberarmEngine.hardwareMap.colorSensor.get("colorSensor");
  }


  @Override
  public void exec() {
    inputChecker.update();
    drivePower = cyberarmEngine.gamepad1.left_stick_y * speedKP;

    leftDrive.setPower(drivePower);
    rightDrive.setPower(drivePower);

    steering.setPosition((cyberarmEngine.gamepad1.right_stick_x + steeringKP) * -1);

    if (inputChecker.check("a")) {
      speedKP += 0.1;
    } else if (inputChecker.check("b")) {
      speedKP -=0.1;
    }

    speedKP = Range.clip(speedKP, 0.1, 1.0);
  }

  @Override
  public void telemetry() {
    cyberarmEngine.telemetry.addLine();

    cyberarmEngine.telemetry.addLine("Rev Roving Robot");
    cyberarmEngine.telemetry.addData("Left Drive Position", leftDrive.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Right Drive Position", rightDrive.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Steering Position", steering.getPosition());

    cyberarmEngine.telemetry.addLine();
    cyberarmEngine.telemetry.addData("Laser Distance Sensor 0", ""+laserDistanceSensor0.getDistance(DistanceUnit.MM)+" MM");
    cyberarmEngine.telemetry.addData("Laser Distance Sensor 1", ""+laserDistanceSensor1.getDistance(DistanceUnit.MM)+" MM");
    cyberarmEngine.telemetry.addData("Laser Distance Sensor 2", ""+laserDistanceSensor2.getDistance(DistanceUnit.MM)+" MM");
    cyberarmEngine.telemetry.addData("Laser Distance Sensor 3", ""+laserDistanceSensor3.getDistance(DistanceUnit.MM)+" MM");
  }

  @Override
  public void stop() {
    leftDrive.setPower(0);
    rightDrive.setPower(0);
  }
}
