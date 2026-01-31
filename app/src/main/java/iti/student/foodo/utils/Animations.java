package iti.student.foodo.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

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
        // Fade out
        view.animate()
                .alpha(0f)
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(() -> {
                    // Update the view while hidden
                    updateAction.run();

                    // Fade back in
                    view.animate()
                            .alpha(1f)
                            .setDuration(duration)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .start();
                })
                .start();
    }


}
