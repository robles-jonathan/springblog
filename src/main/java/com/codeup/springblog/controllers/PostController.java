package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {
    private PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
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
    public String create() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(
            @RequestParam("title") String title,
            @RequestParam("body") String body
    ) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        postDao.save(post);
        return "redirect:/posts";
    }
// Could not use @PostMapping
    @PostMapping("/posts/{id}/edit")
//    @RequestMapping(path = "/posts/{id}/edit", method = {RequestMethod.GET, RequestMethod.POST})
    public String editPost(@PathVariable Long id){
        Post post = postDao.getById(id);
        post.setTitle("NEW TITLE");
        post.setBody("NEW BODY");
        postDao.save(post);
        return "redirect:/posts";
    }

// Could not use @PostMapping
    @PostMapping("/posts/{id}/delete")
//    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id){
        postDao.deleteById(id);
        return "redirect:/posts";
    }
}