package com.devfabiosimones.service;

import com.devfabiosimones.model.Category;
import com.devfabiosimones.model.Food;
import com.devfabiosimones.model.Restaurant;
import com.devfabiosimones.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
    void deleteFood(Long foodId) throws Exception;
    public List<Food> getRestaurantsFood (Long restaurantId, boolean isVegan,
                                          boolean isNonVegan, boolean isSeasonal,
                                          String foodCategory);
    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;
    public Food updateAvaliableStatus(Long foodId) throws Exception;
}
