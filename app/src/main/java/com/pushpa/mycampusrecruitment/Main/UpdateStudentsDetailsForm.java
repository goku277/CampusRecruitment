package com.pushpa.mycampusrecruitment.Main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pushpa.mycampusrecruitment.Database.Universitynamedb;
import com.pushpa.mycampusrecruitment.Model.AddStudentDetailsData;
import com.pushpa.mycampusrecruitment.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UpdateStudentsDetailsForm extends AppCompatActivity {

    private static final String TAG = "UpdateStudentDetails";
    Universitynamedb udb;

    String universityname="";

    Map<String, Set<String>> mapRegToDetsils;
    Map<String, Set<String>> mapRegToKey;

    EditText currentacademic, grad, ten, twelve, keyskills, contact, studentname, dob, emailid, semester, branch;

    TextView name, reg;

    ImageView cig;

    Button changephoto, update, getDetails;

    String user="";

    ArrayList<String> studentDetails= new ArrayList<>();

    Uri imageUri;

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    public static final int IMAGE_PICK_CODE = 2;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    FirebaseStorage storage;
    StorageReference storageReference;

    String uriPath="", newUriPath="";

    AddStudentDetailsData addStudentDetailsData;

    boolean photochanged= false;

    String user11, grad1, currentacademicc, ten1, twelve1, keyskills1, reg11;

    boolean updateshortlisted= false;

    ArrayList<String> regno1= new ArrayList<>();

    boolean updateStudentDetails= false;

    @Override
    protected void onStart() {
        super.onStart();
        if (!updateStudentDetails) {
            startActivity(new Intent(UpdateStudentsDetailsForm.this, AddStudentDetails.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_students_details_form);

        mapRegToDetsils= new LinkedHashMap<>();
        mapRegToKey= new LinkedHashMap<>();

        addStudentDetailsData= new AddStudentDetailsData();

        udb= new Universitynamedb(UpdateStudentsDetailsForm.this);

        database1= FirebaseDatabase.getInstance();

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        name= (TextView) findViewById(R.id.university_name_id);

        reg= (TextView) findViewById(R.id.registration_number_id);

        currentacademic= (EditText) findViewById(R.id.current_academic_id);

        cig= (ImageView) findViewById(R.id.image_id);

        changephoto= (Button) findViewById(R.id.change_pic_id);

        changephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder photo = new AlertDialog.Builder(UpdateStudentsDetailsForm.this);
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

        contact= (EditText) findViewById(R.id.contact_id);

        studentname= (EditText) findViewById(R.id.student_name_id);

        dob= (EditText) findViewById(R.id.dob_id);

        emailid= (EditText) findViewById(R.id.emailid);

        semester= (EditText) findViewById(R.id.semester_id);

        branch= (EditText) findViewById(R.id.branch_id);

        grad= (EditText) findViewById(R.id.graduation_id);

        ten= (EditText) findViewById(R.id.ten_id);

        twelve= (EditText) findViewById(R.id.twelvth_id);

        keyskills= (EditText) findViewById(R.id.keyskills_id);

        changephoto= (Button) findViewById(R.id.change_pic_id);

        update= (Button) findViewById(R.id.update_id);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("From UpdateStudentsDetailsForm uriPath is: " + uriPath);
           //     Toast.makeText(UpdateStudentsDetailsForm.this, "From UpdateStudentsDetailsForm uriPath is: " + uriPath, Toast.LENGTH_SHORT).show();

                String key1= user + " " + reg.getText().toString().trim();

                String key= user + " " + reg.getText().toString().replace("universityId:","").trim();

                ref1= database1.getInstance().getReference("Upload Student Details");

                if (photochanged) {
                   // addStudentDetailsData.setImageUrl(newUriPath);
                    ref1.child(key).child("imageUrl").setValue("uriPath: " + newUriPath);
                }
                else {
                   // addStudentDetailsData.setImageUrl(uriPath);
                    ref1.child(key).child("imageUrl").setValue("uriPath: " + uriPath);
                }

                addStudentDetailsData.setUniversityName(user);
                addStudentDetailsData.setTwelve(twelve.getText().toString().trim());
                addStudentDetailsData.setTen(ten.getText().toString().trim());
                addStudentDetailsData.setKeySkills(keyskills.getText().toString().trim());
                addStudentDetailsData.setGraduation(grad.getText().toString().trim());
                addStudentDetailsData.setCurrentAcademic(currentacademic.getText().toString().trim());
                addStudentDetailsData.setStudentRegistrationNumber(reg.getText().toString().trim());
                addStudentDetailsData.setSemester(semester.getText().toString().trim());
                addStudentDetailsData.setSelectedBranch(branch.getText().toString().trim());
                addStudentDetailsData.setEmailId(emailid.getText().toString().trim());
                addStudentDetailsData.setDob(dob.getText().toString().trim());
                addStudentDetailsData.setStudentName(studentname.getText().toString().trim());
                addStudentDetailsData.setContactNumber(contact.getText().toString().trim());


                ref1.child(key).child("currentAcademic").setValue("currentacademic: " + currentacademic.getText().toString().trim());
                ref1.child(key).child("graduation").setValue("graduation: " + grad.getText().toString().trim());
                ref1.child(key).child("key").setValue("key: " + key1);
                ref1.child(key).child("keySkills").setValue("keyskills: " + keyskills.getText().toString().trim());
                ref1.child(key).child("studentRegistrationNumber").setValue("studentregistrationnumber: " + reg.getText().toString().trim());
                ref1.child(key).child("ten").setValue("ten: " + ten.getText().toString().trim());
                ref1.child(key).child("twelve").setValue("twelve: " + twelve.getText().toString().trim());
                ref1.child(key).child("universityName").setValue("universityname:" + user);

                ref1.child(key).child("semester").setValue("Semester: " + semester.getText().toString().trim());
                ref1.child(key).child("studentName").setValue("studentname: " + semester.getText().toString().replace("Semester:","").trim());
                ref1.child(key).child("selectedBranch").setValue("selectedBranch: " + branch.getText().toString().trim());
                ref1.child(key).child("emailId").setValue("emailid: " + emailid.getText().toString().trim());
                ref1.child(key).child("dob").setValue("date: " + dob.getText().toString().trim());
                ref1.child(key).child("contactNumber").setValue("contactnumber: " + contact.getText().toString().trim());

                Toast.makeText(UpdateStudentsDetailsForm.this, "Data updated", Toast.LENGTH_SHORT).show();

              //  recreate();

                startActivity(new Intent(UpdateStudentsDetailsForm.this, Tpo.class));
                finish();
            }
        });

        getDetails= (Button) findViewById(R.id.get_details_btn_id);

        SQLiteDatabase db1= udb.getWritableDatabase();
        String query1 = "select * from universityname";
        Cursor c11 = db1.rawQuery(query1, null);

        if (c11.getCount() > 0) {
            if (c11.moveToFirst()) {
                universityname= c11.getString(0);
            }
        }

        System.out.println("universityname: " + universityname);

        Intent getData= getIntent();

        updateStudentDetails= getData.getExtras().getBoolean("updatestudent");

        if (!updateStudentDetails) {
            startActivity(new Intent(UpdateStudentsDetailsForm.this, AddStudentDetails.class));
            finish();
        }

        System.out.println("From UpdateStudentDetailsFrom onCreate() updateStudentDetails is: " + updateStudentDetails);

        mapRegToDetsils= (Map<String, Set<String>>) getData.getSerializableExtra("mapregtodetails");

        mapRegToKey= (Map<String, Set<String>>) getData.getSerializableExtra("mapregtokey");

        updateshortlisted= getData.getExtras().getBoolean("updateshortlisted");

        regno1= getData.getStringArrayListExtra("reg");

        user= getData.getStringExtra("users");

        System.out.println("From UpdateStudentsDetailsForm mapRegToDetsils: " + mapRegToDetsils);

        System.out.println("From UpdateStudentsDetailsForm mapRegToKey: " + mapRegToKey);

        getDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key1= user + " " + reg.getText().toString().trim();

              //  delete(key1);

                String getReg= reg.getText().toString().trim();
                for (Map.Entry<String, Set<String>> e1: mapRegToDetsils.entrySet()) {
                    String key =e1.getKey().replace("studentregistrationnumber:","").trim();
                    System.out.println("From UpdateStudentsDetailsForm: " + key + "\t\t" + getReg);
                    if (key.equals(getReg)) {
                        Set<String> val= e1.getValue();
                        studentDetails.addAll(val);
                        System.out.println("From UpdateStudentsDetailsForm studentDetails: " + studentDetails);
                        setData(studentDetails, getReg);
                        break;
                    }
                } }
        });
    }

    private void delete(String key) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Upload Student Details").child(key);
        ref.removeValue();

        Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
    }

    private void setData(ArrayList<String> studentDetails, String reg) {
        System.out.println("From UpdateStudentsDetailsForm setData() studentDetails: " + studentDetails);
        name.setText(user);
        ten.setText(studentDetails.get(0).replace("ten:","").trim());
        twelve.setText(studentDetails.get(1).replace("twelve:","").trim());
        grad.setText(studentDetails.get(2).replace("graduation:","").trim());
        currentacademic.setText(studentDetails.get(3).replace("currentacademic:","").trim());
        keyskills.setText(studentDetails.get(4).replace("keyskills:",""));
        contact.setText(studentDetails.get(9).replace("contactnumber:",""));
        studentname.setText(studentDetails.get(8).replace("studentname:","").replace("Semester:",""));
        String b1= studentDetails.get(11).substring(studentDetails.get(11).indexOf(studentDetails.get(11).charAt(0)), studentDetails.get(11).indexOf("date:"));
        branch.setText(b1.replace("selectedBranch:",""));
        String d1= studentDetails.get(10).substring(studentDetails.get(10).indexOf(studentDetails.get(10).charAt(0)), studentDetails.get(10).indexOf("emailid:"));
        d1= d1.substring(d1.indexOf("date:"));
        dob.setText(d1.replace("date:",""));
        String emailId= studentDetails.get(10).substring(studentDetails.get(10).indexOf("emailid:"));
        emailid.setText(emailId.replace("emailid:",""));
        String sem1= studentDetails.get(10).substring(studentDetails.get(10).indexOf(studentDetails.get(10).charAt(0)), studentDetails.get(10).indexOf("date:"));
        semester.setText(sem1.replace("Semester:",""));
        Glide.with(UpdateStudentsDetailsForm.this).load(studentDetails.get(5).replace("uriPath:","").trim()).centerCrop().into(cig);

        update(user, studentDetails.get(0).replace("ten:","").trim(),
                studentDetails.get(1).replace("twelve:","").trim(),
                studentDetails.get(2).replace("graduation:","").trim(),
                studentDetails.get(3).replace("currentacademic:","").trim(),
                studentDetails.get(4),
                studentDetails.get(5).replace("uriPath:","").trim(), reg);

                uriPath= "uriPath: " + studentDetails.get(5).replace("uriPath:","").trim();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UpdateStudentsDetailsForm.this, Tpo.class));
        finishAffinity();
    }

    private void update(String user, String ten, String twelve, String grad, String currentacademic, String keyskills, String uripath, String reg1) {
        boolean found= false;
        try {
            for (String s : regno1) {
                if (s.trim().equals(reg.getText().toString().trim())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                reg.setError("Please input any registration id from the shortlisted students!");
            }
            if (!user.trim().equals(name.getText().toString().replace("University:", "").trim())) {
                name.setError("University name cannot be changed!");
            }
            if (!reg1.trim().equals(reg.getText().toString().trim())) {
                reg.setError("Registration Id cannot be changed!");
            }
        } catch (Exception e) {}
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(UpdateStudentsDetailsForm.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(UpdateStudentsDetailsForm.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UpdateStudentsDetailsForm.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
        }
        else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues cv= new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "NEW PICTURE");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "FROM TEH CAMERA");
        imageUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                    Toast.makeText(UpdateStudentsDetailsForm.this, "Permission required to click photo!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Toast.makeText(UpdateStudentsDetailsForm.this, "Permission required to upload photo!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void pickFromGallery() {
        Intent intent= new Intent(Intent.ACTION_PICK);
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
            //    Toast.makeText(UpdateStudentsDetailsForm.this, "path is: " + path, Toast.LENGTH_SHORT).show();
                imageUri= Uri.parse(path);
                imageUri = data.getData();
                newUriPath= imageUri + "";
                cig.setImageURI(imageUri);
                uploadLogo();
            }
        }
        if (requestCode== IMAGE_PICK_CODE) {
            try {
                cig.setImageURI(data.getData());
                imageUri = data.getData();
                cig.setImageURI(imageUri);
                newUriPath= imageUri + "";
                uploadLogo();
            } catch (Exception e){}
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
                        Toast.makeText(UpdateStudentsDetailsForm.this, "Profile pic uploaded successfully", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(UpdateStudentsDetailsForm.this, "Uploaded Image url: " + uri + "", Toast.LENGTH_SHORT).show();
                               // uriPath = uri + "";
                                newUriPath = uri + "";
                                System.out.println("uriPath is: " + uriPath);
                                photochanged= true;
                             //   update(uriPath);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UpdateStudentsDetailsForm.this, "Error while uploading profile pic", Toast.LENGTH_SHORT).show();
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
}