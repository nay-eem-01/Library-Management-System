package com.disl.librarymanagementsystem.repository;

import com.disl.librarymanagementsystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    boolean existsAuthorByName(String name);

    Optional<Author> findByName(String name);
}
