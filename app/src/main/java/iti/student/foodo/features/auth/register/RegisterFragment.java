package iti.student.foodo.features.auth.register;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static iti.student.foodo.features.utils.BlurUtils.*;
import static iti.student.foodo.features.utils.CustomToastKt.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.student.foodo.data.remote.firebase.FirebaseAuthDataSource;
import iti.student.foodo.databinding.FragmentRegisterBinding;
import iti.student.foodo.features.auth.data.AuthRepositoryImpl;
import iti.student.foodo.features.utils.BlurUtils;
import iti.student.foodo.features.utils.CustomDialog;
import iti.student.foodo.features.utils.ValidationUtils;

public class RegisterFragment extends Fragment implements RegisterContract.View {
    FragmentRegisterBinding binding;
    private RegisterContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterPresenterImpl(new AuthRepositoryImpl(new FirebaseAuthDataSource()));
        presenter.attachView(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.emailEt.getText().toString().trim();
            String password = binding.passwordEt.getText().toString().trim();
            if (!ValidationUtils.isValidEmail(email)) {
                binding.emailEt.setError("Invalid Email");
                showError("Email must be valid (example@gmail.com)");
                return;
            }
            if (!ValidationUtils.isValidPassword(password)) {
                binding.passwordEt.setError("Invalid Password");
                showError("Password must be at least 6 characters");
                return;
            }
            presenter.register(email, password);
        });
        binding.loginBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onRegisterSuccess() {
        successToast(getContext(), "Register Success");
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }

    @Override
    public void onRegisterFailure(String message) {
        errorToast(getContext(), message);
    }

    @Override
    public void showLoading() {
        binding.loading.setVisibility(VISIBLE);
        blurView(binding.blurView, 30);
    }

    @Override
    public void hideLoading() {
        binding.loading.setVisibility(GONE);
        showBlur(binding.blurView);
    }

    @Override
    public void showError(String message) {
        CustomDialog.showDialog("Error", message, getChildFragmentManager(), new CustomDialog.OnDialogClosedListener() {
            @Override
            public void onOpen() {
                BlurUtils.blurView(binding.blurView, 30);
            }

            @Override
            public void onClosed() {
                BlurUtils.showBlur(binding.blurView);
            }
        });
    }
}