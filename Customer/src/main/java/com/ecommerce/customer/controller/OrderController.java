package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderService;
import com.ecommerce.library.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal) {
        model.addAttribute("title", "Checkout Order Details");
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        if(customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty()
                || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()){
            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the after checkout!");
            return "redirect:/account";
        }else {

            model.addAttribute("customer", customer);
            ShoppingCart shoppingCart = customer.getShoppingCart();
            if(shoppingCart.getCartItem().size() == 0) {
                model.addAttribute("noItem", "No item in cart, Please add products to cart!!");
            }
            model.addAttribute("shopppingCart", shoppingCart);
        }

        return "checkout";
    }

    @GetMapping("/order")
    public String order(Model model, Principal principal) {
        model.addAttribute("title", "Order Confirm");
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Order> orderList = customer.getOrders();
        model.addAttribute("orders", orderList);
        return "order";
    }

    @GetMapping("/save-order")
    public String saveOrder(Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart =customer.getShoppingCart();
        orderService.saveOrder(shoppingCart);
        return "redirect:/order";
    }
}
