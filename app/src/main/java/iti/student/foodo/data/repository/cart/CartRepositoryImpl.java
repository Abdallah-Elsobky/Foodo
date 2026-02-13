package iti.student.foodo.data.repository.cart;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.db.entity.CartIngredientEntity;

public class CartRepositoryImpl implements CartRepository {

    private final MealLocalDataSource localDataSource;

    public CartRepositoryImpl(MealLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    @Override
    public Completable addToCart(CartIngredientEntity item) {
        return localDataSource.addTOCart(item);
    }

    @Override
    public Flowable<List<CartIngredientEntity>> getCartItems() {
        return localDataSource.getCartItems();
    }

    @Override
    public Completable removeItemFromCart(String ingredientName, String measure) {
        return localDataSource.removeItemFromCart(ingredientName, measure);
    }
}
