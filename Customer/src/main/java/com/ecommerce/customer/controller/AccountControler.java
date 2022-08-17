package com.ecommerce.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountControler {

    @GetMapping("/account")
    public String accountHome(Model model) {
        model.addAttribute("title", "My Account");
        return "my-account";
    }
}
