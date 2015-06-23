package com.example.radiosu.web;

import org.json.JSONObject;

import model.Pedido;
import model.ReqLogin;
import model.ReqMusicas;
import model.Result;
import model.Usuario;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Administrador on 14/06/2015.
 */
public interface WebService {

    @POST("/Servico/Usuario/UsuarioService.svc/Entrar")
    void login(@Body ReqLogin req, Callback<Result> callback);

    @POST("/Servico/Player/PlayerService.svc/MusicaTocando")
    void musicaTocando(Callback<Result> callback);

    @POST("/Servico/Pedido/PedidoService.svc/PreencheArtistaMusica")
    void getArtistas(@Body JSONObject req, Callback<Result> callback);

    @POST("/Servico/Pedido/PedidoService.svc/PreencheArtistaMusica")
    void getMusicas(@Body ReqMusicas req, Callback<Result> callback);

    @POST("/Servico/Pedido/PedidoService.svc/Inserir")
    void pedirMusica(@Body Pedido req, Callback<Result> callback);

    @POST("/Servico/Usuario/UsuarioService.svc/Inserir")
    void cadastrarUsuario(@Body Usuario req, Callback<Result> callback);

}
