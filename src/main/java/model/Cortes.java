package model;

import java.time.LocalDate;

public class Cortes {
    private int idCortes;
    private String NumCorte;
    private String Cantidad;
    private String Marca;
    private String Linea;
    private String TipoTela;
    private LocalDate Trazo;
    private LocalDate FechTela;
    private String LlegTela;

    public Cortes(int idCortes, String numCorte, String cantidad, String marca,
                  String linea, String tipoTela, LocalDate trazo,
                  LocalDate fechTela, String llegTela) {
        this.idCortes = idCortes;
        this.NumCorte = numCorte;
        this.Cantidad = cantidad;
        this.Marca = marca;
        this.Linea = linea;
        this.TipoTela = tipoTela;
        this.Trazo = trazo;
        this.FechTela = fechTela;
        this.LlegTela = llegTela;
    }

    public int getIdCortes() {
        return idCortes;
    }
    public void setIdCortes(int idCortes) {
        this.idCortes = idCortes;
    }

    public String getNumCorte() {
        return NumCorte;
    }
    public void setNumCorte(String numCorte) {
        this.NumCorte = numCorte;
    }

    public String getCantidad() {
        return Cantidad;
    }
    public void setCantidad(String cantidad) {
        this.Cantidad = cantidad;
    }

    public String getMarca() {
        return Marca;
    }
    public void setMarca(String marca) {
        this.Marca = marca;
    }

    public String getLinea() {
        return Linea;
    }
    public void setLinea(String linea) {
        this.Linea = linea;
    }

    public String getTipoTela() {
        return TipoTela;
    }
    public void setTipoTela(String tipoTela) {
        this.TipoTela = tipoTela;
    }

    public LocalDate getTrazo() {
        return Trazo;
    }
    public void setTrazo(LocalDate trazo) {
        this.Trazo = trazo;
    }

    public LocalDate getFechTela() {
        return FechTela;
    }
    public void setFechTela(LocalDate fechTela) {
        this.FechTela = fechTela;
    }

    public String getLlegTela() {
        return LlegTela;
    }
    public void setLlegTela(String llegTela) {
        this.LlegTela = llegTela;
    }
}