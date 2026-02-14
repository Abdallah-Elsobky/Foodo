package iti.student.foodo.features.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import iti.student.foodo.databinding.CustomDialogBinding;

public class ErrorDialog extends DialogFragment {

    private static final String TAG = "CustomDialogTag";
    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_MESSAGE = "arg_message";

    private CustomDialogBinding binding;
    private OnDialogListener listener;

    public static void show(
            @NonNull FragmentManager manager,
            @NonNull String title,
            @NonNull String message,
            @Nullable OnDialogListener listener
    ) {
        DialogFragment oldDialog =
                (DialogFragment) manager.findFragmentByTag(TAG);

        if (oldDialog != null) {
            oldDialog.dismissAllowingStateLoss();
        }

        ErrorDialog dialog = newInstance(title, message);
        dialog.setListener(listener);

        if (listener != null) listener.onOpen();

        dialog.show(manager, TAG);
    }


    private static ErrorDialog newInstance(String title, String message) {
        ErrorDialog fragment = new ErrorDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    private void setListener(OnDialogListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = CustomDialogBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            binding.titleTv.setText(getArguments().getString(ARG_TITLE));
            binding.messageTv.setText(getArguments().getString(ARG_MESSAGE));
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
            listener = null;
        }
    }


    public interface OnDialogListener {
        void onOpen();

        void onClosed();
    }
}
