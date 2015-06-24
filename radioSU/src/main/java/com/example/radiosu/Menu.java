package com.example.radiosu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.facebook.Session;

import java.util.zip.Inflater;


public class Menu extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.tela_menu);
	}
	public void selecionarOpcao(View view) {
		switch (view.getId()) {
		case R.id.menu_ouvir:
		startActivity(new Intent(this, Ouvir.class));
		break;
		}
		switch (view.getId()) {
		case R.id.menu_novidades:
		startActivity(new Intent(this, Novidades.class));
		break;
		}
		switch (view.getId()) {
		case R.id.menu_pedidos:
		startActivity(new Intent(this, Pedidos.class));
		break;
		}		
		switch (view.getId()) {
		case R.id.facebook:
			Uri uri = Uri.parse("http://www.facebook.com/radiosu"); 
			Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
			startActivity(intent);
					
		break;
		}	
		switch (view.getId()) {
		case R.id.twitter:
			Uri uri = Uri.parse("http://www.twitter.com/radiosu"); 
			Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
			startActivity(intent);
			
		break;
		}	
		switch (view.getId()) {
		case R.id.instagram:
			Uri uri = Uri.parse("http://www.instagram.com/radiosu"); 
			Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
			startActivity(intent);
			
		break;
		}
		
	}

    public static void callFacebookLogout(Context context) {
        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {

            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved

        }

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            callFacebookLogout(this);
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
