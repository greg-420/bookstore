package com.dxc.bookstore.service;

import java.util.List;
import com.dxc.bookstore.domain.Author;
import com.dxc.bookstore.domain.Book;
import com.dxc.bookstore.repository.BookRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService {
  private final BookRepository bookRepository;

  public BookService(final BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public boolean bookExists(String isbn) {
    Book book;
    log.info("finding book" + isbn);
    try {
      book = bookRepository.getReferenceById(isbn);
      log.info("found" + book);
    } catch (EntityNotFoundException e) {
      log.error("Failed to find book with isbn " + isbn, e);
      return false;
    }
    return book != null;
  }

  public void saveBook(Book book) {
    bookRepository.save(book);
  }

  public List<Book> getBooksByTitleAndAuthor(String title, String author) {
    
    if (!title.isBlank() && !author.isBlank()) {
      Book titleExample = new Book();
      titleExample.setTitle(title);

      List<Book> booksByTitle = bookRepository.findAll(Example.of(titleExample));

      return booksByTitle.stream()
          .filter(b -> b.getAuthors().stream().anyMatch(a -> a.getName().equals(author))).toList();
    }

    else if (author.isBlank()) {
      Book titleExample = new Book();
      titleExample.setTitle(title);
      return bookRepository.findAll(Example.of(titleExample));
    }

    else if (title.isBlank()) {
      return bookRepository.findAll().stream()
          .filter(b -> b.getAuthors().stream().anyMatch(a -> a.getName().equals(author))).toList();
    }
    
    else return List.of();

  }
  
  public void deleteBook(String isbn) {
    bookRepository.deleteById(isbn);
  }

}
