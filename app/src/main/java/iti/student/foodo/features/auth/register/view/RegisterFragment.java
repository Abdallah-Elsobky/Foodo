package iti.student.foodo.features.auth.register.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static iti.student.foodo.features.utils.BlurUtils.*;
import static iti.student.foodo.features.utils.CustomToastKt.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.student.foodo.data.network.firebase.AuthService;
import iti.student.foodo.databinding.FragmentRegisterBinding;
import iti.student.foodo.data.repository.auth.AuthRepositoryImpl;
import iti.student.foodo.features.auth.register.presenter.RegisterContract;
import iti.student.foodo.features.auth.register.presenter.RegisterPresenterImpl;
import iti.student.foodo.features.utils.ErrorDialog;
import iti.student.foodo.features.utils.ValidationUtils;

public class RegisterFragment extends Fragment implements RegisterContract.View {
    FragmentRegisterBinding binding;
    private RegisterContract.Presenter presenter;
    private FragmentManager fragmentManager;
    private ErrorDialog.OnDialogListener dialogListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegisterPresenterImpl(new AuthRepositoryImpl(new AuthService()));
        presenter.attachView(this);
        fragmentManager = getParentFragmentManager();
        dialogListener = new ErrorDialog.OnDialogListener() {
            @Override
            public void onOpen() {
                blurView(binding.blurView, 30);
            }

            @Override
            public void onClosed() {
                showBlur(binding.blurView);
            }
        };
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
        successToast(getContext(), "Register Success\nVerify your email");
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
        ErrorDialog.show(fragmentManager, "Error", message, dialogListener);
    }
}