package iti.student.foodo.features.auth.login.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static iti.student.foodo.features.utils.BlurUtils.blurView;
import static iti.student.foodo.features.utils.BlurUtils.showBlur;
import static iti.student.foodo.features.utils.CustomToastKt.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import iti.student.foodo.R;
import iti.student.foodo.core.network.ApiClient;
import iti.student.foodo.data.datasource.local.MealLocalDataSource;
import iti.student.foodo.data.datasource.local.PrefManager;
import iti.student.foodo.data.datasource.remote.MealsRemoteDataSource;
import iti.student.foodo.data.db.AppDatabase;
import iti.student.foodo.data.network.firebase.AuthService;
import iti.student.foodo.data.network.firebase.FirestoreService;
import iti.student.foodo.data.network.retrofit.ApiService;
import iti.student.foodo.data.repository.favorite.FavoriteRepositoryImpl;
import iti.student.foodo.data.repository.meal.MealRepositoryImpl;
import iti.student.foodo.data.repository.planner.PlannerRepositoryImpl;
import iti.student.foodo.databinding.FragmentLoginBinding;
import iti.student.foodo.data.repository.auth.AuthRepositoryImpl;
import iti.student.foodo.features.auth.login.presenter.LoginContract;
import iti.student.foodo.features.auth.login.presenter.LoginPresenterImpl;
import iti.student.foodo.features.utils.BlurUtils;
import iti.student.foodo.features.utils.CustomDialog;
import iti.student.foodo.features.utils.ValidationUtils;

import android.os.CancellationSignal;

import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;

import java.util.concurrent.Executors;


public class LoginFragment extends Fragment implements LoginContract.View {
    FragmentLoginBinding binding;
    LoginContract.Presenter presenter;
    private FragmentManager fragmentManager;
    private CustomDialog.OnDialogListener dialogListener;
    private PrefManager prefManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(requireContext());
        presenter = new LoginPresenterImpl(
                new AuthRepositoryImpl(
                        new AuthService()),
                prefManager,
                new FavoriteRepositoryImpl(
                        new MealLocalDataSource(
                                AppDatabase.getInstance(requireContext())),
                        new FirestoreService()),
                new PlannerRepositoryImpl(
                        new MealLocalDataSource(
                                AppDatabase.getInstance(requireContext()))
                        ,new FirestoreService()),
                new MealRepositoryImpl(
                        new MealsRemoteDataSource(
                                ApiClient.getInstance().
                                        create(ApiService.class)),
                        new MealLocalDataSource(
                                AppDatabase.getInstance(requireContext()))));
        presenter.attachView(this);
        fragmentManager = getParentFragmentManager();
        dialogListener = new CustomDialog.OnDialogListener() {
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

        binding.googleBtn.setOnClickListener(v -> {
            presenter.onGoogleSignInClicked();
        });

        binding.guestBtn.setOnClickListener(v -> {
            presenter.loginAsGuest();
        });

        binding.facebookBtn.setOnClickListener(v -> {
            warningToast(requireContext(), "Coming Soon");
        });

        binding.githubBtn.setOnClickListener(v -> {
            warningToast(requireContext(), "Coming Soon");
        });
    }


    @Override
    public void onLoginSuccess() {
        successToast(requireContext(), "Login Success");
        Navigation.findNavController(binding.getRoot()).navigate(R.id.navigateToMainFromLogin);
    }

    @Override
    public void onLoginFailure(String message) {
        errorToast(requireContext(), "Invalid Email or Password");
    }

    @Override
    public void launchGoogleSignIn() {
        showLoading();
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(getString(R.string.default_web_client_id))
                .setAutoSelectEnabled(true)
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        CredentialManager credentialManager =
                CredentialManager.create(requireContext());

        credentialManager.getCredentialAsync(
                requireActivity(),
                request,
                new CancellationSignal(),
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {

                    @Override
                    public void onResult(GetCredentialResponse result) {
                        requireActivity().runOnUiThread(
                                () -> {
                                    presenter.loginWithGoogle(result);
                                }
                        );
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                    }
                }
        );

        new Handler(Looper.getMainLooper()).postDelayed(
                this::hideLoading,
                950
        );
    }

    @Override
    public void disableButtons() {
        binding.githubBtn.setEnabled(false);
        binding.googleBtn.setEnabled(false);
        binding.facebookBtn.setEnabled(false);
        binding.guestBtn.setEnabled(false);
        binding.loginBtn.setEnabled(false);
        binding.signupBtn.setEnabled(false);
    }

    @Override
    public void enableButtons() {
        binding.githubBtn.setEnabled(true);
        binding.googleBtn.setEnabled(true);
        binding.facebookBtn.setEnabled(true);
        binding.guestBtn.setEnabled(true);
        binding.loginBtn.setEnabled(true);
        binding.signupBtn.setEnabled(true);
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
        CustomDialog.show(fragmentManager, "Error", message, dialogListener);
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
}