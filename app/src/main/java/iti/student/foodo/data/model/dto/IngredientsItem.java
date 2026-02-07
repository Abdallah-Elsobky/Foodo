package iti.student.foodo.data.model.dto;

public class IngredientsItem {
	private String strDescription;
	private String strIngredient;
	private String strThumb;
	private String strType;
	private String idIngredient;

	public String getStrDescription(){
		return strDescription;
	}

	public String getStrIngredient(){
		return strIngredient;
	}

	public String getStrThumb(){
		return strThumb;
	}

	public String getStrType(){
		return strType;
	}

	public String getIdIngredient(){
		return idIngredient;
	}

    @Override
    public String toString() {
        return "IngredientsItem{" +
                "strDescription='" + strDescription + '\'' +
                ", strIngredient='" + strIngredient + '\'' +
                ", strThumb='" + strThumb + '\'' +
                ", strType=" + strType +
                ", idIngredient='" + idIngredient + '\'' +
                '}';
    }
}
