package com.devfabiosimones.controller;

import com.devfabiosimones.model.Food;
import com.devfabiosimones.model.Restaurant;
import com.devfabiosimones.model.User;
import com.devfabiosimones.request.CreateFoodRequest;
import com.devfabiosimones.response.MessageResponse;
import com.devfabiosimones.service.FoodService;
import com.devfabiosimones.service.RestaurantService;
import com.devfabiosimones.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.createFood(request, request.getCategory(), restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food deleted successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaliableStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvaliableStatus(id);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
}
