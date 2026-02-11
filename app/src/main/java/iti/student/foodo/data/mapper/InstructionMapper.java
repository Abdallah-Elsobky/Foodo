package iti.student.foodo.data.mapper;

import java.util.ArrayList;
import java.util.List;

import iti.student.foodo.data.db.entity.InstructionEntity;
import iti.student.foodo.data.model.domain.Instruction;

public class InstructionMapper {

    static List<Instruction> mapInstructions(String text, String mealId) {
        List<Instruction> instructions = new ArrayList<>();
        if (text == null) return instructions;

        String[] steps = text.split("\\.");

        for (String step : steps) {
            if (!step.trim().isEmpty() && step.trim().length() >= 10) {
                instructions.add(new Instruction(step.trim(), mealId));
            }
        }
        return instructions;
    }

    public static InstructionEntity toEntity(Instruction instruction) {
        return new InstructionEntity(
                instruction.getMealId(),
                instruction.getStep()
        );
    }

    public static List<InstructionEntity> toEntityList(List<Instruction> instructions) {
        List<InstructionEntity> entities = new ArrayList<>();
        for (Instruction instruction : instructions) {
            entities.add(toEntity(instruction));
        }
        return entities;
    }

    public static Instruction fromEntity(InstructionEntity entity) {
        return new Instruction(
                entity.getText(),
                entity.getMealId()
        );
    }

    public static List<Instruction> fromEntityList(List<InstructionEntity> entities) {
        List<Instruction> instructions = new ArrayList<>();
        for (InstructionEntity entity : entities) {
            instructions.add(fromEntity(entity));
        }
        return instructions;
    }

}
