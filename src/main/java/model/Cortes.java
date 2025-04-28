package model;

public class Cortes {
    private int idCortes;
    private String NumCorte;
    private String Cantidad;
    private String Marca;
    private String Trazo;
    private String Linea;
    private String FechTela;
    private String LlegTela;
    private String TipoTela;


    public Cortes(int id, String NumeroCorte, String Cantidad, String Marca, String Trazo,
                   String Linea, String FechaTela, String LLegadaTela, String TipoDeTela) {
        this.idCortes = id;
        this.NumCorte = NumeroCorte;
        this.Cantidad = Cantidad;
        this.Marca = Marca;
        this.Trazo = Trazo;
        this.Linea = Linea;
        this.FechTela = FechaTela;
        this.LlegTela = LLegadaTela;
        this.TipoTela = TipoDeTela;
    }

    public int getidCortes() {
        return idCortes;
    }

    public String getNumCorte() {
        return NumCorte;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public String getMarca() {
        return Marca;
    }

    public String getTrazo() {
        return Trazo;
    }

    public String getLinea() {
        return Linea;
    }

    public String getFechTela() {
        return FechTela;
    }

    public String getLlegTela() { return LlegTela; }

    public String getTipoTela() { return TipoTela; }


    public void setidCortes(int idCortes) {this.idCortes = idCortes;}

    public void setNumCorte(String NumCorte) {this.NumCorte = NumCorte;}

    public void setCantidad(String Cantidad) {this.Cantidad = Cantidad;}

    public void setMarca(String Marca) {this.Marca = Marca;}

    public void setTrazo(String Trazo) {this.Trazo = Trazo;}

    public void setLinea(String Linea) {this.Linea = Linea;}

    public void setFechTela(String FechTela) {this.FechTela = FechTela;}

    public void setLlegTela(String LlegTela) {this.LlegTela = LlegTela;}

    public void setTipoTela(String TipoTela) {this.TipoTela = TipoTela;}
}
