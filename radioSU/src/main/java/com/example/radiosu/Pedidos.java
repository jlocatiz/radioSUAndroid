package com.example.radiosu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radiosu.web.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import model.Artista;
import model.Musica;
import model.Pedido;
import model.ReqMusicas;
import model.Result;
import model.Usuario;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Pedidos extends Activity  {

    WebService service;
    ArrayAdapter<Artista> artistasAdapter;
    ArrayAdapter<Musica> musicasAdapter;
    List<Artista> listArtistas;
    List<Musica> listMusicas;
    Spinner spinnerArtistas,spinnerMusicas;
    Musica mMusica;
    Artista mArtista;
    EditText edTelefone,edRecado,edCidade;
    TextView tvCidade;
    Button btnPedir;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_pedidos);
        edTelefone = (EditText)findViewById(R.id.editText_telefone);
        edRecado = (EditText)findViewById(R.id.editText_recado);
        edCidade = (EditText)findViewById(R.id.editText_cidade);
        tvCidade = (TextView)findViewById(R.id.textView_cidade);
        if (Usuario.current.getCidade() == null || Usuario.current.getCidade().length() == 0){
            edCidade.setVisibility(View.VISIBLE);
            tvCidade.setVisibility(View.VISIBLE);
        }else{
            edCidade.setVisibility(View.GONE);
            tvCidade.setVisibility(View.GONE);
        }
        btnPedir = (Button)findViewById(R.id.button_pedir);

        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedir();
            }
        });
        spinnerArtistas = (Spinner)findViewById(R.id.spinner_artistas);
        spinnerMusicas = (Spinner)findViewById(R.id.spinner_musicas);
        spinnerMusicas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMusica = musicasAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerArtistas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mArtista = artistasAdapter.getItem(position);
                JSONObject jo = new JSONObject();
                service.getMusicas(new ReqMusicas(artistasAdapter.getItem(position).getId()),new Callback<Result>() {
                    @Override
                    public void success(Result result, Response response) {
                        String json = "";
                        try {
                            listMusicas.clear();
                            json =java.net.URLDecoder.decode(result.d, "UTF-8");
                            JSONArray ja = new JSONArray(json);
                            for (int i = 0; i<ja.length();i++){
                                JSONObject jo = ja.getJSONObject(i);
                                Musica musica = new Musica();
                                musica.setNome(jo.getString("Texto"));
                                musica.setId(jo.getInt("Valor"));
                                listMusicas.add(musica);
                            }
                            musicasAdapter = new ArrayAdapter<Musica>(Pedidos.this,android.R.layout.simple_dropdown_item_1line,listMusicas);
                            spinnerMusicas.setAdapter(musicasAdapter);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.radiosertanejo.com.br")
                .build();
        listArtistas = new ArrayList<Artista>();
        listMusicas = new ArrayList<Musica>();
        service = restAdapter.create(WebService.class);
        JSONObject jo = new JSONObject();
        try {
            jo.put("artistaId",null);
            jo.put("usuarioId",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        service.getArtistas(jo,new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                String json = "";
                try {
                    listArtistas.clear();
                    json =java.net.URLDecoder.decode(result.d, "UTF-8");
                    JSONArray ja = new JSONArray(json);
                    for (int i = 0; i<ja.length();i++){
                        JSONObject jo = ja.getJSONObject(i);
                        Artista artista = new Artista();
                        artista.setNome(jo.getString("Texto"));
                        artista.setId(jo.getInt("Valor"));
                        listArtistas.add(artista);
                    }
                    artistasAdapter = new ArrayAdapter<Artista>(Pedidos.this,android.R.layout.simple_dropdown_item_1line,listArtistas);
                    spinnerArtistas.setAdapter(artistasAdapter);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(Pedidos.this, "ocorreu um problema ao buscar artistas", Toast.LENGTH_LONG).show();
            }
        });
	}

    public void pedir(){
        Pedido pedido = new Pedido();
        if (Usuario.current.getCidade() == null || Usuario.current.getCidade().length() == 0)
            pedido.setCidade(edCidade.getText().toString());
        else
            pedido.setCidade(Usuario.current.getCidade());
        pedido.setEmail(Usuario.current.getEmail());
        pedido.setNome(Usuario.current.getNome());
        pedido.setDescricao(edRecado.getText().toString());
        pedido.setArtistaId(mArtista.getId());
        pedido.setMusicaId(mMusica.getId());
        service.pedirMusica(pedido,new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
               Toast.makeText(Pedidos.this,"Pedido enviado",Toast.LENGTH_LONG).show();
               finish();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }
}