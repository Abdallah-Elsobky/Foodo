package iti.student.foodo.features.utils;

import android.content.Context;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

//import iti.student.foodo.R;
import www.sanju.motiontoast.R;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class BlurUtils {

    public static void blurView(View view, float degree) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffect blurEffect = RenderEffect.createBlurEffect(degree, degree, Shader.TileMode.CLAMP);
            view.setRenderEffect(blurEffect);
        }
    }

    public static void showBlur(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            view.setRenderEffect(null);
        }
    }
}