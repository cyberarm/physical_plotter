package org.cyberarm.container_test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.cyberarm.container.Container;

@TeleOp(name = "Rover")
public class Rover extends Container {
    @Override
    public void setup() {
        addActor(new DriveTrain());
    }
}
