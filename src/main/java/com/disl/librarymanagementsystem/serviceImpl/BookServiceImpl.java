package com.disl.librarymanagementsystem.serviceImpl;

import com.disl.librarymanagementsystem.dto.AuthorDto;
import com.disl.librarymanagementsystem.dto.BookDto;
import com.disl.librarymanagementsystem.entity.Author;
import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.exceptionHandler.AlreadyExistsException;
import com.disl.librarymanagementsystem.exceptionHandler.ResourceNotFoundException;
import com.disl.librarymanagementsystem.model.response.BookResponse;
import com.disl.librarymanagementsystem.repository.BookRepository;
import com.disl.librarymanagementsystem.service.AuthorService;
import com.disl.librarymanagementsystem.service.BookService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.engine.search.sort.dsl.SearchSortFactory;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Override
    public void saveBook(BookDto bookDto) {

        if (bookRepository.existsBooksByIsbn(bookDto.getIsbn())) {
            throw new AlreadyExistsException("Book already exists");
        }

        Book book = new Book();
        Set<Author> authors = bookDto.getAuthorDto()
                .stream()
                .map(authorDto ->
                        modelMapper.map(authorDto, Author.class))
                .collect(Collectors.toSet());

        book.setTitle(bookDto.getTitle());
        book.setAuthor(authors);
        book.setIsbn(bookDto.getIsbn());
        book.setAvailable(bookDto.isAvailable());

        bookRepository.save(book);
        log.info("Book entity saved to DB: {}", book);
    }

    @Override
    public List<BookResponse> findBookByKeyword(String keyword) {

        SearchSession searchSession = Search.session(entityManager);

        List<Book> bookList = searchSession.search(Book.class)
                .where(f -> f.bool()
                        .must(f.bool()
                                .should(f.phrase()
                                        .fields("title", "author")
                                        .matching(keyword)
                                        .boost(10.0f))
                                .should(f.match()
                                        .fields("title", "author")
                                        .matching(keyword)
                                        .boost(5.0f)))
                        .should(f.match()
                                .fields("title", "author")
                                .matching(keyword)
                                .fuzzy(1)
                                .boost(0.5f)))

                .sort(SearchSortFactory::score)
                .fetchHits(20);

        List<BookResponse> bookResponseList = bookList
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .collect(Collectors.toList());

        log.info("\nSearching result for keyword '{}'\nResults:{}", keyword, bookResponseList);

        return bookResponseList;
    }

    @Override
    public List<BookResponse> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(book -> modelMapper.map(book, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookDto bookDto) {

        Book bookFromDb = bookRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));

        Set<Author> authors = bookDto.getAuthorDto()
                .stream()
                .map(authorDto ->
                        modelMapper.map(authorDto, Author.class))
                .collect(Collectors.toSet());

        bookFromDb.setTitle(bookDto.getTitle());
        bookFromDb.setAuthor(authors);
        bookFromDb.setIsbn(bookDto.getIsbn());
        bookFromDb.setAvailable(bookDto.isAvailable());

        bookFromDb = bookRepository.save(bookFromDb);

        log.info("Book updated: {}", bookFromDb);

        return modelMapper.map(bookFromDb, BookResponse.class);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));
        log.info("Book deleting...{}", book);
        bookRepository.delete(book);
    }
}
