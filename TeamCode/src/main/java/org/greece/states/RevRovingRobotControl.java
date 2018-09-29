package org.greece.states;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.engine.State;
import org.firstinspires.ftc.robotcontroller.external.samples.SensorREV2mDistance;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RevRovingRobotControl extends State {
  private final DcMotor leftDrive, rightDrive;
  private final Servo steering;
  private DistanceSensor laserDistanceSensor;
  private ColorSensor colorSensor;

  private double speedKP = 1.0;
  private double steeringKP = 0.5;
  private DistanceSensor hybridDistanceSensor;

  public RevRovingRobotControl() {
    leftDrive  = engine.hardwareMap.dcMotor.get("leftDrive");
    rightDrive = engine.hardwareMap.dcMotor.get("rightDrive");
    rightDrive.setDirection(DcMotorSimple.Direction.REVERSE);

    steering   = engine.hardwareMap.servo.get("svSteering");

    laserDistanceSensor = engine.hardwareMap.get(DistanceSensor.class, "distance");

    hybridDistanceSensor = engine.hardwareMap.get(DistanceSensor.class, "colorSensor");
    colorSensor = engine.hardwareMap.colorSensor.get("colorSensor");
  }


  @Override
  public void exec() {
    double power = engine.gamepad1.left_stick_y * speedKP;

    leftDrive.setPower(power);
    rightDrive.setPower(power);

    steering.setPosition((engine.gamepad1.right_stick_x + steeringKP) * -1);
  }

  @Override
  public void telemetry() {
    engine.telemetry.addLine();

    engine.telemetry.addLine("Rev Roving Robot");
    engine.telemetry.addData("Left Drive Position", leftDrive.getCurrentPosition());
    engine.telemetry.addData("Right Drive Position", rightDrive.getCurrentPosition());
    engine.telemetry.addData("Steering Position", steering.getPosition());

    engine.telemetry.addLine();
    engine.telemetry.addData("Laser Distance Sensor", ""+laserDistanceSensor.getDistance(DistanceUnit.MM)+" MM");

    engine.telemetry.addLine();
    String distance = "Undefined";
    String distanceString = String.format("%f", hybridDistanceSensor.getDistance(DistanceUnit.MM));
    if (distanceString.contains("NaN")) {
      distance = "Infinity";
    } else {
      distance = distanceString;
    }
    engine.telemetry.addData("Distance Sensor", ""+distance+" MM");

    engine.telemetry.addLine();
    engine.telemetry.addLine("Color Sensor");
    engine.telemetry.addData("Red", colorSensor.red());
    engine.telemetry.addData("Green", colorSensor.green());
    engine.telemetry.addData("Blue", colorSensor.blue());
    engine.telemetry.addData("Alpha", colorSensor.alpha());
    engine.telemetry.addData("ARGB", colorSensor.argb());
  }

  @Override
  public void stop() {
    leftDrive.setPower(0);
    rightDrive.setPower(0);
  }
}
