package com.pushpa.mycampusrecruitment.Credentials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pushpa.mycampusrecruitment.CustomDialog.CompanyAdmin;
import com.pushpa.mycampusrecruitment.CustomDialog.TpoDialog;
import com.pushpa.mycampusrecruitment.Database.Passworddb;
import com.pushpa.mycampusrecruitment.Database.ReferenceIddb;
import com.pushpa.mycampusrecruitment.Database.Universitynamedb;
import com.pushpa.mycampusrecruitment.Database.Usernamedb;
import com.pushpa.mycampusrecruitment.Main.Companyadmin;
import com.pushpa.mycampusrecruitment.Main.Tpo;
import com.pushpa.mycampusrecruitment.Model.UploadDetails;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText emailid, password, confirmpassword, username, name11, address11, contactnumber;
    Spinner spin;
    TextView gotosignin;

    Button signup;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase database;
    DatabaseReference ref;

    UploadDetails upd;

    List<String> roles = new ArrayList<String>();

    String selectedRole="", referenceid="";

    ReferenceIddb rdb;

    UserStatus us;

    Passworddb pdb;

    Universitynamedb universitynamedb;

    Usernamedb usernamedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

      //  registerReceiver();

        upd= new UploadDetails();

        usernamedb= new Usernamedb(Signup.this);

        rdb= new ReferenceIddb(Signup.this);

        pdb= new Passworddb(Signup.this);

        us= new UserStatus(Signup.this);

        universitynamedb= new Universitynamedb(Signup.this);

        username= (EditText) findViewById(R.id.username_id);

        name11= (EditText) findViewById(R.id.name111_id);

        address11= (EditText) findViewById(R.id.address111_id);

        contactnumber= (EditText) findViewById(R.id.phone_id);

        firebaseAuth = FirebaseAuth.getInstance();

        emailid= (EditText) findViewById(R.id.email_id);
        password= (EditText) findViewById(R.id.password_id);
        confirmpassword= (EditText) findViewById(R.id.cnf_password_id);

        spin= (Spinner) findViewById(R.id.role_spinner_id);

        gotosignin= (TextView) findViewById(R.id.create_new_one_id);

        gotosignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, Signin.class));
                finish();
            }
        });

        signup= (Button) findViewById(R.id.signin_btn_id);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedRole.isEmpty() || !selectedRole.trim().equals("")) {
                    if (selectedRole.equals("Tpo")) {
                        tpofields1(name11.getText().toString().trim(), address11.getText().toString().trim(), contactnumber.getText().toString().trim());
                    }
                    else if (selectedRole.equals("Company admin")) {
                        companyadminfields1(name11.getText().toString().trim(), address11.getText().toString().trim(), contactnumber.getText().toString().trim());
                    }
                    signUp(selectedRole);
                }
                else {
                    Toast.makeText(Signup.this, "Fill all the details first to signup", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Spinner click listener
        spin.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        roles.add(0, "User type");
        roles.add(1,"Tpo");
        roles.add(2,"Company admin");
      //  roles.add(3,"Admin");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedRole= roles.get(position);

        if (selectedRole.equals("Tpo")) {
            name11.setHint("University name");
            address11.setHint("University address");
            contactnumber.setHint("University contact number");
        }

        else if (selectedRole.equals("Company admin")) {
            name11.setHint("Company name");
            address11.setHint("Company address");
            contactnumber.setHint("Company contact number");
        }

        Toast.makeText(this, "User has selected " + selectedRole, Toast.LENGTH_SHORT).show();

        if (selectedRole.equals("Tpo")) {
            if (!name11.getText().toString().trim().isEmpty() && !address11.getText().toString().trim().isEmpty() && !confirmpassword.getText().toString().trim().isEmpty()) {
                System.out.println("From onItemSelected() From Tpo name is: " + name11.getText().toString().trim() + " address is: " + address11.getText().toString().trim());
              //  tpofields1(name11.getText().toString().trim(), address11.getText().toString().trim(), confirmpassword.getText().toString().trim());
            }
        }

        else if (selectedRole.equals("Company admin")) {
            if (!name11.getText().toString().trim().isEmpty() && !address11.getText().toString().trim().isEmpty() && !confirmpassword.getText().toString().trim().isEmpty()) {
                System.out.println("From onItemSelected() from Company admin name is: " + name11.getText().toString().trim() + " address is: " + address11.getText().toString().trim());
              //  companyadminfields1(name11.getText().toString().trim(), address11.getText().toString().trim(), confirmpassword.getText().toString().trim());
            }
        }

        else if (selectedRole.equals("Admin")) {
           // openAdminDialog();
        }
    }

    private void openAdminDialog() {

    }

    private void openCompanyAdminDialog() {
        CompanyAdmin cd= new CompanyAdmin();
        cd.show(getSupportFragmentManager(), "Company Admin Details");
    }

    private void openTpoDialog() {
        TpoDialog td= new TpoDialog();
        td.show(getSupportFragmentManager(), "Tpo Details");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void tpofields1(String name1, String address1, String contact1) {
        System.out.println("From Signup tpo details are: "  + name11.getText().toString().trim() + " " + address1 + " " + contact1);
        SQLiteDatabase db1= universitynamedb.getWritableDatabase();
        String query1 = "select * from universityname";
        Cursor c11 = db1.rawQuery(query1, null);

        if (c11.getCount() > 0) {
            universitynamedb.delete();
            universitynamedb.insertData(name1);
        }
        else {
            universitynamedb.insertData(name1);
        }
        SQLiteDatabase db= rdb.getWritableDatabase();
        String query = "select * from referenceid";
        Cursor c1 = db.rawQuery(query, null);
        if (c1.getCount() < 1) {
            ref = database.getInstance().getReference().child("Tpo");
            String uid = UUID.randomUUID().toString();
            referenceid = password.getText().toString().trim() + " " + uid;
            rdb.insertData(referenceid);
            System.out.println("From tpoFields1() name is: " + name11.getText().toString().trim() + " address: " + address11.getText().toString().trim());
            upd.setName(name1);
            upd.setEmail(emailid.getText().toString().trim());
            upd.setPassword(password.getText().toString().trim());
            upd.setUsername(username.getText().toString().trim());
          //  Toast.makeText(this, "username: " + username + " password: " + password + " emailid: " + emailid, Toast.LENGTH_LONG).show();
            SQLiteDatabase db2= pdb.getWritableDatabase();
            String query2 = "select * from password";
            Cursor c12 = db2.rawQuery(query2, null);
            if (c12.getCount() > 0) {
                pdb.delete();
                pdb.insertData(password.getText().toString().trim());
            }
            else {
                pdb.insertData(password.getText().toString().trim());
            }
            upd.setAddress(address1);
            upd.setContact(contactnumber.getText().toString().trim());
            upd.setReferenceid(referenceid);
            upd.setRole(selectedRole);
            upd.setPassword(password.getText().toString().trim());
            upd.setEmail(emailid.getText().toString().trim());
            if (!username.getText().toString().trim().isEmpty()) {
                upd.setUsername(username.getText().toString().trim());
                SQLiteDatabase db11= usernamedb.getWritableDatabase();
                String query11 = "select * from username";
                Cursor c111 = db11.rawQuery(query11, null);
                if (c111.getCount() > 0) {
                    username.setError("Cannot create multiple username");
                }
                else {
                    usernamedb.insertData(username.getText().toString().trim());
                    upd.setUsername(username.getText().toString().trim());
                }
            }
            else {
                username.setError("Please input username");
            }
            ref.child(referenceid).setValue(upd);

         //   signUp(selectedRole);
        }
        else {
            Toast.makeText(this, "Multiple registration is prohibited here", Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp(final String selectedRole) {
        String getEmail= emailid.getText().toString().trim();
        String getPassword= password.getText().toString().trim();
        String getConfirmPassword= confirmpassword.getText().toString().trim();
        if (TextUtils.isEmpty(getEmail)) {
            emailid.setError("Please input your Email id!");
            return;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Please input your Password!");
            return;
        }
        if (TextUtils.isEmpty(getConfirmPassword)) {
            confirmpassword.setError("Please input this confirm password field!");
            return;
        }
        if (!getPassword.equals(getConfirmPassword)) {
            confirmpassword.setError("Passwords donot match, please try again!");
            return;
        }
        if (!isValidEmail(getEmail)) {
            emailid.setError("Please input valid email!");
            return;
        }
        if (getPassword.length()< 6) {
            password.setError("Password length must be > 5");
        }
        if (getConfirmPassword.length()< 6) {
            password.setError("Incorrect password! p;ease try again");
        }

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Signup.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                    if (selectedRole.equals("Tpo")) {
                        SQLiteDatabase db= us.getWritableDatabase();
                        String query = "select * from user";
                        Cursor c1 = db.rawQuery(query, null);
                        if (c1.getCount() < 1) {
                            us.insertData("Tpo");
                            Intent intent = new Intent(getApplicationContext(), Tpo.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Signup.this, "Multiple registration are not allowed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (selectedRole.equals("Company admin")) {
                        SQLiteDatabase db= us.getWritableDatabase();
                        String query = "select * from user";
                        Cursor c1 = db.rawQuery(query, null);
                        if (c1.getCount() < 1) {
                            us.insertData("Company admin");
                            Intent intent = new Intent(getApplicationContext(), Companyadmin.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Signup.this, "Multiple registration are not allowed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(Signup.this, "SignUp Failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Task failed due to: " + task.getException());
                }
                progressDialog.dismiss();
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public void companyadminfields1(String name1, String address1, String contact1) {
        System.out.println("From Signup company admin details are: "  + name1 + " " + address1 + " " + contact1);

        SQLiteDatabase db11= universitynamedb.getWritableDatabase();
        String query11 = "select * from universityname";
        Cursor c111 = db11.rawQuery(query11, null);

        if (c111.getCount() > 0) {
            universitynamedb.delete();
            universitynamedb.insertData(name1);
        }
        else {
            universitynamedb.insertData(name1);
        }

        SQLiteDatabase db= rdb.getWritableDatabase();
        String query = "select * from referenceid";
        Cursor c1 = db.rawQuery(query, null);
        if (c1.getCount() < 1) {
            ref = database.getInstance().getReference().child("CompanyAdmin");
            String uid = UUID.randomUUID().toString();
            referenceid = password.getText().toString().trim() + " " + uid;
            rdb.insertData(referenceid);
            upd.setName(name1);
            upd.setEmail(emailid.getText().toString().trim());
            upd.setPassword(password.getText().toString().trim());
            SQLiteDatabase db1= pdb.getWritableDatabase();
            String query1 = "select * from password";
            Cursor c11 = db1.rawQuery(query1, null);
            if (c11.getCount() > 0) {
                pdb.delete();
                pdb.insertData(password.getText().toString().trim());
            }
            else {
                pdb.insertData(password.getText().toString().trim());
            }
            upd.setAddress(address1);
            upd.setContact(contact1);
            upd.setReferenceid(referenceid);
         //   upd.setRole(selectedRole);
            upd.setRole(selectedRole);
            if (!username.getText().toString().trim().isEmpty()) {
                upd.setUsername(username.getText().toString().trim());
                SQLiteDatabase db111= usernamedb.getWritableDatabase();
                String query111 = "select * from username";
                Cursor c1111 = db111.rawQuery(query111, null);
                if (c111.getCount() > 0) {
                    username.setError("Cannot create multiple username");
                }
                else {
                    usernamedb.insertData(username.getText().toString().trim());
                }
            }
            else {
                username.setError("Please input username");
            }
            ref.child(referenceid).setValue(upd);
        }
        else {
            Toast.makeText(this, "Multiple registration is prohibited here", Toast.LENGTH_SHORT).show();
        }
    }
}