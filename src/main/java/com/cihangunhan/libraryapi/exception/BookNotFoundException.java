package com.cihangunhan.libraryapi.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Kitap bulunamadı: " + id);
    }
}