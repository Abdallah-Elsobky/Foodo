package iti.student.foodo.features.cart.presenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.data.repository.cart.CartRepository;

public class CartPresenter implements CartContract.Presenter {
    private CartContract.View view;
    private final CartRepository cartRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CartPresenter(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void getCart() {
        Disposable disposable = cartRepository.getCartItems().observeOn(AndroidSchedulers.mainThread()).subscribe(
                items -> {
                    if (view != null) {
                        view.showCart(items);
                    }
                }, throwable -> {
                    if (view != null) {
                        view.showError(throwable.getMessage());
                    }
                }
        );
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteItem(CartIngredientEntity item) {
        cartRepository.removeItemFromCart(item.ingredientName, item.measure).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override
    public void attachView(CartContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
        view = null;
    }
}
