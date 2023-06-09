package com.acevedo.educonnect.commonresources.Clases;

public class Tarea {
    int id;
    String titulo;
    String descripcion;
    String fecha_limite;
    String retroalimentacion;
    String url_trabajo;
    int nota;
    int estado;
    int curso_id;

    public Tarea(int id, String titulo, String descripcion, String fecha_limite, int estado, int curso_id) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_limite = fecha_limite;
        this.estado = estado;
        this.curso_id = curso_id;
    }

    public Tarea(int id, String titulo, String descripcion, String fecha_limite, String retroalimentacion, String url_trabajo, int nota) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_limite = fecha_limite;
        this.retroalimentacion = retroalimentacion;
        this.url_trabajo = url_trabajo;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(int curso_id) {
        this.curso_id = curso_id;
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
}
