package com.dxc.bookstore.service;

import com.dxc.bookstore.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorService extends JpaRepository<Author, Integer>{

}
