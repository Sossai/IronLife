package dev42.ironlife.model;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Fernando on 10/10/2016.
 */

public class Evento implements Serializable {
    private Integer id;
    private String titulo;
    private String dataCriacao;
    private String dataInicio;
    private String horaInicio;
    private String dataEncerramento;
    private String horaEncerramento;
    private Integer idResponsavel;
    private String responsavel;
    private Integer idTipoEvento;
    private String descricaoTipoEvento;
    private String imagem;
    private boolean usuarioRegistrado;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(String dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public String getHoraEncerramento() {
        return horaEncerramento;
    }

    public void setHoraEncerramento(String horaEncerramento) {
        this.horaEncerramento = horaEncerramento;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Integer getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(Integer idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public String getDescricaoTipoEvento() {
        return descricaoTipoEvento;
    }

    public void setDescricaoTipoEvento(String descricaoTipoEvento) {
        this.descricaoTipoEvento = descricaoTipoEvento;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public boolean isUsuarioRegistrado() {
        return usuarioRegistrado;
    }

    public void setUsuarioRegistrado(boolean usuarioRegistrado) {
        this.usuarioRegistrado = usuarioRegistrado;
    }

    public Integer getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }
}
