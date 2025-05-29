package model;

import java.sql.Date;

public class Lavanderia {
    private int idLavanderia;
    private String CorteL;
    private String maquileroLavanderia;
    private String cantidadAsignadaL;
    private Date fechaEntregaCorteL;
    private String precio;
    private String cantidadEntregadaL;

    public Lavanderia(int idLavanderia,
                      String CorteL,
                      String maquileroLavanderia,
                      String cantidadAsignadaL,
                      Date fechaEntregaCorteL,
                      String precio,
                      String cantidadEntregadaL) {
        this.idLavanderia = idLavanderia;
        this.CorteL = CorteL;
        this.maquileroLavanderia = maquileroLavanderia;
        this.cantidadAsignadaL = cantidadAsignadaL;
        this.fechaEntregaCorteL = fechaEntregaCorteL;
        this.precio = precio;
        this.cantidadEntregadaL = cantidadEntregadaL;
    }

    public int getIdLavanderia() {
        return idLavanderia;
    }

    public void setIdLavanderia(int idLavanderia) {
        this.idLavanderia = idLavanderia;
    }

    public String getCorteL() {
        return CorteL;
    }

    public void setCorteL(String CorteL) {
        this.CorteL = CorteL;
    }

    public String getMaquileroLavanderia() {
        return maquileroLavanderia;
    }

    public void setMaquileroLavanderia(String maquileroLavanderia) {
        this.maquileroLavanderia = maquileroLavanderia;
    }

    public String getCantidadAsignadaL() {
        return cantidadAsignadaL;
    }

    public void setCantidadAsignadaL(String cantidadAsignadaL) {
        this.cantidadAsignadaL = cantidadAsignadaL;
    }

    public Date getFechaEntregaCorteL() {
        return fechaEntregaCorteL;
    }

    public void setFechaEntregaCorteL(Date fechaEntregaCorteL) {
        this.fechaEntregaCorteL = fechaEntregaCorteL;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidadEntregadaL() {
        return cantidadEntregadaL;
    }

    public void setCantidadEntregadaL(String cantidadEntregadaL) {
        this.cantidadEntregadaL = cantidadEntregadaL;
    }
}
