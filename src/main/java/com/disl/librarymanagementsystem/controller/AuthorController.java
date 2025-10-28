package com.disl.librarymanagementsystem.controller;

import com.disl.librarymanagementsystem.dto.AuthorDto;
import com.disl.librarymanagementsystem.model.response.AuthorResponse;
import com.disl.librarymanagementsystem.model.response.HttpResponse;
import com.disl.librarymanagementsystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<HttpResponse> addNewAuthor(@RequestBody AuthorDto authorDto){
        authorService.saveAuthor(authorDto);
        return HttpResponse.getResponseEntity(HttpStatus.CREATED,"Author added",null,true);
    }
    @GetMapping("/get-all")
    public ResponseEntity<HttpResponse> getAllAuthor(){
        List<AuthorResponse> authorResponseList = authorService.getAllAuthor();
        return HttpResponse.getResponseEntity(HttpStatus.OK,"Data loaded successfully",authorResponseList,true);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> updateAuthor(@RequestBody AuthorDto authorDto, @PathVariable Long id){
        AuthorResponse authorResponse = authorService.updateAuthor(authorDto,id);
        return HttpResponse.getResponseEntity(HttpStatus.OK,"Author updated",authorResponse,true);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return HttpResponse.getResponseEntity(HttpStatus.OK,"Author deleted",null,true);
    }
}
