package com.pushpa.mycampusrecruitment.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pushpa.mycampusrecruitment.Adapter.UniversityAdapter;
import com.pushpa.mycampusrecruitment.Credentials.Signin;
import com.pushpa.mycampusrecruitment.Credentials.UserStatus;
import com.pushpa.mycampusrecruitment.CustomDialog.CompanyDetailsDialog;
import com.pushpa.mycampusrecruitment.Database.Passworddb;
import com.pushpa.mycampusrecruitment.Database.Profile;
import com.pushpa.mycampusrecruitment.Database.ReferenceIddb;
import com.pushpa.mycampusrecruitment.Database.Universitynamedb;
import com.pushpa.mycampusrecruitment.Database.Usernamedb;
import com.pushpa.mycampusrecruitment.Model.AddCompanyDetails;
import com.pushpa.mycampusrecruitment.Model.AddStudentDetailsData;
import com.pushpa.mycampusrecruitment.Model.AddUniversityDetailsData;
import com.pushpa.mycampusrecruitment.Model.PostJobAdvertisementData;
import com.pushpa.mycampusrecruitment.Model.UniversityData;
import com.pushpa.mycampusrecruitment.Processor.PostJobExtractData;
import com.pushpa.mycampusrecruitment.Processor.ShortListStudentsData;
import com.pushpa.mycampusrecruitment.Processor.UpdateCompanyDetailsData;
import com.pushpa.mycampusrecruitment.Processor.UpdateJobsData;
import com.pushpa.mycampusrecruitment.Processor.receiveData;
import com.pushpa.mycampusrecruitment.Processor.viewUniversitiesdata;
import com.pushpa.mycampusrecruitment.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Companyadmin extends AppCompatActivity implements View.OnClickListener, CompanyDetailsDialog.companydetailscreatelistener, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "Companyadmin";
    Passworddb pdb;
    String password="";

    ImageView addcompanydetails_img, post_jobs_img, check_shortlisted_student_img_id, update_company_details_img_id, view_university_img_id, update_job_add_img;
    TextView addcompanydetails_text, post_jobs_text, check_shortlisted_student_text_id, update_company_details_text_id, view_university_text_id, update_job_add_text;

    ArrayList<UniversityData> universityDataList;

  //  ArrayList<ShortlistedStudentsData> shortlistedStudentsDataList;

    ArrayList<String> getSpecificShortListedStudents;

    ArrayList<String> PostJobAdvertisementData;

    UniversityAdapter universityAdapter;

    Usernamedb usernamedb;

  //  ShortlistedCandidatesAdapter shortlistedCandidatesAdapter;

    FirebaseDatabase database;

    ReferenceIddb rdb;

    UserStatus us;

    String user="", regNo="";

    Universitynamedb universitynamedb;

    RecyclerView recyclerView1, recyclerView11;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    String uriPath= "";

    Profile pf;

    AddCompanyDetails addCompanyDetails;

  //  PostJobExtractData pjdb;

 //   ShortListStudentsData sdb;

    Map<String, ArrayList<Set<String>>> map1;

    Map<String, Set<String>> map2= new LinkedHashMap<>();

    Map<String, Set<String>> shortListedStudents= new LinkedHashMap<>();

    ArrayList<String> addCompanyDetailsArrayList= new ArrayList<String>();


    UpdateCompanyDetailsData updateCompanyDetailsData;

    ArrayList<String> receiveUniversityDetails= new ArrayList<>();

    Map<String, Set<String>> receiveViewUniversityData= new LinkedHashMap<>();

    viewUniversitiesdata universitiesdata;

    ArrayList<String> receivePostJobData= new ArrayList<>();

    PostJobExtractData pjdb;

    ShortListStudentsData sdb;

    receiveData rcv;

    UpdateJobsData updateJobsData;

  //  UpdateJobsData updateJobsData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companyadmin);

        PostJobAdvertisementData= new ArrayList<>();

        usernamedb= new Usernamedb(Companyadmin.this);

        pjdb= new PostJobExtractData();

        sdb= new ShortListStudentsData();

        rcv= new receiveData();

        updateJobsData= new UpdateJobsData();

        universitiesdata= new viewUniversitiesdata();

        pdb= new Passworddb(Companyadmin.this);

        rdb= new ReferenceIddb(Companyadmin.this);

        updateCompanyDetailsData= new UpdateCompanyDetailsData();

    //    pjdb= new PostJobExtractData();

    //    sdb= new ShortListStudentsData();

        map1= new LinkedHashMap<>();

        map2= new LinkedHashMap<>();

        us= new UserStatus(Companyadmin.this);

        getSpecificShortListedStudents= new ArrayList<>();

        database= FirebaseDatabase.getInstance();

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        addCompanyDetails= new AddCompanyDetails();

        pf= new Profile(Companyadmin.this);

        universitynamedb= new Universitynamedb(Companyadmin.this);

        SQLiteDatabase db= universitynamedb.getWritableDatabase();
        String query = "select * from universityname";
        Cursor c1 = db.rawQuery(query, null);

        //  String user="";

        if (c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                user= c1.getString(0);
                System.out.println("From Companyadmin user is: " + c1.getString(0));
            }
        }

        addcompanydetails_img= (ImageView) findViewById(R.id.add_company_details_img_id);
        addcompanydetails_text= (TextView) findViewById(R.id.add_company_details_text_id);

        post_jobs_img= (ImageView) findViewById(R.id.job_advertisement_img_id);
        post_jobs_text= (TextView) findViewById(R.id.post_job_advertisements_text_id);

        post_jobs_img.setOnClickListener(this);
        post_jobs_text.setOnClickListener(this);

        check_shortlisted_student_img_id= (ImageView) findViewById(R.id.check_shortlisted_student_img_id);
        check_shortlisted_student_text_id= (TextView) findViewById(R.id.check_shortlisted_student_text_id);

        update_company_details_img_id= (ImageView) findViewById(R.id.update_company_details_img_id);
        update_company_details_text_id= (TextView) findViewById(R.id.update_company_details_text_id);

        update_job_add_img= (ImageView) findViewById(R.id.update_job_add_img_id);
        update_job_add_text= (TextView) findViewById(R.id.update_job_add_text_id);

        view_university_img_id= (ImageView) findViewById(R.id.view_university_img_id);
        view_university_text_id= (TextView) findViewById(R.id.view_university_text_id);

        check_shortlisted_student_img_id.setOnClickListener(this);
        check_shortlisted_student_text_id.setOnClickListener(this);

        addcompanydetails_img.setOnClickListener(this);
        addcompanydetails_text.setOnClickListener(this);

      //  post_jobs_img.setOnClickListener(this);
     //   post_jobs_text.setOnClickListener(this);

        update_company_details_img_id.setOnClickListener(this);
        update_company_details_text_id.setOnClickListener(this);

        view_university_img_id.setOnClickListener(this);
        view_university_text_id.setOnClickListener(this);

        update_job_add_img.setOnClickListener(this);
        update_job_add_text.setOnClickListener(this);
    }

    @Override
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
    }

    private void deleteUserAccount(String password) {
        System.out.println("From Companyadmin password is: " + password);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("CompanyAdmin").orderByChild("referenceid").equalTo(password);

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
            universitynamedb.delete();
            usernamedb.delete();
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User account deleted.");
                                Toast.makeText(Companyadmin.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(this, "No user to delete!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_company_details_img_id:
                addCompanyDetailsOpenDialog();
                break;
            case R.id.add_company_details_text_id:
                addCompanyDetailsOpenDialog();
                break;
            case R.id.update_company_details_img_id:
                updateCompanyDetails();
                break;
            case R.id.update_company_details_text_id:
                updateCompanyDetails();
                break;
            case R.id.view_university_img_id:
                viewUniversity();
                break;
            case R.id.view_university_text_id:
                viewUniversity();
                break;
            case R.id.job_advertisement_img_id:
                openJobPost();
                break;
            case R.id.post_job_advertisements_text_id:
                openJobPost();
                break;
            case R.id.check_shortlisted_student_img_id:
                checkShortListedStudent();
                break;
            case R.id.check_shortlisted_student_text_id:
                checkShortListedStudent();
                break;
            case R.id.update_job_add_img_id:
                updateJobAdd();
                break;
            case R.id.update_job_add_text_id:
                updateJobAdd();
                break;
        }
    }

    private void openJobPost() {
        startActivity(new Intent(Companyadmin.this, PostJobAdvertisements.class));
    }

    private void viewUniversity() {
        System.out.println("From Companyadmin updateCompanyDetails() user is: " + user);
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

                    Intent sendData= new Intent(Companyadmin.this, ViewUniversities.class);
                    sendData.putExtra("map1", (Serializable) receiveViewUniversityData);

                    startActivity(sendData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateCompanyDetails() {
        System.out.println("From Companyadmin updateCompanyDetails() user is: " + user);

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
                            addCompanyDetailsArrayList.add(addCompanyDetails.getcName());
                            addCompanyDetailsArrayList.add(addCompanyDetails.getcYos());
                            addCompanyDetailsArrayList.add(addCompanyDetails.getcTotal());
                            addCompanyDetailsArrayList.add(addCompanyDetails.getcLocation());
                            addCompanyDetailsArrayList.add(addCompanyDetails.getcAwardsAchievement());
                            addCompanyDetailsArrayList.add(addCompanyDetails.getcImage());
                            addCompanyDetailsArrayList.add(addCompanyDetails.getReferenceId());
                            addCompanyDetailsArrayList.add(addCompanyDetails.getKey());
                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                System.out.println("From Tpo mString is: " + mString);
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }
                    System.out.println("From Companyadmin addCompanyaDetailsArrayList: " + addCompanyDetailsArrayList);

                    String val1= "";

                    for (String s: addCompanyDetailsArrayList) {
                        val1+= s + " ";
                    }

                    Set<String> set1= updateCompanyDetailsData.init(val1, user);

                    System.out.println("From Companyadmin set1 is: " + set1);

                    ArrayList<String> setToList= new ArrayList<>();

                    setToList.addAll(set1);

                    Intent sendData= new Intent(Companyadmin.this, UpdateCompanyDetails.class);
                    sendData.putExtra("settolist", setToList);
                    sendData.putExtra("user", user);
                    startActivity(sendData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addCompanyDetailsOpenDialog() {
        CompanyDetailsDialog cdd= new CompanyDetailsDialog();
        cdd.show(getSupportFragmentManager(), "Company Details");
    }

    private void updateJobAdd() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("PostJobAdvertisement");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                            com.pushpa.mycampusrecruitment.Model.PostJobAdvertisementData addCompanyDetails = new PostJobAdvertisementData((String) userData.get("cName"), (String) userData.get("logo"),
                                    (String) userData.get("cQualification"), (String) userData.get("cRequirement"), (String) userData.get("cJobLocation"),
                                    (String) userData.get("cKeySkills"), (String) userData.get("cYearsOfGap"), (String) userData.get("cTen"),
                                    (String) userData.get("cTwelve"), (String) userData.get("cGrad"), (String) userData.get("cCurrentAcademic"),
                                    (String) userData.get("referenceId"), (String) userData.get("key"), (String) userData.get("ctc"));

                            receivePostJobData.add(addCompanyDetails.getcName());
                            receivePostJobData.add(addCompanyDetails.getLogo());
                            receivePostJobData.add(addCompanyDetails.getcQualification());
                            receivePostJobData.add(addCompanyDetails.getcRequirement());
                            receivePostJobData.add(addCompanyDetails.getcJobLocation());
                            receivePostJobData.add(addCompanyDetails.getcKeySkills());
                            receivePostJobData.add(addCompanyDetails.getcYearsOfGap());
                            receivePostJobData.add(addCompanyDetails.getcTen());
                            receivePostJobData.add(addCompanyDetails.getcTwelve());
                            receivePostJobData.add(addCompanyDetails.getcGrad());
                            receivePostJobData.add(addCompanyDetails.getcCurrentAcademic());
                            receivePostJobData.add(addCompanyDetails.getReferenceId());
                            receivePostJobData.add(addCompanyDetails.getKey());
                            receivePostJobData.add(addCompanyDetails.getCtc());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("From updateJobAdd() receivePostJobData is: " + receivePostJobData);

                    String val1= "";

                    for (String s: receivePostJobData) {
                        val1+= s + " ";
                    }

                    System.out.println("From updateJobAdd() val1 is: " + val1);

                    Map<String, Set<String>> receiveUpdateJobData= updateJobsData.init(val1, user);

                    System.out.println("From Companyadmin receiveUpdateJobData: " + receiveUpdateJobData);

                    Intent sendData= new Intent(Companyadmin.this, UpdateJobFromCompanyAdmin.class);

                    sendData.putExtra("map1", (Serializable) receiveUpdateJobData);

                    sendData.putExtra("user", user);

                    startActivity(sendData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void createcompanydetails(final String Name1, final String yos, final String total, final String location, final String awardsandachievents, Uri imageUri) {
        System.out.println("From Companyadmin createcompanydetails(): " + Name1 + " " + yos + " " + total + " " + location + " " + awardsandachievents);
        if (imageUri!=null) {
            final String referenceId= UUID.randomUUID().toString();
            final StorageReference ref= storageReference.child("Add Company Logos/" + Name1 + " " + referenceId);
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();
            try {
                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(Companyadmin.this, "Profile pic uploaded successfully", Toast.LENGTH_SHORT).show();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(Companyadmin.this, "Uploaded Image url: " + uri+"", Toast.LENGTH_SHORT).show();
                                uriPath= uri + "";
                                System.out.println("uriPath is: " + uriPath);
                                uploadTextsData(Name1, yos, total, location, awardsandachievents, uriPath, referenceId);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Companyadmin.this, "Error while uploading profile pic", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Uploaded: " + progress + "%");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadTextsData(String name1, String yos, String total, String location, String awardsandachievents, String uriPath, String refenceId) {
        addCompanyDetails.setcAwardsAchievement("Acheivements: "+ awardsandachievents);
        addCompanyDetails.setcImage("uriPath: " + uriPath);
        addCompanyDetails.setcLocation("location: " + location);
        addCompanyDetails.setcName("name1: " + name1);
        addCompanyDetails.setcYos("yos: " + yos);
        addCompanyDetails.setcTotal("total: " + total);
        addCompanyDetails.setReferenceId("referenceId: " + refenceId);
        String key= name1 + " " + refenceId;
        addCompanyDetails.setKey("key: " + key);
        ref1= database1.getInstance().getReference().child("AddCompanyDetails");
        ref1.child(key).setValue(addCompanyDetails);
    }

    private void checkShortListedStudent() {
        //  lookupJobAdvertisements();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Upload Student Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.exists()) {
                        HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                        for (String key : dataMap.keySet()) {

                            Object data = dataMap.get(key);

                            try {
                                HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification

                                AddStudentDetailsData displaySpecificShortListedStudentData = new AddStudentDetailsData((String) userData.get("universityName"), (String) userData.get("studentRegistrationNumber"), (String) userData.get("ten"), (String) userData.get("twelve"), (String) userData.get("graduation"), (String) userData.get("currentAcademic"), (String) userData.get("keySkills"), (String) userData.get("imageUrl"), (String) userData.get("key"), (String) userData.get("selectedBranch"),
                                        (String) userData.get("contactNumber"), (String) userData.get("studentName"), (String) userData.get("emailId"), (String) userData.get("dob"), (String) userData.get("semester"));

                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getUniversityName());
                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getStudentRegistrationNumber());
                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getTen());
                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getTwelve());
                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getGraduation());
                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getCurrentAcademic());
                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getKeySkills());
                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getImageUrl());
                                getSpecificShortListedStudents.add(displaySpecificShortListedStudentData.getKey());
                                //   System.out.println("From Tpo getData is: " + getData);

                            } catch (ClassCastException cce) {
                                try {

                                    String mString = String.valueOf(dataMap.get(key));

                                    //   System.out.println("From Tpo mString is: " + mString);


                                } catch (ClassCastException cce2) {

                                }
                            }
                        }

                        System.out.println("From companyadmin getSpecificShortListedStudents: " + getSpecificShortListedStudents);

                        String val= "";

                        for (String s: getSpecificShortListedStudents) {
                            val+= s + " ";
                        }

                        System.out.println("From companyadmin val is: " + val);

                        map2= sdb.init(val);

                        System.out.println("From Companyadmin checkShortListedStudent() map2 is:" + map2);

                        lookupJobAdvertisements();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void lookupJobAdvertisements() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("PostJobAdvertisement");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    for (String key : dataMap.keySet()) {
                        Object data = dataMap.get(key);
                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // cQualification
                            PostJobAdvertisementData displaySpecificShortListedStudentData = new PostJobAdvertisementData((String) userData.get("cName"), (String) userData.get("logo"), (String) userData.get("cQualification"), (String) userData.get("cRequirement"), (String) userData.get("cJobLocation"), (String) userData.get("cKeySkills"), (String) userData.get("cYearsOfGap"), (String) userData.get("cTen"), (String) userData.get("cTwelve"), (String) userData.get("cGrad"), (String) userData.get("cCurrentAcademic"), (String) userData.get("referenceId"), (String) userData.get("key"), (String) userData.get("ctc"));
                            PostJobAdvertisementData.add(displaySpecificShortListedStudentData.getcName());
                            PostJobAdvertisementData.add(displaySpecificShortListedStudentData.getcTwelve());
                            PostJobAdvertisementData.add(displaySpecificShortListedStudentData.getcCurrentAcademic());
                            PostJobAdvertisementData.add(displaySpecificShortListedStudentData.getcGrad());
                            PostJobAdvertisementData.add(displaySpecificShortListedStudentData.getcTen());
                            PostJobAdvertisementData.add(displaySpecificShortListedStudentData.getcKeySkills());
                            PostJobAdvertisementData.add(displaySpecificShortListedStudentData.getKey());
                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                //   System.out.println("From Tpo mString is: " + mString);
                            } catch (ClassCastException cce2) {
                            }
                        }
                    }

                    System.out.println("From companyadmin lookupJobAdvertisements() PostJobAdvertisementData: " + PostJobAdvertisementData);

                    String val="";

                    for (String s: PostJobAdvertisementData) {
                        val+= s + " ";
                    }

                 //  map1= pjdb.init(val, user);

                    map1= pjdb.init(val, user);

                    System.out.println("From Companyadmin lookupJobAdvertisements() map1 is: " + map1);

                    String ten="", twelve="", grad="", currentacademic="";

                    for (Map.Entry<String, ArrayList<Set<String>>> e1: map1.entrySet()) {
                        ArrayList<Set<String>> val1= e1.getValue();
                        for (Set<String> s: val1) {
                            for (String s1: s) {
                                if (s1.contains("Twelvth:")) {
                                    s1= s1.replace("Twelvth:","").trim();
                                    twelve= s1;
                                }
                                if (s1.contains("Tenth:")) {
                                    s1= s1.replace("Tenth:","").trim();
                                    ten= s1;
                                }
                                if (s1.contains("CurrentAcademic:")) {
                                    s1= s1.replace("CurrentAcademic:","").trim();
                                    currentacademic= s1;
                                }
                                if (s1.contains("selectedGrad:")) {
                                    s1= s1.replace("selectedGrad:","").trim();
                                    grad= s1;
                                }
                            }
                            matchCriteria(ten, twelve, currentacademic, grad);
                        }
                    }

                    displayShortListedStudent();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayShortListedStudent() {
        System.out.println("From Companyadmin displayShortListedStudent shortListedStudents: " + shortListedStudents);
        Intent sendData= new Intent(Companyadmin.this, ShortListedStudentsFromCompany.class);
        sendData.putExtra("mapshortlistedstudents", (Serializable) shortListedStudents);
        startActivity(sendData);
    }

    private void matchCriteria(String ten1, String twelve1, String currentacademic1, String grad1) {
        System.out.println("From Companyadmin matchCriteria ten: " + ten1 + "\ttwelve: " + twelve1 + "\tcurrentacademic: " + currentacademic1 + "\tgrad: " + grad1);

        System.out.println("From Companyadmin matchCriteria map2: " + map2);

        boolean criteriaTen=false, criteriaTwelve=false, criteriaGrad= false, criteriaCurrentAcademic= false;

        for (Map.Entry<String, Set<String>> e1: map2.entrySet()) {
            Set<String> set1= e1.getValue();
            for (String s: set1) {
                if (s.contains("ten:")) {
                    s=s.replace("ten:","").trim();
                    System.out.println(s + "\t\t" + ten1.replace("%","").trim());
                    if (Integer.valueOf(s) >= Integer.valueOf(ten1.replace("%","").trim())) {
                        System.out.println("Criteria ten matched");
                        criteriaTen= true;
                    }
                    else {
                        criteriaTen= false;
                    }
                }
                if (s.contains("twelve:")) {
                    s= s.replace("twelve:","").trim();
                    System.out.println(s + "\t\t" + twelve1.replace("%","").trim());
                    if (Integer.valueOf(s) >= Integer.valueOf(twelve1.replace("%","").trim())) {
                        System.out.println("Criteria twelve matched");
                        criteriaTwelve= true;
                    }
                    else {
                        criteriaTwelve= false;
                    }
                }
                if (s.contains("graduation:")) {
                    s= s.replace("graduation:","").trim();
                    System.out.println(s + "\t\t" + grad1.replace("%","").trim());
                    if (Integer.valueOf(s) >= Integer.valueOf(grad1.replace("%","").trim())) {
                        System.out.println("Criteria graduation matched");
                        criteriaGrad= true;
                    }
                    else {
                        criteriaGrad= false;
                    }
                }
                if (s.contains("currentacademic:")) {
                    s= s.replace("currentacademic:","").trim();
                    System.out.println(s + "\t\t" + currentacademic1.replace("%","").trim());
                    if (Integer.valueOf(s) >= Integer.valueOf(currentacademic1.replace("%","").trim())) {
                        System.out.println("Criteria currentacademic matched");
                        criteriaCurrentAcademic= true;
                    }
                    else {
                        criteriaCurrentAcademic= false;
                    }
                }
            }
            if (criteriaTen && criteriaTwelve && criteriaGrad && criteriaCurrentAcademic) {
                shortListedStudents.put(e1.getKey(), e1.getValue());
                System.out.println("From Companyadmin matchCriteria() is: " + shortListedStudents);
                criteriaTen= false;
                criteriaTwelve= false;
                criteriaGrad= false;
                criteriaCurrentAcademic= false;
            }
            else {
                continue;
            }
        }
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