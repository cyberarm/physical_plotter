package org.driver.states;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.engine.Engine;
import org.engine.State;
import org.plotter.Event;

import java.util.ArrayList;

public class Move extends State {
    DcMotor xAxis, yAxis;
    int targetX, targetY;
    int multiplier = 40;
    double speed = 0.1;

    public Move(Engine engine, int x, int y) {
        this.engine = engine;
        targetX = x;
        targetY = y;

        xAxis = engine.hardwareMap.dcMotor.get("xAxis");
        yAxis = engine.hardwareMap.dcMotor.get("yAxis");

        xAxis.setPower(0);
        yAxis.setPower(0);

    }

    @Override
    public void exec() {
        int localX = adjustedTarget(targetX);
        int localY = adjustedTarget(targetY);
        int fuzz = 10;

        if (!between(yAxis.getCurrentPosition(), adjustedTarget(targetY), fuzz)) {
            if (yAxis.getCurrentPosition() < localY+fuzz) {
                yAxis.setPower(-speed);
            } else if(yAxis.getCurrentPosition() > localY-fuzz) {
                yAxis.setPower(speed);

            } else {
                yAxis.setPower(0);
            }
        } else {
            if (xAxis.getCurrentPosition() < localX+fuzz) {
                xAxis.setPower(-speed);
            } else if(xAxis.getCurrentPosition() > localX+fuzz) {
                xAxis.setPower(speed);

            } else {
                xAxis.setPower(0);
                setFinished(true);
            }   
        }
    }

    @Override
    public void stop() {
        xAxis.setPower(0);
        yAxis.setPower(0);
    }

    /*
        position.between?(target-fuzz, target+fuzz)
     */

    private boolean between(int position, int target, int fuzz) {
        if ((position > target-fuzz) && (position < target+fuzz)) {
            return true;
        } else {
            return false;
        }
    }

    private int adjustedTarget(int target) {
        return (target*multiplier);
    }
}
