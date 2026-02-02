package iti.student.foodo.features.onboarding;

import static iti.student.foodo.core.utils.Animations.fadeIn;
import static iti.student.foodo.core.utils.Animations.fadeOut;
import static iti.student.foodo.core.utils.Animations.fadeOutInUpdate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iti.student.foodo.R;
import iti.student.foodo.databinding.FragmentOnboardingBinding;
import iti.student.foodo.core.utils.Constants;

public class OnboardingFragment extends Fragment {

    FragmentOnboardingBinding binding;
    OnBoardingAdapter adapter;
    int previousPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new OnBoardingAdapter(Constants.getItems());
        binding.viewPager.setAdapter(adapter);
        binding.dotsIndicator.attachTo(binding.viewPager);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handleButtonsUi(position);
                handleButtonClicks(position, view);
                previousPosition = position;
            }
        });
    }

    private void handleButtonClicks(int position, @NonNull View view) {
        binding.startBtn.setOnClickListener(v -> {
            if (position == Constants.getItems().size() - 1) {
                Navigation.findNavController(view).navigate(R.id.navigateToAuthFromOnboarding);
            } else {
                binding.viewPager.setCurrentItem(position + 1, true);
            }
        });

        binding.skipBtn.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.navigateToAuthFromOnboarding);
        });
    }

    private void handleButtonsUi(int position) {
        int lastPosition = Constants.getItems().size() - 1,
                beforeLastPosition = Constants.getItems().size() - 2;
        if (position == lastPosition) {
            fadeOutInUpdate(binding.startBtn, 600, () -> {
                binding.startBtn.setText(R.string.get_started);
            });
            fadeOut(binding.skipBtn);
        } else if (position == beforeLastPosition && previousPosition == lastPosition) {
            fadeIn(binding.skipBtn);
            fadeOutInUpdate(binding.startBtn, 600, () -> {
                binding.startBtn.setText(R.string.next);
            });
        } else {
            binding.startBtn.setText(R.string.next);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}