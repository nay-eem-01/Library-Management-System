package com.disl.librarymanagementsystem.service;

import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.model.response.BookResponse;

import java.util.List;

public interface BookService {

    void saveBook(Book book);

    List<BookResponse> findBookByKeyword(String keyword);
}
