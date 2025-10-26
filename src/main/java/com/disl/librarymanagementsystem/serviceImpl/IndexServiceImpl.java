package com.disl.librarymanagementsystem.serviceImpl;

import com.disl.librarymanagementsystem.service.IndexService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexServiceImpl implements IndexService {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public void initializeIndex() {
        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer().startAndWait();
            log.info("Hibernate Search indexing complete!");
        } catch (Exception e) {
            log.warn("Index initialization failed due to:{}", e.toString());
        }
    }
}
