package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
	private long ingredientId;
	private String name;
	private String detail;
	private String unit;
	private int calorie;
	private int carbon;
	private int protein;
	private int fat;
	private int sugar;
	private int natrium;
	private int baseAmount;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IngredientDto [ingredientId=")
		       .append(ingredientId)
		       .append(", name=")
		       .append(name)
			   .append(", detail=")
			   .append(detail)
			   .append(", unit=")
			   .append(unit)
			   .append(", calorie=")
			   .append(calorie)
			   .append(", carbon=")
			   .append(carbon)
			   .append(", protein=")
			   .append(protein)
			   .append(", fat=")
			   .append(fat)
			   .append(", sugar=")
			   .append(sugar)
			   .append(", natrium=")
			   .append(natrium)
			   .append(", baseAmount=")
			   .append(baseAmount)
			   .append("]");
		return builder.toString();
	}
	
	
}
