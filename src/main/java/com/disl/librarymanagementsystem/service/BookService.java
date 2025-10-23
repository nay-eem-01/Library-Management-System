package com.disl.librarymanagementsystem.service;

import com.disl.librarymanagementsystem.dto.BookDto;
import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.model.response.BookResponse;

import java.util.List;

public interface BookService {
    void createBook(BookDto bookDto);
    void saveBook(Book book);

    List<BookResponse> findBookByKeyword(String keyword);

    List<BookResponse> getAllBooks();

    BookResponse updateBook(Long id, BookDto bookDto);

    void deleteBook(Long id);
}
