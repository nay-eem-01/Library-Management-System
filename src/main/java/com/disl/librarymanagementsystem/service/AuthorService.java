package com.disl.librarymanagementsystem.service;

import com.disl.librarymanagementsystem.dto.AuthorDto;
import com.disl.librarymanagementsystem.model.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    void saveAuthor(AuthorDto authorDto);

    List<AuthorResponse> getAllAuthor();

    AuthorResponse updateAuthor(AuthorDto authorDto, Long id);

    void deleteAuthor(Long id);
}
