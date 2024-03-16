package com.acevedo.educonnect.commonresources.Clases;

public class Curso {
    int id;
    String cursoNombre;
    String grado;
    String seccion;
    String img_url;

    public Curso(int id, String cursoNombre, String grado, String seccion, String img_url) {
        this.id = id;
        this.cursoNombre = cursoNombre;
        this.grado = grado;
        this.seccion = seccion;
        this.img_url = img_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCursoNombre() {
        return cursoNombre;
    }

    public void setCursoNombre(String cursoNombre) {
        this.cursoNombre = cursoNombre;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
