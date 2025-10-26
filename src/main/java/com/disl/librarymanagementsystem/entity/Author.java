package com.disl.librarymanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author")
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "author_id")
    private Long id;

    @NotBlank(message = "Author name can not be blank")
    @Column(name = "author_name")
    @KeywordField(normalizer = "lowercase")
    private String name;

    @ManyToMany(mappedBy = "")
    private Set<Book> books;
}
