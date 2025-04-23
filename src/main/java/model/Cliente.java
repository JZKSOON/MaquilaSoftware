package model;

public class Cliente {
    private int id;
    private String razonSocial;
    private String nombre;
    private String celular;
    private String direccion;
    private String estado;
    private String municipio;
    private String cp;
    private String email;
    private String telefono;

    public Cliente(int id, String razonSocial, String nombre, String celular, String direccion,
                   String estado, String municipio, String cp, String email, String telefono) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.nombre = nombre;
        this.celular = celular;
        this.direccion = direccion;
        this.estado = estado;
        this.municipio = municipio;
        this.cp = cp;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCelular() {
        return celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstado() {
        return estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getCp() {
        return cp;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
