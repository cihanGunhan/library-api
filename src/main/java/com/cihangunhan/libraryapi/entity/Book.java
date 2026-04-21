package com.cihangunhan.libraryapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Kitap adı boş olamaz")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Yazar adı boş olamaz")
    @Column(nullable = false)
    private String author;

    @Column(unique = true)
    private String isbn;

    @Min(value = 1, message = "Sayfa sayısı en az 1 olmalı")
    private Integer pageCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status = BookStatus.UNREAD;

    private String notes;
}