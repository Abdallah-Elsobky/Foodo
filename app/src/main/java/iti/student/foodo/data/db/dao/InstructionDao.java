package iti.student.foodo.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import iti.student.foodo.data.db.entity.InstructionEntity;

@Dao
public interface InstructionDao {

    @Query("SELECT * FROM instructions WHERE mealId = :mealId ORDER BY instructionId ASC")
    Flowable<List<InstructionEntity>> getInstructionsForMeal(String mealId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertInstructions(List<InstructionEntity> instructions);
}

