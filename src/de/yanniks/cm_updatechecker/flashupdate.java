package de.yanniks.cm_updatechecker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class flashupdate extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // flash commands from CMUpdater

        try {
            Intent i = new Intent("android.intent.action.MAIN"); 
            ComponentName n = new 
            ComponentName("com.cyanogenmod.updater", 
            "com.cyanogenmod.updater.StartUpdate"); 
            i.setComponent(n); 
            startActivity(i); 
        } catch(ActivityNotFoundException e) {
        	Toast.makeText(getApplicationContext(), getString(R.string.notnew), Toast.LENGTH_LONG).show();
        }
    }
}