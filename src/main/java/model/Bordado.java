package model;

import java.util.Date;

public class Bordado {
    private int idBordado;
    private String corteB;
    private String MaquileroBordado;
    private Date FechaEntradaBordado;
    private Date FechaSalidaBordado;
    private String CostoMaquilaBordado;
    private String CantidadEntregada;

    public Bordado(int idBordado,String corteB, String MaquileroBordado, Date FechaEntradaBordado,
                   Date FechaSalidaBordado, String CostoMaquilaBordado, String CantidadEntregada) {
        this.idBordado = idBordado;
        this.corteB = corteB;
        this.MaquileroBordado = MaquileroBordado;
        this.FechaEntradaBordado = FechaEntradaBordado;
        this.FechaSalidaBordado = FechaSalidaBordado;
        this.CostoMaquilaBordado = CostoMaquilaBordado;
        this.CantidadEntregada = CantidadEntregada;
    }

    public int getIdBordado() {return idBordado;}
    public void setIdBordado(int idBordado) {
        this.idBordado = idBordado;
    }

    public String getCorteB() {return corteB;}
    public void setCorteB(String corteB) { this.corteB = corteB; }

    public String getMaquileroBordado() {return MaquileroBordado;}
    public void setMaquileroBordado(String MaquileroBordado) {
        this.MaquileroBordado = MaquileroBordado;
    }

    public Date getFechaEntradaBordado() {return FechaEntradaBordado;}
    public void setFechaEntradaBordado(Date FechaEntradaBordado) {
        this.FechaEntradaBordado = FechaEntradaBordado;
    }

    public Date getFechaSalidaBordado() {return FechaSalidaBordado;}
    public void setFechaSalidaBordado(Date FechaSalidaBordado) {
        this.FechaSalidaBordado = FechaSalidaBordado;
    }

    public String getCostoMaquilaBordado() {return CostoMaquilaBordado;}
    public void setCostoMaquilaBordado(String CostoMaquilaBordado) {
        this.CostoMaquilaBordado = CostoMaquilaBordado;
    }

    public String getCantidadEntregada() {return CantidadEntregada;}
    public void setCantidadEntregada(String CantidadEntregada) {
        this.CantidadEntregada = CantidadEntregada;
    }
}
