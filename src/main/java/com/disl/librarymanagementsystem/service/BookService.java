package com.disl.librarymanagementsystem.service;

import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.model.response.BookResponse;

public interface BookService {
    void saveBook(Book book);

    BookResponse findBookByKeyword(String keyword);
}
