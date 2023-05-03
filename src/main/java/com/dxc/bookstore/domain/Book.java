package com.dxc.bookstore.domain;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  @NonNull
  @Id
  String isbn;
  @NonNull
  String title;
  @ManyToMany
  @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_isbn"),
      inverseJoinColumns = @JoinColumn(name = "author_name"))
  List<Author> authors;
  int year;
  double price;
  String genre;
}
