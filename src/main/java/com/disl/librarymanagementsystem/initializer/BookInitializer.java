package com.disl.librarymanagementsystem.initializer;

import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookInitializer implements ApplicationListener<ApplicationContextEvent> {

    private final BookService bookService;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        Book book = new Book();
        book.setTitle("Java tutorial");
        book.setAuthor("Alex mark");
        book.setISBN("1234");
        book.setAvailable(true);
        bookService.saveBook(book);
    }
}
