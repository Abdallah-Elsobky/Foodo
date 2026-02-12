package iti.student.foodo.features.cart.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.db.entity.CartIngredientEntity;
import iti.student.foodo.data.network.firebase.FirestoreService;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.MealRepositoryImpl;
import iti.student.foodo.databinding.FragmentCartBinding;
import iti.student.foodo.features.cart.presenter.CartContract;
import iti.student.foodo.features.cart.presenter.CartPresenter;
import iti.student.foodo.features.utils.CustomToastKt;


public class CartFragment extends Fragment implements CartContract.View {

    FragmentCartBinding binding;
    CartAdapter adapter;
    CartContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CartPresenter(new MealRepositoryImpl(
                new MealsRemoteDataSource(
                        ApiClient.getInstance().create(ApiService.class)
                ),
                new MealLocalDataSource(AppDatabase.getInstance(requireContext())),
                new FirestoreService()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        presenter.getCart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        presenter.detachView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {
        CustomToastKt.errorToast(requireContext(), message);
    }

    @Override
    public void showCart(List<CartIngredientEntity> items) {
        if (items.isEmpty())
            binding.noItemContainer.setVisibility(View.VISIBLE);
        else
            binding.noItemContainer.setVisibility(View.GONE);
        binding.cartCount.setText(String.valueOf(items.size()));
        Log.d("loco", "items: " + items.size());
        adapter = new CartAdapter(item -> {
            presenter.deleteItem(item);
        });
        adapter.submitList(items);
        binding.cartRv.setAdapter(adapter);
    }
}