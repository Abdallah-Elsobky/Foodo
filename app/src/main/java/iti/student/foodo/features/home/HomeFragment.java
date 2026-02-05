package iti.student.foodo.features.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.data.model.MealsResponse;
import iti.student.foodo.data.remote.retrofit.ApiService;
import iti.student.foodo.databinding.FragmentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        apiService.getMeals().enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                Log.d("FIIIIO", "onResponse: " + response.body().getMeals().get(0).toString());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                Log.d("FIIIIO", "onResponse: " + t.getMessage());
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}