package dev42.ironlife.model;

/**
 * Created by Fernando on 05/10/2016.
 */
//  **  Olhar Webview   **
//  **  https://developer.android.com/reference/android/webkit/WebView.html
public class News {
    private Integer id;
    private String titulo;
    private String imagem;
    private String textNews;
    private String data;

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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTextNews() {
        return textNews;
    }

    public void setTextNews(String textNews) {
        this.textNews = textNews;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
