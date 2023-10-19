package com.example.demo.resource;

import com.example.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

@GraphQlTest(GraphQLController.class)
public class GraphQLControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void user() {
        this.graphQlTester
                .documentName("book")
                .execute()
                .path("book.title")
                .entityList(String.class).isEqualTo(List.of("AndreiBook"));
    }
}
