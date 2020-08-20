package com.ssafy.cookblog.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.cookblog.dto.RecipePhotoDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDto {
   private String nickname;
   private List<String> foodCategoryName;
   private int recipeId;
   private String recipeName;
   private String recipeThumbnailSrc;
   private String recipeDetail;
   private int userId;
   private LocalDateTime regTime;
   private List<Integer> foodCategoryId;
   private int cookTime;
   private int level;
   private int calorie;
   private int carbon;
   private int protein;
   private int fat;
   private int sugar;
   private int natrium;
   private double avgRating;
   private List<RecipePhotoDto> recipePhotoList;
   private List<RecipeIngredientResponseDto> ingredientList;
   private List<ReviewResponseDto> reviewDtoList;


}
