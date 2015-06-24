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
import model.Estado;
import model.Musica;
import model.Pedido;
import model.ReqMusicas;
import model.Result;
import model.Sexo;
import model.Usuario;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CadastroUsuarios extends Activity  {

    WebService service;
    EditText edTelefone;
    EditText edCidade;
    EditText ednome;
    EditText edEmail;
    EditText edSenha,edConfirmaSenha;
    EditText edNascimento;
    Spinner spinnerEstado,spinnerSexo;
    List<Estado> mEstados;
    List<Sexo> mSexos;
    Estado mEstado;
    Sexo mSexo;
    ArrayAdapter<Estado> adapterEstados;
    ArrayAdapter<Sexo> adapterSexos;
    Button btnCadastrar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_cadastro_usuarios);
        mEstados = new ArrayList<Estado>();
        mSexos = new ArrayList<Sexo>();
        btnCadastrar = (Button)findViewById(R.id.button_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
        ednome = (EditText)findViewById(R.id.editText_nome);
        edSenha = (EditText)findViewById(R.id.editText_senha);
        edConfirmaSenha = (EditText)findViewById(R.id.editText_confirmaSenha);
        edNascimento = (EditText)findViewById(R.id.editText_nascimento);
        edEmail = (EditText)findViewById(R.id.editText_email);
        edTelefone = (EditText)findViewById(R.id.editText_telefone);
        spinnerSexo= (Spinner)findViewById(R.id.spinner_sexo);
        spinnerEstado = (Spinner)findViewById(R.id.spinner_estado);
        createEntities();
        spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSexo = adapterSexos.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mEstado = adapterEstados.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edCidade = (EditText)findViewById(R.id.editText_cidade);



	}

    private void cadastrar(){
        if (!edSenha.getText().toString().equals(edConfirmaSenha.getText().toString())){
            Toast.makeText(CadastroUsuarios.this,"Confirmação de senha incorreta",Toast.LENGTH_LONG).show();
            edConfirmaSenha.setError("Confirmação de senha incorreta");
            return;
        }
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.radiosertanejo.com.br")
                .build();

        service = restAdapter.create(WebService.class);
        Usuario usuario = new Usuario();
        usuario.setNome(ednome.getText().toString());
        usuario.setEmail(edEmail.getText().toString());
        usuario.setCidade(edCidade.getText().toString());
        usuario.setEstado(mEstado.getUf());
        usuario.setNascimento(edNascimento.getText().toString());
        usuario.setSenha(edSenha.getText().toString());
        usuario.setSexo(mSexo.getId());
        service.cadastrarUsuario(usuario,new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                Toast.makeText(CadastroUsuarios.this,"Cadastro enviado",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CadastroUsuarios.this,"Houve um problema ao cadastrar, tente novamente",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void createEntities(){
        mEstados.add(new Estado("SC","Santa Catarina"));
        mEstados.add(new Estado("AC","Acre"));
        mEstados.add(new Estado("AL","Alagoas"));
        mEstados.add(new Estado("AP","Amapá"));
        mEstados.add(new Estado("AM","Amazonas"));
        mEstados.add(new Estado("BA","Bahia"));
        mEstados.add(new Estado("CE","Ceará"));
        mEstados.add(new Estado("DF","Distrito Federal"));
        mEstados.add(new Estado("ES","Espírito Santo"));
        mEstados.add(new Estado("GO","Goiás"));
        mEstados.add(new Estado("MA","Maranhão"));
        mEstados.add(new Estado("MT","Mato Grosso"));
        mEstados.add(new Estado("MS","Mato Grosso do Sul"));
        mEstados.add(new Estado("MG","Minas Gerais"));
        mEstados.add(new Estado("PA","Pará"));
        mEstados.add(new Estado("PB","Paraíba"));
        mEstados.add(new Estado("PR","Paraná"));
        mEstados.add(new Estado("PE","Pernambuco"));
        mEstados.add(new Estado("PI","Piauí"));
        mEstados.add(new Estado("RJ","Rio de Janeiro"));
        mEstados.add(new Estado("RN","Rio Grande do Norte"));
        mEstados.add(new Estado("RS","Rio Grande do Sul"));
        mEstados.add(new Estado("RO","Rondônia"));
        mEstados.add(new Estado("RR","Roraima"));
        mEstados.add(new Estado("SP","São Paulo"));
        mEstados.add(new Estado("SE","Sergipe"));
        mEstados.add(new Estado("TO","Tocantins"));
        mSexos.add(new Sexo("M","Masculino"));
        mSexos.add(new Sexo("F","Feminino"));
        adapterEstados = new ArrayAdapter<Estado>(this,android.R.layout.simple_dropdown_item_1line,mEstados);
        adapterSexos = new ArrayAdapter<Sexo>(this,android.R.layout.simple_dropdown_item_1line,mSexos);
        spinnerSexo.setAdapter(adapterSexos);
        spinnerEstado.setAdapter(adapterEstados);
    }

}