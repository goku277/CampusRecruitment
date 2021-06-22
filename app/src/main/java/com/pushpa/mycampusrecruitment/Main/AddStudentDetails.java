package com.pushpa.mycampusrecruitment.Main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pushpa.mycampusrecruitment.Model.AddStudentDetailsData;
import com.pushpa.mycampusrecruitment.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStudentDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CircleImageView cig;
    EditText universityname, studentregistrationnumber, keyskills, contactnumber, studentname, emailid;
    ImageView image;

    TextView dob;
   // Spinner branch;
    Button upload;

    String date="";

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    public static final int IMAGE_PICK_CODE = 2;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    FirebaseStorage storage;
    StorageReference storageReference;

    Uri imageUri;

    String uriPath="";

    AddStudentDetailsData addStudentDetailsData;

    Spinner ten, twelve, grad, current_academic, branch;

    String selectedTen="", selectedTwelve="", selectedGrad="", selectedCurrentAcademic="";

    List<String> tens = new ArrayList<String>();

    List<String> twelves = new ArrayList<String>();

    List<String> grads = new ArrayList<String>();

    List<String> currentAcademics = new ArrayList<String>();

    List<String> branches= new ArrayList<>();

    String selectedBranch="";

    Spinner semester;

    String selectedSemester="";

    ArrayList<String> semesterList= new ArrayList<>();

    DatePickerDialog.OnDateSetListener setListener;

    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_details);

        universityname= (EditText) findViewById(R.id.university_name_id);
        dob= (TextView) findViewById(R.id.dob_text_id);
        studentregistrationnumber= (EditText) findViewById(R.id.student_registration_number_id);
        ten= (Spinner) findViewById(R.id.class_tenth_percentage_id);
        semester= (Spinner) findViewById(R.id.semester_id);
        branch= (Spinner) findViewById(R.id.branch_id);
        twelve= (Spinner) findViewById(R.id.class_twelvth_percentage_id);
        grad= (Spinner) findViewById(R.id.graduation_percentage_id);
        current_academic= (Spinner) findViewById(R.id.current_academic_percentage_id);
        keyskills= (EditText) findViewById(R.id.keyskills_id);

        image= (ImageView) findViewById(R.id.imageView);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                year= calendar.get(Calendar.YEAR);
                month= calendar.get(Calendar.MONTH);
                day= calendar.get(Calendar.DAY_OF_MONTH);
                System.out.println("From AddStudentDetails year is: " + year + " month: " + month + " day: " + day);

                DatePickerDialog datePickerDialog= new DatePickerDialog(AddStudentDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });

        setListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month= month+1;
                date= day + " / " + month + " / " + year;
                dob.setText(date);
            }
        };

        contactnumber= (EditText) findViewById(R.id.contact_number_id);
        studentname= (EditText) findViewById(R.id.student_name_id);

        emailid= (EditText) findViewById(R.id.email_id);

        addStudentDetailsData= new AddStudentDetailsData();

        database1= FirebaseDatabase.getInstance();

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        upload= (Button) findViewById(R.id.upload_student_details_btn_id);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriPath!=null) {
                    uploadTextData(universityname.getText().toString().trim(), studentregistrationnumber.getText().toString().trim(), selectedTen, selectedTwelve, selectedGrad, selectedCurrentAcademic, keyskills.getText().toString().trim(), uriPath);
                }
                else {
                    Toast.makeText(AddStudentDetails.this, "Upload student pic first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        semesterList.add(0, "Choose your current semester");
        semesterList.add(1, "1");
        semesterList.add(2, "2");
        semesterList.add(3, "3");
        semesterList.add(4, "4");
        semesterList.add(5, "5");
        semesterList.add(6, "6");
        semesterList.add(7, "7");
        semesterList.add(8, "8");
        semesterList.add(9, "9");
        semesterList.add(10, "10");

        semester.setOnItemSelectedListener(this);



        branches.add(0, "Select Branch");
        branches.add(1, "Bca");
        branches.add(2, "Mca");
        branches.add(3, "Btech");
        branches.add(4, "Mtech");
        branches.add(5, "Phd");
        branches.add(6, "Bcom");
        branches.add(7, "Msc");
        branches.add(8, "Mcom");

        branch.setOnItemSelectedListener(this);


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

        ArrayAdapter<String> dataAdapter111= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semesterList);

        ArrayAdapter<String> dataAdapter0= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branches);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tens);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, twelves);

        ArrayAdapter<String> dataAdapter11 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, grads);

        ArrayAdapter<String> dataAdapter22 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currentAcademics);

        semester.setAdapter(dataAdapter111);

        dataAdapter111.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        branch.setAdapter(dataAdapter0);

        ten.setAdapter(dataAdapter);
        twelve.setAdapter(dataAdapter1);
        grad.setAdapter(dataAdapter11);
        current_academic.setAdapter(dataAdapter22);

        dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSemester= semesterList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBranch = branches.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        cig= (CircleImageView) findViewById(R.id.stdent_profile_img_id);

        cig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder photo = new AlertDialog.Builder(AddStudentDetails.this);
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
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(AddStudentDetails.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(AddStudentDetails.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddStudentDetails.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "NEW PICTURE");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "FROM THE CAMERA");
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
                    Toast.makeText(AddStudentDetails.this, "Permission required to click photo!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Toast.makeText(AddStudentDetails.this, "Permission required to upload photo!", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(AddStudentDetails.this, "path is: " + path, Toast.LENGTH_SHORT).show();
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
              //  uploadTextData(universityname.getText().toString().trim(), studentregistrationnumber.getText().toString().trim(), ten.getText().toString().trim(), twelve.getText().toString().trim(), graduation.getText().toString().trim(), currentacademic.getText().toString().trim(), keyskills.getText().toString().trim(), uriPath);
            }
        }
        if (requestCode == IMAGE_PICK_CODE) {
            try {
                cig.setImageURI(data.getData());
                imageUri = data.getData();
                cig.setImageURI(imageUri);
                uploadLogo();
             //   uploadTextData(universityname.getText().toString().trim(), studentregistrationnumber.getText().toString().trim(), ten.getText().toString().trim(), twelve.getText().toString().trim(), graduation.getText().toString().trim(), currentacademic.getText().toString().trim(), keyskills.getText().toString().trim(), uriPath);
            } catch (Exception e) {
            }
        }
    }

    private void uploadLogo() {
        if (imageUri != null) {
            final StorageReference ref = storageReference.child("Student Profile Picture/" + UUID.randomUUID().toString());
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();
            try {
                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(AddStudentDetails.this, "Profile pic uploaded successfully", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(AddStudentDetails.this, "Uploaded Image url: " + uri + "", Toast.LENGTH_SHORT).show();
                                uriPath = uri + "";
                                System.out.println("uriPath is: " + uriPath);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddStudentDetails.this, "Error while uploading profile pic", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddStudentDetails.this, Tpo.class));
        finishAffinity();
    }

    private void uploadTextData(String universityname, String studentregistrationnumber, String ten, String twelve, String graduation, String currentacademic, String keyskills, String uriPath) {
        addStudentDetailsData.setCurrentAcademic("currentacademic: "+ currentacademic.replace("%",""));
        addStudentDetailsData.setGraduation("graduation: " + graduation.replace("%",""));
        addStudentDetailsData.setImageUrl("uriPath: " + uriPath);
        String key = universityname + " " + studentregistrationnumber;
        addStudentDetailsData.setKey("key: " + key);
        addStudentDetailsData.setKeySkills("keyskills: " + keyskills);
        addStudentDetailsData.setTen("ten: " + ten.replace("%",""));
        addStudentDetailsData.setTwelve("twelve: " + twelve.replace("%",""));
        addStudentDetailsData.setUniversityName("universityname:" + universityname);
        addStudentDetailsData.setStudentRegistrationNumber("studentregistrationnumber: " + studentregistrationnumber);
        addStudentDetailsData.setStudentName("studentname: " + studentname.getText().toString().trim());
        addStudentDetailsData.setContactNumber("contactnumber: " + contactnumber.getText().toString().trim());
        addStudentDetailsData.setEmailId("emailid: " + emailid.getText().toString().trim());
        addStudentDetailsData.setSelectedBranch("selectedBranch: " + selectedBranch);
        addStudentDetailsData.setDob("date: " + date);
        if (!selectedSemester.equals("Choose your current semester")) {
            addStudentDetailsData.setSemester("Semester: " + selectedSemester);
        }
        else Toast.makeText(this, "Please choose a correct semester", Toast.LENGTH_SHORT).show();
        ref1= database1.getInstance().getReference().child("Upload Student Details");
        ref1.child(key).setValue(addStudentDetailsData);
        Toast.makeText(this, "Data uploaded", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder a11= new AlertDialog.Builder(AddStudentDetails.this);
        a11.setTitle("Message");
        a11.setMessage("Data uploaded successfully");
        a11.setCancelable(false);
        a11.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog a1= a11.create();
        a1.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedSemester= semesterList.get(position);
        selectedBranch= branches.get(position);
        selectedTen= tens.get(position);
        selectedTwelve= twelves.get(position);
        selectedGrad= grads.get(position);
        selectedCurrentAcademic= currentAcademics.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}