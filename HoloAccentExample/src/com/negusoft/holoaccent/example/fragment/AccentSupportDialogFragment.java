package com.negusoft.holoaccent.example.fragment;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;

import com.negusoft.holoaccent.dialog.DividerPainter;

/**
 * A copy of AccentDialogFragment that extends the suport DialogFragment in stead of the native one.
 * <br/><br/>
 * You can copy this file into your project and make your DialogFragments extend it if you
 * are using the support library.
 */
public class AccentSupportDialogFragment extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();

        // Set the correct accent color to the divider
        Dialog d = getDialog();
        if (d != null)
            new DividerPainter(getActivity()).paint(d.getWindow());
    }

}
