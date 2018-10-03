package org.cyberarm.engine;

import android.util.Log;

public class Support {
    public static void puts(String tag, String message) {
        Log.d(tag, message);
    }

    public static void puts(String message) {
        Log.d("|_|", message);
    }
}
