package com.disl.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import java.util.Set;

@Entity
@Data
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
    @FullTextField(analyzer = "name")
    private String name;

    @ManyToMany(mappedBy = "author")
    @JsonIgnore
    private Set<Book> books;
}
