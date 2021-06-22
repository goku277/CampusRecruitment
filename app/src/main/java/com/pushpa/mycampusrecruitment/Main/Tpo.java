package com.pushpa.mycampusrecruitment.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pushpa.mycampusrecruitment.Credentials.Signin;
import com.pushpa.mycampusrecruitment.Credentials.UserStatus;
import com.pushpa.mycampusrecruitment.Database.Passworddb;
import com.pushpa.mycampusrecruitment.Database.ReferenceIddb;
import com.pushpa.mycampusrecruitment.Database.Universitynamedb;
import com.pushpa.mycampusrecruitment.Database.Usernamedb;
import com.pushpa.mycampusrecruitment.Model.AddStudentDetailsData;
import com.pushpa.mycampusrecruitment.Model.AddUniversityDetailsData;
import com.pushpa.mycampusrecruitment.Model.JobAdvertisementData;
import com.pushpa.mycampusrecruitment.Model.PostJobAdvertisementData;
import com.pushpa.mycampusrecruitment.Model.ShortlistedStudentsData;
import com.pushpa.mycampusrecruitment.Model.UploadDetails;
import com.pushpa.mycampusrecruitment.Model.UploadShortListedStudentsDetailsData;
import com.pushpa.mycampusrecruitment.Processor.FetchStudents;
import com.pushpa.mycampusrecruitment.Processor.GetData1;
import com.pushpa.mycampusrecruitment.Processor.UpdateStudentsExtractData;
import com.pushpa.mycampusrecruitment.Processor.UpdateUniversityDetailsData;
import com.pushpa.mycampusrecruitment.Processor.receiveData;
import com.pushpa.mycampusrecruitment.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Tpo extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "Tpo";
    Passworddb pdb;
    ReferenceIddb rdb;
    UserStatus us;
    String password="";

    ImageView addstudentdetailsimg, update_student_details_img, update_shortlisted_student_details_img, update_university_details_img, adduniversitydetails_img, checkjobAdd;
    TextView addstudentdetailstext, update_student_details_text, update_shortlisted_student_details_text, update_university_details_text, adduniversitydetails_text, checkJobAdd_text;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    ArrayList<JobAdvertisementData> jobAdvertisementdata;

    RecyclerView recyclerView1, recyclerView11;

    Map<String, ArrayList<ArrayList<String>>> a1= new LinkedHashMap<>();

    //GetData gd;

     GetData1 gd1;

    Usernamedb usernamedb;

    UploadDetails udp;

    ArrayList<String> getData= new ArrayList<>();

    ArrayList<String> getStudentDetailsData= new ArrayList<>();

    ArrayList<String> getShortListedStudentData= new ArrayList<>();

    ArrayList<String> getUniversityData= new ArrayList<>();

    Set<String> set1= new LinkedHashSet<>();

    Map<String, ArrayList<String>> getImage= new LinkedHashMap<>();

    ArrayList<Map<String, Set<ArrayList<String>>>> receiveData= new ArrayList<>();

    Universitynamedb universitynamedb;

  //  GetData1 gd1;

    //FetchStudents fetchStudents;

    Set<ArrayList<String>> aSet= new LinkedHashSet<>();

    String user="", regNo="";

    String grad="", ten="", twelve="", currentacademic="";

    String CurrentAcademic="", Graduation="", Ten= "";

    String UniversityName="";

    UpdateUniversityDetailsData upd11;

    Map<String, ArrayList<String>> selectShortListedStudents= new LinkedHashMap<>();
    Map<ArrayList<String>, String> getShortListedStudents= new LinkedHashMap<>();
    Map<ArrayList<String>, String> mapStudentToCompany= new LinkedHashMap<>();
    Map<String, String> mapImageUrlToStudentDetails= new LinkedHashMap<>();
    Map<String, String> mapImageUrlToCompany= new LinkedHashMap<>();

    boolean isEligible= false;

    String companyTag= "";

    ArrayList<String> companyTagList= new ArrayList<>();
    ArrayList<String> fetchImageUrl= new ArrayList<>();

    UpdateStudentsExtractData updateStudentsExtractData;

    ArrayList<Map<String, Set<String>>> a11;

    Map<String, Set<String>> mapRegToDetsils;
    Map<String, Set<String>> mapRegToKey;

    ImageView view_update_img;
    TextView view_update_text;

    boolean shortlisted= false, checkjob= false, updateshortlisted= false;

    Set<String> regNo1= new LinkedHashSet<>();

    com.pushpa.mycampusrecruitment.Processor.receiveData receiveData1;

    ImageView menu_logo;

    FetchStudents fetchStudents;

    UploadShortListedStudentsDetailsData uploadShortListedStudentsDetailsData;

    ArrayList<ShortlistedStudentsData> shortlistedStudentsDataArrayList;

    ArrayList<String> setToList= new ArrayList<>();

    ArrayList<String> fetchJobRequirements= new ArrayList<>();

    String fetchJobRequirements1="";

    boolean updateUniversityDetails= false, updateStudentDetails= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpo);

        uploadShortListedStudentsDetailsData= new UploadShortListedStudentsDetailsData();

        udp= new UploadDetails();

        fetchStudents= new FetchStudents();

      //  menu_logo= (ImageView) findViewById(R.id.menu_id);

        gd1= new GetData1();

        receiveData1= new receiveData();

        usernamedb= new Usernamedb(Tpo.this);

        view_update_img= (ImageView) findViewById(R.id.view_or_update_shortlisted_students_img_id);
        view_update_text= (TextView) findViewById(R.id.view_or_update_shortlisted_students_text_id);

        view_update_img.setOnClickListener(this);
        view_update_text.setOnClickListener(this);

        upd11= new UpdateUniversityDetailsData();

        checkjobAdd= (ImageView) findViewById(R.id.update_shortlisted_student_img_id);

        checkJobAdd_text= (TextView) findViewById(R.id.update_shortlisted_student_text_id);

     //   fetchStudents= new FetchStudents();

        universitynamedb= new Universitynamedb(Tpo.this);

        updateStudentsExtractData= new UpdateStudentsExtractData();

        mapRegToDetsils= new LinkedHashMap<>();

        mapRegToKey= new LinkedHashMap<>();

       // uploadShortListedStudentsDetailsData= new UploadShortListedStudentsDetailsData();

        database1= FirebaseDatabase.getInstance();

        a11= new ArrayList<>();

      //  gd= new GetData();
      //  gd1= new GetData1();

        pdb= new Passworddb(Tpo.this);
        rdb= new ReferenceIddb(Tpo.this);
        us= new UserStatus(Tpo.this);

        SQLiteDatabase db= universitynamedb.getWritableDatabase();
        String query = "select * from universityname";
        Cursor c1 = db.rawQuery(query, null);

      //  String user="";

        if (c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                user= c1.getString(0);
                System.out.println("From Tpo user is: " + c1.getString(0));
            }
        }

      //  String user= udp.getName();

        update_student_details_img= (ImageView) findViewById(R.id.update_students_details_img_id);
        update_student_details_text= (TextView) findViewById(R.id.update_students_details_text_id);

        checkjobAdd.setOnClickListener(this);
        checkJobAdd_text.setOnClickListener(this);

        update_student_details_img.setOnClickListener(this);
        update_student_details_text.setOnClickListener(this);

      //  update_shortlisted_student_details_img= (ImageView) findViewById(R.id.update_shortlisted_student_img_id);
      //  update_shortlisted_student_details_text= (TextView) findViewById(R.id.update_shortlisted_student_text_id);

     //   update_shortlisted_student_details_img.setOnClickListener(this);
     //   update_shortlisted_student_details_text.setOnClickListener(this);


        update_university_details_img= (ImageView) findViewById(R.id.update_university_details_id);
        update_university_details_text= (TextView) findViewById(R.id.update_university_details_text_id);


        update_university_details_img.setOnClickListener(this);
        update_university_details_text.setOnClickListener(this);

        adduniversitydetails_img= (ImageView) findViewById(R.id.add_company_details_img_id);
        adduniversitydetails_text= (TextView) findViewById(R.id.add_company_details_text_id);

        adduniversitydetails_img.setOnClickListener(this);
        adduniversitydetails_text.setOnClickListener(this);


        addstudentdetailsimg= (ImageView) findViewById(R.id.job_advertisement_img_id);

        addstudentdetailsimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tpo.this, AddStudentDetails.class));
            }
        });
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,Menu.FIRST,Menu.NONE,"Logout");
        menu.add(0,Menu.FIRST+1,Menu.NONE,"Remove Account");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Signin.class));
                finish();
                break;
            case Menu.FIRST+1:
                SQLiteDatabase db1= rdb.getWritableDatabase();
                String query1 = "select * from referenceid";
                Cursor c11 = db1.rawQuery(query1, null);
                if (c11!= null && c11.getCount() > 0) {
                    if (c11.moveToFirst()) {
                        password= c11.getString(0);
                        if (password!=null && !password.trim().equals("")) {
                            deleteUserAccount(password);
                        }
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }   */

    private void deleteUserAccount(String password) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Tpo/"+ password).orderByChild("referenceid").equalTo(password);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null) {
            rdb.delete();
            us.delete();
            usernamedb.delete();
            usernamedb.delete();
            universitynamedb.delete();
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User account deleted.");
                                Toast.makeText(Tpo.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        Toast.makeText(this, "User not available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_students_details_img_id:
                updateStudentsDetails();
                break;
            case R.id.update_students_details_text_id:
                updateStudentsDetails();
                break;
            case R.id.update_university_details_id:
                updateUniversityDetails();
                break;
            case R.id.update_university_details_text_id:
                updateUniversityDetails();
                break;
            case R.id.add_company_details_img_id:
                addUniversityDetails();
                break;
            case R.id.add_company_details_text_id:
                addUniversityDetails();
                break;
            case R.id.update_shortlisted_student_img_id:
                checkjob= true;
                checkJobAdvertisements();
                break;
            case R.id.update_shortlisted_student_text_id:
                checkjob= true;
                checkJobAdvertisements();
                break;
            case R.id.view_or_update_shortlisted_students_img_id:
                showAlert();
                break;
            case R.id.view_or_update_shortlisted_students_text_id:
                showAlert();
                break;
        }
    }

    private void showAlert() {
        AlertDialog.Builder a11= new AlertDialog.Builder(Tpo.this);
        a11.setTitle("View or Update shortlisted students:");
        a11.setMessage("You can choose either view shortlisted students or update shortlisted students:");
        a11.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("From showAlert(): checkJobAdvertisement has been called!");
                shortlisted= true;
                checkJobAdvertisements();
              //  checkShortListedStudents();
            }
        });
        a11.setNegativeButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateshortlisted= true;
                checkJobAdvertisements();
              //  checkShortListedStudents();
                //  dialog.cancel();
            }
        });
        AlertDialog a1= a11.create();
        a1.show();
    }

    private void checkJobAdvertisements() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PostJobAdvertisement");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            PostJobAdvertisementData postJobAdvertisementData = new PostJobAdvertisementData((String) userData.get("cName"), (String) userData.get("logo"), (String) userData.get("cQualification"), (String) userData.get("cRequirement"), (String) userData.get("cJobLocation"), (String) userData.get("cKeySkills".replace(",", " ")), (String) userData.get("cYearsOfGap"), (String) userData.get("cTen"), (String) userData.get("cTwelve"), (String) userData.get("cGrad"), (String) userData.get("cCurrentAcademic"), (String) userData.get("referenceId"), (String) userData.get("key"), (String) userData.get("ctc"));

                        /*    System.out.println("From Tpo value is: " + postJobAdvertisementData.getcName() + "\n" + postJobAdvertisementData.getcCurrentAcademic()
                            + "\n" + postJobAdvertisementData.getcGrad() + "\n" + postJobAdvertisementData.getcJobLocation() + "\n" +
                                    postJobAdvertisementData.getcKeySkills() + "\n" + postJobAdvertisementData.getcQualification() + "\n" +
                                    postJobAdvertisementData.getcRequirement() + "\n" + postJobAdvertisementData.getcTen() + "\n" +
                                    postJobAdvertisementData.getcTwelve() + "\n" + postJobAdvertisementData.getcYearsOfGap() + "\n" +
                                    postJobAdvertisementData.getLogo() + "\n" + postJobAdvertisementData.getKey() + "\n" + postJobAdvertisementData.getReferenceId());    */


                            getData.add(postJobAdvertisementData.getcName());
                            getData.add(postJobAdvertisementData.getcCurrentAcademic());
                            getData.add(postJobAdvertisementData.getcGrad());
                            getData.add(postJobAdvertisementData.getcJobLocation());
                            getData.add(postJobAdvertisementData.getcKeySkills());
                            getData.add(postJobAdvertisementData.getcQualification());
                            getData.add(postJobAdvertisementData.getcRequirement());
                            getData.add(postJobAdvertisementData.getcTen());
                            getData.add(postJobAdvertisementData.getcTwelve());
                            getData.add(postJobAdvertisementData.getcYearsOfGap());
                            getData.add(postJobAdvertisementData.getLogo());
                            getData.add(postJobAdvertisementData.getCtc());

                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                //   System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    String value = "";
                    for (String s : getData) {
                        value += s + " ";
                    }

                    System.out.println("From Tpo getData is: " + getData);

                    System.out.println("From Tpo value is: " + value);

                    receiveData = gd1.init(value);

                    System.out.println("From Tpo receiveData is: " + receiveData);

                    getImage = gd1.setImage();

                    System.out.println("From Tpo getImage is: " + getImage);

                    int size = getImage.size();

                    String jobImageUrl[] = new String[size], url = "https://d24v5oonnj2ncn.cloudfront.net/wp-content/uploads/2018/10/16030301/Amazon-Logo-Black.jpg";

                    ArrayList<String> a1 = new ArrayList<>();

                    for (Map.Entry<String, ArrayList<String>> e1 : getImage.entrySet()) {
                        for (int i = 0; i < e1.getValue().size(); i++) {
                            a1.add(e1.getValue().get(i).replace("imageUri:", "").trim());
                        }
                    }

                    for (int i = 0; i < size; i++) {
                        jobImageUrl[i] = String.valueOf(getImage.keySet());
                    }

                    //   System.out.println("From Tpo jobImageUrl is: ");
                    for (String s : a1) {
                        System.out.println("s : " + s);
                    }

                    String newJobImageUrl[] = new String[a1.size()];

                    int i5 = 0;

                    for (String s : a1) {
                        newJobImageUrl[i5++] = s;
                    }

                    String jobName[] = new String[size];

                    for (int i = 0; i < size; i++) {
                        jobName[i] = String.valueOf(getImage.keySet());
                    }

                    //  System.out.println("From Tpo jobName is: ");

                    Set<String> nameJob = new LinkedHashSet<>();

                    for (String s : jobName) {
                        String split[] = s.split("\\[|\\]|,");
                        for (String s1 : split) {
                            if (!s1.trim().isEmpty()) {
                                s1 = s1.trim();
                                System.out.println("s1: " + s1);
                                nameJob.add(s1);
                            }
                        }
                    }

                    String newJobName[] = new String[nameJob.size()];

                    i5 = 0;

                    for (String s : nameJob) {
                        newJobName[i5++] = s;
                    }

                    if (checkjob) {

                        for (String s : newJobImageUrl) {
                            System.out.println("From checkJobAdvertisements() newJobImageUrl: " + s);
                        }

                        for (String s : newJobName) {
                            System.out.println("From checkJobAdvertisements() newJobName: " + s);
                        }

                        jobAdvertisementdata = new ArrayList<>();

                        ArrayList<String> companyName = new ArrayList<>();
                        ArrayList<String> companyLogo = new ArrayList<>();

                        for (int i = 0, i1 = 0; (i < newJobImageUrl.length && i1 < jobName.length); i++, i1++) {
                            JobAdvertisementData jobAdvertisementData = new JobAdvertisementData(newJobImageUrl[i], newJobName[i1]);
                            companyLogo.add(newJobImageUrl[i]);
                            companyName.add(newJobName[i1]);
                            jobAdvertisementdata.add(jobAdvertisementData);
                        }
                        System.out.println("companyLogo: " + companyLogo);
                        System.out.println("companyName: " + companyName);
                        Intent sendData = new Intent(Tpo.this, CheckJobAdd.class);
                        sendData.putExtra("companylogo", companyLogo);
                        sendData.putExtra("companyname", companyName);
                        sendData.putExtra("receivedata", receiveData);
                        startActivity(sendData);
                        checkjob = false;
                    }
                    checkShortListedStudents();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

            private void checkShortListedStudents() {
                // System.out.println("From Tpo checkShortiListedStudents user is: " + user);
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Upload Student Details");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                            for (String key : dataMap.keySet()){
                                Object data = dataMap.get(key);
                                try{
                                    HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification
                                    AddStudentDetailsData addStudentDetailsData= new AddStudentDetailsData((String) userData.get("universityName"), (String) userData.get("studentRegistrationNumber"), (String) userData.get("ten"), (String) userData.get("twelve"), (String) userData.get("graduation"), (String) userData.get("currentAcademic"), (String) userData.get("keySkills"), (String) userData.get("imageUrl"), (String) userData.get("key"), (String) userData.get("selectedBranch"), (String) userData.get("contactNumber"),(String) userData.get("studentName"),
                                    (String) userData.get("emailId"),(String) userData.get("dob"),(String) userData.get("semester"));
                                    getShortListedStudentData.add(addStudentDetailsData.getUniversityName()); getShortListedStudentData.add(addStudentDetailsData.getStudentRegistrationNumber());
                                    getShortListedStudentData.add(addStudentDetailsData.getTen()); getShortListedStudentData.add(addStudentDetailsData.getTwelve());
                                    getShortListedStudentData.add(addStudentDetailsData.getGraduation());getShortListedStudentData.add(addStudentDetailsData.getCurrentAcademic());
                                    getShortListedStudentData.add(addStudentDetailsData.getKeySkills()); getShortListedStudentData.add(addStudentDetailsData.getImageUrl());
                                    getShortListedStudentData.add(addStudentDetailsData.getContactNumber());
                                }catch (ClassCastException cce){
                                    try{
                                        String mString = String.valueOf(dataMap.get(key));
                                        System.out.println("From Tpo mString is: " + mString);
                                    }catch (ClassCastException cce2){
                                    }
                                }
                            }
                        }
                        set1.addAll(getShortListedStudentData);
                        System.out.println("From Tpo getShortListedStudentData is: " + getShortListedStudentData);
                        String value= "";
                        for (String s: getShortListedStudentData) {
                            value+= s + " ";
                        }
                        System.out.println("From Tpo value is: " + value);
                        aSet= fetchStudents.init(value, user);
                        System.out.println("From Tpo aSet is: " + aSet);
                        shortList();
                    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

            private void shortList() {
                System.out.println("From shortList() aSet: " + aSet);
                System.out.println("From shortList() receiveData: " + receiveData);
                boolean criteriaMatched1= false, criteriaMatched11= false, criteriaMatched111= false;

                System.out.println("From Tpo shortList() aSet is: " + aSet);

                for (ArrayList<String> list1 : aSet) {
                   // setToList.addAll(list1);
                    for (String s : list1) {
                        //   System.out.println("Student's academic marks");
                        if (s.contains("studentregistrationnumber:")) {
                            System.out.println(s);
                        }
                        if (s.contains("ten:")) {
                            ten = s.replace("ten:", "").replace(",", "").replace("%", "").trim();
                        }
                        if (s.contains("twelve:")) {
                            twelve= s.replace("twelve:","").replace(",","").trim();
                            System.out.println("From Tpo shortList() twelve is: " + s);
                        }
                        if (s.contains("graduation:")) {
                            grad = s.replace("graduation:", "").replace(",", "").replace("%", "").trim();
                        }
                        if (s.contains("currentacademic:")) {
                            s = s.substring(s.indexOf("currentacademic:"), s.indexOf("keyskills:")).replace("currentacademic:", "").replace(",", "").replace("%", "").trim();
                            currentacademic = s;
                        }
                    }

                    boolean isMatched= matchWithCompany(list1, ten, grad, currentacademic, twelve);
                    System.out.println();
                    System.out.println("companyTagList: " + companyTagList);
                    String companyList= "";

                    for (String s: companyTagList) {
                        companyList+= s + " | ";
                    }

                    //list1.add();

                    String getReq= addJobRequirements();

                    list1.add(getReq.trim());

                    setToList.addAll(list1);

                    System.out.println("From shortList getReq: " + getReq);

                    System.out.println("list1: " + list1);

                    if (isMatched) {
                        mapStudentToCompany.put(list1, companyList);
                    }
                    companyTagList.clear();
                }
                System.out.println("mapStudentToCompany: " + mapStudentToCompany);

                System.out.println("From shortList() setToList is: " + setToList);

                fetchImageUrl= fetchImageUrl(mapStudentToCompany);

                System.out.println("From Tpo shortList() fetchImageUrl is: " + fetchImageUrl);
                System.out.println("From Tpo shortList() mapImageUrlToStudentDetails: " + mapImageUrlToStudentDetails);
                System.out.println("From Tpo shortList() mapImageUrlToCompany: " + mapImageUrlToCompany);

                ArrayList<String> getImageUrlList= new ArrayList<>();

                for (Map.Entry<String, String> e1: mapImageUrlToStudentDetails.entrySet()) {
                    getImageUrlList.add(e1.getValue().trim());
                }

                System.out.println("From Tpo shortList() getImageUrlList: " + getImageUrlList);


                // upload shortlisted student details in the firebase real time database...

                System.out.println("From Tpo shortList() user is: " + user);

                System.out.println("From shortList setToList: " + setToList);

                for (Map.Entry<String, String> e1: mapImageUrlToStudentDetails.entrySet()) {
                    String key= e1.getKey();
                    System.out.println("From shortList() key is: " + key);
                    if (key.contains("studentregistrationnumber:")) {
                        String reg= key.substring(key.indexOf("studentregistrationnumber:"), key.indexOf("ten:")).replace(",","").trim();
                        regNo= reg;
                        uploadShortListedStudentsDetailsData.setRegistration(reg);
                    }
                    if (key.contains("ten:")) {
                        String ten= key.substring(key.indexOf("ten:"), key.indexOf("twelve:")).replace(",","").trim();
                        uploadShortListedStudentsDetailsData.setTen(ten);
                    }
                    if (key.contains("graduation:")) {
                        String grad= key.substring(key.indexOf("graduation:"), key.indexOf("currentacademic:")).replace(",","").trim();
                        uploadShortListedStudentsDetailsData.setGraduation(grad);
                    }
                    if (key.contains("twelve:")) {
                        String twel= key.substring(key.indexOf("twelve:"), key.indexOf("graduation:")).replace(",","").trim();
                        uploadShortListedStudentsDetailsData.setTwelve(twel);
                    }
                    if (key.contains("currentacademic:")) {
                        String academic= key.substring(key.indexOf("currentacademic:"), key.indexOf("keyskills:")).replace(",","").trim();
                        uploadShortListedStudentsDetailsData.setCurrentacademic(academic);
                    }
                    if (key.contains("keyskills:")) {
                        String keyskills= key.substring(key.indexOf("keyskills:")).replace("keyskills:","").replace(",","").trim();
                        uploadShortListedStudentsDetailsData.setKeyskills(keyskills);
                    }
                    if (setToList.size()!=0) {
                        String contactNumber= setToList.get(6).replace("contactnumber:","").trim();
                        uploadShortListedStudentsDetailsData.setContactNumber(contactNumber);
                    }
                    if (setToList.size()!=0) {
                        String req= setToList.get(7).trim();
                        uploadShortListedStudentsDetailsData.setJobrequirement(req);
                    }

                   /* if (key.contains("contactnumber:")) {
                        String contactNumber= key.substring(key.indexOf("contactnumber:")).replace("contactnumber:","").trim();
                        uploadShortListedStudentsDetailsData.setContactNumber(contactNumber);
                    }   .*/
                    String val= e1.getValue().trim();
                    uploadShortListedStudentsDetailsData.setImageurl(val);

                    String company= mapImageUrlToCompany.get(key);

                    company= company.replace("|","").trim();

                    uploadShortListedStudentsDetailsData.setCompaniesselected(company);

                    String key1= user + " " + regNo;
                    uploadShortListedStudentsDetailsData.setUniversity("University: " + user);

                    ref1= database1.getInstance().getReference().child("Upload ShortListed Student Details");
                    ref1.child(key1).setValue(uploadShortListedStudentsDetailsData);
                }
                // upload shortlisted student details in the firebase real time database ends over here...

                if (shortlisted || updateshortlisted) {

                    System.out.println("From shortList() receiveData is: " + receiveData);

                    System.out.println("shortList() mapImageUrlToCompany is: " + mapImageUrlToCompany);

                    System.out.println("shortList() mapImageUrlToStudentDetails is: " + mapImageUrlToStudentDetails);

                    shortlistedStudentsDataArrayList = new ArrayList<>();

                    for (int i = 0; i < getImageUrlList.size(); i++) {
                        //   JobAdvertisementData jobAdvertisementData= new JobAdvertisementData(newJobImageUrl[i], newJobName[i1]);
                        //  jobAdvertisementdata.add(jobAdvertisementData);

                        ShortlistedStudentsData shortlistedStudentsData = new ShortlistedStudentsData(getImageUrlList.get(i));
                        shortlistedStudentsDataArrayList.add(shortlistedStudentsData);
                    }

                    if (shortlisted && !updateshortlisted) {

                        Intent sendData = new Intent(Tpo.this, CheckShortlistedStudents.class);

                        sendData.putStringArrayListExtra("getimageurllist", getImageUrlList);
                        sendData.putExtra("mapimageurltocompany", (Serializable) mapImageUrlToCompany);
                        sendData.putExtra("mapimageurltostudentdetails", (Serializable) mapImageUrlToStudentDetails);
                        sendData.putExtra("users", user);

                        startActivity(sendData);

                        shortlisted = false;
                    }

                    else if (updateshortlisted && !shortlisted) {
                        for (Map.Entry<String, String> e1: mapImageUrlToCompany.entrySet()) {
                            String k= e1.getKey();
                            if (k.contains("studentregistrationnumber:") && k.contains("ten:")) {
                                k= k.substring(k.indexOf("studentregistrationnumber:"), k.indexOf("ten:")).replace("studentregistrationnumber:","").replace(",","").trim();
                                regNo1.add(k);
                            }
                        }
                        System.out.println("From Tpo shortList() regNo1: " + regNo1);
                        updateStudentsDetails();
                    }
                }
            }

            ArrayList<String> req= new ArrayList<>();

    private String addJobRequirements() {
        System.out.println("From addJobRequirements receiveData receiveData: " + receiveData);

        System.out.println("From addJobRequirements user: " + user);

        String fetchJobReq= "";

        for (Map<String, Set<ArrayList<String>>> s1: receiveData) {
            System.out.println("receiveData and receiveData: " + s1);
            String concat= s1 + "";
            System.out.println("concat : " + concat);
            String split[]= concat.split("\\{|\\}|\\[|\\]");
            for (String s11: split) {
                if (!s11.trim().equals(",") || !s11.trim().isEmpty()) {
                    System.out.println("s11 split: " + s11);
                    if (s11.contains("Requirements:") && s11.contains("CurrentAcademic:")) {
                        System.out.println(s11.substring(s11.indexOf("Requirements:"), s11.indexOf("CurrentAcademic:")).replace("Requirements:","").trim());
                        fetchJobReq+= s11.substring(s11.indexOf("Requirements:"), s11.indexOf("CurrentAcademic:")).replace("Requirements:","").replace(",","").trim() + " ";

                        req.add(fetchJobReq);

                        System.out.println("From addJobRequirements() fetchJobReq: " + fetchJobReq);

                        return fetchJobReq;

                        // fetchJobReq+= "\t";
                        //  fetchJobRequirements1+= s11.substring(s11.indexOf("Requirements:"), s11.indexOf("CurrentAcademic:")).replace("Requirements:","").trim() + "\t";
                    }
                }
            }
            System.out.println("From addJobRequirements() req: " + req);
        }

      //  System.out.println("From addJobRequirements() fetchJObRequirements1: " + fetchJobRequirements1);
        return fetchJobReq;
    }

    private ArrayList<String> fetchImageUrl(Map<ArrayList<String>, String> mapStudentToCompany) {
                System.out.println("From fetchImageUrl mapStudentToCompany is: " + mapStudentToCompany);
                ArrayList<String> imageUrlList= new ArrayList<>();

                for (Map.Entry<ArrayList<String>, String> e1: mapStudentToCompany.entrySet()) {
                    //  System.out.println("From fetchImageUrl e1.getKey(): " + e1.getKey());
                    String extractStudentDetails= e1.getKey() + "".replace("[","").replace("]","").replace("[","").trim();
                    extractStudentDetails= extractStudentDetails.substring(extractStudentDetails.indexOf(extractStudentDetails.charAt(0)), extractStudentDetails.indexOf("uriPath:")).trim();
                    //   System.out.println("From fetchImageUrl extractStudentDetails: " + extractStudentDetails.replace("[",""));
                    for (String s1: e1.getKey()) {
                        if (s1.contains("uriPath:")) {
                            s1= s1.replace("uriPath:","").replace(",","").trim();
                            mapImageUrlToStudentDetails.put(extractStudentDetails.replace("[","").trim(),s1);
                            imageUrlList.add(s1);
                        }
                    }

                    String s1= e1.getValue();

                    System.out.println("From Tpo fetchImageUrl e1.getValue() is: " + s1);

                    for (String s11: e1.getKey()) {
                        if (s11.contains("uriPath:")) {
                            s11= s11.replace("uriPath:","").replace(",","").trim();
                            mapImageUrlToCompany.put(extractStudentDetails.replace("[",""),e1.getValue());
                            imageUrlList.add(s11);
                        }
                    }
                }
                //  System.out.println("From fetchUrl imageUrlList is: " + imageUrlList);
                return imageUrlList;
            }

            private boolean matchWithCompany(ArrayList<String> list11, String ten, String grad, String currentacademic, String twelve) {
                boolean criteriaMatched1= false, criteriaMatched11=false, criteriaMatched111= false, criteriaMatched12= false;
                for (Map<String, Set<ArrayList<String>>> e1: receiveData) {
                    for (Map.Entry<String, Set<ArrayList<String>>> e11:e1.entrySet()) {
                        Set<ArrayList<String>> value= e11.getValue();
                        for (ArrayList<String> list1: value) {
                            System.out.println("From Tpo matchWithCompany() list1: " + list1);
                            for (String s1: list1) {
                                if (s1.contains("CurrentAcademic:")) {
                                    s1= s1.replace("CurrentAcademic:","").replace("%","").trim();
                                    if (Integer.parseInt(currentacademic) >= Integer.parseInt(s1)) {
                                        criteriaMatched1= true;
                                    }
                                }
                                if (s1.contains("selectedGrad:")) {
                                    s1= s1.replace("selectedGrad:","").replace("%","").trim();
                                    if (Integer.parseInt(grad) >= Integer.parseInt(s1)) {
                                        criteriaMatched11= true;
                                    }
                                }
                                if (s1.contains("Twelvth:")) {
                                    s1= s1.replace("Twelvth:","").replace("%","").trim();
                                    //    if (Integer.parseInt(twelve) >= Integer.parseInt(s1)) {
                                    criteriaMatched12= true;
                                    //   }
                                }
                                if (s1.contains("Tenth:")) {
                                    s1= s1.replace("Tenth:","").replace("%","").trim();
                                    if (Integer.parseInt(ten) >= Integer.parseInt(s1)) {
                                        criteriaMatched111= true;
                                    }
                                }
                                if (criteriaMatched1 && criteriaMatched11 && criteriaMatched111 && criteriaMatched12) {
                                    if (!companyTagList.contains(e11.getKey())) {
                                        if (isCriteriaSatisfied(e11.getValue(), ten, grad, currentacademic, twelve))
                                            companyTagList.add(e11.getKey());
                                    }
                                }
                            }
                        }
                        companyTag= e11.getKey();
                    }
                    if (criteriaMatched1 && criteriaMatched11 && criteriaMatched111 && criteriaMatched12) {
                        return true;
                    }
                    else {
                        break;
                    }
                }
                return false;
            }

            private boolean isCriteriaSatisfied(Set<ArrayList<String>> value, String ten, String grad, String currentacademic, String twelve) {
                boolean criteriaMatched1= false, criteriaMatched11= false, criteriaMatched111= false, criteriaMatched12= false;
                for (ArrayList<String> list1: value) {
                    System.out.println("From Tpo isCriteriaSatisfied() value: " + list1);
                    for (String s1: list1) {
                        if (s1.contains("CurrentAcademic:")) {
                            s1 = s1.replace("CurrentAcademic:", "").replace("%", "").trim();
                            if (Integer.parseInt(currentacademic) >= Integer.parseInt(s1)) {
                                criteriaMatched1 = true;
                            }
                        }
                        if (s1.contains("selectedGrad:")) {
                            s1 = s1.replace("selectedGrad:", "").replace("%", "").trim();
                            if (Integer.parseInt(grad) >= Integer.parseInt(s1)) {
                                criteriaMatched11 = true;
                            }
                        }
                        if (s1.contains("Tenth:")) {
                            s1 = s1.replace("Tenth:", "").replace("%", "").trim();
                            if (Integer.parseInt(ten) >= Integer.parseInt(s1)) {
                                criteriaMatched111 = true;
                            }
                        }
                        if (s1.contains("Twelvth:")) {
                            s1= s1.replace("Twelvth:","").replace("%","").trim();
                            System.out.println("From Tpo isCriteriaSatisfied Twelvth is: " + s1);
                            System.out.println("From Tpo isCriteriaSatisfied twelve is: " + twelve);
                            if (Integer.parseInt(twelve) >= Integer.parseInt(s1)) {
                                criteriaMatched12= true;
                            }
                        }
                    }
                }
                return true?criteriaMatched1 && criteriaMatched11 && criteriaMatched111 && criteriaMatched12:false;
            }


            private void updateUniversityDetails() {
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

                            AddUniversityDetailsData addUniversityDetailsData = new AddUniversityDetailsData((String) userData.get("universityname"), (String) userData.get("universitylocation"), (String) userData.get("universityid"), (String) userData.get("branchesavailable"), (String) userData.get("yearofestablishment"), (String) userData.get("imageuri"), (String) userData.get("key"));

                            getUniversityData.add(addUniversityDetailsData.getUniversityname());
                            getUniversityData.add(addUniversityDetailsData.getUniversitylocation());
                            getUniversityData.add(addUniversityDetailsData.getUniversityid());
                            getUniversityData.add(addUniversityDetailsData.getBranchesavailable());
                            getUniversityData.add(addUniversityDetailsData.getYearofestablishment());
                            getUniversityData.add(addUniversityDetailsData.getImageuri());
                            getUniversityData.add(addUniversityDetailsData.getKey());

                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                //   System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }

                        System.out.println("getUniversityData: " + getUniversityData);

                        String val11= "";

                        for (String s: getUniversityData) {
                            val11+= s + " ";
                        }

                        System.out.println("From Tpo updateUniversityDetails() val1: " + val11);

                        System.out.println("From Tpo user is: " + user);

                        Set<String> a1= upd11.init(val11, user);

                        System.out.println("From Tpo updateUniversityDetails() a1 is: " + a1);

                        ArrayList<String> a11= new ArrayList<>();

                        a11.addAll(a1);

                        Intent sendData= new Intent(Tpo.this, UpdateUniversityDetails.class);

                        updateUniversityDetails= true;

                        sendData.putExtra("udateuniversity", updateUniversityDetails);


                        sendData.putStringArrayListExtra("getuniversitydata", a11);
                        startActivity(sendData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addUniversityDetails() {
       // startActivity(new Intent(Tpo.this, AddUniversityDetails.class));
        Intent intent= new Intent(Tpo.this, AddUniversityDetails.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void updateStudentsDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Upload Student Details");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            AddStudentDetailsData addStudentDetailsData = new AddStudentDetailsData((String) userData.get("universityName"), (String) userData.get("studentRegistrationNumber"), (String) userData.get("ten"), (String) userData.get("twelve"), (String) userData.get("graduation"), (String) userData.get("currentAcademic".replace(",", " ")), (String) userData.get("keySkills"), (String) userData.get("imageUrl"), (String) userData.get("key"),
                                    (String) userData.get("selectedBranch"), (String) userData.get("contactNumber"), (String) userData.get("studentName"), (String) userData.get("emailId"), (String) userData.get("dob"), (String) userData.get("semester"));

                            getStudentDetailsData.add(addStudentDetailsData.getUniversityName());
                            getStudentDetailsData.add(addStudentDetailsData.getStudentRegistrationNumber());
                            getStudentDetailsData.add(addStudentDetailsData.getTen());
                            getStudentDetailsData.add(addStudentDetailsData.getTwelve());
                            getStudentDetailsData.add(addStudentDetailsData.getGraduation());
                            getStudentDetailsData.add(addStudentDetailsData.getCurrentAcademic());
                            getStudentDetailsData.add(addStudentDetailsData.getKeySkills());
                            getStudentDetailsData.add(addStudentDetailsData.getImageUrl());
                            getStudentDetailsData.add(addStudentDetailsData.getKey());
                            getStudentDetailsData.add(addStudentDetailsData.getStudentName());
                            getStudentDetailsData.add(addStudentDetailsData.getContactNumber());
                            getStudentDetailsData.add(addStudentDetailsData.getSemester());
                            getStudentDetailsData.add(addStudentDetailsData.getSelectedBranch());
                            getStudentDetailsData.add(addStudentDetailsData.getDob());
                            getStudentDetailsData.add(addStudentDetailsData.getEmailId());

                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {

                                String mString = String.valueOf(dataMap.get(key));

                                //   System.out.println("From Tpo mString is: " + mString);


                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("getStudentDetailsData: " + getStudentDetailsData);

                    String val1= "";

                    for (String s: getStudentDetailsData) {
                        val1+= s + " ";
                    }

                  //  String universityname= "name:"+ user;

                    String universityname= user;

                 //   System.out.println("From Tpo updateStudentsDetails() universityname: " + universityname);

                  //  universityname= universityname.toLowerCase();

                    System.out.println("From Tpo updateStudentsDetails() universityname is: " + universityname);

                    System.out.println("From Tpo updateStudentsDetails() val1 is: " + val1);

                    System.out.println("From Tpo updateStudentsDetails() universityname: " + universityname);

                    a11= updateStudentsExtractData.init(val1, universityname);

                  //  System.out.println("From Tpo updateStudentsDetails() a11: ");

                    System.out.println("From Tpo updateStudentsDetails() a11.get(0): " + a11.get(0));

                    System.out.println("From Tpo updateStudentsDetails() a11: " + a11.get(1));

                    mapRegToDetsils= a11.get(0);

                    mapRegToKey= a11.get(1);

                    Intent intent= new Intent(Tpo.this, UpdateStudentsDetailsForm.class);

                    intent.putExtra("users", user);

                    if (updateshortlisted) {
                        ArrayList<String> reg11= new ArrayList<>();
                        reg11.addAll(regNo1);
                        System.out.println("From Tpo updateStudentDetails() reg11: " + reg11);
                        intent.putExtra("updateshortlisted", updateshortlisted);
                        intent.putStringArrayListExtra("reg", reg11);
                    }
                    intent.putExtra("mapregtodetails", (Serializable) mapRegToDetsils);
                    intent.putExtra("mapregtokey", (Serializable) mapRegToKey);
                    intent.putExtra("updateshortlisted", updateshortlisted);
                    updateStudentDetails= true;
                    intent.putExtra("updatestudent", updateStudentDetails);
                    updateshortlisted= false;
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_id:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Signin.class));
                finish();
                break;
            case R.id.remove_id:
                SQLiteDatabase db1= rdb.getWritableDatabase();
                String query1 = "select * from referenceid";
                Cursor c11 = db1.rawQuery(query1, null);
                if (c11!= null && c11.getCount() > 0) {
                    if (c11.moveToFirst()) {
                        password= c11.getString(0);
                        if (password!=null && !password.trim().equals("")) {
                            deleteUserAccount(password);
                        }
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}