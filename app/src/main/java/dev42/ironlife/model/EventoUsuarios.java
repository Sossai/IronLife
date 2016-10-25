package dev42.ironlife.model;

import java.util.List;

/**
 * Created by Fernando on 24/10/2016.
 */

public class EventoUsuarios extends Evento {
    private List<Usuario> usuarios;

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
