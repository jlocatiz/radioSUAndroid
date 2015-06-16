package com.example.radiosu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
 
@SuppressLint("SetJavaScriptEnabled") public class Cadastro extends Activity {
    /** Called when the activity is first created. */
    private WebView meuSiteWV;
 
    private static final String TAG_NAME = Cadastro.class.getName();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
 
        Log.d(TAG_NAME, "Recuperando componentes...");
        this.meuSiteWV = (WebView) findViewById(R.id.siteWebView);
        Log.d(TAG_NAME, "Carregando web-site...");
        meuSiteWV.getSettings().setJavaScriptEnabled(true);
        meuSiteWV.loadUrl("http://radiosertanejo.com.br");
        Log.d(TAG_NAME, "Acabou!");
    }
 
}