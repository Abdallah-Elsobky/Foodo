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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import iti.student.foodo.R;
import iti.student.foodo.data.datasource.local.PrefManager;
import iti.student.foodo.databinding.FragmentSplashBinding;


public class SplashFragment extends Fragment {

    FragmentSplashBinding binding;
    PrefManager prefManager;
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(requireContext());
        rotateWithFadeIn(binding.logo);
        new Handler().postDelayed(() -> {
            if (prefManager.isFirstTime()) {
                Navigation.findNavController(view).navigate(R.id.navigateToOnboardingFromSplash);
            } else if (auth.getCurrentUser() != null) {
                Navigation.findNavController(view).navigate(R.id.navigateToMainFromSplash);
            } else {
                Navigation.findNavController(view).navigate(R.id.navigateToAuthFromSplash);
            }
        }, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}