package iti.student.foodo.features.splash;

import static iti.student.foodo.core.utils.Animations.rotateWithFadeIn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.student.foodo.R;
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
        rotateWithFadeIn(binding.logo);

        new Handler().postDelayed(() -> {
            Navigation.findNavController(view).navigate(R.id.navigateToOnboardingFromSplash);
        }, 3000);
//            Navigation.findNavController(view).navigate(R.id.navigateToMainFromSplash);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}