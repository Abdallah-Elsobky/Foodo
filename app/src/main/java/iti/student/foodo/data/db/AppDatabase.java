package iti.student.foodo.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import iti.student.foodo.data.db.dao.CartDao;
import iti.student.foodo.data.db.dao.FavoriteDao;
import iti.student.foodo.data.db.dao.IngredientDao;
import iti.student.foodo.data.db.dao.InstructionDao;
import iti.student.foodo.data.db.dao.MealDao;
import iti.student.foodo.data.db.dao.PlannedMealDao;
import iti.student.foodo.data.db.dao.UserDao;
import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.data.db.entity.FavoriteMealEntity;
import iti.student.foodo.data.db.entity.IngredientEntity;
import iti.student.foodo.data.db.entity.InstructionEntity;
import iti.student.foodo.data.db.entity.MealEntity;
import iti.student.foodo.data.db.entity.PlannedMealEntity;
import iti.student.foodo.data.db.entity.UserEntity;

@Database(
        entities = {
                MealEntity.class,
                IngredientEntity.class,
                InstructionEntity.class,
                FavoriteMealEntity.class,
                CartIngredientEntity.class,
                PlannedMealEntity.class,
                UserEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract MealDao mealDao();

    public abstract IngredientDao ingredientDao();

    public abstract InstructionDao instructionDao();

    public abstract FavoriteDao favoriteDao();

    public abstract UserDao userDao();


    public abstract CartDao cartDao();

    public abstract PlannedMealDao plannedMealDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "foodo_db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

