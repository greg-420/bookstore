package com.dxc.bookstore.controller;

import java.util.List;
import java.util.Map;
import com.dxc.bookstore.domain.Author;
import com.dxc.bookstore.domain.Book;
import com.dxc.bookstore.dto.ResponseSchema;
import com.dxc.bookstore.service.AuthorService;
import com.dxc.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BookController {

  private final BookService bookService;
  private final AuthorService authorService;

  public BookController(final BookService bookService, final AuthorService authorService) {
    this.bookService = bookService;
    this.authorService = authorService;
  }


  @PostMapping(value = "/book")
  public ResponseEntity<Map<String, Object>> createBook(@RequestBody Book book) {
    try {
      Book newBook = saveBookAuthor(book);
      return ResponseEntity.ok(new ResponseSchema(200, newBook).getSuccessResponse());
    } catch (Exception e) {
      log.error("Exception while creating book", e);
      return ResponseEntity.status(500)
          .body(new ResponseSchema(500, "Error occurred when creating book").getErrorResponse());
    }
  }

  @PutMapping(value = "/book")
  public ResponseEntity<Map<String, Object>> updateBook(@RequestBody Book book) throws Exception {
    if (bookService.bookExists(book.getIsbn())) {
      Book newBook = saveBookAuthor(book);
      return ResponseEntity.ok(new ResponseSchema(200, newBook).getSuccessResponse());
    } else
      return ResponseEntity.status(400)
          .body(new ResponseSchema(400, "ISBN does not exist").getErrorResponse());
  }

  @GetMapping(value = "/book")
  public ResponseEntity<Map<String, Object>> getBooks(@RequestParam String title,
      @RequestParam String author) {
    List<Book> books = bookService.getBooksByTitleAndAuthor(title, author);
    return ResponseEntity.ok(new ResponseSchema(200, books).getSuccessResponse());
  }

  @DeleteMapping(value = "/admin/book")
  public ResponseEntity<Map<String, Object>> deleteBook(@RequestParam String isbn) {
    bookService.deleteBook(isbn);
    log.info(isbn + " successfully deleted");
    return ResponseEntity.ok(new ResponseSchema(200).getSuccessResponse());
  }

  @GetMapping(value = "/admin/book")
  public String testPrivileges() {
    return "Hello world";
  }

  private Book saveBookAuthor(Book book) {
    for (Author author : book.getAuthors()) {
      authorService.save(author);
    }
    bookService.saveBook(book);
    return book;
  }

}
