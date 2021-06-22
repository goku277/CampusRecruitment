package com.pushpa.mycampusrecruitment.CustomDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pushpa.mycampusrecruitment.Model.AddUniversityDetailsData;
import com.pushpa.mycampusrecruitment.Processor.viewUniversitiesdata;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerifyUniversity extends AppCompatDialogFragment {

    companyadminlistener listener;

    TextView univ_id, branches, year, location;

    CircleImageView cig;

    Spinner spin;

    ArrayList<String> receiveUniversityDetails= new ArrayList<>();

    Map<String, Set<String>> receiveViewUniversityData= new LinkedHashMap<>();

    viewUniversitiesdata universitiesdata;

    String selectedUniversity="", key="";

    Set<String> keys= new LinkedHashSet<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder profileDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.verify_university_layout_xml, null);
        profileDialog.setView(view)
                .setTitle("Verify or Delete University Details")
                .setNegativeButton("Keep", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (!key.trim().equals("")) {
                            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Upload University Details").child(key.trim());
                            ref.removeValue();
                            Toast.makeText(getActivity(), "University Removed Successfully!", Toast.LENGTH_SHORT).show();
                        }

                        listener.companyadminfields("", "", "");
                    }
                });
        univ_id= view.findViewById(R.id.univ_id);
        branches= view.findViewById(R.id.branch_id);
        year= view.findViewById(R.id.year_id);
        location= view.findViewById(R.id.university_location_id);
        spin= view.findViewById(R.id.univ_name_id);
        cig= (CircleImageView) view.findViewById(R.id.cig_id);

        universitiesdata= new viewUniversitiesdata();

        viewUniversity();


     //   for (Map.Entry<String, Set<String>> e1: receiveJobUpdatedata.entrySet()) {
     //       spinnerData.add(e1.getKey());
     //   }

        return profileDialog.create();
    }

    private void viewUniversity() {
        //  System.out.println("From Companyadmin updateCompanyDetails() user is: " + user);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Upload University Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            AddUniversityDetailsData addCompanyDetails = new AddUniversityDetailsData((String) userData.get("universityname"), (String) userData.get("universitylocation"), (String) userData.get("universityid"), (String) userData.get("branchesavailable"), (String) userData.get("yearofestablishment"), (String) userData.get("imageuri"), (String) userData.get("key"));

                            receiveUniversityDetails.add(addCompanyDetails.getUniversityname());
                            receiveUniversityDetails.add(addCompanyDetails.getUniversitylocation());
                            receiveUniversityDetails.add(addCompanyDetails.getUniversityid());
                            receiveUniversityDetails.add(addCompanyDetails.getBranchesavailable());
                            receiveUniversityDetails.add(addCompanyDetails.getYearofestablishment());
                            receiveUniversityDetails.add(addCompanyDetails.getImageuri());
                            receiveUniversityDetails.add(addCompanyDetails.getKey());
                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }
                    System.out.println("From viewUniversity() receiveUniversityDetails: " + receiveUniversityDetails);
                    String val1= "";
                    for (String s: receiveUniversityDetails) {
                        val1+= s + " ";
                    }
                    System.out.println("From viewUniversity() val1 is: " + val1);
                    receiveViewUniversityData= universitiesdata.init(val1);
                    System.out.println("From viewUniversity() receiveViewUniversityData is: " + receiveViewUniversityData);


                    ArrayList<String> spinnerData= new ArrayList<>();

                    spinnerData.add(0, "Choose among the Universities");

                    System.out.println("From onCreateDialog() receiveViewUniversityData is: " + receiveViewUniversityData);

                    for (Map.Entry<String, Set<String>> e1: receiveViewUniversityData.entrySet()) {
                        keys.add(e1.getKey());
                    }

                    spinnerData.addAll(keys);

                    System.out.println("From VerifyData onCreateDialog() spinnerData: " + spinnerData);

                    ArrayAdapter<String> adp = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item,spinnerData);
                    // APP CURRENTLY CRASHING HERE
                    spin.setAdapter(adp);
                    //Set listener Called when the item is selected in spinner
                    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3)
                        {
                            String choose = parent.getItemAtPosition(position).toString();

                            if (!choose.contains("Choose among the Universities")) {
                                Toast.makeText(parent.getContext(), choose, Toast.LENGTH_LONG).show();

                                fillData(choose, receiveViewUniversityData);
                            }
                        }
                        public void onNothingSelected(AdapterView<?> arg0)
                        {
                            // TODO Auto-generated method stub
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fillData(String choose, Map<String, Set<String>> receiveViewUniversityData) {
        Set<String> UniversityDetails= receiveViewUniversityData.get(choose);
        System.out.println("From fillData() UniversityDetails: " + UniversityDetails);

        ArrayList<String> list1= new ArrayList<>();

        list1.addAll(UniversityDetails);

        univ_id.setText("UniversityId:\t\t\t" + list1.get(1).replace("universityId:",""));
        branches.setText("Branches:\t\t\t" + list1.get(2).replace("branches:",""));
        year.setText("Year:\t\t\t" + list1.get(3).replace("year:",""));
        String loc= list1.get(0).replace("location:","").trim();
        location.setText("Location:\t\t\t" + loc);

        key= list1.get(5).replace("key:","").trim();

        Glide.with(getActivity()).load(list1.get(4).replace("uriPath:","").trim()).into(cig);

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