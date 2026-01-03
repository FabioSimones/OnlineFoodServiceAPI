package com.devfabiosimones.service.implementation;

import com.devfabiosimones.model.Cart;
import com.devfabiosimones.model.CartItem;
import com.devfabiosimones.model.Food;
import com.devfabiosimones.model.User;
import com.devfabiosimones.repository.CartItemRepository;
import com.devfabiosimones.repository.CartRepository;
import com.devfabiosimones.repository.FoodRepository;
import com.devfabiosimones.request.AddCartItemRequest;
import com.devfabiosimones.service.CartService;
import com.devfabiosimones.service.FoodService;
import com.devfabiosimones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(request.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItems()){
            if (cartItem.getFood().equals(food)){
                int newQuantity = cartItem.getQuantity()+ request.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setIngredients(request.getIngredients());
        newCartItem.setTotalPrice(request.getQuantity()*food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);

        if (optionalCartItem.isEmpty()){
            throw new Exception("Cart item not found.");
        }

        CartItem cartItem = optionalCartItem.get();
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice()*quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);

        if (optionalCartItem.isEmpty()){
            throw new Exception("Cart item not found.");
        }

        CartItem cartItem = optionalCartItem.get();
        cart.getItems().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {

        Long total = 0L;

        for (CartItem cartItem : cart.getItems()){
            total += cartItem.getFood().getPrice()*cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isEmpty()){
            throw new Exception("cart not found with id " + id);
        }

        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return cartRepository.findByCustomerId(user.getId());
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart = findCartByUserId(jwt);
        cart.getItems().clear();

        return cartRepository.save(cart);
    }
}
