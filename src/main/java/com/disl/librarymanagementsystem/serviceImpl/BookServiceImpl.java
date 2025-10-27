package com.disl.librarymanagementsystem.serviceImpl;

import com.disl.librarymanagementsystem.dto.BookDto;
import com.disl.librarymanagementsystem.entity.Author;
import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.exceptionHandler.AlreadyExistsException;
import com.disl.librarymanagementsystem.exceptionHandler.ResourceNotFoundException;
import com.disl.librarymanagementsystem.model.response.AuthorResponse;
import com.disl.librarymanagementsystem.model.response.BookResponse;
import com.disl.librarymanagementsystem.repository.AuthorRepository;
import com.disl.librarymanagementsystem.repository.BookRepository;
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
    private final AuthorRepository authorRepository;

    @Override
    public void saveBook(BookDto bookDto) {

        if (bookRepository.existsBooksByIsbn(bookDto.getIsbn())) {
            throw new AlreadyExistsException("Book already exists");
        }

        Book book = new Book();
        Set<Author> authors = bookDto.getAuthorDto()
                .stream()
                .map(authorDto -> authorRepository.findByName(authorDto.getName())
                        .orElseGet(() -> {
                            Author author = new Author();
                            author.setName(authorDto.getName());
                            return authorRepository.save(author);
                        })
                )
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
                                        .fields("title","author.name")
                                        .matching(keyword)
                                        .boost(10.0f))
                                .should(f.match()
                                        .fields("title", "author.name")
                                        .matching(keyword)
                                        .boost(8.0f)))
                        .should(f.match()
                                .fields("title", "author.name")
                                .matching(keyword)
                                .fuzzy(1)
                                .boost(2f)))

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
        return bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookDto bookDto) {

        Book bookFromDb = bookRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));

        Set<Author> authors = bookDto.getAuthorDto()
                .stream()
                .map(authorDto -> authorRepository.findByName(authorDto.getName())
                        .orElseGet(() -> {
                            Author author = new Author();
                            author.setName(authorDto.getName());
                            return authorRepository.save(author);
                        })
                )
                .collect(Collectors.toSet());

        bookFromDb.setTitle(bookDto.getTitle());
        bookFromDb.setAuthor(authors);
        bookFromDb.setIsbn(bookDto.getIsbn());
        bookFromDb.setAvailable(bookDto.isAvailable());
        bookFromDb = bookRepository.save(bookFromDb);

        Set<AuthorResponse> authorResponses = bookFromDb.getAuthor()
                .stream()
                .map(author ->
                        modelMapper.map(author, AuthorResponse.class))
                .collect(Collectors.toSet());

        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(bookFromDb.getId());
        bookResponse.setTitle(bookFromDb.getTitle());
        bookResponse.setAuthors(authorResponses);
        bookResponse.setISBN(bookFromDb.getIsbn());
        bookResponse.setAvailable(bookFromDb.isAvailable());

        log.info("\nBook updated:\nTitle: {}\nAuthor: {}\nISBN: {}\nAvailable: {}",
                bookFromDb.getTitle(),
                bookFromDb.getAuthor().stream().map(Author::getName).collect(Collectors.toSet()),
                bookFromDb.getIsbn(),
                bookFromDb.isAvailable());

        return bookResponse;
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));
        log.info("Book deleting...{}", book);
        bookRepository.delete(book);
    }
}
