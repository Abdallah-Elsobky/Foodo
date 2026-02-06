package iti.student.foodo.data.model.dto;

import java.util.List;

public class MealsResponse {
	private List<MealsItem> meals;

	public List<MealsItem> getMeals(){
		return meals;
	}
}