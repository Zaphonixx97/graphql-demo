package com.example.demo.resource;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest(GraphQLController.class)
public class GraphQLControllerTest {

    private static Author testAuthor;
    private static Book testBook1;
    private static Book testBook2;
    @Autowired
    private GraphQlTester graphQlTester;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;

    @BeforeAll
    static void init() {
        testAuthor = new Author("Cosmin");
        testBook1 = new Book("GraphQL", 2023, testAuthor);
        testBook2 = new Book("GraphQL", 2023, testAuthor);
    }

    @Test
    void testGetAuthors() {
        var response = List.of(testAuthor);
        when(authorRepository.findAll()).thenReturn(response);

        this.graphQlTester
                .documentName("authors")
                .execute()
                .path("authors")
                .entityList(Author.class)
                .isEqualTo(response);
    }

    @Test
    void testCreateAuthor() {
        var response = testAuthor;
        response.setId(null);
        when(authorRepository.save(any(Author.class))).thenReturn(testAuthor);

        this.graphQlTester
                .documentName("createAuthor")
                .variable("name", "Cosmin")
                .execute()
                .path("createAuthor")
                .entity(Author.class)
                .isEqualTo(testAuthor);
    }

    @Test
    void testGetBooks() {
        var repositoryResponse = List.of(testBook1, testBook2);
        var responseBook1 = testBook1;
        responseBook1.setAuthor(null);
        var responseBook2 = testBook2;
        responseBook2.setAuthor(null);
        var response = List.of(responseBook1, responseBook2);
        when(bookRepository.findAll()).thenReturn(repositoryResponse);

        this.graphQlTester
                .documentName("books")
                .execute()
                .path("books")
                .entityList(Book.class)
                .isEqualTo(response);
    }

    @Test
    void testUpdateBook() {
        var response = new Book(1L, "GraphQLNewEdition", 2024, testAuthor);
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(testBook1));
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(testAuthor));
        when(bookRepository.save(any(Book.class))).thenReturn(response);

        this.graphQlTester
                .documentName("updateBook")
                .variable("id", 1)
                .variable("title", "GraphQLNewEdition")
                .variable("publicationYear", 2024)
                .variable("authorId", 1)
                .execute()
                .path("updateBook")
                .entity(Book.class)
                .isEqualTo(response);
    }
}
