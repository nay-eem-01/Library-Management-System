package com.disl.librarymanagementsystem.serviceImpl;

import com.disl.librarymanagementsystem.dto.BookDto;
import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.model.response.BookResponse;
import com.disl.librarymanagementsystem.repository.BookRepository;
import com.disl.librarymanagementsystem.service.BookService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;


    @Override
    public void createBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        System.out.println("ISBN" + bookDto.getIsbn());
        book.setIsbn(bookDto.getIsbn());
        book.setAvailable(bookDto.isAvailable());
        bookRepository.save(book);
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<BookResponse> findBookByKeyword(String keyword) {
        SearchSession searchSession = Search.session(entityManager);

        List<Book> bookList = searchSession.search(Book.class)
                .where(f -> f.bool()
                        .should(f.phrase()
                                .fields("title", "author")
                                .matching(keyword)
                                .boost(3.0f))
                        .should(f.match()
                                .fields("title", "author")
                                .matching(keyword)
                                .boost(1.0f))
                        .should(f.match()
                                .fields("title", "author")
                                .matching(keyword)
                                .fuzzy(2, 2)
                                .boost(0.5f)))
                .sort(f -> f.score())
                .fetchHits(20);

        return bookList.stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(book -> modelMapper.map(book, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookDto bookDto) {

        Book bookFromDb = bookRepository.findById(id).get();

        bookFromDb.setTitle(bookDto.getTitle());
        bookFromDb.setAuthor(bookDto.getAuthor());
        bookFromDb.setIsbn(bookDto.getIsbn());
        bookFromDb.setAvailable(bookDto.isAvailable());

        bookFromDb = bookRepository.save(bookFromDb);

        return modelMapper.map(bookFromDb, BookResponse.class);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).get();
        bookRepository.delete(book);
    }
}
