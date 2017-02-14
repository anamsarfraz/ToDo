package com.example.usarfraz.todo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;

public class ConfirmationFragment extends DialogFragment {

    public interface DeleteToDoDialogListener {
        void onConfirmDeleteDialog();
    }

    public ConfirmationFragment() {
        // Empty constructor required for DialogFragment
    }

    public static ConfirmationFragment newInstance(String title) {
        ConfirmationFragment frag = new ConfirmationFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Are you sure you want to delete the todo item?");
        alertDialogBuilder.setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                DeleteToDoDialogListener listener = (DeleteToDoDialogListener) getActivity();
                listener.onConfirmDeleteDialog();

                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return alertDialogBuilder.create();
    }
}