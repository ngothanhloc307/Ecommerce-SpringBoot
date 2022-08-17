package com.ecommerce.library.service.imp;

import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CartItemRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();
        if(cart == null) {
            cart = new ShoppingCart();
        }
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, product.getId());
        if(cartItems == null) {
            cartItems = new HashSet<>();
            if(cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }
        }else{
            if(cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }else{
                cartItem.setTotalPrice(cartItem.getTotalPrice() * quantity * product.getCostPrice());
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemRepository.save(cartItem);
            }

        }
        cart.setCartItem(cartItems);

        int totalItems = totalItems(cart.getCartItem());
        double totalPrices = totalPrices(cart.getCartItem());

        cart.setTotalPrices(totalPrices);
        cart.setTotalItems(totalItems);
        cart.setCustomer(customer);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateItemInCart(Product product, int quantity, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem item = findCartItem(cartItems, product.getId());
        item.setQuantity(quantity);
        item.setTotalPrice(quantity * product.getCostPrice());
        cartItemRepository.save(item);

        int totalItems = totalItems(cartItems);
        double totalPrices = totalPrices(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrices);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart deleteItemInCart(Product product, Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();

        Set<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, product.getId());

        cartItems.remove(cartItem);
        cartItemRepository.delete(cartItem);

        int totalItems = totalItems(cartItems);
        double totalPrices = totalPrices(cartItems);
        DecimalFormat format = new DecimalFormat("0.#");

        cart.setCartItem(cartItems);
        cart.setTotalPrices(totalPrices);
        cart.setTotalItems(totalItems);

        return shoppingCartRepository.save(cart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {
        if(cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for(CartItem item : cartItems) {
            if(item.getProduct().getId() == productId){
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems) {
        int totalItems = 0;
        for(CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private double totalPrices(Set<CartItem> cartItems) {
        double totalPrices  = 0.0;
        for(CartItem item : cartItems) {
            totalPrices += item.getTotalPrice();
        }
        return totalPrices;
    }

}
