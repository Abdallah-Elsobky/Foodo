package iti.student.foodo.features.auth.login;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static iti.student.foodo.features.utils.CustomToastKt.errorToast;
import static iti.student.foodo.features.utils.CustomToastKt.successToast;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import iti.student.foodo.R;
import iti.student.foodo.data.remote.firebase.FirebaseAuthDataSource;
import iti.student.foodo.databinding.FragmentLoginBinding;
import iti.student.foodo.features.auth.data.AuthRepositoryImpl;
import iti.student.foodo.features.utils.BlurUtils;
import iti.student.foodo.features.utils.CustomDialog;
import iti.student.foodo.features.utils.ValidationUtils;


public class LoginFragment extends Fragment implements LoginContract.View {
    FragmentLoginBinding binding;
    LoginContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenterImpl(new AuthRepositoryImpl(new FirebaseAuthDataSource()));
        presenter.attachView(this);
    }

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

        binding.loginBtn.setOnClickListener(v -> {
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
            presenter.login(email, password);
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
        presenter.detachView();
    }

    @Override
    public void onLoginSuccess() {
        successToast(getContext(), "Login Success");
        Navigation.findNavController(binding.getRoot()).navigate(R.id.navigateToMainFromLogin);
    }

    @Override
    public void onLoginFailure(String message) {
        errorToast(getContext(), "Invalid Email or Password");
    }

    @Override
    public void showLoading() {
        BlurUtils.blurView(binding.blurView, 30);
        binding.loading.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.loading.setVisibility(GONE);
        BlurUtils.showBlur(binding.blurView);
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