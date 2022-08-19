package com.ecommerce.customer.controller;

import com.ecommerce.library.model.City;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CityService;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class AccountControler {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/account")
    public String accountHome(Model model, Principal principal) {
        model.addAttribute("title", "My Account");
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if(customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()){
            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the after checkout!");
        }
        model.addAttribute("customer", customer);

        return "personal-inf";
    }

    @RequestMapping(value = "/update-info", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateCustomer(Model model,
                                 @ModelAttribute("customer") Customer customer,
                                 RedirectAttributes attributes,
                                 Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }

        Customer customerSaved = customerService.saveCustomer(customer);
        attributes.addFlashAttribute("customer", customerSaved);
        return "redirect:/account";
    }
}
