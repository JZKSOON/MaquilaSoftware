package model;

import java.time.LocalDate;

public class Corte {
    private int idCorte;
    private String corteMC;
    private String entregaEncogimientos;
    private LocalDate liberacionTrazo;
    private LocalDate fechaCorte;
    private String precioDeCorte;
    private String cantidadCortada;

    public Corte(int idCorte, String corteMC, String entregaEncogimientos,
                 LocalDate liberacionTrazo, LocalDate fechaCorte,
                 String precioDeCorte, String cantidadCortada) {
        this.idCorte = idCorte;
        this.corteMC = corteMC;
        this.entregaEncogimientos = entregaEncogimientos;
        this.liberacionTrazo = liberacionTrazo;
        this.fechaCorte = fechaCorte;
        this.precioDeCorte = precioDeCorte;
        this.cantidadCortada = cantidadCortada;
    }

    public int getIdCorte() { return idCorte; }
    public void setIdCorte(int idCorte) { this.idCorte = idCorte; }

    public String getCorteMC() { return corteMC; }
    public void setCorteMC(String corteMC) { this.corteMC = corteMC; }

    public String getEntregaEncogimientos() { return entregaEncogimientos; }
    public void setEntregaEncogimientos(String entregaEncogimientos) { this.entregaEncogimientos = entregaEncogimientos; }

    public LocalDate getLiberacionTrazo() { return liberacionTrazo; }
    public void setLiberacionTrazo(LocalDate liberacionTrazo) { this.liberacionTrazo = liberacionTrazo; }

    public LocalDate getFechaCorte() { return fechaCorte; }
    public void setFechaCorte(LocalDate fechaCorte) { this.fechaCorte = fechaCorte; }

    public String getPrecioDeCorte() { return precioDeCorte; }
    public void setPrecioDeCorte(String precioDeCorte) { this.precioDeCorte = precioDeCorte; }

    public String getCantidadCortada() { return cantidadCortada; }
    public void setCantidadCortada(String cantidadCortada) { this.cantidadCortada = cantidadCortada; }
}
