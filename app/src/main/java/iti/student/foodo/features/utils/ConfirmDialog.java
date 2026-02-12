package iti.student.foodo.features.utils;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class ConfirmDialog {

    public interface OnConfirmListener {
        void onConfirm();
        void onCancel();
    }

    private Context context;
    private String title;
    private String message;
    private OnConfirmListener listener;

    public ConfirmDialog(Context context, String title, String message, OnConfirmListener listener) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.listener = listener;
    }

    public void show() {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (listener != null) listener.onConfirm();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    if (listener != null) listener.onCancel();
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
    }
}

