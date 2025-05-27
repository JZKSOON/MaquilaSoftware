package model;

import java.time.LocalDate;

public class Corte {
    private int idCorte;
    private String CorteMC;
    private String EntregaEncogimientos;
    private String LiberacionTrazo;
    private String FechaCorte;
    private String PrecioDeCorte;
    private String CantidadCortada;

    public Corte(int idCorte,String CorteMC, String EntregaEncogimientos, String LiberacionTrazo,
                 String FechaCorte, String PrecioDeCorte, String CantidadCortada) {
        this.idCorte = idCorte;
        this.CorteMC = CorteMC;
        this.EntregaEncogimientos = EntregaEncogimientos;
        this.LiberacionTrazo = LiberacionTrazo;
        this.FechaCorte = FechaCorte;
        this.PrecioDeCorte = PrecioDeCorte;
        this.CantidadCortada = CantidadCortada;
    }

    public int getIdCorte() {return idCorte;}
    public void setIdCorte(int idCorte) {
        this.idCorte = idCorte;
    }

    public String getCorteMC() {return CorteMC;}
    public void setCorteMC(String CorteMC) {this.CorteMC = CorteMC;}

    public String getEntregaEncogimientos() {return EntregaEncogimientos;}
    public void setEntregaEncogimientos(String EntregaEncogimientos) {this.EntregaEncogimientos = EntregaEncogimientos;}

    public String getLiberacionTrazo() {return LiberacionTrazo;}
    public void setLiberacionTrazo(String LiberacionTrazo) {
        this.LiberacionTrazo = LiberacionTrazo;
    }

    public String getFechaCorte() {return FechaCorte;}
    public void setFechaCorte(String FechaCorte) {
        this.FechaCorte = FechaCorte;
    }

    public String getPrecioDeCorte() {return PrecioDeCorte;}
    public void setPrecioDeCorte(String PrecioDeCorte) {
        this.PrecioDeCorte = PrecioDeCorte;
    }

    public String getCantidadCortada() {return CantidadCortada;}
    public void setCantidadCortada(String CantidadCortada) {
        this.CantidadCortada = CantidadCortada;
    }
}