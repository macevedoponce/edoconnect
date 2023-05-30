package com.acevedo.educonnect.Clases;

public class Alternativas {
    Integer idAlternativa;
    String nombreAlternativa;
    Integer preguntaid;
    Integer esCorrecto;

    public Alternativas() {
    }

    public Alternativas(Integer idAlternativa, String nombreAlternativa, Integer preguntaid, Integer esCorrecto) {
        this.idAlternativa = idAlternativa;
        this.nombreAlternativa = nombreAlternativa;
        this.preguntaid = preguntaid;
        this.esCorrecto = esCorrecto;
    }

    public Integer getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(Integer idAlternativa) {
        this.idAlternativa = idAlternativa;
    }

    public String getNombreAlternativa() {
        return nombreAlternativa;
    }

    public void setNombreAlternativa(String nombreAlternativa) {
        this.nombreAlternativa = nombreAlternativa;
    }

    public Integer getPreguntaid() {
        return preguntaid;
    }

    public void setPreguntaid(Integer preguntaid) {
        this.preguntaid = preguntaid;
    }

    public Integer getEsCorrecto() {
        return esCorrecto;
    }

    public void setEsCorrecto(Integer esCorrecto) {
        this.esCorrecto = esCorrecto;
    }

    @Override
    public String toString() {
        return "Alternativas{" +
                "idAlternativa=" + idAlternativa +
                ", nombreAlternativa='" + nombreAlternativa + '\'' +
                ", preguntaid=" + preguntaid +
                ", esCorrecto=" + esCorrecto +
                '}';
    }
}
