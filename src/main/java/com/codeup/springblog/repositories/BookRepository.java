package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Will return a SINGLE book with the matching title.
//    Book findBookByTitle();
//    @Query("from Book b where b.title like %:bookTitle%");
//    List<Book> get
}
