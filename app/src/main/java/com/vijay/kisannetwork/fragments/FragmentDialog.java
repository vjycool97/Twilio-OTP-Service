package com.vijay.kisannetwork.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class FragmentDialog extends DialogFragment {
    private String message = "Error : Unknown";
    private String positiveButtonText = "Ok";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.message)
                .setPositiveButton(this.positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("Dialog", "Ok Button Pressed");
                    }
                });

        return builder.create();
    }

    public void initDialogue(String message, String positiveButtonText) {
        this.message = message;
        this.positiveButtonText = positiveButtonText;
    }


}
