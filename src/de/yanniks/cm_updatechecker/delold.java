package de.yanniks.cm_updatechecker;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;

public class delold extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delold);
        File file = new File("/sdcard/Download/cm-current.zip");
        boolean deleted = file.delete();
    }
}