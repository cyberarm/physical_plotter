package org.container_test;

import org.container.Container;
import org.container.Set;

public class Rover extends Container {
    @Override
    public void setup() {
        addSet(new Set());

    }
}
