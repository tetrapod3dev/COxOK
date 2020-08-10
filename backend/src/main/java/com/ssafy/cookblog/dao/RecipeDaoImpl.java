package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.FoodCategoryDto;
import com.ssafy.cookblog.dto.IngredientDto;
import com.ssafy.cookblog.dto.LikeDto;
import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.RecipeFoodCategoryDto;
import com.ssafy.cookblog.dto.RecipeIngredientDto;
import com.ssafy.cookblog.dto.request.RecipeSearchRequestDto;
import com.ssafy.cookblog.dto.response.RecipeIngredientResponseDto;
import com.ssafy.cookblog.dto.response.RecipeResponseDto;

@Repository
public class RecipeDaoImpl implements RecipeDao{
	
	@Autowired
	SqlSession session;
	
	public RecipeResponseDto select(long recipeId) {
		return session.selectOne("recipe.select", recipeId);
	}

	@Override
	public List<RecipeDto> selectAll(int startIndex) {
		return session.selectList("recipe.selectAll", startIndex);
	}

	@Override
	public int write(RecipeDto recipeDto) {
		return session.insert("recipe.write", recipeDto);
	}

	@Override
	public List<FoodCategoryDto> selectAllFoodCategory() {
		return session.selectList("recipe.selectAllFoodCategory");
	}

	@Override
	public List<IngredientDto> selectAllIngredient() {
		return session.selectList("recipe.selectAllIngredient");
	}
	
	@Override
	public List<IngredientDto> selectAllIngredientAdmin(int startIndex) {
		return session.selectList("recipe.selectAllIngredientAdmin", startIndex);
	}
	
	@Override
	public int insertIngredientAdmin(IngredientDto ingredient) {
		return session.insert("recipe.registerIngredientAdmin", ingredient);
	}
	
	@Override
	public int updateIngredientAdmin(IngredientDto ingredient) {
		return session.update("recipe.modifyIngredientAdmin", ingredient);
	}

	@Override
	public List<RecipeIngredientResponseDto> selectRecipeIngredient(long recipeId) {
		return session.selectList("recipe.selectRecipeIngredient", recipeId);
	}

	@Override
	public int delete(long recipeId) {
		return session.delete("recipe.delete", recipeId);
	}

	@Override
	public long getRecipeId() {
		return session.selectOne("recipe.getRecipeId");
	}

	@Override
	public IngredientDto selectIngredientById(long ingredientId) {
		return session.selectOne("recipe.selectIngredientById", ingredientId);
	}
	
	public int insertRecipeCategory(RecipeFoodCategoryDto recipeFoodCategoryDto) {
		return session.insert("category.insertRecipeCategory", recipeFoodCategoryDto);
	}

	@Override
	public int insertRecipeIngredient(RecipeIngredientDto recipeIngredient) {
		return session.insert("recipe.insertRecipeIngredient", recipeIngredient);
	}
	
	@Override
	public int deleteRecipeIngredient(long recipeId) {
		return session.delete("recipe.deleteRecipeIngredient", recipeId);
	}
	
	@Override
	public Long selectTotalRecipeNum() {
		return session.selectOne("recipe.selectTotalRecipeNum");
	}
	
	@Override
	public List<RecipeDto> search(RecipeSearchRequestDto recipeSearchRequestDto) {
		return session.selectList("recipe.search",recipeSearchRequestDto);
	}
	
	@Override
	public Long searchTotal(RecipeSearchRequestDto recipeSearchRequestDto) {
		return session.selectOne("recipe.searchTotal",recipeSearchRequestDto);
	}

	@Override
	public int insertRecipeLike(LikeDto likeDto) {
		return session.insert("likeRecipe.createLike", likeDto);
	}

	@Override
	public int cancelRecipeLike(LikeDto likeDto) {
		return session.delete("likeRecipe.cancelLike", likeDto);
	}

	@Override
	public int recipeLikeCount(long recipeId) {
		return session.selectOne("likeRecipe.likeCount", recipeId);
	}

	@Override
	public boolean reipceUserLike(LikeDto like) {
		return session.selectOne("likeRecipe.userLike", like);
	}
	
	@Override
	public List<LikeDto> allUserLike(long recipeId) {
		return session.selectList("recipe.allUserLike", recipeId);
	}

	@Override
	public int updateRecipe(RecipeDto recipeDto) {
		return session.update("recipe.updateRecipe", recipeDto);
	}

	
	@Override
	public List<RecipeDto> selectRecipeListByCategoryId(long foodCategoryId) {
		return session.selectList("recipe.selectRecipeListByCategoryId", foodCategoryId);
	}

	@Override
	public int deleteIngredientAdmin(long ingredientId) {
		return session.delete("recipe.removeIngredientAdmin", ingredientId);
	}

	@Override
	public int insertIngredientUser(IngredientDto ingredient) {
		return session.insert("recipe.registerIngredientUser", ingredient);
	}

	@Override
	public List<IngredientDto> selectAllIngredientToBeUpdated(int startIndex) {
		return session.selectList("recipe.allIngredientToBeUpdated", startIndex);
	}

}
