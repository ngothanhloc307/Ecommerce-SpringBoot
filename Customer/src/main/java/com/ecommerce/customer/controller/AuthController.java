package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("customerDto")CustomerDto customerDto,
                                  BindingResult result,
                                  Model model) {
        model.addAttribute("title", "Register");
        try {
            if(result.hasErrors()){
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            Customer customer = customerService.findByUsername(customerDto.getUsername());
            if(customer != null){
                model.addAttribute("username", "Username have been register!");
                model.addAttribute("customerDto", customerDto);
                return "register";
            } else {
                if(customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
                    customerDto.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
                    customerService.save(customerDto);
                    model.addAttribute("customerDto", customerDto);
                    model.addAttribute("success", "Register successfully!");
                    return "register";
                }else {
                    model.addAttribute("password", "Password is not same!");
                    model.addAttribute("customerDto", customerDto);
                    return "register";
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "Can not register because of error sever, Please try again!");
        }
        return "register";
    }
}
