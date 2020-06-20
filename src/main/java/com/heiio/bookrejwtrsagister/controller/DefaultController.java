package com.heiio.bookrejwtrsagister.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {
    @RequestMapping("/find")
    @ResponseBody
    public String findProduct() {
        return "you found it";
    }

    @RequestMapping("/book-list")
    public String bookList() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home() {
        return "book-list";
    }
}
