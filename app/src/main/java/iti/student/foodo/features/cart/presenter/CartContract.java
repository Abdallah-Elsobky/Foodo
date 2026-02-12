package iti.student.foodo.features.cart.presenter;

import java.util.List;

import iti.student.foodo.core.base.BasePresenter;
import iti.student.foodo.core.base.BaseView;
import iti.student.foodo.data.db.entity.CartIngredientEntity;

public interface CartContract {
    interface View extends BaseView {
        void showCart(List<CartIngredientEntity> items);
    }

    interface Presenter extends BasePresenter<View> {
        void getCart();

        void deleteItem(CartIngredientEntity item);
    }
}
