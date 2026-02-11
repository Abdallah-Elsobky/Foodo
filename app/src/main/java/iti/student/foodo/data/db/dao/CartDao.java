package iti.student.foodo.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.rxjava3.core.Completable;
import iti.student.foodo.data.db.entity.CartIngredientEntity;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCartItem(CartIngredientEntity item);

    @Query("DELETE FROM cart WHERE ingredientName = :name AND measure = :measure")
    Completable deleteCartItem(
            String name,
            String measure
    );

    @Query("UPDATE cart SET isBought = :isBought WHERE ingredientName = :name AND measure = :measure")
    Completable updateBoughtState(
            String name,
            String measure,
            boolean isBought
    );

    @Query("DELETE FROM cart")
    Completable clearCart();
}

