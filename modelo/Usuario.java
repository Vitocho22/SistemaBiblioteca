package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String tipo;
    private String correo;

    public Usuario(String nombre, String tipo, String correo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.correo = correo;
    }

    public Usuario(int id, String nombre, String tipo, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.correo = correo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
