// model/Confeccion.java
package model;

import java.util.Date;

public class Confeccion {
    private int idConfeccion;
    private String MaquileroConfeccion;
    private String PrecioConfeccion;
    private String CantidadAsignadaConfeccion;
    private Date FechaEntregaCorteConfeccion;

    public Confeccion(int idConfeccion,
                      String MaquileroConfeccion,
                      String PrecioConfeccion,
                      String CantidadAsignadaConfeccion,
                      Date FechaEntregaCorteConfeccion) {
        this.idConfeccion = idConfeccion;
        this.MaquileroConfeccion = MaquileroConfeccion;
        this.PrecioConfeccion = PrecioConfeccion;
        this.CantidadAsignadaConfeccion = CantidadAsignadaConfeccion;
        this.FechaEntregaCorteConfeccion = FechaEntregaCorteConfeccion;
    }

    public int getIdConfeccion() {
        return idConfeccion;
    }
    public void setIdConfeccion(int idConfeccion) {
        this.idConfeccion = idConfeccion;
    }

    public String getMaquileroConfeccion() {
        return MaquileroConfeccion;
    }
    public void setMaquileroConfeccion(String maquileroConfeccion) {
        MaquileroConfeccion = maquileroConfeccion;
    }

    public String getPrecioConfeccion() {
        return PrecioConfeccion;
    }
    public void setPrecioConfeccion(String precioConfeccion) {
        PrecioConfeccion = precioConfeccion;
    }

    public String getCantidadAsignadaConfeccion() {
        return CantidadAsignadaConfeccion;
    }
    public void setCantidadAsignadaConfeccion(String cantidadAsignadaConfeccion) {
        CantidadAsignadaConfeccion = cantidadAsignadaConfeccion;
    }

    public Date getFechaEntregaCorteConfeccion() {
        return FechaEntregaCorteConfeccion;
    }
    public void setFechaEntregaCorteConfeccion(Date fechaEntregaCorteConfeccion) {
        FechaEntregaCorteConfeccion = fechaEntregaCorteConfeccion;
    }
}
