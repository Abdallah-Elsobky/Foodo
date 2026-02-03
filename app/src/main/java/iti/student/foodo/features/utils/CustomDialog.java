package iti.student.foodo.features.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import iti.student.foodo.databinding.CustomDialogBinding;

public class CustomDialog extends DialogFragment {

    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_MESSAGE = "arg_message";


    public static void showDialog(String title, String message, FragmentManager manager, OnDialogClosedListener listener) {
        listener.onOpen();
        CustomDialog.newInstance(title, message, listener).show(manager, "CustomDialogTag");
    }

    CustomDialogBinding binding;
    static OnDialogClosedListener listener;

    public static CustomDialog newInstance(String title, String message, OnDialogClosedListener listener) {
        CustomDialog fragment = new CustomDialog();
        CustomDialog.listener = listener;
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = CustomDialogBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            String title = getArguments().getString(ARG_TITLE);
            String message = getArguments().getString(ARG_MESSAGE);
            binding.titleTv.setText(title);
            binding.messageTv.setText(message);
        }

        binding.cancelBtn.setOnClickListener(v -> dismiss());

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            getDialog().getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener != null) {
            listener.onClosed();
        }
    }

    public interface OnDialogClosedListener {
        void onOpen();

        void onClosed();
    }
}