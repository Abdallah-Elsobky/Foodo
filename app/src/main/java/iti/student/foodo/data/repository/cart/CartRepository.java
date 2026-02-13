package iti.student.foodo.data.repository.cart;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import iti.student.foodo.data.db.entity.CartIngredientEntity;

public interface CartRepository {

    Completable addToCart(CartIngredientEntity item);

    Flowable<List<CartIngredientEntity>> getCartItems();

    Completable removeItemFromCart(String ingredientName, String measure);
}
