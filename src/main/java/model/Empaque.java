// model/Empaque.java
package model;

public class Empaque {
    private String idEmpaque;
    private String corteE;
    private String maquileroEmpaque;
    private String cantidadAsignadaEmpaque;
    private String precioEmpaque;
    private String cantidadEntregadaEC;
    private String restosPrimerasEntregadas;
    private String segundasEntregadas;

    public Empaque(String idEmpaque,
                   String corteE,
                   String maquileroEmpaque,
                   String cantidadAsignadaEmpaque,
                   String precioEmpaque,
                   String cantidadEntregadaEC,
                   String restosPrimerasEntregadas,
                   String segundasEntregadas) {
        this.idEmpaque = idEmpaque;
        this.corteE = corteE;
        this.maquileroEmpaque = maquileroEmpaque;
        this.cantidadAsignadaEmpaque = cantidadAsignadaEmpaque;
        this.precioEmpaque = precioEmpaque;
        this.cantidadEntregadaEC = cantidadEntregadaEC;
        this.restosPrimerasEntregadas = restosPrimerasEntregadas;
        this.segundasEntregadas = segundasEntregadas;
    }

    public String getIdEmpaque() { return idEmpaque; }
    public void setIdEmpaque(String idEmpaque) { this.idEmpaque = idEmpaque; }

    public String getCorteE() { return corteE; }
    public void setCorteE(String corteE) { this.corteE = corteE; }

    public String getMaquileroEmpaque() { return maquileroEmpaque; }
    public void setMaquileroEmpaque(String maquileroEmpaque) { this.maquileroEmpaque = maquileroEmpaque; }

    public String getCantidadAsignadaEmpaque() { return cantidadAsignadaEmpaque; }
    public void setCantidadAsignadaEmpaque(String cantidadAsignadaEmpaque) { this.cantidadAsignadaEmpaque = cantidadAsignadaEmpaque; }

    public String getPrecioEmpaque() { return precioEmpaque; }
    public void setPrecioEmpaque(String precioEmpaque) { this.precioEmpaque = precioEmpaque; }

    public String getCantidadEntregadaEC() { return cantidadEntregadaEC; }
    public void setCantidadEntregadaEC(String cantidadEntregadaEC) { this.cantidadEntregadaEC = cantidadEntregadaEC; }

    public String getRestosPrimerasEntregadas() { return restosPrimerasEntregadas; }
    public void setRestosPrimerasEntregadas(String restosPrimerasEntregadas) { this.restosPrimerasEntregadas = restosPrimerasEntregadas; }

    public String getSegundasEntregadas() { return segundasEntregadas; }
    public void setSegundasEntregadas(String segundasEntregadas) { this.segundasEntregadas = segundasEntregadas; }
}
