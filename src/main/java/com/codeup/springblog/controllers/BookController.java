package com.codeup.springblog.controllers;

import com.codeup.springblog.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {
    private BookRepository bookDao;

    public BookController(BookRepository bookDao){
        this.bookDao = bookDao;
    }

    @GetMapping("/books")
    public String showBooks(Model model){
        model.addAttribute("books",bookDao.findAll());
        return "books";
    }
}
