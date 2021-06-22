package com.pushpa.mycampusrecruitment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.pushpa.mycampusrecruitment.Adapter.ShortlistedstudentsfromcompanyAdapter;
import com.pushpa.mycampusrecruitment.Model.Shortlistedstudentsfromcompanydata;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ShortListedStudentsFromCompany extends AppCompatActivity {

    Map<String, Set<String>> shortListedStudents= new LinkedHashMap<>();

    Set<String> collegeName= new LinkedHashSet<>();

    ArrayList<String> setToList= new ArrayList<>();

    ArrayList<Shortlistedstudentsfromcompanydata> shortlistedstudentsfromcompanydata;

    ShortlistedstudentsfromcompanyAdapter shortlistedstudentsfromcompanyAdapter;

    RecyclerView recyclerView1;

    String reg="";

    String finalKey="";

    ArrayList<String> universityKeys= new ArrayList<>();

    Set<String> fetchImageUrl= new LinkedHashSet<>();

    Map<String, String> mapKeyToImageUrl= new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_listed_students_from_company);

        shortlistedstudentsfromcompanydata= new ArrayList<>();

        recyclerView1= (RecyclerView) findViewById(R.id.recycler_view_id);

        Intent getData= getIntent();
        shortListedStudents= (Map<String, Set<String>>) getData.getSerializableExtra("mapshortlistedstudents");

        System.out.println("From ShortListedStudentsFromCompany shortListedStudents: " + shortListedStudents);

        System.out.println("From ShortListedStudentsFromCompany finalKey is: " + finalKey);

        for (Map.Entry<String, Set<String>> e1: shortListedStudents.entrySet()) {
            String str= e1.getKey();
            String s1[]= str.split("\\s+");
           // for (String s5: s1) {
              //  System.out.println("From ShortListedStudentsFromCompany s1: " + s5);
           // }
            reg= s1[s1.length-1];
            int limit= s1.length;
            String UniversityName= "";
            for (int i=0;i<limit-1;i++) {
                UniversityName+= s1[i] + " ";
            }
            UniversityName= UniversityName.trim();
            if (!collegeName.contains(UniversityName)) {
                collegeName.add(UniversityName);
            }

            setToList.addAll(collegeName);

            Set<String> set1= new LinkedHashSet<>();

            set1.addAll(setToList);

            setToList.clear();

            setToList.addAll(set1);

            System.out.println("From ShortListedStudentsFromCompany reg is: " + reg + "\t\tUniversityName: " + UniversityName);

            System.out.println("From ShortListedStudentsFromCompany setToList: " + setToList);

            for (String s: setToList) {
                for (Map.Entry<String, Set<String>> e11 : shortListedStudents.entrySet()) {
                    String key= e11.getKey();
                    if (key.contains(s)) {
                        Set<String> set11 = e11.getValue();
                        for (String s11 : set11) {
                            if (s11.contains("uriPath:")) {
                                mapKeyToImageUrl.put(s, s11.substring(s11.indexOf("uriPath:")).replace("uriPath:","").replace(",","").trim());
                            }
                        }
                    }
                }
            }
        }

        System.out.println("From ShortListedStudentsFromCompany mapKeyToImageUrl: " + mapKeyToImageUrl);

        for (int i=0;i<setToList.size();i++) {
            Shortlistedstudentsfromcompanydata shortlistedstudentsfromcompanydata1= new Shortlistedstudentsfromcompanydata(setToList.get(i));
            shortlistedstudentsfromcompanydata.add(shortlistedstudentsfromcompanydata1);
        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(ShortListedStudentsFromCompany.this, LinearLayoutManager.VERTICAL, false);

        recyclerView1.setLayoutManager(layoutManager);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        shortlistedstudentsfromcompanyAdapter  = new ShortlistedstudentsfromcompanyAdapter(mapKeyToImageUrl, shortlistedstudentsfromcompanydata, shortListedStudents, ShortListedStudentsFromCompany.this, reg);

        recyclerView1.setAdapter(shortlistedstudentsfromcompanyAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShortListedStudentsFromCompany.this, Companyadmin.class));
        finishAffinity();
    }
}