package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {
    private PostRepository postDao;
    private UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable long id, Model model) {
        Post postOne = postDao.getById(id);
        model.addAttribute("post", postOne);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String create(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post){
        User currentUser = userDao.getById(1L);
        post.setUser(currentUser);
        emailService.prepareAndSend(post, post.getTitle(), post.getBody());
        postDao.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/send-email")
    public String sendEmail() {
        emailService.prepareAndSend("Testing", "Did this work");
        return "redirect:/";
    }

    @GetMapping("/posts/{id}/edit")
    public String viewEditPostForm(@PathVariable Long id, Model model){
        Post oldPost = postDao.getById(id);
        model.addAttribute("post", oldPost);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String editPost(@ModelAttribute Post post){
        postDao.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }
}