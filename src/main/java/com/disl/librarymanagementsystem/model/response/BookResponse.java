package com.disl.librarymanagementsystem.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable;
}
