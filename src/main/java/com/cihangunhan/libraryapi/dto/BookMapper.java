package com.cihangunhan.libraryapi.dto;

import com.cihangunhan.libraryapi.entity.Book;

public class BookMapper {

    public static Book toEntity(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPageCount(request.getPageCount());
        book.setStatus(request.getStatus());
        book.setNotes(request.getNotes());
        return book;
    }

    public static BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setIsbn(book.getIsbn());
        response.setPageCount(book.getPageCount());
        response.setStatus(book.getStatus());
        response.setNotes(book.getNotes());
        return response;
    }
}