package com.dxc.bookstore.repository;

import com.dxc.bookstore.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
 
}
