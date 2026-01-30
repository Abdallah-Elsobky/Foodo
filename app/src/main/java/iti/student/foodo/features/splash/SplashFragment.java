package iti.student.foodo.features.splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.student.foodo.R;
import iti.student.foodo.databinding.FragmentSearchBinding;
import iti.student.foodo.databinding.FragmentSplashBinding;


public class SplashFragment extends Fragment {

    FragmentSplashBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.splash.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.navigateToOnboardingFromSplash);
        });

        binding.getRoot().setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.navigateToAuthFromSplash);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}