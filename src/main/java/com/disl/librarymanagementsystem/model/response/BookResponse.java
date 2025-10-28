package com.disl.librarymanagementsystem.model.response;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookResponse {
    private Long id;
    private String title;
    private Set<AuthorResponse> authors;
    private String ISBN;
    private boolean isAvailable;
}
