package com.example.radiosu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import com.example.radiosu.web.WebService;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Session.StatusCallback;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Result;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


@SuppressWarnings("unused")
public class Login extends Activity {

    private EditText email,senha;
    private Button btnConnectar;

	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChanged(session, state, exception);
			
		}
	};
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .build();
        ImageLoader.getInstance().init(config);
		setContentView(R.layout.tela_login);
		email = (EditText)findViewById(R.id.emailped);
        senha = (EditText)findViewById(R.id.senha);
        btnConnectar = (Button)findViewById(R.id.conectar);
        btnConnectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("http://www.radiosertanejo.com.br")
                        .build();

                WebService service = restAdapter.create(WebService.class);
                service.login(email.getText().toString(),senha.getText().toString(),new Callback<Result>() {
                    @Override
                    public void success(Result result, retrofit.client.Response response) {
                        Toast.makeText(Login.this,result.d,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("radio","erro no login",error);
                    }
                });
            }
        });
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
	
	
		LoginButton lb =(LoginButton) findViewById(R.id.authButton);
		lb.setPublishPermissions(Arrays.asList("email", "public_profile", "user_friends"));
	}
		public void conectarOnClick(View v){
			Intent intent = new Intent(this, com.example.radiosu.Menu.class);
			startActivity(intent);

		}
			
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Session session = Session.getActiveSession();
		if(session != null && (session.isClosed() || session.isOpened())){
			onSessionStateChanged(session, session.getState(),null);
			Intent intent = new Intent(this, com.example.radiosu.Menu.class);
			startActivity(intent);
			session = null;
						
        }
		
		uiHelper.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		uiHelper.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		uiHelper.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		uiHelper.onSaveInstanceState(bundle);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}


//METHODS FACEBOOK
public void onSessionStateChanged(Session session, SessionState state, Exception exception){
	if(session != null && session.isOpened()){
		Log.i("Script", "Usuario conectado");
		Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if(user != null){
										
				}
			}
		}).executeAsync();
	}
	else{
		Log.i("Script", "Usuario n�o conectado");
	}
	}

}
