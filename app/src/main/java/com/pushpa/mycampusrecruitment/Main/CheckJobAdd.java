package com.pushpa.mycampusrecruitment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.pushpa.mycampusrecruitment.Adapter.JobAdvertisementAdapter;
import com.pushpa.mycampusrecruitment.Model.JobAdvertisementData;
import com.pushpa.mycampusrecruitment.Processor.receiveData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.Map;

public class CheckJobAdd extends AppCompatActivity {

    ArrayList<String> companyLogo= new ArrayList<>();
    ArrayList<String> companyName= new ArrayList<>();

    JobAdvertisementAdapter jobAdvertisementAdapter;

    ArrayList<String> receiveData1= new ArrayList<>();

    receiveData rv;

    ArrayList<JobAdvertisementData> jobAdvertisementData= new ArrayList<>();

    RecyclerView recyclerView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_job_add);

        recyclerView1= (RecyclerView) findViewById(R.id.recycler_view_id);

        rv= new receiveData();

        Intent getData= getIntent();
        companyLogo= getData.getStringArrayListExtra("companylogo");
        companyName= getData.getStringArrayListExtra("companyname");

        receiveData1= getData.getStringArrayListExtra("receivedata");

        for (int i=0,i1=0; (i< companyLogo.size() && i1< companyName.size()); i++, i1++) {
            JobAdvertisementData jobAdvertisementData1 = new JobAdvertisementData(companyLogo.get(i), companyName.get(i1));
            jobAdvertisementData.add(jobAdvertisementData1);
        }

        System.out.println("From CheckJobAdd companyLogo: " + companyLogo);
        System.out.println("From CheckJobAdd companyName: " + companyName);

        String val1= receiveData1 + "";

        System.out.println("From CheckJobAdd receiveData: " + val1);

        Map<String, ArrayList<ArrayList<String>>> map1= rv.init(val1);

        System.out.println("From CheckJobAd map1 is: " + map1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(CheckJobAdd.this, LinearLayoutManager.VERTICAL, false);

        recyclerView1.setLayoutManager(layoutManager);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        System.out.println("From CheckJobAdd jobAdvertisementData: " + jobAdvertisementData);

        ArrayList<String> requirementList= new ArrayList<>();

        for (Map.Entry<String, ArrayList<ArrayList<String>>> e1: map1.entrySet()) {
            ArrayList<ArrayList<String>> val= e1.getValue();
            for (ArrayList<String> s1: val) {
                for (String s11: s1) {
                    if (s11.contains("Requirements:")) {
                        int index= s11.indexOf("Requirements:");
                        requirementList.add(s11.substring(index));
                    }
                }
            }
        }

        System.out.println("requirementList: " + requirementList);

        jobAdvertisementAdapter = new JobAdvertisementAdapter(CheckJobAdd.this, jobAdvertisementData, requirementList, map1);

        recyclerView1.setAdapter(jobAdvertisementAdapter);
//
    }
}