package com.disl.librarymanagementsystem.serviceImpl;

import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.model.response.BookResponse;
import com.disl.librarymanagementsystem.repository.BookRepository;
import com.disl.librarymanagementsystem.service.BookService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
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
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<BookResponse> findBookByKeyword(String keyword) {

        SearchSession searchSession = Search.session(entityManager);

        List<Book> books = searchSession.search(Book.class)
                .where(f-> f.match().fields("title","author").matching(keyword)).fetchHits(20);

        return books.stream().map(book -> modelMapper.map(book,BookResponse.class)).collect(Collectors.toList());
    }
}
