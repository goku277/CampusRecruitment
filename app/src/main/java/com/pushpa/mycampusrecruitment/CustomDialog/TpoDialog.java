package com.pushpa.mycampusrecruitment.CustomDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.pushpa.mycampusrecruitment.R;


public class TpoDialog extends AppCompatDialogFragment {

    EditText u_name, u_email, u_address, u_contact_no;

    tpolistener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder profileDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.tpo_input_layout, null);

        profileDialog.setView(view)
                .setTitle("Set Tpo details")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Name = u_name.getText().toString().trim();
                        String Address = u_address.getText().toString().trim();
                        String Contact = u_contact_no.getText().toString().trim();

                        listener.tpofields(Name, Address, Contact);
                    }
                });

        u_name = (EditText) view.findViewById(R.id.university_name_id);
        u_address= (EditText) view.findViewById(R.id.university_address_id);
        u_contact_no= (EditText) view.findViewById(R.id.university_contact_number_id);

        return profileDialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (tpolistener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implement this Doctor_profile_Listener");
        }
        ;
    }

    public interface tpolistener {
        public void tpofields(String name1, String address1, String contact1);
    }
}