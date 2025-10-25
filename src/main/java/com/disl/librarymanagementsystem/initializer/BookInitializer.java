package com.disl.librarymanagementsystem.initializer;

import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.repository.BookRepository;
import com.disl.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookInitializer implements ApplicationListener<ApplicationContextEvent> {

    private final BookRepository bookRepository;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (bookRepository.findAll().isEmpty()){
            Book book = new Book();
            book.setTitle("Java tutorial");
            book.setAuthor("Alex mark");
            book.setIsbn("1234");
            bookRepository.save(book);
        }
    }
}
