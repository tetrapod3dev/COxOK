package com.ssafy.cookblog.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.CategoryDao;
import com.ssafy.cookblog.dao.RecipeDao;
import com.ssafy.cookblog.dao.RecipePhotoDao;
import com.ssafy.cookblog.dao.ReviewDao;
import com.ssafy.cookblog.dto.FoodCategoryDto;
import com.ssafy.cookblog.dto.IngredientDto;
import com.ssafy.cookblog.dto.LikeDto;
import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.RecipeFoodCategoryDto;
import com.ssafy.cookblog.dto.RecipeIngredientDto;
import com.ssafy.cookblog.dto.RecipePhotoDto;
import com.ssafy.cookblog.dto.request.RecipeRegisterRequestDto;
import com.ssafy.cookblog.dto.request.RecipeSearchRequestDto;
import com.ssafy.cookblog.dto.request.RecipeUpdateRequestDto;
import com.ssafy.cookblog.dto.response.RecipeIngredientResponseDto;
import com.ssafy.cookblog.dto.response.RecipeResponseDto;

@Service
public class RecipeServiceImpl implements RecipeService {
	
	@Autowired
	RecipeDao recipeDao;
	
	@Autowired
	RecipePhotoDao recipePhotoDao;
	
	@Autowired
	ReviewDao reviewDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Override
	public RecipeResponseDto getOneRecipe(long recipeId) {
		RecipeResponseDto recipeResponseDto = recipeDao.select(recipeId);
		recipeResponseDto.setRecipePhotoList(recipePhotoDao.selectAll(recipeId));
		recipeResponseDto.setIngredientList(recipeDao.selectRecipeIngredient(recipeId));
		recipeResponseDto.setReviewDtoList(reviewDao.selectAll(recipeId));
		recipeResponseDto.setFoodCategoryId(categoryDao.selectRecipeCategoryId(recipeId));
		recipeResponseDto.setFoodCategoryName(categoryDao.selectRecipeCategoryName(recipeId));
		return recipeResponseDto;
	}

	@Override
	public List<RecipeDto> selectAll(int startIndex) {
		return recipeDao.selectAll(startIndex);
	}

	@Override
	public int registerRecipe(RecipeRegisterRequestDto recipeRequestDto) {
		RecipeDto recipeDto = new RecipeDto();
		
		recipeDto.setUserId(recipeRequestDto.getUserId());
		
		recipeDto.setLevel(Integer.parseInt(recipeRequestDto.getLevel()));
		recipeDto.setCookTime(Integer.parseInt(recipeRequestDto.getCookTime()));
		
		// 재료 영양성분 계산
		int len = recipeRequestDto.getIngredientPk().length;
		int sumCalorie=0, sumCarbon=0, sumProtein=0, sumFat=0, sumSugar=0, sumNatrium=0;
		for(int i=0; i < len; i++) {
			long ingredientId = recipeRequestDto.getIngredientPk()[i];
			IngredientDto ingredientDto = recipeDao.selectIngredientById(ingredientId);
			double per = (double) recipeRequestDto.getIngredientAmount()[i] / ingredientDto.getBaseAmount();
			sumCalorie += (int) (ingredientDto.getCalorie() * per);
			sumCarbon += (int) (ingredientDto.getCarbon() * per);
			sumProtein += (int) (ingredientDto.getProtein() * per);
			sumFat += (int) (ingredientDto.getFat() * per);
			sumSugar += (int) (ingredientDto.getSugar() * per);
			sumNatrium += (int) (ingredientDto.getNatrium() * per);
		}
		
		recipeDto.setCalorie(sumCalorie);
		recipeDto.setCarbon(sumCarbon);
		recipeDto.setProtein(sumProtein);
		recipeDto.setFat(sumFat);
		recipeDto.setSugar(sumSugar);
		recipeDto.setNatrium(sumNatrium);
				
		recipeDto.setRecipeName(recipeRequestDto.getRecipeName());
		
		// 썸네일 사진 이미지 변경
		
		String fileName = recipeRequestDto.getRecipeThumbnail().getOriginalFilename();
		String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());  //현재시간
	    String realFileName = now + "_" + fileName;
	    try {
			recipeRequestDto.getRecipeThumbnail().transferTo(new File(realFileName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		recipeDto.setRecipeThumbnailSrc(realFileName);
		recipeDto.setRecipeDetail(recipeRequestDto.getRecipeDetail());
		
		int res = recipeDao.write(recipeDto);
		if(res == 0)
			return res;
		
		long recipeId = getRecipeId();

		//카테고리 추가
		for(int categoryId : recipeRequestDto.getCategories()) {
			RecipeFoodCategoryDto dto = new RecipeFoodCategoryDto();
			dto.setRecipeId(recipeId);
			dto.setFoodCategoryId(categoryId);
			res = categoryDao.insertRecipeCategory(dto);
			if(res == 0) return res;
		}
		
		len = recipeRequestDto.getDescription().length;
		for(int i = 0; i < len; i++) {
			// 레시피 사진 이미지 이름 변경
			fileName = recipeRequestDto.getPhoto()[i].getOriginalFilename();
			now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());  //현재시간
		    realFileName = now + "_" + fileName;
		    try {
				recipeRequestDto.getPhoto()[i].transferTo(new File(realFileName));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			recipeDto.setRecipeThumbnailSrc(realFileName);
			RecipePhotoDto photoDto = new RecipePhotoDto();
			photoDto.setRecipeId(recipeId);
			photoDto.setPhotoSrc(realFileName);
			photoDto.setPhotoDetail(recipeRequestDto.getDescription()[i]);
			res = recipePhotoDao.insert(photoDto);
			if(res == 0) return res;
		}
		
		for(int i = 0; i < recipeRequestDto.getIngredientPk().length; i++) {
			RecipeIngredientDto recipeIngredient = new RecipeIngredientDto();
			recipeIngredient.setIngredientId(recipeRequestDto.getIngredientPk()[i]);
			recipeIngredient.setRecipeId(recipeId);
			recipeIngredient.setAmount(recipeRequestDto.getIngredientAmount()[i]);
			res = recipeDao.insertRecipeIngredient(recipeIngredient);
			if(res == 0) return res;
		}
		
		return 1;
	}
	
	@Override
	public int updateRecipe(RecipeUpdateRequestDto recipeUpdateRequestDto) {
		RecipeDto recipeDto = new RecipeDto();
		
		long recipeId = recipeUpdateRequestDto.getRecipeId();
		categoryDao.deleteRecipeCategory(recipeId); // recipeId에 연결된 카테고리 삭제
		recipePhotoDao.delete(recipeId); // recipeId에 연결된 포토 삭제
		recipeDao.deleteRecipeIngredient(recipeId); // recipeId에 연결된 재료 삭제
		
		recipeDto.setRecipeId(recipeId);
		recipeDto.setUserId(recipeUpdateRequestDto.getUserId());
		
		recipeDto.setLevel(Integer.parseInt(recipeUpdateRequestDto.getLevel()));
		recipeDto.setCookTime(Integer.parseInt(recipeUpdateRequestDto.getCookTime()));
		
		// 재료 영양성분 계산
		int len = recipeUpdateRequestDto.getIngredientPk().length;
		int sumCalorie=0, sumCarbon=0, sumProtein=0, sumFat=0, sumSugar=0, sumNatrium=0;
		for(int i=0; i < len; i++) {
			long ingredientId = recipeUpdateRequestDto.getIngredientPk()[i];
			IngredientDto ingredientDto = recipeDao.selectIngredientById(ingredientId);
			double per = (double) recipeUpdateRequestDto.getIngredientAmount()[i] / ingredientDto.getBaseAmount();
			sumCalorie += (int) (ingredientDto.getCalorie() * per);
			sumCarbon += (int) (ingredientDto.getCarbon() * per);
			sumProtein += (int) (ingredientDto.getProtein() * per);
			sumFat += (int) (ingredientDto.getFat() * per);
			sumSugar += (int) (ingredientDto.getSugar() * per);
			sumNatrium += (int) (ingredientDto.getNatrium() * per);
		}
		
		recipeDto.setCalorie(sumCalorie);
		recipeDto.setCarbon(sumCarbon);
		recipeDto.setProtein(sumProtein);
		recipeDto.setFat(sumFat);
		recipeDto.setSugar(sumSugar);
		recipeDto.setNatrium(sumNatrium);
				
		recipeDto.setRecipeName(recipeUpdateRequestDto.getRecipeName());
		
		// 썸네일 사진 이미지 변경
		recipeDto.setRecipeThumbnailSrc(recipeUpdateRequestDto.getRecipeThumbnail());
		recipeDto.setRecipeDetail(recipeUpdateRequestDto.getRecipeDetail());
		
		int res = recipeDao.updateRecipe(recipeDto);
		if(res == 0)
			return res;

		//카테고리 추가
		for(int categoryId : recipeUpdateRequestDto.getCategories()) {
			RecipeFoodCategoryDto dto = new RecipeFoodCategoryDto();
			dto.setRecipeId(recipeId);
			dto.setFoodCategoryId(categoryId);
			res = categoryDao.insertRecipeCategory(dto);
			if(res == 0) return res;
		}
		
		len = recipeUpdateRequestDto.getDescription().length;
		for(int i = 0; i < len; i++) {
			RecipePhotoDto photoDto = new RecipePhotoDto();
			photoDto.setRecipeId(recipeId);
			photoDto.setPhotoSrc(recipeUpdateRequestDto.getPhoto()[i]);
			photoDto.setPhotoDetail(recipeUpdateRequestDto.getDescription()[i]);
			res = recipePhotoDao.insert(photoDto);
			if(res == 0) return res;
		}
		
		for(int i = 0; i < recipeUpdateRequestDto.getIngredientPk().length; i++) {
			RecipeIngredientDto recipeIngredient = new RecipeIngredientDto();
			recipeIngredient.setIngredientId(recipeUpdateRequestDto.getIngredientPk()[i]);
			recipeIngredient.setRecipeId(recipeId);
			recipeIngredient.setAmount(recipeUpdateRequestDto.getIngredientAmount()[i]);
			res = recipeDao.insertRecipeIngredient(recipeIngredient);
			if(res == 0) return res;
		}
		
		return 1;
	}

	@Override
	public List<FoodCategoryDto> selectAllFoodCategory() {
		return recipeDao.selectAllFoodCategory();
	}

	@Override
	public List<IngredientDto> selectAllIngredient() {
		return recipeDao.selectAllIngredient();
	}
	
	@Override
	public List<IngredientDto> selectAllIngredientAdmin(int startIndex) {
		return recipeDao.selectAllIngredientAdmin(startIndex);
	}
	
	@Override
	public int registerIngredientAdmin(IngredientDto ingredient) {
		return recipeDao.insertIngredientAdmin(ingredient);
	}

	@Override
	public List<RecipeIngredientResponseDto> selectRecipeIngredient(long recipeId) {
		return recipeDao.selectRecipeIngredient(recipeId);
	}

	@Override
	public int deleteRecipe(long recipeId) {
		return recipeDao.delete(recipeId);
	}

	@Override
	public long getRecipeId() {
		return recipeDao.getRecipeId();
	}
	@Override
	public long getTotalRecipeNum() {
		return recipeDao.selectTotalRecipeNum();
	}

	@Override
	public List<RecipeDto> search(RecipeSearchRequestDto recipeSearchRequestDto) {
		List<String> categoryList = recipeSearchRequestDto.getSelectedCategory();
		List<String> ingredientList = recipeSearchRequestDto.getSelectedIngredients();
		
		List<Integer> tempCategory=new ArrayList<Integer>();
		List<Integer> tempIngredient=new ArrayList<Integer>();
		
		if(categoryList != null) {
			for(String category : categoryList) 
				tempCategory.add(Integer.parseInt(category));
			recipeSearchRequestDto.setCategoryId(tempCategory);
		}

		if(ingredientList != null) {
			for(String ingredient : ingredientList) 
				tempIngredient.add(Integer.parseInt(ingredient));
			recipeSearchRequestDto.setIngredientId(tempIngredient);
		}
		
		return recipeDao.search(recipeSearchRequestDto);
	}
	@Override
	public Long searchTotal(RecipeSearchRequestDto recipeSearchRequestDto) {
		List<String> categoryList = recipeSearchRequestDto.getSelectedCategory();
		List<String> ingredientList = recipeSearchRequestDto.getSelectedIngredients();
		
		List<Integer> tempCategory=new ArrayList<Integer>();
		List<Integer> tempIngredient=new ArrayList<Integer>();
		
		if(categoryList != null) {
			for(String category : categoryList) 
				tempCategory.add(Integer.parseInt(category));
			recipeSearchRequestDto.setCategoryId(tempCategory);
		}

		if(ingredientList != null) {
			for(String ingredient : ingredientList) 
				tempIngredient.add(Integer.parseInt(ingredient));
			recipeSearchRequestDto.setIngredientId(tempIngredient);
		}
		
		return recipeDao.searchTotal(recipeSearchRequestDto);
	}

	@Override
	public int insertRecipeLike(LikeDto likeDto) {
		return recipeDao.insertRecipeLike(likeDto);
	}

	@Override
	public int cancelRecipeLike(LikeDto likeDto) {
		return recipeDao.cancelRecipeLike(likeDto);
	}

	@Override
	public int recipeLikeCount(long recipeId) {
		return recipeDao.recipeLikeCount(recipeId);
	}

	@Override
	public boolean reipceUserLike(LikeDto likeDto) {
		return recipeDao.reipceUserLike(likeDto);
	}

	@Override
	public List<LikeDto> allUserLike(long recipeId) {
		return recipeDao.allUserLike(recipeId);
	}

	@Override
	public FoodCategoryDto getRandomCategoryByUserId(long userId) {
		return categoryDao.selectRandomCategoryByUserId(userId);
	}
	
	@Override
	public List<RecipeDto> getRecipeListByCategoryId(long foodCategoryId) {
		return recipeDao.selectRecipeListByCategoryId(foodCategoryId);
	}

	@Override
	public int modifyIngredientAdmin(IngredientDto ingredient) {
		//재료가 포함된 레시피 리스트
		List<Long> recipeList = recipeDao.selectRecipeIdByIngredientId(ingredient.getIngredientId());

		//재료 내부 업데이트
		recipeDao.updateIngredientAdmin(ingredient);
		
		if(recipeList!=null) {
			for(long recipeId : recipeList) {	//레시피 하나당
				List<RecipeIngredientResponseDto> recipeIngredient = recipeDao.selectRecipeIngredient(recipeId);
				int len =recipeIngredient.size();
				
				RecipeDto recipeDto = new RecipeDto();
				
				int sumCalorie=0, sumCarbon=0, sumProtein=0, sumFat=0, sumSugar=0, sumNatrium=0;
				
				for(int i=0; i < len; i++) {
					IngredientDto ingredientDto = recipeDao.selectIngredientById(recipeIngredient.get(i).getIngredientId());
					double per = (double) recipeIngredient.get(i).getAmount() / ingredientDto.getBaseAmount();
					sumCalorie += (int) (ingredientDto.getCalorie() * per);
					sumCarbon += (int) (ingredientDto.getCarbon() * per);
					sumProtein += (int) (ingredientDto.getProtein() * per);
					sumFat += (int) (ingredientDto.getFat() * per);
					sumSugar += (int) (ingredientDto.getSugar() * per);
					sumNatrium += (int) (ingredientDto.getNatrium() * per);
				}

				recipeDto.setRecipeId(recipeId);
				recipeDto.setCalorie(sumCalorie);
				recipeDto.setCarbon(sumCarbon);
				recipeDto.setProtein(sumProtein);
				recipeDto.setFat(sumFat);
				recipeDto.setSugar(sumSugar);
				recipeDto.setNatrium(sumNatrium);
						
				recipeDao.updateNutrient(recipeDto);
			}
		}
		return 1;
	}

	@Override
	public int removeIngredientAdmin(long ingredientId) {
		
		//재료가 포함된 레시피 리스트
		List<Long> recipeList = recipeDao.selectRecipeIdByIngredientId(ingredientId);

		//재료 내부 업데이트
		recipeDao.deleteIngredientAdmin(ingredientId);
		
		if(recipeList!=null) {
			for(long recipeId : recipeList) {	//레시피 하나당
				List<RecipeIngredientResponseDto> recipeIngredient = recipeDao.selectRecipeIngredient(recipeId);
				int len =recipeIngredient.size();
				
				RecipeDto recipeDto = new RecipeDto();
				
				int sumCalorie=0, sumCarbon=0, sumProtein=0, sumFat=0, sumSugar=0, sumNatrium=0;
				
				for(int i=0; i < len; i++) {
					IngredientDto ingredientDto = recipeDao.selectIngredientById(recipeIngredient.get(i).getIngredientId());
					double per = (double) recipeIngredient.get(i).getAmount() / ingredientDto.getBaseAmount();
					sumCalorie += (int) (ingredientDto.getCalorie() * per);
					sumCarbon += (int) (ingredientDto.getCarbon() * per);
					sumProtein += (int) (ingredientDto.getProtein() * per);
					sumFat += (int) (ingredientDto.getFat() * per);
					sumSugar += (int) (ingredientDto.getSugar() * per);
					sumNatrium += (int) (ingredientDto.getNatrium() * per);
				}

				recipeDto.setRecipeId(recipeId);
				recipeDto.setCalorie(sumCalorie);
				recipeDto.setCarbon(sumCarbon);
				recipeDto.setProtein(sumProtein);
				recipeDto.setFat(sumFat);
				recipeDto.setSugar(sumSugar);
				recipeDto.setNatrium(sumNatrium);
						
				recipeDao.updateNutrient(recipeDto);
			}
		}
		return 1;
	}

	@Override
	public IngredientDto readIngredientAdmin(long ingredientId) {
		return recipeDao.selectIngredientById(ingredientId);
	}

}
