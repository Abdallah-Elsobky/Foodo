package iti.student.foodo.data.model.domain;

public class Instruction {
    private String step;
    private String mealId;

    public String getMealId() {
        return mealId;
    }

    public Instruction(String step, String mealId) {
        this.step = step;
        this.mealId = mealId;
    }

    public String getStep() {
        return step;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "step='" + step + '\'' +
                '}' + "\n";
    }
}
