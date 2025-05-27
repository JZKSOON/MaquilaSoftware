// model/Empaque.java
package model;

public class Empaque {
    private String idEmpaque;
    private String maquileroEmpaque;
    private String precioEmpaque;
    private String cantidadEntregadaEC;
    private String restosPrimerasEntregadas;
    private String segundasEntregadas;

    public Empaque(String idEmpaque,
                   String maquileroEmpaque,
                   String precioEmpaque,
                   String cantidadEntregadaEC,
                   String restosPrimerasEntregadas,
                   String segundasEntregadas) {
        this.idEmpaque = idEmpaque;
        this.maquileroEmpaque = maquileroEmpaque;
        this.precioEmpaque = precioEmpaque;
        this.cantidadEntregadaEC = cantidadEntregadaEC;
        this.restosPrimerasEntregadas = restosPrimerasEntregadas;
        this.segundasEntregadas = segundasEntregadas;
    }

    public String getIdEmpaque() { return idEmpaque; }
    public void setIdEmpaque(String idEmpaque) { this.idEmpaque = idEmpaque; }

    public String getMaquileroEmpaque() { return maquileroEmpaque; }
    public void setMaquileroEmpaque(String maquileroEmpaque) { this.maquileroEmpaque = maquileroEmpaque; }

    public String getPrecioEmpaque() { return precioEmpaque; }
    public void setPrecioEmpaque(String precioEmpaque) { this.precioEmpaque = precioEmpaque; }

    public String getCantidadEntregadaEC() { return cantidadEntregadaEC; }
    public void setCantidadEntregadaEC(String cantidadEntregadaEC) { this.cantidadEntregadaEC = cantidadEntregadaEC; }

    public String getRestosPrimerasEntregadas() { return restosPrimerasEntregadas; }
    public void setRestosPrimerasEntregadas(String restosPrimerasEntregadas) { this.restosPrimerasEntregadas = restosPrimerasEntregadas; }

    public String getSegundasEntregadas() { return segundasEntregadas; }
    public void setSegundasEntregadas(String segundasEntregadas) { this.segundasEntregadas = segundasEntregadas; }
}
