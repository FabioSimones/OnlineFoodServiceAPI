package com.devfabiosimones.request;

import com.devfabiosimones.model.Category;
import com.devfabiosimones.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long restaurantId;
    private boolean vegan;
    private boolean seasional;
    private List<IngredientsItem> ingredients;
}
