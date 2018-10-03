package org.timecrafters.gfp.state.file;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import org.timecrafters.engine.Engine;
import org.timecrafters.gfp.config.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by goldfishpi on 12/9/17.
 */

public class MakeFile extends Config {
    public MakeFile(Engine engine) {
        super(engine);
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
