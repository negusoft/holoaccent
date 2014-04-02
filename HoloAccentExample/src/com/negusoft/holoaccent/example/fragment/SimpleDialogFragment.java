package com.negusoft.holoaccent.example.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.negusoft.holoaccent.dialog.AccentDialogFragment;
import com.negusoft.holoaccent.example.R;

public class SimpleDialogFragment extends AccentDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_fragment_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_button_positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // positive action
                            }
                        }
                )
                .setNegativeButton(R.string.dialog_button_negative,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // negative action
                            }
                        }
                );
        return builder.create();
    }

}
