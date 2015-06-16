package com.example.radiosu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

@SuppressLint("SetJavaScriptEnabled")
public class Novidades extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_novidades);
		
		WebView wv = (WebView) findViewById(R.id.webView1);
			
				
			WebSettings ws = wv.getSettings();
			ws.setJavaScriptEnabled(true);
			ws.setSupportZoom(false);
			
		   wv.loadUrl("http://www.radiosertanejo.com.br/Novidade/");
                    wv.setWebViewClient(new WebViewClient() {

                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
			        return false;
			    }
			});
		}
	}

