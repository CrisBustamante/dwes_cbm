package org.example.completeapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

// Anotaciones Lombok
@Setter
@Getter
@ToString
@NoArgsConstructor

// Anotaciones Hibernate
@Entity
public class Book {

    // Indicamos que este campo es la clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicamos que el valor de la clave primaria se generará automáticamente
    private Long id;

    // Indicamos la columna título de la tabla, es una cadena, no puede ser nulo
    @Column(name = "title", nullable = false)
    private String title;

    // Indicamos la columna isbn de la tabla, es una cadena, no puede ser nulo
    @Column(name = "isbn", nullable = false)
    private String isbn;

    // Indicamos la columna páginas de la tabla, es un entero, no puede ser nulo
    @Column(name = "pages")
    private int pages;

    // Indicamos la columna precio de la tabla, es un double, no puede ser nulo
    @Column(name = "price")
    private double price;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    // Hacemos la relación muchos a uno con la tabla authors
    @ManyToOne(cascade = CascadeType.REMOVE) // Indicamos que se eliminará el libro si se elimina el autor
    @JoinColumn(name = "author_id", nullable = false) // Indicamos la columna de la tabla authors que se usará para la relación
    private Author author; // Tendremos un objeto de tipo Author

    public Book(String title, String isbn, int pages, double price, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.pages = pages;
        this.price = price;
        this.author = author;
    }
}
