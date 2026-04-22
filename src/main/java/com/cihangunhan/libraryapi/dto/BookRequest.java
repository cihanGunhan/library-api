package com.cihangunhan.libraryapi.dto;

import com.cihangunhan.libraryapi.entity.BookStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "Kitap adı boş olamaz")
    private String title;

    @NotBlank(message = "Yazar adı boş olamaz")
    private String author;

    private String isbn;

    @Min(value = 1, message = "Sayfa sayısı en az 1 olmalı")
    private Integer pageCount;

    private BookStatus status = BookStatus.UNREAD;

    private String notes;
}