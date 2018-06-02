package org.container;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

public abstract class Container extends OpMode {
    protected ArrayList<Collection> collections;
    protected int collectionIndex = 0;
    protected InputChecker inputCheckerGamepad1;
    protected InputChecker inputCheckerGamepad2;

    @Override
    public void init() {
        ContainerGlobal.activeContainer = this;
        collections = new ArrayList<>();

        inputCheckerGamepad1 = new InputChecker(gamepad1);
        inputCheckerGamepad2 = new InputChecker(gamepad2);

        addCollection();
        setup();
        collectionIndex = 0;
    }

    public abstract void setup();

    @Override
    public void start() {
        collections.get(collectionIndex).initialize();
    }

    @Override
    public void loop() {
        if (collections.get(collectionIndex).complete()) {
            collectionIndex++;
            collections.get(collectionIndex).initialize();
        }

        collections.get(collectionIndex).update();
        inputCheckerGamepad1.update();
        inputCheckerGamepad2.update();
    }

    public void buttonUp(Gamepad gamepad, String button) {
        collections.get(collectionIndex).buttonUp(gamepad, button);
    }

    public void currentCollection() {
        collections.get(collectionIndex);
    }

    public void addCollection() {
        collections.add(new Collection());
    }

    public void addActor(Actor actor) {
        collections.get(collectionIndex).addActor(actor);
        collectionIndex++;
    }
}
