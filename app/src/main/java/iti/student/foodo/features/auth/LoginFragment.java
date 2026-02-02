package iti.student.foodo.features.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.student.foodo.R;
import iti.student.foodo.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("top", "onCreateView: ");
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("top", "onViewCreated: ");
        binding.signupBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.navigateToRegisterFromLogin);
        });
        binding.guestBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.navigateToMainFromLogin);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("top", "onDestroyView: ");
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("top", "onDestroy: ");
    }
}