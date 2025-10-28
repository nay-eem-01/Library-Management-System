package com.disl.librarymanagementsystem.config;

import com.disl.librarymanagementsystem.entity.Author;
import com.disl.librarymanagementsystem.entity.Book;
import com.disl.librarymanagementsystem.model.response.AuthorResponse;
import com.disl.librarymanagementsystem.model.response.BookResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Author.class, AuthorResponse.class);
        modelMapper.typeMap(Book.class, BookResponse.class)
                .addMappings(mapper->
                        mapper.map(Book::getAuthor,BookResponse::setAuthors));

        return modelMapper;
    }
}
