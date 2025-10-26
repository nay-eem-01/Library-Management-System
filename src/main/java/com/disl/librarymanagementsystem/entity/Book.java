package com.disl.librarymanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.engine.backend.types.TermVector;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import java.util.Set;

@Entity
@Table(name = "books")
@Indexed
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id",unique = true)
    private Long id;

    @Column(name = "book_title")
    @NotBlank(message = "Book must contain a title")
    @FullTextField(analyzer = "english", termVector = TermVector.YES)
    private String title;

    @Column(name = "isbn")
    @NotBlank(message = "Book must contain ISBN number")
    @FullTextField(termVector = TermVector.YES)
    private String isbn;

    @Column(name = "is_book_available")
    private boolean isAvailable;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @IndexedEmbedded
    private Set<Author> author;
}
