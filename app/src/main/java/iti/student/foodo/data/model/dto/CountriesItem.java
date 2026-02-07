package iti.student.foodo.data.model.dto;

public class CountriesItem {
	private String strArea;

	public String getStrArea(){
		return strArea;
	}

    @Override
    public String toString() {
        return "CountriesItem{" +
                "strArea='" + strArea + '\'' +
                '}';
    }
}
