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
import com.pushpa.mycampusrecruitment.Model.AddUniversityDetailsData;
import com.pushpa.mycampusrecruitment.R;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.UUID;

public class AddUniversityDetails extends AppCompatActivity {

    ImageView cig;

    EditText name, loc, Id, branches, year;

    Button upload_logo, uploadDetails;

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

    String user="";

    AddUniversityDetailsData addUniversityDetailsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_university_details);

        cig= (ImageView) findViewById(R.id.university_logo_id);
        name= (EditText) findViewById(R.id.university_name_id);

        loc= (EditText) findViewById(R.id.location_id);
        Id= (EditText) findViewById(R.id.university_id);

        branches= (EditText) findViewById(R.id.branches_available_id);
        year= (EditText) findViewById(R.id.year_of_establishment_id);

        upload_logo= (Button) findViewById(R.id.upload_university_logo_id);
        uploadDetails= (Button) findViewById(R.id.upload_data_id);

        addUniversityDetailsData= new AddUniversityDetailsData();

        database1= FirebaseDatabase.getInstance();

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference();

        Intent getData= getIntent();

        user= getData.getStringExtra("user");

        upload_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder photo = new AlertDialog.Builder(AddUniversityDetails.this);
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

        uploadDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAllDetails();
            }
        });
    }

    private void uploadAllDetails() {
        addUniversityDetailsData.setBranchesavailable("branches: "+ branches.getText().toString().replace(","," ").trim());
        addUniversityDetailsData.setImageuri("uriPath: " + uriPath);
        addUniversityDetailsData.setUniversityid("universityId: "+ Id.getText().toString().trim());
        addUniversityDetailsData.setUniversitylocation("location: "+ loc.getText().toString().replace(","," ").trim());
        addUniversityDetailsData.setUniversityname("universityName: " + user.replace(","," "));
        addUniversityDetailsData.setYearofestablishment("year: " + year.getText().toString().trim());

        String key= user + " " + Id.getText().toString().trim();
        addUniversityDetailsData.setKey("key: "+ key);

        ref1= database1.getInstance().getReference().child("Upload University Details");
        ref1.child(key).setValue(addUniversityDetailsData);

        Toast.makeText(this, "University details added successfully", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(AddUniversityDetails.this, Tpo.class));
        finish();
    }

    private void upload() {
       /* addUniversityDetailsData.setBranchesavailable(branches.getText().toString().replace(","," ").trim());
        addUniversityDetailsData.setImageuri(uriPath);
        addUniversityDetailsData.setUniversityid(Id.getText().toString().trim());
        addUniversityDetailsData.setUniversitylocation(loc.getText().toString().replace(","," ").trim());
        addUniversityDetailsData.setUniversityname(user.replace(","," "));
        addUniversityDetailsData.setYearofestablishment(year.getText().toString().trim());

        String key= user + " " + Id.getText().toString().trim();
        addUniversityDetailsData.setKey(key);

        ref1= database1.getInstance().getReference().child("Upload University Details");
        ref1.child(key).setValue(addUniversityDetailsData);    */
    }


    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(AddUniversityDetails.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(AddUniversityDetails.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddUniversityDetails.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
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
                    Toast.makeText(AddUniversityDetails.this, "Permission required to click photo!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Toast.makeText(AddUniversityDetails.this, "Permission required to upload photo!", Toast.LENGTH_SHORT).show();

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
           //     Toast.makeText(AddUniversityDetails.this, "path is: " + path, Toast.LENGTH_SHORT).show();
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
            final StorageReference ref = storageReference.child(user + " University Logo Upload/" + UUID.randomUUID().toString());
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();
            try {
                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(AddUniversityDetails.this, "Profile pic uploaded successfully", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(AddUniversityDetails.this, "Uploaded Image url: " + uri + "", Toast.LENGTH_SHORT).show();
                                uriPath = uri + "";
                                System.out.println("uriPath is: " + uriPath);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(AddUniversityDetails.this, "Error while uploading profile pic", Toast.LENGTH_SHORT).show();
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