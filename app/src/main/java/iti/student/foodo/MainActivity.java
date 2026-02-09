package iti.student.foodo;

import static iti.student.foodo.core.utils.Animations.hide;
import static iti.student.foodo.core.utils.Animations.show;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.HashSet;
import java.util.Set;

import iti.student.foodo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        NavController navController = setupNavigation();
        handleBottomNavBar(navController);
    }

    @NonNull
    private NavController setupNavigation() {

        NavController navController =
                Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (navController.getCurrentDestination() != null &&
                    item.getItemId() == navController.getCurrentDestination().getId()) {
                return false;
            }
            NavOptions options = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .setPopUpTo(R.id.homeFragment, false, true)
                    .build();

            try {
                navController.navigate(item.getItemId(), null, options);
                return true;
            } catch (Exception e) {
                return NavigationUI.onNavDestinationSelected(item, navController);
            }
        });

        return navController;
    }

    private void handleBottomNavBar(NavController navController) {
        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.homeFragment);
        topLevelDestinations.add(R.id.searchFragment);
        topLevelDestinations.add(R.id.plannerFragment);
        topLevelDestinations.add(R.id.favoritesFragment);
        topLevelDestinations.add(R.id.cartFragment);

        navController.addOnDestinationChangedListener((controller, destination, args) -> {
            if (topLevelDestinations.contains(destination.getId()))
                show(binding.bottomNavigationView);
            else
                hide(binding.bottomNavigationView);
        });
    }
}