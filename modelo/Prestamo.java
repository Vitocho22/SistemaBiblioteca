package modelo;

import java.util.Date;

public class Prestamo {
    private int id;
    private int idLibro;
    private int idUsuario;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private boolean devuelto;

    // Constructor para guardar
    public Prestamo(int idLibro, int idUsuario, Date fechaPrestamo, Date fechaDevolucion, boolean devuelto) {
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    // Constructor completo
    public Prestamo(int id, int idLibro, int idUsuario, Date fechaPrestamo, Date fechaDevolucion, boolean devuelto) {
        this.id = id;
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    // Getters y Setters
    public int getId() { return id; }
    public int getIdLibro() { return idLibro; }
    public int getIdUsuario() { return idUsuario; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public Date getFechaDevolucion() { return fechaDevolucion; }
    public boolean isDevuelto() { return devuelto; }

    public void setId(int id) { this.id = id; }
    public void setDevuelto(boolean devuelto) { this.devuelto = devuelto; }
}
