package model;

import java.time.LocalDate;

public class Corte {
    private int idCorte;
    private String EntregEncog;
    private String LiberTrazo;
    private String FechaCorte;
    private String PrecioCorte;
    private String CantidadCortada;

    public Corte(int idCorte, String EntregEncog, String LiberTrazo,
                 String FechaCorte, String PrecioCorte, String CantidadCortada) {
        this.idCorte = idCorte;
        this.EntregEncog = EntregEncog;
        this.LiberTrazo = LiberTrazo;
        this.FechaCorte = FechaCorte;
        this.PrecioCorte = PrecioCorte;
        this.CantidadCortada = CantidadCortada;
    }

    public int getIdCorte() {return idCorte;}

    public void setIdCorte(int idCorte) {
        this.idCorte = idCorte;
    }
    public String getEntregEncog() {return EntregEncog;}

    public void setEntregEncog(String EntregEncog) {
        this.EntregEncog = EntregEncog;
    }
    public String getLiberTrazo() {return LiberTrazo;}
    public void setLiberTrazo(String LiberTrazo) {
        this.LiberTrazo = LiberTrazo;
    }
    public String getFechaCorte() {return FechaCorte;}
    public void setFechaCorte(String FechaCorte) {
        this.FechaCorte = FechaCorte;
    }
    public String getPrecioCorte() {return PrecioCorte;}
    public void setPrecioCorte(String PrecioCorte) {
        this.PrecioCorte = PrecioCorte;
    }
    public String getCantidadCortada() {return CantidadCortada;}
    public void setCantidadCortada(String CantidadCortada) {
        this.CantidadCortada = CantidadCortada;
    }
}