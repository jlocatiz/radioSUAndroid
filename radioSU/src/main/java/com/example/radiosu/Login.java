package com.example.radiosu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.ReqLogin;
import model.Result;
import model.Usuario;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


@SuppressWarnings("unused")
public class Login extends Activity {

    private EditText email,senha;
    private Button btnConnectar, btnCadastro;
    private boolean isMenuOpended = false;

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
        isMenuOpended = false;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .build();
        ImageLoader.getInstance().init(config);
		setContentView(R.layout.tela_login);
		email = (EditText)findViewById(R.id.emailped);
        senha = (EditText)findViewById(R.id.senha);
        btnCadastro = (Button) findViewById(R.id.cadastro);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CadastroUsuarios.class);
                startActivity(intent);
            }
        });
        btnConnectar = (Button)findViewById(R.id.conectar);
        btnConnectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("http://www.radiosertanejo.com.br")
                        .build();

                WebService service = restAdapter.create(WebService.class);
                ReqLogin reqLogin = new ReqLogin();
                reqLogin.setUsuario(email.getText().toString());
                reqLogin.setSenha(senha.getText().toString());
                service.login(reqLogin,new Callback<Result>() {
                    @Override
                    public void success(Result result, retrofit.client.Response response) {
                        String[] aux = result.d.split(",");
                        Usuario usuario = new Usuario();
                        usuario.setId(Integer.parseInt(aux[0]));
                        usuario.setNome(aux[1]);
                        usuario.setEmail(aux[2]);
                        usuario.setCidade(aux[3]);
                        Usuario.current = usuario;
                        Intent intent = new Intent(Login.this, com.example.radiosu.Menu.class);
                        startActivity(intent);
                        finish();
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

			
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Session session = Session.getActiveSession();
		if(session != null && (session.isClosed() || session.isOpened())){
			onSessionStateChanged(session, session.getState(),null);

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
                    Usuario usuario = new Usuario();
                    usuario.setNome(user.getFirstName()+" "+user.getLastName());
                    usuario.setEmail(user.getProperty("email").toString());
                    Usuario.current = usuario;
                    if (!isMenuOpended) {
                        Intent intent = new Intent(Login.this, com.example.radiosu.Menu.class);
                        startActivity(intent);
                        isMenuOpended = true;
                    }
                    finish();
				}
			}
		}).executeAsync();
	}
	else{
		Log.i("Script", "Usuario n�o conectado");
	}
	}
}
