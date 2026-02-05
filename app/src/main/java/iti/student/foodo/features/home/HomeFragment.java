package iti.student.foodo.features.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.counter.setText(String.valueOf(counter));
        binding.home.setOnClickListener(v -> {
            counter++;
            binding.counter.setText(String.valueOf(counter));
            getMeal();
        });
    }


    void getMeal() {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        apiService.getMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    Log.d("FIIIIO", "onResponse: " + response.getMeals().get(0).toString());
                },
                error -> {
                    Log.d("FIIIIO", "onResponse: " + error.getMessage());
                }
        );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}