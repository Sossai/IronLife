package dev42.ironlife.model;

/**
 * Created by Fernando on 24/10/2016.
 */

public class Usuario {
    private Integer id;
    private String nickpsn;
    private String nome;
    private String email;
    private Integer idtipousuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickpsn() {
        return nickpsn;
    }

    public void setNickpsn(String nickpsn) {
        this.nickpsn = nickpsn;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIdtipousuario() {
        return idtipousuario;
    }

    public void setIdtipousuario(Integer idtipousuario) {
        this.idtipousuario = idtipousuario;
    }
}
