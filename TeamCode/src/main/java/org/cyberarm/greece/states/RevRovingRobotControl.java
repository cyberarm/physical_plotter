package org.cyberarm.greece.states;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.container.InputChecker;
import org.cyberarm.engine.State;
import org.firstinspires.ftc.robotcontroller.external.samples.SensorREV2mDistance;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RevRovingRobotControl extends State {
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
    leftDrive  = engine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
    rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

    steering   = engine.hardwareMap.servo.get("svSteering");
    inputChecker = new InputChecker(engine.gamepad1);

    laserDistanceSensor0 = engine.hardwareMap.get(DistanceSensor.class, "distance0");
    laserDistanceSensor1 = engine.hardwareMap.get(DistanceSensor.class, "distance1");
    laserDistanceSensor2 = engine.hardwareMap.get(DistanceSensor.class, "distance2");
    laserDistanceSensor3 = engine.hardwareMap.get(DistanceSensor.class, "distance3");

//    hybridDistanceSensor = engine.hardwareMap.get(DistanceSensor.class, "colorSensor");
//    colorSensor = engine.hardwareMap.colorSensor.get("colorSensor");
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
    engine.telemetry.addLine();

    engine.telemetry.addLine("Rev Roving Robot");
    engine.telemetry.addData("Left Drive Position", leftDrive.getCurrentPosition());
    engine.telemetry.addData("Right Drive Position", rightDrive.getCurrentPosition());
    engine.telemetry.addData("Steering Position", steering.getPosition());

    engine.telemetry.addLine();
    engine.telemetry.addData("Laser Distance Sensor 0", ""+laserDistanceSensor0.getDistance(DistanceUnit.MM)+" MM");
    engine.telemetry.addData("Laser Distance Sensor 1", ""+laserDistanceSensor1.getDistance(DistanceUnit.MM)+" MM");
    engine.telemetry.addData("Laser Distance Sensor 2", ""+laserDistanceSensor2.getDistance(DistanceUnit.MM)+" MM");
    engine.telemetry.addData("Laser Distance Sensor 3", ""+laserDistanceSensor3.getDistance(DistanceUnit.MM)+" MM");
  }

  @Override
  public void stop() {
    leftDrive.setPower(0);
    rightDrive.setPower(0);
  }
}
