package org.container;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;

public class Container extends OpMode {
    protected ArrayList<Set> sets;
    protected int setIndex = 0;

    @Override
    public void init() {
        ContainerGlobal.activeContainer = this;
        sets = new ArrayList<>();
        setup();
    }

    @Override
    public void start() {
        sets.get(setIndex).initialize();
    }

    @Override
    public void loop() {
        sets.get(setIndex).update();
    }

    public void setup() {

    }

    public Set addSet(Set set) {
        sets.add(set);
        return set;
    }
}
