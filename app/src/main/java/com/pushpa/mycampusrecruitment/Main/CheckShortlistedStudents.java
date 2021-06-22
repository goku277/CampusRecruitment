package com.pushpa.mycampusrecruitment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.pushpa.mycampusrecruitment.Adapter.ShortlistedCandidatesAdapter;
import com.pushpa.mycampusrecruitment.Model.ShortlistedStudentsData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CheckShortlistedStudents extends AppCompatActivity {

    Map<String, String> mapImageUrlToStudentDetails= new LinkedHashMap<>();
    Map<String, String> mapImageUrlToCompany= new LinkedHashMap<>();
    ArrayList<String> fetchImageUrl= new ArrayList<>();
    private ArrayList<ShortlistedStudentsData> shortlistedStudentsDataArrayList;

    ShortlistedCandidatesAdapter shortlistedCandidatesAdapter;

    RecyclerView recyclerView11;

    String user="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_shortlisted_students);

        recyclerView11= (RecyclerView) findViewById(R.id.recycler_view_id);
        Intent receiveData= getIntent();
        fetchImageUrl= receiveData.getStringArrayListExtra("getimageurllist");
        mapImageUrlToCompany= (Map<String, String>) receiveData.getSerializableExtra("mapimageurltocompany");
        mapImageUrlToStudentDetails= (Map<String, String>) receiveData.getSerializableExtra("mapimageurltostudentdetails");
        user= receiveData.getStringExtra("users");


        System.out.println("From CheckShortListedStudents fetchImageUrl is: " + fetchImageUrl);
        System.out.println("From CheckShortListedStudents mapImageUrlToCompany is: " + mapImageUrlToCompany);
        System.out.println("From CheckShortListedStudents mapImageUrlToStudentDetails is: " + mapImageUrlToStudentDetails);
        System.out.println("From CheckShortListedStudents user is: " + user);

        shortlistedStudentsDataArrayList = new ArrayList<>();

        for (int i = 0; i < fetchImageUrl.size(); i++) {
            ShortlistedStudentsData shortlistedStudentsData = new ShortlistedStudentsData(fetchImageUrl.get(i));
            shortlistedStudentsDataArrayList.add(shortlistedStudentsData);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(CheckShortlistedStudents.this, LinearLayoutManager.VERTICAL, false);

            recyclerView11.setLayoutManager(layoutManager);

            recyclerView11.setItemAnimator(new DefaultItemAnimator());

            shortlistedCandidatesAdapter = new ShortlistedCandidatesAdapter(CheckShortlistedStudents.this, shortlistedStudentsDataArrayList, mapImageUrlToCompany, mapImageUrlToStudentDetails, user);

            recyclerView11.setAdapter(shortlistedCandidatesAdapter);
    }
}