package modelo;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String editorial;
    private int anio;
    private boolean disponible;

    // Constructor sin ID (para crear nuevos)
    public Libro(String titulo, String autor, String editorial, int anio, boolean disponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.disponible = disponible;
    }

    // ✅ Constructor CON ID (para editar / listar)
    public Libro(int id, String titulo, String autor, String editorial, int anio, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.disponible = disponible;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
