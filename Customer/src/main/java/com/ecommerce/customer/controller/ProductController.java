package com.ecommerce.customer.controller;

import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;



    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        model.addAttribute("products", products);
        model.addAttribute("listViewProducts", listViewProducts);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String findByProductById(@PathVariable("id")Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Product> productRelatedList = productService.getRelatedProducts(product.getCategory().getId());
        model.addAttribute("productRelatedList", productRelatedList);
        model.addAttribute("product", product);
        return "product-detail";
    }

//    @GetMapping("/product-detail")
//    public String
}
