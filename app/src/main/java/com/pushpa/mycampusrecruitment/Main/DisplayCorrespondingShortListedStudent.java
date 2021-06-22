package com.pushpa.mycampusrecruitment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pushpa.mycampusrecruitment.Adapter.DisplayShortListedStudentAdapter;
import com.pushpa.mycampusrecruitment.Model.DisplaySpecificShortListedStudentData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;

public class DisplayCorrespondingShortListedStudent extends AppCompatActivity {

    ArrayList<DisplaySpecificShortListedStudentData> displaySpecificShortListedStudentDataArrayList= new ArrayList<>();

    DisplayShortListedStudentAdapter displayShortListedStudentAdapter;

    RecyclerView recyclerView1, recyclerView11;

    String studentDetails="", companySelected="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_corresponding_short_listed_student);

        recyclerView1= (RecyclerView) findViewById(R.id.recycler_view_id);

        Intent getData= getIntent();

        studentDetails= getData.getStringExtra("studentdetails");
        companySelected= getData.getStringExtra("companyselected").replace("|","").trim();

        System.out.println("From DisplayCorrespondingShortListedStudent studentDetails: " + studentDetails);
        System.out.println("From DisplayCorrespondingShortListedStudent companySelected: " + companySelected);

        String reg="", ten="", grad="", currentacademic="", keyskills="", twelve="";

        if (studentDetails.contains("studentregistrationnumber:")) {
            reg= studentDetails.substring(studentDetails.indexOf("studentregistrationnumber:"), studentDetails.indexOf("ten:")).replace("studentregistrationnumber:","Registration number:\t").replace(",","").trim();
        }
        if (studentDetails.contains("ten:")) {
            ten= studentDetails.substring(studentDetails.indexOf("ten:"), studentDetails.indexOf("twelve:")).replace("ten:","10th %:\t").replace(",","").trim();
        }
        if (studentDetails.contains("twelve:")) {
            twelve= studentDetails.substring(studentDetails.indexOf("twelve:"), studentDetails.indexOf("graduation:")).replace("twelve:","12th %:\t").replace(",","").trim();
        }
        if (studentDetails.contains("graduation:")) {
            grad= studentDetails.substring(studentDetails.indexOf("graduation:"), studentDetails.indexOf("currentacademic:")).replace("graduation:","Graduation %:\t").replace(",","").trim();
        }
        if (studentDetails.contains("currentacademic:")) {
            currentacademic= studentDetails.substring(studentDetails.indexOf("currentacademic:"), studentDetails.indexOf("keyskills:")).replace("currentacademic:","Current Academic %:").replace(",","").trim();
        }
        if (studentDetails.contains("keyskills:")) {
            keyskills= studentDetails.substring(studentDetails.indexOf("keyskills:")).replace("keyskills:","keyskills:\t");
        }

        System.out.println("From DisplayCorrespondingShortListedStudent reg: " + reg + "\tten: " + ten + "\ttwelve: "+ twelve + "\tgrad: " + grad + "\tcurrentacademic: " + currentacademic + "\tkeyskills: " + keyskills);

        DisplaySpecificShortListedStudentData displaySpecificShortListedStudentData= new DisplaySpecificShortListedStudentData(reg, ten, grad, currentacademic, keyskills, companySelected, twelve);

        displaySpecificShortListedStudentDataArrayList.add(displaySpecificShortListedStudentData);

        LinearLayoutManager layoutManager= new LinearLayoutManager(DisplayCorrespondingShortListedStudent.this, LinearLayoutManager.VERTICAL, false);

        recyclerView1.setLayoutManager(layoutManager);

        recyclerView1.setItemAnimator(new DefaultItemAnimator());

        displayShortListedStudentAdapter= new DisplayShortListedStudentAdapter(DisplayCorrespondingShortListedStudent.this, displaySpecificShortListedStudentDataArrayList, companySelected);

       // displayCorrespondingJobAdapter= new DisplayCorrespondingJobAdapter(DisplayCorrespondingShortListedStudent.this, displayCorrespondingJobData1);

        // jobAdvertisementAdapter  = new JobAdvertisementAdapter(DisplayCorrespondingJob.this, jobAdvertisementdata, receiveData);

        recyclerView1.setAdapter(displayShortListedStudentAdapter);
    }
}