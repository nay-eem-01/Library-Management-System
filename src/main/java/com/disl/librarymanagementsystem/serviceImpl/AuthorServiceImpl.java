package com.disl.librarymanagementsystem.serviceImpl;

import com.disl.librarymanagementsystem.dto.AuthorDto;
import com.disl.librarymanagementsystem.entity.Author;
import com.disl.librarymanagementsystem.exceptionHandler.AlreadyExistsException;
import com.disl.librarymanagementsystem.exceptionHandler.ResourceNotFoundException;
import com.disl.librarymanagementsystem.model.response.AuthorResponse;
import com.disl.librarymanagementsystem.repository.AuthorRepository;
import com.disl.librarymanagementsystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveAuthor(AuthorDto authorDto) {
        if (authorRepository.existsAuthorByName(authorDto.getName())) {
            throw new AlreadyExistsException("Author already exists");
        }
        Author author = new Author();
        author.setName(authorDto.getName());
        authorRepository.save(author);
    }

    @Override
    public List<AuthorResponse> getAllAuthor() {
        List<Author> authors = authorRepository.findAll();
        return authors
                .stream()
                .map(author ->
                        modelMapper.map(author, AuthorResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponse updateAuthor(AuthorDto authorDto, Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author does not exist"));
        author.setName(author.getName());
        author = authorRepository.save(author);
        return modelMapper.map(author, AuthorResponse.class);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author does not exist"));
        authorRepository.delete(author);
    }
}
