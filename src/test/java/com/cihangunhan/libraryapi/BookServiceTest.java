package com.cihangunhan.libraryapi;

import com.cihangunhan.libraryapi.dto.BookRequest;
import com.cihangunhan.libraryapi.dto.BookResponse;
import com.cihangunhan.libraryapi.entity.Book;
import com.cihangunhan.libraryapi.entity.BookStatus;
import com.cihangunhan.libraryapi.exception.BookNotFoundException;
import com.cihangunhan.libraryapi.repository.BookRepository;
import com.cihangunhan.libraryapi.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private BookRequest testRequest;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Dune");
        testBook.setAuthor("Frank Herbert");
        testBook.setIsbn("978-0441013593");
        testBook.setPageCount(688);
        testBook.setStatus(BookStatus.UNREAD);
        testBook.setNotes("Bilim kurgu klasiği");

        testRequest = new BookRequest();
        testRequest.setTitle("Dune");
        testRequest.setAuthor("Frank Herbert");
        testRequest.setIsbn("978-0441013593");
        testRequest.setPageCount(688);
        testRequest.setStatus(BookStatus.UNREAD);
        testRequest.setNotes("Bilim kurgu klasiği");
    }

    @Test
    @DisplayName("Tüm kitaplar başarıyla listelenir")
    void getAllBooks_ShouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(testBook));

        List<BookResponse> result = bookService.getAllBooks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Dune");
        assertThat(result.get(0).getAuthor()).isEqualTo("Frank Herbert");
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("ID ile kitap başarıyla getirilir")
    void getBookById_WhenBookExists_ShouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        BookResponse result = bookService.getBookById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Dune");
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Olmayan kitap getirilince BookNotFoundException fırlatılır")
    void getBookById_WhenBookNotExists_ShouldThrowException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookById(99L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("99");

        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Yeni kitap başarıyla oluşturulur")
    void createBook_WhenIsbnNotExists_ShouldCreateBook() {
        when(bookRepository.existsByIsbn(testRequest.getIsbn()))
                .thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        BookResponse result = bookService.createBook(testRequest);

        assertThat(result.getTitle()).isEqualTo("Dune");
        assertThat(result.getAuthor()).isEqualTo("Frank Herbert");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Aynı ISBN ile kitap eklenince hata fırlatılır")
    void createBook_WhenIsbnExists_ShouldThrowException() {
        when(bookRepository.existsByIsbn(testRequest.getIsbn()))
                .thenReturn(true);

        assertThatThrownBy(() -> bookService.createBook(testRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ISBN");

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    @DisplayName("Kitap başarıyla güncellenir")
    void updateBook_WhenBookExists_ShouldUpdateBook() {
        BookRequest updateRequest = new BookRequest();
        updateRequest.setTitle("Dune Messiah");
        updateRequest.setAuthor("Frank Herbert");
        updateRequest.setStatus(BookStatus.READING);

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Dune Messiah");
        updatedBook.setAuthor("Frank Herbert");
        updatedBook.setStatus(BookStatus.READING);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        BookResponse result = bookService.updateBook(1L, updateRequest);

        assertThat(result.getTitle()).isEqualTo("Dune Messiah");
        assertThat(result.getStatus()).isEqualTo(BookStatus.READING);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Kitap başarıyla silinir")
    void deleteBook_WhenBookExists_ShouldDeleteBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Olmayan kitap silinince BookNotFoundException fırlatılır")
    void deleteBook_WhenBookNotExists_ShouldThrowException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.deleteBook(99L))
                .isInstanceOf(BookNotFoundException.class);

        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Duruma göre kitaplar listelenir")
    void getBooksByStatus_ShouldReturnFilteredBooks() {
        when(bookRepository.findByStatus(BookStatus.UNREAD))
                .thenReturn(List.of(testBook));

        List<BookResponse> result = bookService.getBooksByStatus(BookStatus.UNREAD);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(BookStatus.UNREAD);
        verify(bookRepository, times(1)).findByStatus(BookStatus.UNREAD);
    }

    @Test
    @DisplayName("Başlığa göre arama çalışır")
    void searchByTitle_ShouldReturnMatchingBooks() {
        when(bookRepository.findByTitleContainingIgnoreCase("Dune"))
                .thenReturn(List.of(testBook));

        List<BookResponse> result = bookService.searchByTitle("Dune");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Dune");
        verify(bookRepository, times(1))
                .findByTitleContainingIgnoreCase("Dune");
    }
}