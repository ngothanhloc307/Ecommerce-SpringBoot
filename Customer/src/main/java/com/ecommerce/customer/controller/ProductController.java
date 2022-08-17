package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
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

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model) {
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProducts = productService.listViewProducts();
        model.addAttribute("title", "Product Details");
        model.addAttribute("products", products);
        model.addAttribute("listViewProducts", listViewProducts);
        model.addAttribute("categoryDtoList", categoryDtoList);
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

    @GetMapping("/products-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Long categoryId,Model model) {
        Category category = categoryService.findById(categoryId);
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> productList = productService.getProductsInCategory(categoryId);
        model.addAttribute("title", "Product In Category");
        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("category", category);
        model.addAttribute("products", productList);
        return "products-in-category";
    }

    @GetMapping("/high-price")
    public String filterHighPriceProduct(Model model){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Category> categories = categoryService.findAllCategoryActivated();
        List<Product> products = productService.filterHighPriceProduct();
        model.addAttribute("title", "Product In Category");
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDtoList", categoryDtoList);
        return "filter-high-price";
    }

    @GetMapping("/low-price")
    public String filterLowPriceProduct(Model model){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Category> categories = categoryService.findAllCategoryActivated();
        List<Product> products = productService.filterLowPriceProduct();
        model.addAttribute("title", "Product In Category");
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryDtoList", categoryDtoList);
        return "filter-low-price";
    }


}
