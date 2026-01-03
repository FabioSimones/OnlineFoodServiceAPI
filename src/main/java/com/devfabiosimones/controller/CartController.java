package com.devfabiosimones.controller;

import com.devfabiosimones.model.Cart;
import com.devfabiosimones.model.CartItem;
import com.devfabiosimones.request.AddCartItemRequest;
import com.devfabiosimones.request.UpdateCartItemRequest;
import com.devfabiosimones.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem = cartService.addItemToCart(request, jwt);

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemToQuantity(
            @RequestBody UpdateCartItemRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception {

        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());

        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.removeItemFromCart(id, jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.clearCart(jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt) throws Exception {

        Cart cart = cartService.findCartByUserId(jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
