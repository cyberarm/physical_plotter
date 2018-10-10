package org.cyberarm.greece.states;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.cyberarm.container.InputChecker;
import org.cyberarm.engine.CyberarmState;
import org.cyberarm.greece.statues.LaserObjectDetector;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;

public class RevRovingRobotControl extends CyberarmState {
  private final DcMotor leftDrive, rightDrive;
  private final Servo steering;
  private final InputChecker inputChecker;
  private final double average;

  private double speedKP = 1.0;
  private double steeringKP = 0.5;
  private double drivePower;

  private DistanceSensor laserDistanceSensor0,
                         laserDistanceSensor1,
                         laserDistanceSensor2,
                         laserDistanceSensor3;
  private ArrayList<DistanceSensor> distanceSensors;

  private LaserObjectDetector laserObjectDetector;

  private int block = 22, // MM // 45 for Flat face, ~22 for waffle
              sphere= 65, // MM
              distanceKP=5;//MM

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

    distanceSensors = new ArrayList<>();
    distanceSensors.add(laserDistanceSensor0);
    distanceSensors.add(laserDistanceSensor1);
    distanceSensors.add(laserDistanceSensor2);
    distanceSensors.add(laserDistanceSensor3);

    average = averageDistance();
    laserObjectDetector = new LaserObjectDetector(distanceSensors);
  }

  public boolean getDistances(double distance) {
    int sensors = 0;

    if (mmDistance(laserDistanceSensor0) > distance)  { sensors++;}
    if (mmDistance(laserDistanceSensor1) > distance)  { sensors++;}
    if (mmDistance(laserDistanceSensor2) > distance)  { sensors++;}
    if (mmDistance(laserDistanceSensor3) > distance)  { sensors++;}

    if (sensors == 4) {
      return true;
    } else {
      return false;
    }
  }

  public double mmDistance(DistanceSensor sensor) { return sensor.getDistance(DistanceUnit.MM); }

  public double averageDistance() {
    double total = 0;
    for (int i = 0; i < distanceSensors.size(); i++) {
      total += mmDistance(distanceSensors.get(i));
    }

    return total / distanceSensors.size();
  }

  @Override
  public void exec() {
    inputChecker.update();
    drivePower = cyberarmEngine.gamepad1.left_stick_y * speedKP;

    laserObjectDetector.update();

    leftDrive.setPower(drivePower);
    rightDrive.setPower(drivePower);

    steering.setPosition((cyberarmEngine.gamepad1.right_stick_x + steeringKP) * -1);

    if (inputChecker.check("a")) {
      speedKP += 0.1;
    } else if (inputChecker.check("b")) {
      speedKP -=0.1;
    }

    speedKP = Range.clip(speedKP, 0.1, 1.0);

    averageSensors();
  }

  private void averageSensors() {

  }

  @Override
  public void telemetry() {
    boolean foundBlock = false;
    boolean foundSphere = false;
    double highestPoint = 0;

    cyberarmEngine.telemetry.addLine();

    cyberarmEngine.telemetry.addLine("Rev Roving Robot");
    cyberarmEngine.telemetry.addData("Left Drive Position", leftDrive.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Right Drive Position", rightDrive.getCurrentPosition());
    cyberarmEngine.telemetry.addData("Steering Position", steering.getPosition());

    laserObjectDetector.telemetry(cyberarmEngine);

    cyberarmEngine.telemetry.addLine();
    for (int i = 0; i < distanceSensors.size(); i++) {
      cyberarmEngine.telemetry.addData("LaserDistanceSensor"+i, mmDistance(distanceSensors.get(i)));
    }

    cyberarmEngine.telemetry.addLine();

    for (int i = 0; i < distanceSensors.size(); i++) {
      double d = (mmDistance(distanceSensors.get(i))-average)*-1;
      cyberarmEngine.telemetry.addData("LaserDistanceSensor"+i, d);
      if (d > highestPoint) { highestPoint = d; }
    }

    cyberarmEngine.telemetry.addLine();

    for (int i = 0; i < distanceSensors.size(); i++) {
      cyberarmEngine.telemetry.addData("LaserDistanceSensor"+i, (mmDistance(distanceSensors.get(i))-averageDistance())*-1);
    }
    cyberarmEngine.telemetry.addData("Average Distance (1 sample)", average);

    cyberarmEngine.telemetry.addLine();
    cyberarmEngine.telemetry.addData("Highest Point", highestPoint);

    if (sphere >= (highestPoint - distanceKP) && sphere <= (highestPoint + distanceKP)) {
      foundSphere = true;
    } else if (block >= (highestPoint - distanceKP) && block <= (highestPoint + distanceKP)) {
      foundBlock = true;
    }
    cyberarmEngine.telemetry.addData("Block Found", foundBlock);
    cyberarmEngine.telemetry.addData("Sphere Found", foundSphere);
  }

  @Override
  public void stop() {
    leftDrive.setPower(0);
    rightDrive.setPower(0);
  }
}
