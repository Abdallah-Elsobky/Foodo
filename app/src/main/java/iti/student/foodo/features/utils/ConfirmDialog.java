package iti.student.foodo.features.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import iti.student.foodo.R;

public class ConfirmDialog {

    public interface OnConfirmListener {
        void onConfirm();

        void onCancel();
    }

    private final Context context;
    private final String title;
    private final String message;
    private final OnConfirmListener listener;

    public ConfirmDialog(Context context, String title, String message, OnConfirmListener listener) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.listener = listener;
    }

    public void show() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_confirm, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();

        TextView tvTitle = view.findViewById(R.id.titleTv);
        TextView tvMessage = view.findViewById(R.id.messageTv);
        Button btnConfirm = view.findViewById(R.id.confirmBtn);
        Button btnCancel = view.findViewById(R.id.cancelBtn);

        tvTitle.setText(title);
        tvMessage.setText(message);

        btnConfirm.setOnClickListener(v -> {
            if (listener != null) listener.onConfirm();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            if (listener != null) listener.onCancel();
            dialog.dismiss();
        });

        dialog.setCancelable(false);
        dialog.show();
    }

}

