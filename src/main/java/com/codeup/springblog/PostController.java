package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {
    @GetMapping("/posts")
    @ResponseBody
    public String index(){
        return "Post index page";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String viewPost(@PathVariable long id){
        return "This is the individual post with the id of: " + id + ".";
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

