package com.cihangunhan.libraryapi.service;

import com.cihangunhan.libraryapi.dto.BookMapper;
import com.cihangunhan.libraryapi.dto.BookRequest;
import com.cihangunhan.libraryapi.dto.BookResponse;
import com.cihangunhan.libraryapi.entity.Book;
import com.cihangunhan.libraryapi.entity.BookStatus;
import com.cihangunhan.libraryapi.exception.BookNotFoundException;
import com.cihangunhan.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return BookMapper.toResponse(book);
    }

    public BookResponse createBook(BookRequest request) {
        if (request.getIsbn() != null &&
                bookRepository.existsByIsbn(request.getIsbn())) {
            throw new RuntimeException(
                    "Bu ISBN zaten kayıtlı: " + request.getIsbn());
        }
        Book book = BookMapper.toEntity(request);
        return BookMapper.toResponse(bookRepository.save(book));
    }

    public BookResponse updateBook(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        existing.setTitle(request.getTitle());
        existing.setAuthor(request.getAuthor());
        existing.setIsbn(request.getIsbn());
        existing.setPageCount(request.getPageCount());
        existing.setStatus(request.getStatus());
        existing.setNotes(request.getNotes());
        return BookMapper.toResponse(bookRepository.save(existing));
    }

    public void deleteBook(Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.deleteById(id);
    }

    public List<BookResponse> getBooksByStatus(BookStatus status) {
        return bookRepository.findByStatus(status)
                .stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author)
                .stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }
}