package com.disl.librarymanagementsystem.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private Set<AuthorDto> authorDto;
    private String isbn;
    private boolean available;
}
