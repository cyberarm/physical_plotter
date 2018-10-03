package org.cyberarm.container;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

public class Collection {
    protected Container container;
    protected ArrayList<Actor> actors;
    private boolean isCompleted = false;

    public Collection() {
        container = ContainerGlobal.activeContainer;
        actors = new ArrayList<>();
    }

    public void initialize() {
        for (int i = 0; i < actors.size(); i++) {
            actors.get(i).setup();
        }
    }

    public void update() {
        int completedActors = 0;
        for (int i = 0; i < actors.size(); i++) {
            Actor actor  = actors.get(i);
            if (!actor.completed()) {
                if (actor.isSleeping) {
                    if (System.currentTimeMillis() >= actor.wakeupTime) {
                        actor.isSleeping = false;
                        actor.wakeupTime = 0;
                        actor.update();
                    }
                } else {
                    actor.update();
                }
            } else {
                completedActors++;
            }
        }

        if (completedActors == actors.size()) {
            isCompleted = true;
        }
    }

    public void buttonUp(Gamepad gamepad, String button) {
        for (Actor actor: actors) {
            actor.buttonUp(gamepad, button);
        }
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public boolean complete() {
        return isCompleted;
    }
}
