package com.pushpa.mycampusrecruitment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pushpa.mycampusrecruitment.Adapter.DisplayCorrespondingJobAdapter;
import com.pushpa.mycampusrecruitment.Model.DisplayCorrespondingJobData;
import com.pushpa.mycampusrecruitment.Processor.NewDataParse;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;

public class DisplayCorrespondingJob extends AppCompatActivity {

    ArrayList<String> receiveData= new ArrayList<>();

    ArrayList<ArrayList<String>> a1= new ArrayList<>();

    DisplayCorrespondingJobAdapter displayCorrespondingJobAdapter;

    RecyclerView recyclerView1, recyclerView11;

    ArrayList<DisplayCorrespondingJobData> displayCorrespondingJobData1= new ArrayList<>();

    NewDataParse ndp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_corresponding_job);

        recyclerView1= (RecyclerView) findViewById(R.id.recycler_view_id);

        ndp= new NewDataParse();

        Intent getData= getIntent();

        receiveData= getData.getStringArrayListExtra("comb");

        System.out.println("From DisplayCorrespondingJob receiveData is: " + receiveData);

        String dataToBeSent= "";

        for (String s: receiveData) {
            dataToBeSent+= s+",";
            dataToBeSent+=" ";
        }

        a1= ndp.init(dataToBeSent);

        System.out.println("From DisplayCorrespondingJob a1 is: " + a1);

        for (int i=0;i<a1.size();i++) {
            DisplayCorrespondingJobData displayCorrespondingJobData = new DisplayCorrespondingJobData(a1.get(i).get(0), a1.get(i).get(1), a1.get(i).get(2),
                    a1.get(i).get(3), a1.get(i).get(4), a1.get(i).get(5), a1.get(i).get(6), a1.get(i).get(7));

            displayCorrespondingJobData1.add(displayCorrespondingJobData);
        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(DisplayCorrespondingJob.this, LinearLayoutManager.VERTICAL, false);

        recyclerView1.setLayoutManager(layoutManager);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        displayCorrespondingJobAdapter= new DisplayCorrespondingJobAdapter(DisplayCorrespondingJob.this, displayCorrespondingJobData1);

       // jobAdvertisementAdapter  = new JobAdvertisementAdapter(DisplayCorrespondingJob.this, jobAdvertisementdata, receiveData);

        recyclerView1.setAdapter(displayCorrespondingJobAdapter);
    }
}