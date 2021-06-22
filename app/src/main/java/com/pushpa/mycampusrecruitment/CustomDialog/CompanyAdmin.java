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

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyAdmin extends AppCompatDialogFragment {

    CircleImageView cig;

    EditText u_name, u_email, u_address, u_contact_no;

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    public static final int IMAGE_PICK_CODE = 2;

    public static final int REQUEST_CODE_PERMISSION_RESULT = 5;

    companyadminlistener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder profileDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.company_admin_input_layout, null);
        profileDialog.setView(view)
                .setTitle("Set Company details")
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
                        listener.companyadminfields(Name, Address, Contact);
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
            listener = (companyadminlistener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implement this Doctor_profile_Listener");
        }
    }

    public interface companyadminlistener {
        public void companyadminfields(String name1, String address1, String contact1);
    }
}