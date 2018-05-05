package com.example.galleryn.galleryn.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.galleryn.galleryn.R;

public class ErrorDialog extends DialogFragment {
    private String message;
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        message = bundle != null ? bundle.getString(ERROR_MESSAGE) : "";
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.error_fragment, container);
        setDialogProperties(view);
        return view;
    }

    private void setDialogProperties(View view) {
        Dialog mDialog = getDialog();
        if (mDialog != null)
            mDialog.setTitle(getResources().getString(R.string.error));

        ((TextView) view.findViewById(R.id.tvError)).setText(message);

        (view.findViewById(R.id.bOK)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}