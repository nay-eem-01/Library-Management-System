package com.disl.librarymanagementsystem.serviceImpl;

import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.model.response.BookResponse;
import com.disl.librarymanagementsystem.repository.BookRepository;
import com.disl.librarymanagementsystem.service.BookService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.util.QueryBuilder;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;


    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public BookResponse findBookByKeyword(String keyword) {
        SearchSession searchSession = Search.session(entityManager);
        SearchScope<Book> scope = searchSession.scope(Book.class);

        Book book = (Book) searchSession.search(Book.class)
                .where(f-> f.match().field("title").matching("java"));

        return null;
    }
}
