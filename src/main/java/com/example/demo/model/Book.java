package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int publicationYear;
    @ManyToOne
    private Author author;

    public Book(String title, int publicationYear, Author author) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
    }

    public static List<Book> books = Arrays.asList(
            new Book(1L, "Harry Potter and the Philosopher's Stone", 223, new Author()),
            new Book(2L, "Moby Dick", 635, new Author()),
            new Book(3L, "Interview with the vampire", 371, new Author())
    );

    public static Book getById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }
}
