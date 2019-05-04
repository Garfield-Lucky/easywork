package com.easy.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping(value = {"/", "/home"})
    public String home(Model model, String name) {
        model.addAttribute("name", name);
        return "index";
    }
}