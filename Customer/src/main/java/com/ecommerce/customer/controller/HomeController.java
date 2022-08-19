package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

   @Autowired
   private ProductService productService;

   @Autowired
   private CustomerService customerService;
   @Autowired
   private CategoryService categoryService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value = { "/index","/" }, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session){
        if(principal != null) {
            session.setAttribute("username", principal.getName());
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getShoppingCart();
            session.setAttribute("shoppingCart", cart);
            session.setAttribute("totalItems", cart.getTotalItems());
            session.setAttribute("subTotal",cart.getTotalPrices());
        }else{
            session.removeAttribute("username");
        }
        model.addAttribute("title", "Home Page");
        return "home";
    }

    @GetMapping("/home")
    public String index(Model model, Principal principal, HttpSession session){
        session.setAttribute("username", principal.getName());
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getShoppingCart();
        List<Category> categories = categoryService.findAll();
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("title", "Home Page");
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        session.setAttribute("shoppingCart", cart);
        session.setAttribute("totalItems", cart.getTotalItems());
        session.setAttribute("subTotal",cart.getTotalPrices());
        return "index";
    }

    @GetMapping("/contact")
    public String contact(Model model){
        model.addAttribute("title", "Contact");
        return "contact-us";
    }
}
