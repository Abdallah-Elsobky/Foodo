package iti.student.foodo.data.model.domain;

public class Instruction {
    private String step;

    public Instruction(String step) {
        this.step = step;
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
