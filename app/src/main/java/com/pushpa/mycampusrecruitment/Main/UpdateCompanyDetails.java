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
import com.pushpa.mycampusrecruitment.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class UpdateCompanyDetails extends AppCompatActivity {

    EditText totalemployees, yearsofestablishment, location, achievents, total;

    TextView name;

    ImageView cig;

    Button changelogo, update;

    Uri imageUri, newImageUri;

    FirebaseDatabase database1;
    DatabaseReference ref1;

    FirebaseStorage storage;
    StorageReference storageReference;


    boolean photochanged= false;

    ArrayList<String> setToList= new ArrayList<>();

    String user="", key="", referenceId="", imagePath="", uriPath="", newUriPath="";

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION = 1;
    public static final int IMAGE_PICK_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company_details);

        name= (TextView) findViewById(R.id.company_name_id);
        yearsofestablishment= (EditText) findViewById(R.id.year_of_establishment_id);
        location= (EditText) findViewById(R.id.location_id);
        achievents= (EditText) findViewById(R.id.achievements_id);
        total= (EditText) findViewById(R.id.total_id);

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        database1= FirebaseDatabase.getInstance();

        cig= (ImageView) findViewById(R.id.cig_id);

        changelogo= (Button) findViewById(R.id.change_logo_id);
        update= (Button) findViewById(R.id.update_data_id);

        changelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder photo = new AlertDialog.Builder(UpdateCompanyDetails.this);
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  key= user + " " + reg.getText().toString().trim();

                ref1= database1.getInstance().getReference("AddCompanyDetails");

                if (photochanged) {
                    // addStudentDetailsData.setImageUrl(newUriPath);
                    ref1.child(key).child("cImage").setValue("uriPath: " + newUriPath);
                }
                else {
                    // addStudentDetailsData.setImageUrl(uriPath);
                    ref1.child(key).child("cImage").setValue("uriPath: " + imagePath.replace(",","").replace("#","").replace("$","").replace(",","").trim());
                }
                ref1.child(key).child("cAwardsAchievement").setValue("Acheivements: " + achievents.getText().toString().trim());
                ref1.child(key).child("cLocation").setValue("location: " + location.getText().toString().trim());
                ref1.child(key).child("cName").setValue("name1: " + name.getText().toString().trim());
                ref1.child(key).child("cTotal").setValue("total: " + total.getText().toString().trim());
                ref1.child(key).child("cYos").setValue("yos: " + yearsofestablishment.getText().toString().trim());
                ref1.child(key).child("key").setValue("key: " + key);
                ref1.child(key).child("referenceId").setValue(referenceId);
            }
        });

        Intent getData= getIntent();
        setToList= getData.getStringArrayListExtra("settolist");
        user= getData.getStringExtra("user");
        System.out.println("From UpdateCompanyDetails setToList: " + setToList);
        update();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UpdateCompanyDetails.this, Companyadmin.class));
        finishAffinity();
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(UpdateCompanyDetails.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(UpdateCompanyDetails.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UpdateCompanyDetails.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
        }
        else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues cv= new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "NEW PICTURE");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "FROM THE CAMERA");
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
                    Toast.makeText(UpdateCompanyDetails.this, "Permission required to click photo!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Toast.makeText(UpdateCompanyDetails.this, "Permission required to upload photo!", Toast.LENGTH_SHORT).show();
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
             //   Toast.makeText(UpdateCompanyDetails.this, "path is: " + path, Toast.LENGTH_SHORT).show();
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
    //    Toast.makeText(this, "Entered into uploadLogo()", Toast.LENGTH_SHORT).show();
        if (imageUri != null) {
         //   Toast.makeText(this, "imageUri: " + imageUri, Toast.LENGTH_SHORT).show();
            final StorageReference ref = storageReference.child("Company Profile Picture/" + UUID.randomUUID().toString());
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();
            try {
                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(UpdateCompanyDetails.this, "Profile pic uploaded successfully", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(UpdateCompanyDetails.this, "Uploaded Image url: " + uri + "", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(UpdateCompanyDetails.this, "Error while uploading profile pic", Toast.LENGTH_SHORT).show();
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

    private void update() {
        for (String s: setToList) {
            if (s.contains("1:")) {
                s= s.trim().replace("1:", "").trim();
                name.setText(s);
            }
            if (s.contains("yos:")) {
                s= s.replace("yos:", "Yoe:");
                yearsofestablishment.setText(s.replace("Yoe:",""));
            }
            if (s.contains("total:")) {
                total.setText(s.replace("total:",""));
            }
            if (s.contains("Acheivements:")) {
                achievents.setText(s.replace("Acheivements:",""));
            }
            if (s.contains("uriPath:")) {
                s= s.replace("uriPath:","").trim();
                imagePath= s;
                Glide.with(UpdateCompanyDetails.this).load(imagePath).into(cig);
            }
            if (s.contains("location:")) {
                location.setText(s.replace("location:",""));
            }
            if (s.contains("key:")) {
                key= s.replace("key:","").trim();
            }
            if (s.contains("referenceId")) {
                referenceId= s;
            }
        }
    }
}