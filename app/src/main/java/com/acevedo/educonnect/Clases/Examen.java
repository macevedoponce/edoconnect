package com.acevedo.educonnect.Clases;

public class Examen {
    Integer idExamen;
    String nombreExamen;
    String descripcion;
    Integer estado;

    public Examen() {
    }

    public Examen(Integer idExamen, String nombreExamen, String descripcion, Integer estado) {
        this.idExamen = idExamen;
        this.nombreExamen = nombreExamen;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Integer getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(Integer idExamen) {
        this.idExamen = idExamen;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "idExamen=" + idExamen +
                ", nombreExamen='" + nombreExamen + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado=" + estado +
                '}';
    }
}
