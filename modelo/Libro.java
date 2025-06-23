package modelo;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String editorial;
    private int anio;
    private boolean disponible;

    // Constructor vacío (opcional)
    public Libro() {}

    // Constructor con parámetros
    public Libro(String titulo, String autor, String editorial, int anio, boolean disponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.disponible = disponible;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getAnio() {
        return anio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public int getId() {
        return id;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setId(int id) {
        this.id = id;
    }
}
