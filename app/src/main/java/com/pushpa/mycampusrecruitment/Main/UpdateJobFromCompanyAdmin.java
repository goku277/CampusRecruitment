package com.pushpa.mycampusrecruitment.Main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pushpa.mycampusrecruitment.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UpdateJobFromCompanyAdmin extends AppCompatActivity implements View.OnClickListener {

    EditText qual, loc, gap, ten, twelve, currentacademic, ctc, keyskills;

    TextView name;

    ImageView cig;

    Button changelogo, update;

    Map<String, Set<String>> receiveJobUpdatedata= new LinkedHashMap<>();

    String user= "";

    Spinner spin;

    Uri imageUri, newImageUri;

    boolean logoChanged= false;

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    public static final int IMAGE_PICK_CODE = 2;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    String uriPath="", key="", sameUriPath="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_job_from_company_admin);

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        database1= FirebaseDatabase.getInstance();

        name= findViewById(R.id.company_name_id);
        qual= findViewById(R.id.qualification_id);
        loc= findViewById(R.id.location_id);
        keyskills= findViewById(R.id.keyskills_id);
        gap= findViewById(R.id.gap_id);
        ten= findViewById(R.id.tenth_id);
        twelve= findViewById(R.id.twelvthhh_id);
        currentacademic= findViewById(R.id.currentacademic_id);
        ctc= findViewById(R.id.ctc_id);

        cig= (ImageView) findViewById(R.id.cig_id);

        changelogo= findViewById(R.id.change_logo_id);
        update= findViewById(R.id.update_job_add_id);

        changelogo.setOnClickListener(this);
        update.setOnClickListener(this);


        spin= findViewById(R.id.choose_among_reference_ids_id);

        Intent getData= getIntent();
        receiveJobUpdatedata= (Map<String, Set<String>>) getData.getSerializableExtra("map1");
        user= getData.getStringExtra("user");
        System.out.println("From UpdateJobFromCompanyData receiveJobUpdateData is: " + receiveJobUpdatedata);

        ArrayList<String> spinnerData= new ArrayList<>();

        spinnerData.add(0, "Choose among the referenceIds");

        for (Map.Entry<String, Set<String>> e1: receiveJobUpdatedata.entrySet()) {
            spinnerData.add(e1.getKey());
        }

        ArrayAdapter<String> adp = new ArrayAdapter<String> (UpdateJobFromCompanyAdmin.this, android.R.layout.simple_spinner_dropdown_item,spinnerData);
        // APP CURRENTLY CRASHING HERE
        spin.setAdapter(adp);
        //Set listener Called when the item is selected in spinner
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3)
            {
                String choose = "You chose: " + parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), choose, Toast.LENGTH_LONG).show();
                if (!choose.contains("Choose among the referenceIds")) {
                    GetSetGo(choose);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });

        System.out.println("From UpdateJobFromCompanyData spinnerData is: " + spinnerData);
    }

    private void GetSetGo(String choose) {
        System.out.println("From GetSetGo() choose is: " + choose);

        Set<String> set1= receiveJobUpdatedata.get(choose.replace("You chose:","").trim());

        System.out.println("From GetSetGo() set1 is: " + set1);

        ArrayList<String> setToList= new ArrayList<>();

        setToList.addAll(set1);

        name.setText(setToList.get(0));

        qual.setText(setToList.get(2).replace("Qualification:",""));

        loc.setText(setToList.get(3).replace("j:",""));

        keyskills.setText(setToList.get(4).replace("KeySkills:",""));

        gap.setText(setToList.get(5).replace("Gap:",""));

        ten.setText(setToList.get(6).replace("Tenth:",""));

        twelve.setText(setToList.get(7).replace("Twelvth:",""));

        currentacademic.setText(setToList.get(8).replace("CurrentAcademic:",""));

        String split[]= setToList.get(10).split("\\s+");

        String concat= split[0] + " " + split[1] + " " + split[2];

        ctc.setText(concat.replace("Expected CTC:",""));

        key= setToList.get(9);

        Glide.with(UpdateJobFromCompanyAdmin.this).load(setToList.get(1).replace("imageUri:","").trim()).into(cig);

        sameUriPath= setToList.get(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_logo_id:
                AlertDialog.Builder photo = new AlertDialog.Builder(UpdateJobFromCompanyAdmin.this);
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
                break;
            case R.id.update_job_add_id:
                upload();
        }
    }

    private void upload() {
      // PostJobAdvertisement

        ref1= database1.getInstance().getReference("PostJobAdvertisement");

        System.out.println("From upload() key is: " + key);

        key = key.replace("Key:","").trim();

        if (logoChanged) {
            ref1.child(key).child("logo").setValue("imageUri: " + uriPath);
        }
        else {
            ref1.child(key).child("logo").setValue("imageUri: " + sameUriPath);
        }
        if (user.trim().equals(name.getText().toString().replace("Name: ","").trim())) {
            ref1.child(key).child("cCurrentAcademic").setValue("CurrentAcademic:" + currentacademic.getText().toString().trim());
            ref1.child(key).child("cQualification").setValue("Qualification: " + qual.getText().toString().trim());
            ref1.child(key).child("key").setValue("Key: "+ key);
            ref1.child(key).child("cKeySkills").setValue("KeySkills: " + keyskills.getText().toString().trim());
            ref1.child(key).child("cTen").setValue("Tenth: " + ten.getText().toString().trim());
            ref1.child(key).child("cTwelve").setValue("Twelvth: " + twelve.getText().toString().trim());
            String name1= name.getText().toString().replace("Name:","").trim();
            ref1.child(key).child("cName").setValue("Name: " + name1);
            ref1.child(key).child("cJobLocation").setValue("j:" + loc.getText().toString().trim());
            String ctc1= ctc.getText().toString().trim();
            ref1.child(key).child("ctc").setValue("Expected CTC: " + ctc1);
          //  ref1.child(key).child("cGrad").setValue("selectedGrad: " + );
            Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
        }
        else {
            name.setError("Cannot change company name");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UpdateJobFromCompanyAdmin.this, Companyadmin.class));
        finishAffinity();
    }

    private void uploadLogo() {
        if (newImageUri != null) {
            final StorageReference ref = storageReference.child("Student Profile Picture/" + UUID.randomUUID().toString());
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();
            try {
                ref.putFile(newImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(UpdateJobFromCompanyAdmin.this, "Company logo uploaded successfully", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(UpdateJobFromCompanyAdmin.this, "Uploaded Image url: " + uri + "", Toast.LENGTH_SHORT).show();
                                uriPath= uri + "";
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UpdateJobFromCompanyAdmin.this, "Error while uploading profile pic", Toast.LENGTH_SHORT).show();
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
        if (ContextCompat.checkSelfPermission(UpdateJobFromCompanyAdmin.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(UpdateJobFromCompanyAdmin.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UpdateJobFromCompanyAdmin.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
        }
        else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues cv= new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "NEW PICTURE");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "FROM TEH CAMERA");
        newImageUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
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
                    Toast.makeText(UpdateJobFromCompanyAdmin.this, "Permission required to click photo!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Toast.makeText(UpdateJobFromCompanyAdmin.this, "Permission required to upload photo!", Toast.LENGTH_SHORT).show();
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
        if (resultCode==RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                //  circleImageView.setImageBitmap(image);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = "";
                //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                path = MediaStore.Images.Media.insertImage(getContentResolver(), image, "IMG_" + Calendar.getInstance().getTime(), null);
            //    Toast.makeText(UpdateJobFromCompanyAdmin.this, "path is: " + path, Toast.LENGTH_SHORT).show();
                newImageUri= Uri.parse(path);
                newImageUri = data.getData();
                cig.setImageURI(newImageUri);
                logoChanged= true;
                uploadLogo();
            }
        }
        if (requestCode== IMAGE_PICK_CODE) {
            try {
                cig.setImageURI(data.getData());
                newImageUri = data.getData();
                cig.setImageURI(newImageUri);
                logoChanged= true;
                uploadLogo();
            } catch (Exception e){}
        }
    }
}