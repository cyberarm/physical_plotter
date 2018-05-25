package org.container;

import java.util.ArrayList;

public class Set {
    protected Container container;
    protected ArrayList<Actor> actors;
    public Set() {
        container = ContainerGlobal.activeContainer;
        actors = new ArrayList<>();
    }

    public void initialize() {
        for (int i = 0; i < actors.size(); i++) {
//            actors.get(i).setContainer(container);
        }
    }

    public void update() {
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).update();
        }
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }
}
