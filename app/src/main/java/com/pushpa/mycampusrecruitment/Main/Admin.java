package com.pushpa.mycampusrecruitment.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.pushpa.mycampusrecruitment.CustomDialog.VerifyCompany;
import com.pushpa.mycampusrecruitment.CustomDialog.VerifyUniversity;
import com.pushpa.mycampusrecruitment.Database.Passworddb;
import com.pushpa.mycampusrecruitment.Database.ReferenceIddb;
import com.pushpa.mycampusrecruitment.Processor.ViewCompanyData;
import com.pushpa.mycampusrecruitment.Processor.viewUniversitiesdata;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Admin extends AppCompatActivity implements View.OnClickListener, VerifyUniversity.companyadminlistener, VerifyCompany.companyadminlistener1 {

    private static final String TAG = "Admin";
    Passworddb pdb;
    ReferenceIddb rdb;
    String password="";

    FirebaseDatabase database;
    DatabaseReference ref;

    ImageView verify, delete;
    TextView verify_text, delete_text;

    ArrayList<String> receiveUniversityDetails= new ArrayList<>();

    Map<String, Set<String>> receiveViewUniversityData= new LinkedHashMap<>();

    viewUniversitiesdata universitiesdata;

    ArrayList<String> receiveCompanyDetails= new ArrayList<>();

    ViewCompanyData viewCompanyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        universitiesdata= new viewUniversitiesdata();

        viewCompanyData= new ViewCompanyData();

        pdb = new Passworddb(Admin.this);
        rdb= new ReferenceIddb(Admin.this);

        database= FirebaseDatabase.getInstance();

        verify= (ImageView) findViewById(R.id.check_all_users_img_id);
        verify_text= (TextView) findViewById(R.id.check_all_users_text_id);

        delete= (ImageView) findViewById(R.id.delete_users_img_id);
        delete_text= (TextView) findViewById(R.id.delete_users_text_id);

        verify.setOnClickListener(this);
        verify_text.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_text.setOnClickListener(this);

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

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Admin/"+ password).orderByChild("referenceid").equalTo(password);

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
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                            Toast.makeText(Admin.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_all_users_img_id:
                verify();
                break;
            case R.id.check_all_users_text_id:
                verify();
                break;
            case R.id.delete_users_img_id:
                delete();
                break;
            case R.id.delete_users_text_id:
                delete();
                break;
        }
    }

    private void delete() {
        AlertDialog.Builder a1= new AlertDialog.Builder(Admin.this);
        a1.setTitle("Delete users");
        a1.setMessage("You can either select Company or University to delete at once");
        a1.setPositiveButton("Company", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openDialog1();
            }
        });
        a1.setNegativeButton("University", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openDialog();
            }
        });
        AlertDialog a11= a1.create();
        a11.show();
    }

    private void openDialog1() {
        VerifyCompany vc= new VerifyCompany();
        vc.show(getSupportFragmentManager(), "Verify Company");
    }

    private void verify() {
        AlertDialog.Builder a1= new AlertDialog.Builder(Admin.this);
        a1.setTitle("Verify users");
        a1.setMessage("You can either select Company or University to verify at once");
        a1.setPositiveButton("Company", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openDialog1();
            }
        });
        a1.setNegativeButton("University", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                verifyUniversity();
            }
        });
        AlertDialog a11= a1.create();
        a11.show();
    }

    private void verifyUniversity() {
        openDialog();
    }

    private void openDialog() {
        VerifyUniversity vf= new VerifyUniversity();
        vf.show(getSupportFragmentManager(), "VerifyUniversity");
    }

    @Override
    public void companyadminfields(String name1, String address1, String contact1) {

    }

    @Override
    public void companyadminfields1(String name1, String address1, String contact1) {

    }
}