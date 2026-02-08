package iti.student.foodo.core.utils;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

public class Animations {
    public static void rotateWithFadeIn(View view) {

        view.setScaleX(0f);
        view.setScaleY(0f);
        view.setRotation(0f);

        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        ObjectAnimator rotateForward = ObjectAnimator.ofFloat(view, "rotation", 0f, 380f);
        rotateForward.setDuration(900);
        rotateForward.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator rotateBack = ObjectAnimator.ofFloat(view, "rotation", 380f, 360f);
        rotateBack.setDuration(300);
        rotateBack.setInterpolator(new DecelerateInterpolator());

        AnimatorSet rotationSet = new AnimatorSet();
        rotationSet.playSequentially(rotateForward, rotateBack);
        rotationSet.start();
    }

    public static void fadeIn(View view) {
        view.setAlpha(0f);
        view.animate()
                .alpha(1f)
                .setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    public static void fadeOut(View view) {
        view.setAlpha(1f);
        view.animate()
                .alpha(0f)
                .setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    public static void fadeOutInUpdate(View view, long duration, Runnable updateAction) {
        view.animate()
                .alpha(0f)
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(() -> {
                    updateAction.run();

                    view.animate()
                            .alpha(1f)
                            .setDuration(duration)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .start();
                })
                .start();
    }

    public static void hintVertical(View view) {
        view.animate()
                .translationYBy(-100f)
                .setDuration(300)
                .withEndAction(() ->
                        view.animate()
                                .translationYBy(100f)
                                .setDuration(300)
                );
    }

    public static void hintHorizontal(View view, Runnable runnable) {
        view.animate()
                .translationXBy(-50f)
                .setDuration(600)
                .withEndAction(() ->
                        view.animate()
                                .translationXBy(50f)
                                .setDuration(600)
                                .withEndAction(runnable)
                );
    }

    public static void smoothScrollTo(ScrollView scrollView, int targetY) {
        ObjectAnimator animator = ObjectAnimator.ofInt(scrollView, "scrollY", targetY);

        animator.setDuration(1000);

        animator.setInterpolator(new DecelerateInterpolator());

        animator.start();
    }

    public static void shakeView(View view) {
        view.animate()
                .translationXBy(-10f)
                .setDuration(50)
                .withEndAction(() ->
                        view.animate()
                                .translationXBy(20f)
                                .setDuration(100)
                                .withEndAction(() ->
                                        view.animate()
                                                .translationXBy(-20f)
                                                .setDuration(100)
                                                .withEndAction(() ->
                                                        view.animate()
                                                                .translationXBy(10f)
                                                                .setDuration(50)
                                                )
                                )
                );
    }

    public static void addToFav(View view) {
        view.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(150)
                .withEndAction(() ->
                        view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(150)
                );
    }

}
