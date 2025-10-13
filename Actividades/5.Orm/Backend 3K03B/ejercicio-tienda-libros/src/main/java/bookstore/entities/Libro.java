package bookstore.entities;

import bookstore.entities.Autor;
import bookstore.entities.Editorial;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private String isbn;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "stock")
    private int stock;

    @Column(name = "precio")
    private double precio;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;

    public Libro() {}

    public Libro(String isbn, String titulo, int stock, double precio, Autor autor, Editorial editorial) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.stock = stock;
        this.precio = precio;
        this.autor = autor;
        this.editorial = editorial;
    }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public Editorial getEditorial() { return editorial; }
    public void setEditorial(Editorial editorial) { this.editorial = editorial; }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                ", autor=" + (autor != null ? autor.getNombre() + " " + autor.getApellido() : "N/A") +
                ", editorial=" + (editorial != null ? editorial.getNombre() : "N/A") +
                '}';
    }
}
