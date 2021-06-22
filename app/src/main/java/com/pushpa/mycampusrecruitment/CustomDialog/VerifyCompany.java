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
import com.pushpa.mycampusrecruitment.Model.AddCompanyDetails;
import com.pushpa.mycampusrecruitment.Processor.ViewCompanyData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerifyCompany extends AppCompatDialogFragment {

    TextView location, totalemployees, yearsofestablishment, acheivements;

    CircleImageView cig;

    Spinner spin;

    String selectedUniversity="", key="";

    companyadminlistener1 listener;

    Set<String> keys= new LinkedHashSet<>();

    ArrayList<String> receiveCompanyDetails= new ArrayList<>();

    ViewCompanyData viewCompanyData;

    ArrayAdapter<String> adp;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        viewCompanyData= new ViewCompanyData();

        AlertDialog.Builder profileDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.verify_company_list_layout_item, null);
        profileDialog.setView(view)
                .setTitle("Verify or Delete Company Details")
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
                            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("AddCompanyDetails").child(key.trim());
                            ref.removeValue();
                            Toast.makeText(getActivity(), "Company Removed Successfully!", Toast.LENGTH_SHORT).show();
                        }

                        listener.companyadminfields1("", "", "");
                    }
                });
        totalemployees= view.findViewById(R.id.total_id);
        yearsofestablishment= view.findViewById(R.id.years_id);
        location= view.findViewById(R.id.location_id);
        spin= view.findViewById(R.id.name_id);
        cig= (CircleImageView) view.findViewById(R.id.cig_id);
        acheivements= (TextView) view.findViewById(R.id.achievements_id);

        verifyCompany();

        return profileDialog.create();
    }



    private void fillData(String choose, Map<String, Set<String>> receiveViewUniversityData) {
        System.out.println("From fillData receiveViewUniversityData: " + receiveViewUniversityData);
        Set<String> UniversityDetails= receiveViewUniversityData.get(choose);
        System.out.println("From fillData() UniversityDetails: " + UniversityDetails);

        ArrayList<String> list1= new ArrayList<>();

        for (String s: UniversityDetails) {
            list1.add(s);
        }

      /*  list1.addAll(UniversityDetails);  */

        totalemployees.setText("UniversityId:\t\t\t" + list1.get(1).replace("universityId:",""));
        location.setText("Location:\t\t\t" + list1.get(2).replace("location:",""));
        yearsofestablishment.setText("Year of Establishment:\t\t\t" + list1.get(0).replace("yos:",""));
        String loc= list1.get(2).replace("location:","").trim();
        location.setText("Location:\t\t\t" + loc);
        totalemployees.setText("Total:\t\t\t" + list1.get(1).replace("total:","").trim());
        acheivements.setText("Achievements:\t\t\t" + list1.get(3).replace("Acheivements:","").trim());

        key= list1.get(6).replace("key:","").trim();

        Glide.with(getActivity()).load(list1.get(4).replace("uriPath:","").trim()).into(cig);

    }

    private void verifyCompany() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("AddCompanyDetails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    for (String key : dataMap.keySet()) {
                        Object data = dataMap.get(key);
                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification
                            AddCompanyDetails addCompanyDetails = new AddCompanyDetails((String) userData.get("cName"), (String) userData.get("cYos"), (String) userData.get("cTotal"), (String) userData.get("cLocation"), (String) userData.get("cAwardsAchievement"), (String) userData.get("cImage"), (String) userData.get("referenceId"), (String) userData.get("key"));
                            receiveCompanyDetails.add(addCompanyDetails.getcName());
                            receiveCompanyDetails.add(addCompanyDetails.getcYos());
                            receiveCompanyDetails.add(addCompanyDetails.getcTotal());
                            receiveCompanyDetails.add(addCompanyDetails.getcLocation());
                            receiveCompanyDetails.add(addCompanyDetails.getcAwardsAchievement());
                            receiveCompanyDetails.add(addCompanyDetails.getcImage());
                            receiveCompanyDetails.add(addCompanyDetails.getReferenceId());
                            receiveCompanyDetails.add(addCompanyDetails.getKey());
                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("From Admin verifyCompany() receiveCompanyDetails: " + receiveCompanyDetails);

                    String val1 = "";

                    for (String s : receiveCompanyDetails) {
                        val1 += s + " ";
                    }

                    System.out.println("From Admin verifyCompany() val1 is: " + val1);

                    final Map<String, Set<String>> a1 = viewCompanyData.init(val1);

                    System.out.println("From Admin verifyCompany() a1 is: " + a1);

                    ArrayList<String> spinnerData = new ArrayList<>();

                    spinnerData.add(0, "Choose among the Companies");

                    System.out.println("From verifyCompany() a1 is: " + a1);

                    for (Map.Entry<String, Set<String>> e1 : a1.entrySet()) {
                        keys.add(e1.getKey());
                    }

                    spinnerData.addAll(keys);

                    System.out.println("From VerifyData onCreateDialog() spinnerData: " + spinnerData);

                    try {

                        adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinnerData);
                    } catch (Exception e) {}
                        // APP CURRENTLY CRASHING HERE
                        spin.setAdapter(adp);
                        //Set listener Called when the item is selected in spinner
                        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
                                String choose = parent.getItemAtPosition(position).toString();

                                if (!choose.contains("Choose among the Companies")) {
                                    Toast.makeText(parent.getContext(), choose, Toast.LENGTH_LONG).show();
                                    System.out.println("choose is: " + choose);
                                    fillData(choose, a1);
                                }
                            }

                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                            }
                        });
                   // } catch (Exception e) {}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (companyadminlistener1) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implement this Doctor_profile_Listener");
        }
    }

    public interface companyadminlistener1 {
        public void companyadminfields1(String name1, String address1, String contact1);
    }
}