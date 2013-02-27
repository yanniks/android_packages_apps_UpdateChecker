package de.yanniks.cm_updatechecker;

import de.yanniks.cm_updatechecker.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class downloadcurrent extends Activity {
	private WebView mWebView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadcurrent);
    	mWebView = (WebView) findViewById(R.id.downloadcurrent);
    	mWebView.loadUrl("http://yanniks.de/roms/cm10-1-ace");
    }
}