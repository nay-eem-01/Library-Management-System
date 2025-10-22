package com.disl.librarymanagementsystem.serviceImpl;

import com.disl.librarymanagementsystem.service.IndexService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void initializeIndex() {
        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer().startAndWait();
            System.out.println("Hibernate Search indexing complete!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Indexing interrupted: " + e.getMessage());
        }
    }
}
