package com.example.radiosu.web;

import model.Result;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Administrador on 14/06/2015.
 */
public interface WebService {

    @FormUrlEncoded
    @POST("/Servico/Usuario/UsuarioService.svc/Entrar")
    void login(@Field("usuario") String usuario,@Field("senha") String senha, Callback<Result> callback);


    @POST("/Servico/Player/PlayerService.svc/MusicaTocando")
    void musicaTocando(Callback<Result> callback);

}
