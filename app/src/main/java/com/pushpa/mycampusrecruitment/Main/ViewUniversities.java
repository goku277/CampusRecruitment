package com.pushpa.mycampusrecruitment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.pushpa.mycampusrecruitment.Adapter.ViewUniversitiesAdapter;
import com.pushpa.mycampusrecruitment.Model.ViewUniversitiesModelData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ViewUniversities extends AppCompatActivity {

    Map<String, Set<String>> receiveViewUniversityData= new LinkedHashMap<>();

    ViewUniversitiesModelData viewUniversitiesModelData;

    ArrayList<ViewUniversitiesModelData> arrayListViewUNiversityModelData= new ArrayList<>();

    RecyclerView recyclerView1;

    ViewUniversitiesAdapter viewUniversitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_universities);

        recyclerView1= findViewById(R.id.recycler_view_id);

        Intent getData= getIntent();

        receiveViewUniversityData= (Map<String, Set<String>>) getData.getSerializableExtra("map1");

        System.out.println("From ViewUniversities receiveViewUniversityData: " + receiveViewUniversityData);

        for (Map.Entry<String, Set<String>> e1: receiveViewUniversityData.entrySet()) {
            String universityName= e1.getKey();
            Set<String> set1= e1.getValue();
            fillData(universityName, set1);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(ViewUniversities.this, LinearLayoutManager.VERTICAL, false);

        recyclerView1.setLayoutManager(layoutManager);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        viewUniversitiesAdapter = new ViewUniversitiesAdapter(arrayListViewUNiversityModelData, ViewUniversities.this);

        recyclerView1.setAdapter(viewUniversitiesAdapter);
    }

    private void fillData(String universityName, Set<String> set1) {
        String id="", branch="", location="", year="", uri="", key="", name = "";

        for (String s: set1) {
            if (s.contains("location:")) {
                location= s.replace("location:","").trim();
            }
            if (s.contains("universityId:")) {
                id= s.replace("universityId:","").trim();
            }
            if (s.contains("branches:")) {
                branch= s.replace("branches:","").trim();
            }
            if (s.contains("year:")) {
                year= s.replace("year:","").trim();
            }
            if (s.contains("uriPath:")) {
                uri= s.replace("uriPath:","").trim();
            }
            if (s.contains("key:")) {
                key= s.replace("key:","").trim();
            }
            name= universityName;
        }

        ViewUniversitiesModelData viewUniversitiesModelData= new ViewUniversitiesModelData(name, id, branch, location, year, uri, key);
        arrayListViewUNiversityModelData.add(viewUniversitiesModelData);
    }
}