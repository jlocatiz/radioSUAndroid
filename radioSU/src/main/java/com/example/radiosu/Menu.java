package com.example.radiosu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


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
	
}
