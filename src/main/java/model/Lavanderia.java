package model;

public class Lavanderia {
    private int idLavanderia;
    private String maquilero;
    private String precio;

    public Lavanderia(int idLavanderia, String maquilero, String precio) {
        this.idLavanderia = idLavanderia;
        this.maquilero = maquilero;
        this.precio = precio;
    }

    public int getIdLavanderia() {
        return idLavanderia;
    }
    public void setIdLavanderia(int idLavanderia) {
        this.idLavanderia = idLavanderia;
    }

    public String getMaquilero() {
        return maquilero;
    }
    public void setMaquilero(String maquilero) {
        this.maquilero = maquilero;
    }

    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
}