package iti.student.foodo.data.model.dto;

public class CategoriesItem{
	private String strCategory;
	private String strCategoryDescription;
	private String idCategory;
	private String strCategoryThumb;

    public CategoriesItem(String strCategory, String strCategoryDescription) {
        this.strCategory = strCategory;
        this.strCategoryDescription = strCategoryDescription;
    }

    public String getStrCategory(){
		return strCategory;
	}

	public String getStrCategoryDescription(){
		return strCategoryDescription;
	}

	public String getIdCategory(){
		return idCategory;
	}

	public String getStrCategoryThumb(){
		return strCategoryThumb;
	}
}
