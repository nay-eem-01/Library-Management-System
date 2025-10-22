package com.disl.librarymanagementsystem.controller;

import com.disl.librarymanagementsystem.model.response.BookResponse;
import com.disl.librarymanagementsystem.service.BookService;
import com.disl.librarymanagementsystem.model.response.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    public ResponseEntity<HttpResponse> searchBookByKeyword(@RequestParam String keyword){
        BookResponse bookResponse = bookService.findBookByKeyword(keyword);

        return HttpResponse.getResponseEntity(HttpStatus.OK,"Data loaded successfully",bookResponse,true);
    }
}
