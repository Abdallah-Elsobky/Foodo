package iti.student.foodo.core.utils;

import static kotlin.text.Typography.degree;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import iti.student.foodo.R;
import iti.student.foodo.features.onboarding.BoardItem;

public class Constants {
    public static List<BoardItem> getItems() {
        List<BoardItem> items = new ArrayList<>();
        items.add(new BoardItem(R.drawable.board1, "Discover Flavors", "Explore thousands of recipes from around the world."));
        items.add(new BoardItem(R.drawable.board2, "Plan Your Week", "Plan your weekly meals and never stress about dinner."));
        items.add(new BoardItem(R.drawable.board3, "Cook Offline", "Save favorites and ingredients to your cart, accessible anywhere."));
        return items;
    }
}
