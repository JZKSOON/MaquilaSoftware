package model;

public class ReporteProduccion {
    private String cliente;
    private String corte;
    private String cantidadProgramada;
    private String marca;
    private String linea;
    private String tipoTela;
    private String mesaCorte;
    private String entregaEncogimientos;
    private String liberacion;
    private String fechaCorte;
    private String cantidadCortada;
    private String estadoBordado;
    private String confeccion;
    private String cantidadAsignadaConfeccion;
    private String cantidadEntregadaConfeccion;
    private String faltantesConfeccion;
    private String porcentajeConfeccion;
    private String lavanderia;
    private String cantidadAsignadaLavanderia;
    private String cantidadEntregadaLavanderia;
    private String faltantesLavanderia;
    private String porcentajeLavanderia;
    private String empaque;
    private String cantidadAsignadaEmpaque;
    private String cantidadEntregadaClientes;
    private String restosPrimeras;
    private String segundasEntregadas;
    private String totalEmpaque;
    private String faltantesEmpaque;
    private String porcentajeEmpaque;

    public ReporteProduccion() {}

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getCorte() { return corte; }
    public void setCorte(String corte) { this.corte = corte; }

    public String getCantidadProgramada() { return cantidadProgramada; }
    public void setCantidadProgramada(String cantidadProgramada) { this.cantidadProgramada = cantidadProgramada; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getLinea() { return linea; }
    public void setLinea(String linea) { this.linea = linea; }

    public String getTipoTela() { return tipoTela; }
    public void setTipoTela(String tipoTela) { this.tipoTela = tipoTela; }

    public String getMesaCorte() { return mesaCorte; }
    public void setMesaCorte(String mesaCorte) { this.mesaCorte = mesaCorte; }

    public String getEntregaEncogimientos() { return entregaEncogimientos; }
    public void setEntregaEncogimientos(String entregaEncogimientos) { this.entregaEncogimientos = entregaEncogimientos; }

    public String getLiberacion() { return liberacion; }
    public void setLiberacion(String liberacion) { this.liberacion = liberacion; }

    public String getFechaCorte() { return fechaCorte; }
    public void setFechaCorte(String fechaCorte) { this.fechaCorte = fechaCorte; }

    public String getCantidadCortada() { return cantidadCortada; }
    public void setCantidadCortada(String cantidadCortada) { this.cantidadCortada = cantidadCortada; }

    public String getEstadoBordado() {
        return estadoBordado;
    }

    public void setEstadoBordado(String estadoBordado) {
        this.estadoBordado = estadoBordado;
    }

public String getConfeccion() { return confeccion; }
    public void setConfeccion(String confeccion) { this.confeccion = confeccion; }

    public String getCantidadAsignadaConfeccion() { return cantidadAsignadaConfeccion; }
    public void setCantidadAsignadaConfeccion(String cantidadAsignadaConfeccion) { this.cantidadAsignadaConfeccion = cantidadAsignadaConfeccion; }

    public String getCantidadEntregadaConfeccion() { return cantidadEntregadaConfeccion; }
    public void setCantidadEntregadaConfeccion(String cantidadEntregadaConfeccion) { this.cantidadEntregadaConfeccion = cantidadEntregadaConfeccion; }

    public String getFaltantesConfeccion() { return faltantesConfeccion; }
    public void setFaltantesConfeccion(String faltantesConfeccion) { this.faltantesConfeccion = faltantesConfeccion; }

    public String getPorcentajeConfeccion() { return porcentajeConfeccion; }
    public void setPorcentajeConfeccion(String porcentajeConfeccion) { this.porcentajeConfeccion = porcentajeConfeccion; }

    public String getLavanderia() { return lavanderia; }
    public void setLavanderia(String lavanderia) { this.lavanderia = lavanderia; }

    public String getCantidadAsignadaLavanderia() { return cantidadAsignadaLavanderia; }
    public void setCantidadAsignadaLavanderia(String cantidadAsignadaLavanderia) { this.cantidadAsignadaLavanderia = cantidadAsignadaLavanderia; }

    public String getCantidadEntregadaLavanderia() { return cantidadEntregadaLavanderia; }
    public void setCantidadEntregadaLavanderia(String cantidadEntregadaLavanderia) { this.cantidadEntregadaLavanderia = cantidadEntregadaLavanderia; }

    public String getFaltantesLavanderia() { return faltantesLavanderia; }
    public void setFaltantesLavanderia(String faltantesLavanderia) { this.faltantesLavanderia = faltantesLavanderia; }

    public String getPorcentajeLavanderia() { return porcentajeLavanderia; }
    public void setPorcentajeLavanderia(String porcentajeLavanderia) { this.porcentajeLavanderia = porcentajeLavanderia; }

    public String getEmpaque() { return empaque; }
    public void setEmpaque(String empaque) { this.empaque = empaque; }

    public String getCantidadAsignadaEmpaque() { return cantidadAsignadaEmpaque; }
    public void setCantidadAsignadaEmpaque(String cantidadAsignadaEmpaque) { this.cantidadAsignadaEmpaque = cantidadAsignadaEmpaque; }

    public String getCantidadEntregadaClientes() { return cantidadEntregadaClientes; }
    public void setCantidadEntregadaClientes(String cantidadEntregadaClientes) { this.cantidadEntregadaClientes = cantidadEntregadaClientes; }

    public String getRestosPrimeras() { return restosPrimeras; }
    public void setRestosPrimeras(String restosPrimeras) { this.restosPrimeras = restosPrimeras; }

    public String getSegundasEntregadas() { return segundasEntregadas; }
    public void setSegundasEntregadas(String segundasEntregadas) { this.segundasEntregadas = segundasEntregadas; }

    public String getTotalEmpaque() { return totalEmpaque; }
    public void setTotalEmpaque(String totalEmpaque) { this.totalEmpaque = totalEmpaque; }

    public String getFaltantesEmpaque() { return faltantesEmpaque; }
    public void setFaltantesEmpaque(String faltantesEmpaque) { this.faltantesEmpaque = faltantesEmpaque; }

    public String getPorcentajeEmpaque() { return porcentajeEmpaque; }
    public void setPorcentajeEmpaque(String porcentajeEmpaque) { this.porcentajeEmpaque = porcentajeEmpaque; }

}
