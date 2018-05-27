package org.container;

import android.util.Log;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class InputChecker {
    private Gamepad gamepad;
    private HashMap<String, Boolean> buttons;
    private ArrayList<String> buttonList;
    public InputChecker(Gamepad _gamepad) {
        gamepad = _gamepad;
        buttonList = new ArrayList<>();

        buttonList.add("a"); buttonList.add("b"); buttonList.add("x"); buttonList.add("y");
        buttonList.add("start"); buttonList.add("guide"); buttonList.add("back");
        buttonList.add("left_bumper"); buttonList.add("right_bumper");
        buttonList.add("left_stick_button"); buttonList.add("right_stick_button");
        buttonList.add("dpad_left"); buttonList.add("dpad_right");
        buttonList.add("dpad_up"); buttonList.add("dpad_down");
    }

    public void update() {
        for (int i = 0; i < buttonList.size(); i++) {
            try {
                if ((boolean) gamepad.getClass().getMethod(buttonList.get(i)).invoke(gamepad)) {
                    buttons.put(buttonList.get(i), true);
                } else {
                    check(buttonList.get(i));
                }
            } catch (NoSuchMethodException|InvocationTargetException|IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void check(String button) {
        if (buttons.containsKey(button) && buttons.get(button)) {
            ContainerGlobal.activeContainer.buttonUp(gamepad, button);
            Log.d("InputChecker","button \""+button+"\" has been released on \"gamepad"+gamepad.getGamepadId()+"\"");

        }
        buttons.put(button, false);
    }
}
