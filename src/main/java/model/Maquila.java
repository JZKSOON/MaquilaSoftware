package model;

public class Maquila {
    private int idM;
    private String NombreMaquila;
    private String AreaMaquila;
    private String nombreM;
    private String celularM;
    private String direccionM;
    private String estadoM;
    private String municipioM;
    private String cpM;
    private String email;
    private String telefono;

    public Maquila(int id, String razonSocial,String AreaMaquila, String nombre, String celular, String direccion,
                   String estado, String municipio, String cp, String email, String telefono) {
        this.idM = id;
        this.NombreMaquila = razonSocial;
        this.AreaMaquila = AreaMaquila;
        this.nombreM = nombre;
        this.celularM = celular;
        this.direccionM = direccion;
        this.estadoM = estado;
        this.municipioM = municipio;
        this.cpM = cp;
        this.email = email;
        this.telefono = telefono;
    }

    public int getIdM() {
        return idM;
    }

    public String getNombreMaquila() {
        return NombreMaquila;
    }

    public String getAreaMaquila() {return AreaMaquila;}

    public String getNombreM() {
        return nombreM;
    }

    public String getCelularM() {
        return celularM;
    }

    public String getDireccionM() {
        return direccionM;
    }

    public String getEstadoM() {
        return estadoM;
    }

    public String getMunicipioM() {
        return municipioM;
    }

    public String getCpM() {
        return cpM;
    }

    public String getEmailM() {
        return email;
    }

    public String getTelefonoM() {
        return telefono;
    }

    public void setId(int id) {
        this.idM = id;
    }

    public void setNombreMaquila(String nombreMaquila) {
        this.NombreMaquila = nombreMaquila;
    }

    public void setNombreM(String nombreM) {
        this.nombreM = nombreM;
    }

    public void setAreaMaquila(String areaMaquila) {this.AreaMaquila = areaMaquila;}

    public void setCelularM(String celularM) {
        this.celularM = celularM;
    }

    public void setDireccionM(String direccionM) {
        this.direccionM = direccionM;
    }

    public void setEstadoM(String estadoM) {
        this.estadoM = estadoM;
    }

    public void setMunicipioM(String municipioM) {
        this.municipioM = municipioM;
    }

    public void setCp(String cp) {
        this.cpM = cp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
