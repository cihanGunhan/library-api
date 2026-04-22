package com.cihangunhan.libraryapi.dto;

import com.cihangunhan.libraryapi.entity.BookStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer pageCount;
    private BookStatus status;
    private String notes;
}