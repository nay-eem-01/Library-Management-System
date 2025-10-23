package com.disl.librarymanagementsystem.controller;

import com.disl.librarymanagementsystem.dto.BookDto;
import com.disl.librarymanagementsystem.model.response.BookResponse;
import com.disl.librarymanagementsystem.service.BookService;
import com.disl.librarymanagementsystem.model.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/search-book")
    public ResponseEntity<HttpResponse> searchBookByKeyword(@RequestParam(name = "keyword") String keyword){
        List<BookResponse> bookResponses = bookService.findBookByKeyword(keyword);
        return HttpResponse.getResponseEntity(HttpStatus.OK,"Data loaded successfully",bookResponses,true);
    }
    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getAllBooks(){
        List<BookResponse> books = bookService.getAllBooks();
        return HttpResponse.getResponseEntity(HttpStatus.OK,"Data loaded successfully",books,true);
    }
    @PostMapping("/add")
    public ResponseEntity<HttpResponse> addBook(@RequestBody BookDto bookDto){
        bookService.createBook(bookDto);
        return HttpResponse.getResponseEntity(HttpStatus.CREATED,"Book added to the library",null,true);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpResponse> updateBook(@RequestBody BookDto bookDto, @PathVariable Long id){
        BookResponse bookResponse = bookService.updateBook(id,bookDto);
        return HttpResponse.getResponseEntity(HttpStatus.OK,"Book information updated",bookResponse,true);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return HttpResponse.getResponseEntity(HttpStatus.OK,"Book deleted from Library",null,true);
    }
}
