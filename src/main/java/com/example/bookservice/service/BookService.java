package com.example.bookservice.service;

import com.example.bookservice.exception.BookConflictException;
import com.example.bookservice.exception.ResourceNotFoundException;
import com.example.bookservice.model.Book;
import com.example.bookservice.model.enums.BookStatus;
import com.example.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID id) { // Parâmetro agora é UUID
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com o ID: " + id));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(UUID id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitulo(bookDetails.getTitulo());
        book.setAutor(bookDetails.getAutor());
        book.setStatus(bookDetails.getStatus());
        return bookRepository.save(book);
    }

    public Book updateBookStatus(UUID id, Map<String, String> updates) {
        if (!updates.containsKey("status")) {
            throw new IllegalArgumentException("O campo para atualização deve ser 'status'.");
        }

        Book book = getBookById(id);
        String newStatusStr = updates.get("status").toUpperCase();
        BookStatus newStatus;

        try {
            newStatus = BookStatus.valueOf(newStatusStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("O status deve ser 'DISPONIVEL' ou 'RESERVADO'.");
        }

        if (book.getStatus() == newStatus) {
            throw new BookConflictException("O livro já se encontra no estado '" + newStatus.name().toLowerCase() + "'.");
        }

        book.setStatus(newStatus);
        return bookRepository.save(book);
    }
}