package com.webp.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LastController {
    @GetMapping("test")
    public String test() {
        return "last/test";
    }

    @PostMapping("result")
    public String result(String one, String two, String three, String four, String five, Model model) {
        int sum = 0;
        if(one.equals("5")) {
            model.addAttribute("one", "정답");
            sum++;
        }
        else model.addAttribute("one", "오답");

        if(one.equals("10")) {
            model.addAttribute("two", "정답");
            sum++;
        }
        else model.addAttribute("two", "오답");

        if(one.equals("8"))  {
            model.addAttribute("three", "정답");
            sum++;
        }
        else model.addAttribute("three", "오답");

        if(one.equals("70")) {
            model.addAttribute("four", "정답");
            sum++;
        }
        else model.addAttribute("four", "오답");

        if(one.equals("5")) {
            model.addAttribute("five", "정답");
            sum++;
        }
        else model.addAttribute("five", "오답");

        model.addAttribute("score", sum*20);

        return "last/result";
    }
}
