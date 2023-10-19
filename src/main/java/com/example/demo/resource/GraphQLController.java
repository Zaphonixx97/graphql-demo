package com.example.demo.resource;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @QueryMapping
    public Book book(@Argument Long id) {
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @QueryMapping
    public List<Book> books() {
        return bookRepository.findAll();
    }

    @MutationMapping
    public Book createBook(@Argument String title, @Argument int publicationYear, @Argument Long authorId) {
        var author = this.authorRepository.findById(authorId).orElseThrow(RuntimeException::new);
        var book =  new Book(title, publicationYear, author);
        return bookRepository.save(book);
    }

    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument String title, @Argument int publicationYear, @Argument Long authorId) {
        var book = this.bookRepository.findById(id).orElseThrow(RuntimeException::new);
        var author = this.authorRepository.findById(authorId).orElseThrow(RuntimeException::new);
        book.setTitle(title);
        book.setPublicationYear(publicationYear);
        book.setAuthor(author);
        return this.bookRepository.save(book);
    }

    @MutationMapping
    public boolean deleteBook(@Argument Long id) {
        this.bookRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public Author author(@Argument Long id) {
        return authorRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @QueryMapping
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @MutationMapping
    public Author createAuthor(@Argument String name) {
        var author = new Author(name);
        return authorRepository.save(author);
    }

    @MutationMapping
    public Author updateAuthor(@Argument Long id, @Argument String name) {
        var author = this.authorRepository.findById(id).orElseThrow(RuntimeException::new);
        author.setName(name);
        return this.authorRepository.save(author);
    }

    @MutationMapping
    public boolean deleteAuthor(@Argument Long id) {
        this.authorRepository.deleteById(id);
        return true;
    }

}
