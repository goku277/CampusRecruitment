package com.pushpa.mycampusrecruitment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pushpa.mycampusrecruitment.Adapter.DisplaySpecificShortListedStudentAdapter;
import com.pushpa.mycampusrecruitment.Model.DisplaySpecificShortListedStudentFromCompanyData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DisplaySpecificShortListedStudent extends AppCompatActivity {

    String str="";

    ArrayList<String> segmentList;

    ArrayList<DisplaySpecificShortListedStudentFromCompanyData> displaySpecificShortListedStudentFromCompanyData= new ArrayList<>();

    String reg1= "";

    ArrayList<String> key= new ArrayList<>();

    Map<String, Set<String>> map1= new LinkedHashMap<>();

    RecyclerView recyclerView1;

    DisplaySpecificShortListedStudentAdapter display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_specific_short_listed_student);

        recyclerView1= (RecyclerView) findViewById(R.id.recycler_view_id);

        Intent getData= getIntent();
        str= getData.getStringExtra("str");
        key= getData.getStringArrayListExtra("key");
        reg1= getData.getStringExtra("reg");

        map1= (Map<String, Set<String>>) getData.getSerializableExtra("map1");

        System.out.println("From DisplaySpecificShortListedStudent map1 is: " + map1);

        System.out.println("From DisplaySpecificShortListedStudent key is: " + key);

        System.out.println("From DisplaySpecificShortListedStudent reg1 is: " + reg1);

    //    System.out.println("From DisplaySpecificShortListedStudent atr: " + str);

        String s1[]= str.split("\\]");

        for (Map.Entry<String, Set<String>> e1: map1.entrySet()) {
            String s= e1.getValue() + "";
            System.out.println("From DisplaySpecificShortListedStudent e1.getValue() is: " + s);
            if (!s.trim().isEmpty()) {
                if (s.contains("[")) {
                    s= s.replace("[","");
                }
                if (s.contains("]")) {
                    s= s.replace("]","");
                }
                //   System.out.println("s1: " + s);
                placeData(s, e1.getKey());
            }
        }

      /*  for (String s: s1) {
            if (!s.trim().isEmpty()) {
                if (s.contains("[")) {
                    s= s.replace("[","");
                }
                if (s.contains("]")) {
                    s= s.replace("]","");
                }
             //   System.out.println("s1: " + s);
                placeData(s);
            }
            System.out.println("\n\n");
        }    */
    }

    private void placeData(String s, String s11) {
        segmentList= new ArrayList<>();
        String s1[]= s.split(",");

        for (String s55: s1) {
            System.out.println("From placeData() s1: " + s55);
        }

        for (String s5: s1) {
            if (!s5.trim().isEmpty()) {
                System.out.println("From placeData s1: " + s5);
                if (s5.contains("ten:")) {
                    s5= s5.replace("ten:","10th%: ").trim();
                    segmentList.add(s5);
                }
                if (s5.contains("twelve:")) {
                    s5= s5.replace("twelve:","12th%: ").trim();
                    segmentList.add(s5);
                }
                if (s5.contains("graduation:")) {
                    s5= s5.replace("graduation:","Graduation%: ").trim();
                    segmentList.add(s5);
                }
                if (s5.contains("currentacademic:")) {
                    s5= s5.replace("currentacademic:","CurrentAcademic%: ").trim();
                    segmentList.add(s5);
                }
                if (s5.contains("uriPath:")) {
                    s5= s5.replace("uriPath:","").trim();
                    segmentList.add(s5);
                }
                if (s5.contains("keyskills:")) {
                    s5= s5.replace("keyskills:","Keyskills: ").trim();
                    segmentList.add(s5);
                }
            }
        }

      String reg="", universityName="";

        String s55[]= s11.split("\\s+");

        reg= s55[s55.length-1];

        for (int i=0;i<s55.length-1;i++) {
            universityName+= s55[i] + " ";
        }

      System.out.println("From placeData reg is: " + reg);

     // for (int i=0,i1=0;(i<reg.size() && i1< universityname.size()); i++, i1++) {

          DisplaySpecificShortListedStudentFromCompanyData disp = new DisplaySpecificShortListedStudentFromCompanyData(segmentList.get(0),
                  segmentList.get(1),
                  segmentList.get(2),
                  segmentList.get(3),
                  reg,
                  universityName,
                  segmentList.get(4),
                  segmentList.get(5));

          displaySpecificShortListedStudentFromCompanyData.add(disp);

          LinearLayoutManager layoutManager = new LinearLayoutManager(DisplaySpecificShortListedStudent.this, LinearLayoutManager.VERTICAL, false);

          recyclerView1.setLayoutManager(layoutManager);

          recyclerView1.setItemAnimator(new DefaultItemAnimator());

          //  jobAdvertisementAdapter  = new JobAdvertisementAdapter(Tpo.this, jobAdvertisementdata, receiveData);

          display = new DisplaySpecificShortListedStudentAdapter(displaySpecificShortListedStudentFromCompanyData, DisplaySpecificShortListedStudent.this);

          //     shortlistedCandidatesAdapter= new ShortlistedCandidatesAdapter(DisplaySpecificShortListedStudent.this, shortlistedStudentsDataArrayList, mapImageUrlToCompany, mapImageUrlToStudentDetails);

          recyclerView1.setAdapter(display);
      }
 //   }
}