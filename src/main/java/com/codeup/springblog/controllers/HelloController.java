package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {
    @GetMapping("/hello/{name}")
    @ResponseBody
    public String hello(@PathVariable("name") String name){
        return "Hello, " + name + "!";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }

    //REQUEST MAPPING
    @RequestMapping(path = "increment/{number}", method = RequestMethod.GET)
    @ResponseBody
    public String increment(@PathVariable int number){
        return number + " plus one is " + (number + 1) + "!";
    }
}
