package com.disl.librarymanagementsystem.initializer;

import com.disl.librarymanagementsystem.entity.Author;
import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.repository.BookRepository;
import com.disl.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookInitializer implements ApplicationListener<ApplicationContextEvent> {

    private final BookRepository bookRepository;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (bookRepository.findAll().isEmpty()){
            Book book = new Book();
            book.setTitle("Java tutorial");
            book.setAuthor(Set.of(new Author(1L,"Alex")));
            book.setIsbn("1234");
            bookRepository.save(book);
            log.info("First entry on database: {}",book);
        }
    }
}
