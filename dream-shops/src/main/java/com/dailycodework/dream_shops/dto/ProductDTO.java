package com.dailycodework.dream_shops.dto;

import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
    private List<ImageDTO> images;
}
