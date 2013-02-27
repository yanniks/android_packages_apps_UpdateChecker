package de.yanniks.cm_updatechecker;

import de.yanniks.cm_updatechecker.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UpdateChecker extends Activity {
	private WebView mWebView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatecheck);
    	mWebView = (WebView) findViewById(R.id.updatecheck);
    	mWebView.getSettings().setJavaScriptEnabled(true);
    	mWebView.loadUrl("http://yanniks.de/roms/current-cm10ace.html");
    	mWebView.setWebViewClient(new LoginClient());
    }
	public void download (final View view) {
    	startActivity (new Intent (this,downloadcurrent.class));
	}
    private class LoginClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}