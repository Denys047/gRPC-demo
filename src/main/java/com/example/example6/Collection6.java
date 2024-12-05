package com.example.example6;

import org.example.grpc.proto.collection.Book;
import org.example.grpc.proto.collection.Library;

import java.util.List;
import java.util.Set;

public class Collection6 {

    public static void main(String[] args) {
        Book book1 = Book.newBuilder().
                setTitle("Book Title")
                .setAuthor("anti-mage1")
                .setIsbn("1")
                .setPublicationYear(2000)
                .build();

        Book book2 = Book.newBuilder().
                setTitle("Book Title")
                .setAuthor("anti-mage")
                .setIsbn("1")
                .setPublicationYear(2000)
                .build();

        Book book3 = Book.newBuilder().
                setTitle("Book Title")
                .setAuthor("anti-mage")
                .setIsbn("12")
                .setPublicationYear(2000)
                .build();

        Library library = Library.newBuilder()
                .setId(1L)
                .addAllBooks(Set.of(book1, book2, book3))
                .setName("proto")
                .build();

        System.out.println(library);
    }

}
