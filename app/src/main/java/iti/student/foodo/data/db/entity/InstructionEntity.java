package iti.student.foodo.data.db.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "instructions",
        indices = {@Index(value = {"mealId", "text"}, unique = true)}
)
public class InstructionEntity {

    @PrimaryKey(autoGenerate = true)
    public int instructionId;

    @NonNull
    public String mealId;

    @NonNull
    public String text;

    public int getInstructionId() {
        return instructionId;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public InstructionEntity(@NonNull String mealId, @NonNull String text) {
        this.mealId = mealId;
        this.text = text;
    }
}
