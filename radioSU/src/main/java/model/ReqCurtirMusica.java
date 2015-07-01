package model;

/**
 * Created by guilherme on 23/06/15.
 */
public class ReqCurtirMusica {

    Integer musicaId = null;
    Integer usuarioId = null;

    public ReqCurtirMusica(Integer musicaId, Integer usuarioid) {
        this.musicaId = musicaId;
        this.usuarioId = usuarioid;
    }
}
