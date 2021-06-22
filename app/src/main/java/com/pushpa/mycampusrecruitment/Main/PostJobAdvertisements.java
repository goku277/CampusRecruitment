package com.pushpa.mycampusrecruitment.Main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pushpa.mycampusrecruitment.Database.Universitynamedb;
import com.pushpa.mycampusrecruitment.Model.PostJobAdvertisementData;
import com.pushpa.mycampusrecruitment.Model.UploadDetails;
import com.pushpa.mycampusrecruitment.Model.UploadShortListedStudentsDetailsData;
import com.pushpa.mycampusrecruitment.Processor.getJobPostData;
import com.pushpa.mycampusrecruitment.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostJobAdvertisements extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name, qual, req, jobloc, keyskills, gap, exp_ctc;

    Spinner ten, twelve, grad, current_academic;

    CircleImageView cig;

    Uri imageUri;

    Button upload1;

    String selectedTen="", selectedTwelve="", selectedGrad="", selectedCurrentAcademic="";

    List<String> tens = new ArrayList<String>();

    List<String> twelves = new ArrayList<String>();

    List<String> grads = new ArrayList<String>();

    List<String> currentAcademics = new ArrayList<String>();

    FirebaseDatabase database1;
    DatabaseReference ref1;

    FirebaseStorage storage;
    StorageReference storageReference;


    PostJobAdvertisementData postJobAdvertisementData;

    ArrayList<String> receiveTpoDetails= new ArrayList<>();

    ArrayList<String> receiveShortListedStudentsContactNumber= new ArrayList<>();

    ArrayList<String> receiveShortListedStudentsJobRequirements= new ArrayList<>();

    ArrayList<String> receiveTenth= new ArrayList<>();
    ArrayList<String> receiveTwelvth= new ArrayList<>();
    ArrayList<String> receiveGradd= new ArrayList<>();
    ArrayList<String> receiveAcademic= new ArrayList<>();
    ArrayList<String> receiveRequirements= new ArrayList<>();

    ArrayList<ArrayList<String>> ArrayListOfArrayLists= new ArrayList<ArrayList<String>>();


    getJobPostData getJobPostData1;

    private static final String SMS_SEND_ACTION = "CTS_SMS_SEND_ACTION";
    private static final String SMS_DELIVERY_ACTION = "CTS_SMS_DELIVERY_ACTION";


    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    public static final int IMAGE_PICK_CODE = 2;

    String uriPath="";

    Universitynamedb universitynamedb;

    String user="";

    ArrayList<String> receiveJobPostDetails= new ArrayList<>();

    ArrayList<ArrayList<String>> receiveJobPostData= new ArrayList<ArrayList<String>>();

    ArrayList<String> stdTen= new ArrayList<>();
    ArrayList<String> stdTwelve= new ArrayList<>();
    ArrayList<String> stdGrad= new ArrayList<>();
    ArrayList<String> stdAcademic= new ArrayList<>();
    ArrayList<String> stdContact= new ArrayList<>();

    Map<String, String> matchingMap= new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_advertisements);

        requestSmsPermission();

        getJobPostData1= new getJobPostData();

        database1= FirebaseDatabase.getInstance();

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        universitynamedb= new Universitynamedb(PostJobAdvertisements.this);

        SQLiteDatabase db= universitynamedb.getWritableDatabase();
        String query = "select * from universityname";
        Cursor c1 = db.rawQuery(query, null);

        if (c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                user= c1.getString(0);
            }
        }

        System.out.println("From PostJobAdvertisements Company name is: " + user);

        postJobAdvertisementData= new PostJobAdvertisementData();

        ten= (Spinner) findViewById(R.id.ten_id);
        twelve= (Spinner) findViewById(R.id.twelve_id);
        grad= (Spinner) findViewById(R.id.graduation_id);
        current_academic= (Spinner) findViewById(R.id.current_academic_id);

        name= (EditText) findViewById(R.id.company_name);
        qual = (EditText) findViewById(R.id.qualification_id);
        req = (EditText) findViewById(R.id.requirements_id);
        jobloc = (EditText) findViewById(R.id.job_location_id);
        keyskills= (EditText) findViewById(R.id.key_skills_id);
        gap= (EditText) findViewById(R.id.gap_id);
        exp_ctc= (EditText) findViewById(R.id.expected_ctc_id);


        cig = (CircleImageView) findViewById(R.id.company_logo_id);

        cig.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       AlertDialog.Builder photo = new AlertDialog.Builder(PostJobAdvertisements.this);
                                       photo.setTitle("Use appropriate actions");
                                       photo.setMessage("Upload or Click your profile photo!\n\n");
                                       photo.setPositiveButton("Click photo", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialogInterface, int i) {
                                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                   askCameraPermission();
                                               }
                                           }
                                       });
                                       photo.setNeutralButton("Upload photo", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialogInterface, int i) {
                                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                   if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                                       requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION);
                                                   } else {
                                                       pickFromGallery();
                                                   }
                                               } else {
                                                   pickFromGallery();
                                               }
                                           }
                                       });
                                       AlertDialog a1 = photo.create();
                                       a1.show();

                                   }
                               });


        upload1= (Button) findViewById(R.id.upload_id);

        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    String n= name.getText().toString().trim();
                    String q = qual.getText().toString().trim();
                    String r = req.getText().toString().trim();
                    String j = jobloc.getText().toString().trim();
                    String k = keyskills.getText().toString().trim();
                    String g = gap.getText().toString().trim();
                    String exp= exp_ctc.getText().toString().trim();
                    System.out.println("From PostJobAdvertisements " + n + " " + q + " " + r + " " + j + " " + k + " " + g + " " + selectedTen + " " + selectedTwelve + " " +
                            selectedGrad + " " + selectedCurrentAcademic);

                    if (imageUri!=null) {

                        uploadTextsData(n, q, r, j, k, g, selectedTen, selectedTwelve, selectedGrad, selectedCurrentAcademic, uriPath, exp);
                    }
                }
                else {
                    Toast.makeText(PostJobAdvertisements.this, "Please fill up all the necessary details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ten.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        tens.add(0, "10th qualifying criteria");
        tens.add(1, "50%");
        tens.add(2, "55%");
        tens.add(3, "60%");
        tens.add(4, "65%");
        tens.add(5, "70%");
        tens.add(6, "75%");
        tens.add(7, "80%");

        twelve.setOnItemSelectedListener(this);

        twelves.add(0, "12th qualifying criteria");
        twelves.add(1, "50%");
        twelves.add(2, "55%");
        twelves.add(3, "60%");
        twelves.add(4, "65%");
        twelves.add(5, "70%");
        twelves.add(6, "75%");
        twelves.add(7, "80%");

        grad.setOnItemSelectedListener(this);

        grads.add(0, "Gradiation qualifying criteria");
        grads.add(1, "50%");
        grads.add(2, "55%");
        grads.add(3, "60%");
        grads.add(4, "65%");
        grads.add(5, "70%");
        grads.add(6, "75%");
        grads.add(7, "80%");

        current_academic.setOnItemSelectedListener(this);

        currentAcademics.add(0, "Current Academic qualifying criteria");
        currentAcademics.add(1, "50%");
        currentAcademics.add(2, "55%");
        currentAcademics.add(3, "60%");
        currentAcademics.add(4, "65%");
        currentAcademics.add(5, "70%");
        currentAcademics.add(6, "75%");
        currentAcademics.add(7, "80%");

        //  roles.add(3,"Admin");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tens);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, twelves);

        ArrayAdapter<String> dataAdapter11 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, grads);

        ArrayAdapter<String> dataAdapter22 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currentAcademics);

        ten.setAdapter(dataAdapter);
        twelve.setAdapter(dataAdapter1);
        grad.setAdapter(dataAdapter11);
        current_academic.setAdapter(dataAdapter22);


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTen= tens.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        twelve.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTwelve= twelves.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dataAdapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        grad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGrad= grads.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataAdapter22.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        current_academic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCurrentAcademic= currentAcademics.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // attaching data adapter to spinner
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(PostJobAdvertisements.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(PostJobAdvertisements.this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PostJobAdvertisements.this,new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, 100);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PostJobAdvertisements.this, Companyadmin.class));
        finishAffinity();
    }

    private void uploadLogo() {
        if (imageUri != null) {
            final StorageReference ref = storageReference.child("Company Logo/" + UUID.randomUUID().toString());

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();

            try {

                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(PostJobAdvertisements.this, "Profile pic uploaded successfully", Toast.LENGTH_SHORT).show();

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(PostJobAdvertisements.this, "Uploaded Image url: " + uri + "", Toast.LENGTH_SHORT).show();
                                uriPath = uri + "";
                                System.out.println("uriPath is: " + uriPath);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(PostJobAdvertisements.this, "Error while uploading profile pic", Toast.LENGTH_SHORT).show();
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


    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(PostJobAdvertisements.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(PostJobAdvertisements.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PostJobAdvertisements.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "NEW PICTURE");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "FROM TEH CAMERA");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(PostJobAdvertisements.this, "Permission required to click photo!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Toast.makeText(PostJobAdvertisements.this, "Permission required to upload photo!", Toast.LENGTH_SHORT).show();

                    //  pickFromGallery();
                }
            }
        }
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                //  circleImageView.setImageBitmap(image);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = "";
                //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                path = MediaStore.Images.Media.insertImage(getContentResolver(), image, "IMG_" + Calendar.getInstance().getTime(), null);
             //   Toast.makeText(PostJobAdvertisements.this, "path is: " + path, Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = Uri.parse(path);
                    //  imageUri = data.getData();
                    //  imageUri = data.getData();
                    cig.setImageURI(imageUri);
                    uploadLogo();
                }
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                //  cig.setImageURI(imageUri);
                    imageUri = data.getData();
                    cig.setImageURI(imageUri);
                    uploadLogo();
            }
        }
        if (requestCode == IMAGE_PICK_CODE) {
            try {
                cig.setImageURI(data.getData());
                imageUri = data.getData();
                cig.setImageURI(imageUri);
                uploadLogo();
            } catch (Exception e) {
            }
        }
    }

    private void uploadTextsData(String n, String q, String r, String j, String k, String g, String selectedTen, String selectedTwelve, String selectedGrad, String selectedCurrentAcademic, String imageUri, String exp) {

        String referenceId= UUID.randomUUID().toString(), key= n + " " + referenceId;

        postJobAdvertisementData.setcCurrentAcademic("CurrentAcademic:" +selectedCurrentAcademic);
        postJobAdvertisementData.setLogo("imageUri: "+imageUri);
        postJobAdvertisementData.setcGrad("selectedGrad: "+ selectedGrad);
        postJobAdvertisementData.setcJobLocation("j: "+ j);
        postJobAdvertisementData.setcName("Name: "+ n);
        postJobAdvertisementData.setcKeySkills("KeySkills: "+ k);
        postJobAdvertisementData.setcQualification("Qualification: " + q);
        postJobAdvertisementData.setcRequirement("Requirements: " + r);
        postJobAdvertisementData.setcYearsOfGap("Gap: "+ g);
        postJobAdvertisementData.setcTen("Tenth: " + selectedTen);
        postJobAdvertisementData.setcTwelve("Twelvth: " + selectedTwelve);
        postJobAdvertisementData.setReferenceId("ReferenceId: " + referenceId);
        postJobAdvertisementData.setKey("Key: " + key);
        postJobAdvertisementData.setCtc("Expected CTC: " + exp);

        ref1= database1.getInstance().getReference().child("PostJobAdvertisement");
        ref1.child(key).setValue(postJobAdvertisementData);

        notifyAllTpos();

        notifyAllShortListedStudents();

        AlertDialog.Builder a11= new AlertDialog.Builder(PostJobAdvertisements.this);
        a11.setTitle("Acknowledgement message");
        a11.setMessage("You have successfully uploaded data");
        a11.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog a1= a11.create();
        a1.show();
    }

    // Need to modify this method to extract out the correct order of shortlisted students...

    private void notifyAllShortListedStudents() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("PostJobAdvertisement");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // Qualification

                            PostJobAdvertisementData getTpoDetails1 = new PostJobAdvertisementData((String) userData.get("cName"), (String) userData.get("logo"), (String) userData.get("cQualification"), (String) userData.get("cRequirement"), (String) userData.get("cJobLocation"), (String) userData.get("cKeySkills"), (String) userData.get("cYearsOfGap"), (String) userData.get("cTen"),
                                    (String) userData.get("cTwelve"), (String) userData.get("cGrad"),  (String) userData.get("cCurrentAcademic"), (String) userData.get("referenceId"), (String) userData.get("key"),(String) userData.get("ctc"));

                            receiveJobPostDetails.add(getTpoDetails1.getcName());
                            receiveJobPostDetails.add(getTpoDetails1.getLogo());
                            receiveJobPostDetails.add(getTpoDetails1.getcQualification());
                            receiveJobPostDetails.add(getTpoDetails1.getcRequirement());
                            receiveJobPostDetails.add(getTpoDetails1.getcJobLocation());
                            receiveJobPostDetails.add(getTpoDetails1.getcKeySkills());
                            receiveJobPostDetails.add(getTpoDetails1.getcYearsOfGap());
                            receiveJobPostDetails.add(getTpoDetails1.getcTen());
                            receiveJobPostDetails.add(getTpoDetails1.getcTwelve());
                            receiveJobPostDetails.add(getTpoDetails1.getcGrad());
                            receiveJobPostDetails.add(getTpoDetails1.getcCurrentAcademic());
                            receiveJobPostDetails.add(getTpoDetails1.getReferenceId());
                            receiveJobPostDetails.add(getTpoDetails1.getKey());
                            receiveJobPostDetails.add(getTpoDetails1.getCtc());

                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                System.out.println("From PostJobAdvetisements notifyAll()Tpos() mString is: " + mString);
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("From PostJobAdvertisements  receiveJobPostDetails: " +  receiveJobPostDetails);

                    String val1= "";

                    for (String s: receiveJobPostDetails) {
                        val1+= s + " ";
                    }

                    System.out.println("From PostJobAdvertisements vali1 is: " + val1);

                    receiveJobPostData= getJobPostData1.init(val1, user);

                    System.out.println("From PostJObAdvertisements receiveJobPostData: " + receiveJobPostData);

                    receiveTenth= receiveJobPostData.get(0);
                    receiveTwelvth= receiveJobPostData.get(1);
                    receiveGradd= receiveJobPostData.get(2);
                    receiveAcademic= receiveJobPostData.get(3);
                    receiveRequirements= receiveJobPostData.get(4);

                    System.out.println("From PostJobAdvertisements receiveTenth: " + receiveTenth + "\treceiveTwelvth: " + receiveTwelvth + "\treceiveGradd: " + receiveGradd + "\treceiveAcademic: " + receiveAcademic + "\treceiveRequirements: " + receiveRequirements);

                    String ten111= receiveTenth.get(0), twelve111= receiveTwelvth.get(0), gradd111= receiveGradd.get(0), academic111= receiveAcademic.get(0), req111= receiveRequirements.get(0);

                    extractShortListedStudents(ten111, twelve111, gradd111, academic111, req111);

                    System.out.println("From PostJobAdvertisements matchingMap: " + matchingMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void extractShortListedStudents(final String ten111, final String twelve111, final String gradd111, final String academic111, final String req111) {
        System.out.println("notifyAllShortListedStudents() initiated...");
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Upload ShortListed Student Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // Qualification

                            UploadShortListedStudentsDetailsData getTpoDetails = new UploadShortListedStudentsDetailsData((String) userData.get("university"), (String) userData.get("registration"), (String) userData.get("imageurl"), (String) userData.get("ten"), (String) userData.get("twelve"), (String) userData.get("graduation"), (String) userData.get("currentacademic"), (String) userData.get("keyskills"),
                                    (String) userData.get("companiesselected"), (String) userData.get("contactNumber"), (String) userData.get("jobrequirement"));

                            stdTen.add(getTpoDetails.getTen());
                            stdTwelve.add(getTpoDetails.getTwelve());
                            stdGrad.add(getTpoDetails.getGraduation());
                            stdAcademic.add(getTpoDetails.getCurrentacademic());
                            stdContact.add(getTpoDetails.getContactNumber());

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                System.out.println("From PostJobAdvetisements notifyAll()Tpos() mString is: " + mString);
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("From PostJobAdvertisements extractShortListedStudents ten111: " + ten111 + "\ttwelve111: " + twelve111 + "\tgradd111: " +
                            gradd111 + "\tacademic111: " + academic111 + "\treq111: " + req111);

                    System.out.println("From PostJobAdvertisements extractShortListedStudents stdTen: " + stdTen + "\tstdTwelve: " + stdTwelve + "\tstdGrad: " +
                            stdGrad + "\tstdAcademic: " + stdAcademic + "\tstdContact: " + stdContact);



                    for (int i=0, i1=0, i2=0, i3=0, i4=0; (i < stdTen.size() && i1 < stdTwelve.size() && i2 < stdGrad.size() && i3 < stdAcademic.size() && i4 < stdContact.size()); i++, i1++, i2++, i3++, i4++) {
                        if (Integer.parseInt(stdTen.get(i).replace("ten:","").trim()) >= Integer.parseInt(ten111) && Integer.parseInt(stdTwelve.get(i1).replace("twelve:","").trim()) >= Integer.parseInt(twelve111) &&
                            Integer.parseInt(stdGrad.get(i2).replace("graduation:","").trim()) >= Integer.parseInt(gradd111) && Integer.parseInt(stdAcademic.get(i3).replace("currentacademic:","").trim()) >= Integer.parseInt(academic111)) {
                            matchingMap.put(stdContact.get(i4), req111);
                        }
                    }

                    System.out.println("From PostJobAdvertisements extractShortListedStudents matchingMap: " + matchingMap);

                    Set<String> fetchMobileNumber= new LinkedHashSet<>();
                    Set<String> fetchRequirements= new LinkedHashSet<>();

                    if (matchingMap!=null || matchingMap.size()!=0) {

                        for (Map.Entry<String, String> e1 : matchingMap.entrySet()) {
                            fetchMobileNumber.add(e1.getKey());
                            fetchRequirements.add(e1.getValue().replace("Requirements:", "").trim());
                        }

                        ArrayList<String> setToList1 = new ArrayList<>();
                        ArrayList<String> setToList11 = new ArrayList<>();

                        setToList1.addAll(fetchMobileNumber);
                        setToList11.addAll(fetchRequirements);

                        String extractRequiremnets = "";

                        try {
                            extractRequiremnets= setToList11.get(0);
                        }
                        catch (Exception e){
                            Toast.makeText(PostJobAdvertisements.this, "Desired candidates haven't been found", Toast.LENGTH_LONG).show();
                        }

                        System.out.println("From PostJobAdvertisements extractShortListedStudents extractRequirements: " + extractRequiremnets);

                        for (String s : setToList1) {
                            SmsManager sm = SmsManager.getDefault();

                            IntentFilter sendIntentFilter = new IntentFilter(SMS_SEND_ACTION);
                            IntentFilter receiveIntentFilter = new IntentFilter(SMS_DELIVERY_ACTION);

                            PendingIntent sentPI = PendingIntent.getBroadcast(PostJobAdvertisements.this, 0, new Intent(SMS_SEND_ACTION), 0);
                            PendingIntent deliveredPI = PendingIntent.getBroadcast(PostJobAdvertisements.this, 0, new Intent(SMS_DELIVERY_ACTION), 0);

                            BroadcastReceiver messageSentReceiver = new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    switch (getResultCode()) {
                                        case Activity.RESULT_OK:
                                            Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
                                            break;
                                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                            Toast.makeText(context, "Generic failure", Toast.LENGTH_SHORT).show();
                                            break;
                                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                                            Toast.makeText(context, "No service", Toast.LENGTH_SHORT).show();
                                            break;
                                        case SmsManager.RESULT_ERROR_NULL_PDU:
                                            Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show();
                                            break;
                                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                                            Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            };

                            try {

                                registerReceiver(messageSentReceiver, sendIntentFilter);

                                BroadcastReceiver messageReceiveReceiver = new BroadcastReceiver() {
                                    @Override
                                    public void onReceive(Context arg0, Intent arg1) {
                                        switch (getResultCode()) {
                                            case Activity.RESULT_OK:
                                                Toast.makeText(PostJobAdvertisements.this, "SMS Delivered", Toast.LENGTH_SHORT).show();
                                                break;
                                            case Activity.RESULT_CANCELED:
                                                Toast.makeText(PostJobAdvertisements.this, "SMS Not Delivered", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }
                                };
                                registerReceiver(messageReceiveReceiver, receiveIntentFilter);
                                String message1 = "Congratulations, you have been selected for the post " + extractRequiremnets + "\nRegards,\n" + user;
                                ArrayList<String> parts = sm.divideMessage(message1);
                                ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
                                ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
                                for (int i = 0; i < parts.size(); i++) {
                                    sentIntents.add(PendingIntent.getBroadcast(PostJobAdvertisements.this, 0, new Intent(SMS_SEND_ACTION), 0));
                                    deliveryIntents.add(PendingIntent.getBroadcast(PostJobAdvertisements.this, 0, new Intent(SMS_DELIVERY_ACTION), 0));
                                }
                                sm.sendMultipartTextMessage(s, null, parts, sentIntents, deliveryIntents);
                            } catch (Exception e) {
                            }
                        }
                    }
                    else {
                        Toast.makeText(PostJobAdvertisements.this, "ShortListed students haven't found yet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void notifyAllTpos() {
        System.out.println("notifyAllTpos() initiated...");
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Tpo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();
                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);

                        try {
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;         // Qualification

                            UploadDetails getTpoDetails = new UploadDetails((String) userData.get("name"), (String) userData.get("email"), (String) userData.get("address"), (String) userData.get("contact"), (String) userData.get("password"), (String) userData.get("referenceid"), (String) userData.get("role"), (String) userData.get("username"));

                            receiveTpoDetails.add(getTpoDetails.getContact());

                            //   System.out.println("From Tpo getData is: " + getData);

                        } catch (ClassCastException cce) {
                            try {
                                String mString = String.valueOf(dataMap.get(key));
                                System.out.println("From PostJobAdvetisements notifyAll()Tpos() mString is: " + mString);
                            } catch (ClassCastException cce2) {

                            }
                        }
                    }

                    System.out.println("From notifyAllTpos() receiveUniversityDetails: " + receiveTpoDetails);

                    String val1= "";

                    String message="Hello greetings from " + user + ". " + user + " has posted a new job requirement, kindly please check it.";

                    System.out.println("message is: " + message);

                    Set<String> listToSet= new LinkedHashSet<>();

                    listToSet.addAll(receiveTpoDetails);


                    for (String s : listToSet) {
                        SmsManager sm = SmsManager.getDefault();

                        IntentFilter sendIntentFilter = new IntentFilter(SMS_SEND_ACTION);
                        IntentFilter receiveIntentFilter = new IntentFilter(SMS_DELIVERY_ACTION);

                        PendingIntent sentPI = PendingIntent.getBroadcast(PostJobAdvertisements.this, 0, new Intent(SMS_SEND_ACTION), 0);
                        PendingIntent deliveredPI = PendingIntent.getBroadcast(PostJobAdvertisements.this, 0, new Intent(SMS_DELIVERY_ACTION), 0);

                        BroadcastReceiver messageSentReceiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                switch (getResultCode()) {
                                    case Activity.RESULT_OK:
                                        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
                                        break;
                                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                        Toast.makeText(context, "Generic failure", Toast.LENGTH_SHORT).show();
                                        break;
                                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                                        Toast.makeText(context, "No service", Toast.LENGTH_SHORT).show();
                                        break;
                                    case SmsManager.RESULT_ERROR_NULL_PDU:
                                        Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show();
                                        break;
                                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                                        Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        };

                        try {

                            registerReceiver(messageSentReceiver, sendIntentFilter);

                            BroadcastReceiver messageReceiveReceiver = new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context arg0, Intent arg1) {
                                    switch (getResultCode()) {
                                        case Activity.RESULT_OK:
                                            Toast.makeText(PostJobAdvertisements.this, "SMS Delivered", Toast.LENGTH_SHORT).show();
                                            break;
                                        case Activity.RESULT_CANCELED:
                                            Toast.makeText(PostJobAdvertisements.this, "SMS Not Delivered", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            };
                            registerReceiver(messageReceiveReceiver, receiveIntentFilter);
                            String message1= "Hello greetings from " + user + ".\n" + user + " has posted a new job requirement, kindly please check it";
                            ArrayList<String> parts = sm.divideMessage(message1);
                            ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
                            ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
                            for (int i = 0; i < parts.size(); i++) {
                                sentIntents.add(PendingIntent.getBroadcast(PostJobAdvertisements.this, 0, new Intent(SMS_SEND_ACTION), 0));
                                deliveryIntents.add(PendingIntent.getBroadcast(PostJobAdvertisements.this, 0, new Intent(SMS_DELIVERY_ACTION), 0));
                            }
                            sm.sendMultipartTextMessage(s, null, parts, sentIntents, deliveryIntents);
                        } catch (Exception e) {
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean check() {
        return qual.getText().toString().trim()!=null || !qual.getText().toString().trim().equals("") &&
                req.getText().toString().trim()!=null || !req.getText().toString().trim().equals("") &&
                jobloc.getText().toString().trim()!=null || !jobloc.getText().toString().trim().equals("") &&
                keyskills.getText().toString().trim()!=null || !keyskills.getText().toString().trim().equals("") &&
                gap.getText().toString().trim()!=null || !gap.getText().toString().trim().equals("") &&
                exp_ctc.getText().toString().trim()!=null || !exp_ctc.getText().toString().trim().equals("") &&
                selectedTen!=null && selectedTwelve!=null  && selectedGrad!= null && selectedCurrentAcademic!=null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedTen= tens.get(position);
        selectedTwelve= twelves.get(position);
        selectedGrad= grads.get(position);
        selectedCurrentAcademic= currentAcademics.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}