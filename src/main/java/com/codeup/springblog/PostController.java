package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    @GetMapping("/posts")
    public String index(Model model){
        Post postOne = new Post("Post One Title", "This is the body of Post One.");
        Post postTwo = new Post("Post Two Title", "This is the body of Post Two.");
        List<Post> posts = new ArrayList<>();
        posts.add(postOne);
        posts.add(postTwo);
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
        public String viewPost(@PathVariable long id, Model model){
        Post postOne = new Post("title", "body description");
        model.addAttribute("post", postOne);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String create(){
        return "This is the create form.";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(){
        return "Post Created";
    }
}

