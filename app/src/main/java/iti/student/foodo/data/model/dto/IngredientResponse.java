package iti.student.foodo.data.model.dto;

import java.util.List;

public class IngredientResponse {
	private List<IngredientsItem> meals;

	public List<IngredientsItem> getIngredients(){
		return meals;
	}
}