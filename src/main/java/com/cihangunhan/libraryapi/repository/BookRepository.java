package com.cihangunhan.libraryapi.repository;

import com.cihangunhan.libraryapi.entity.Book;
import com.cihangunhan.libraryapi.entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByStatus(BookStatus status);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    boolean existsByIsbn(String isbn);
}