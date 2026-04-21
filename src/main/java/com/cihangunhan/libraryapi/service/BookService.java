package com.cihangunhan.libraryapi.service;

import com.cihangunhan.libraryapi.entity.Book;
import com.cihangunhan.libraryapi.entity.BookStatus;
import com.cihangunhan.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı: " + id));
    }

    public Book createBook(Book book) {
        if (book.getIsbn() != null && bookRepository.existsByIsbn(book.getIsbn())) {
            throw new RuntimeException("Bu ISBN zaten kayıtlı: " + book.getIsbn());
        }
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existing = getBookById(id);
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setIsbn(updatedBook.getIsbn());
        existing.setPageCount(updatedBook.getPageCount());
        existing.setStatus(updatedBook.getStatus());
        existing.setNotes(updatedBook.getNotes());
        return bookRepository.save(existing);
    }

    public void deleteBook(Long id) {
        getBookById(id);
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByStatus(BookStatus status) {
        return bookRepository.findByStatus(status);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
}