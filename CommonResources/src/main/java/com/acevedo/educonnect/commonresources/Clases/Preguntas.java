package com.acevedo.educonnect.commonresources.Clases;

import java.util.List;

public class Preguntas {
    Integer id_pregunta;
    String nombre;
    String retroalimentacion;
    Integer puntaje;

    private List<Alternativas> alternativas;
    public void setAlternativas(List<Alternativas> alternativas) {
        this.alternativas = alternativas;
    }

    public List<Alternativas> getAlternativas() {
        return alternativas;
    }

    public Preguntas() {
    }

    public Preguntas(Integer id_pregunta, String nombre, String retroalimentacion, Integer puntaje) {
        this.id_pregunta = id_pregunta;
        this.nombre = nombre;
        this.retroalimentacion = retroalimentacion;
        this.puntaje = puntaje;
    }

    public Integer getId_pregunta() {
        return id_pregunta;
    }

    public void setId_pregunta(Integer id_pregunta) {
        this.id_pregunta = id_pregunta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Preguntas{" +
                "id_pregunta=" + id_pregunta +
                ", nombre='" + nombre + '\'' +
                ", retroalimentacion='" + retroalimentacion + '\'' +
                ", puntaje=" + puntaje +
                '}';
    }
}
