package com.example.bookstore.service;

import com.example.bookstore.dto.BookRequest;
import com.example.bookstore.dto.BookResponse;
import com.example.bookstore.entity.Book;
import com.example.bookstore.exception.DuplicateResourceException;
import com.example.bookstore.exception.ResourceNotFoundException;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateResourceException(
                "Book with ISBN " + request.isbn() + " already exists");
        }
        Book book = new Book();
        applyRequest(book, request);
        return toResponse(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAll() {
        return bookRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BookResponse getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = findOrThrow(id);
        if (!book.getIsbn().equals(request.isbn())
                && bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateResourceException(
                "Book with ISBN " + request.isbn() + " already exists");
        }
        applyRequest(book, request);
        return toResponse(bookRepository.save(book));
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.delete(findOrThrow(id));
    }

    private Book findOrThrow(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    private void applyRequest(Book book, BookRequest r) {
        book.setTitle(r.title());
        book.setAuthor(r.author());
        book.setIsbn(r.isbn());
        book.setPrice(r.price());
        book.setStock(r.stock());
    }

    private BookResponse toResponse(Book b) {
        return new BookResponse(b.getId(), b.getTitle(), b.getAuthor(),
                                b.getIsbn(), b.getPrice(), b.getStock());
    }
}