package com.acevedo.educonnect.Clases;

public class EntregaTareas {
    int id;
    String retroalimentacion;
    String url_trabajo;
    int nota;
    String us_nombres;
    String us_apellidos;

    public EntregaTareas(int id, String retroalimentacion, String url_trabajo, int nota, String us_nombres, String us_apellidos) {
        this.id = id;
        this.retroalimentacion = retroalimentacion;
        this.url_trabajo = url_trabajo;
        this.nota = nota;
        this.us_nombres = us_nombres;
        this.us_apellidos = us_apellidos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public String getUrl_trabajo() {
        return url_trabajo;
    }

    public void setUrl_trabajo(String url_trabajo) {
        this.url_trabajo = url_trabajo;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getUs_nombres() {
        return us_nombres;
    }

    public void setUs_nombres(String us_nombres) {
        this.us_nombres = us_nombres;
    }

    public String getUs_apellidos() {
        return us_apellidos;
    }

    public void setUs_apellidos(String us_apellidos) {
        this.us_apellidos = us_apellidos;
    }
}
