package com.aaron.book.web;

import com.aaron.book.domain.Book;
import com.aaron.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.저장하기(book), HttpStatus.CREATED); // 201
    }

    @CrossOrigin
    @GetMapping("/book")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(bookService.모두가져오기(), HttpStatus.OK); // 200
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) { // 랩핑 클래스 null 관련
        return new ResponseEntity<>(bookService.한건가져오기(id), HttpStatus.OK); // 200
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book) { // 랩핑 클래스 null 관련
        return new ResponseEntity<>(bookService.수정하기(id, book), HttpStatus.OK); // 200
    }

    // 자바 제네릭 물음표 찾아보기
    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) { // 랩핑 클래스 null 관련
        return new ResponseEntity<>(bookService.삭제하기(id), HttpStatus.OK); // 200
    }
}
