package com.codeup.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "welcome";
    }

    @GetMapping("/quote-of-the-day/by/{author}")
    public String quote(@PathVariable String author, Model model) {
        model.addAttribute("author", author);
        return "quotes";
    }

    @GetMapping("/join")
    public String showJoinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String joinCohort(@RequestParam(name = "cohort") String cohort, Model model) {
        model.addAttribute("cohort", "Welcome to " + cohort + "!");
        return "join";
    }

    @GetMapping("/greek-gods")
    public String showGreekGods(Model model) {
        String[] names = {"Zeus", "Hercules", "Hades", "Apollo"};
        model.addAttribute("greekGods", names);
        return "greekGods";
    }
}
