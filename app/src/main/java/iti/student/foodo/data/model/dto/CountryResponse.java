package iti.student.foodo.data.model.dto;

import java.util.List;

public class CountryResponse{
	private List<CountriesItem> meals;

	public List<CountriesItem> getCountries(){
		return meals;
	}
}