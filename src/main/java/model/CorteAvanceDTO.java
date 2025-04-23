package model;

public class CorteAvanceDTO {
    private int numeroCorte;
    private double porcentajeAvance;

    public CorteAvanceDTO(int numeroCorte, double porcentajeAvance) {
        this.numeroCorte = numeroCorte;
        this.porcentajeAvance = porcentajeAvance;
    }

    public int getNumeroCorte() {
        return numeroCorte;
    }

    public double getPorcentajeAvance() {
        return porcentajeAvance;
    }
}
