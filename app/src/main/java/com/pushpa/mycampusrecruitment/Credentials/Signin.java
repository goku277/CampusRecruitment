package com.pushpa.mycampusrecruitment.Credentials;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pushpa.mycampusrecruitment.Database.Profile;
import com.pushpa.mycampusrecruitment.Database.ReferenceIddb;
import com.pushpa.mycampusrecruitment.Database.Usernamedb;
import com.pushpa.mycampusrecruitment.Main.Admin;
import com.pushpa.mycampusrecruitment.Main.Companyadmin;
import com.pushpa.mycampusrecruitment.Main.Tpo;
import com.pushpa.mycampusrecruitment.R;

public class Signin extends AppCompatActivity implements View.OnClickListener {

    TextView signupnow;

    EditText email, password;
    Button signIn;
    TextView goToSignUp;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth, mAuth;

    EditText username;

    Button google;

    UserStatus us;

    Usernamedb usernamedb;

    String usernameMatch="";



    ReferenceIddb rdb;

    String status= "";

    FirebaseUser user1;

    TextView forgetPassword;

    private final static int RC_SIGN_IN= 123;

    @Override
    protected void onStart() {
        super.onStart();
        user1= firebaseAuth.getCurrentUser();
        if (user1!=null) {
                SQLiteDatabase db= us.getWritableDatabase();
                String query = "select * from user";
                Cursor c1 = db.rawQuery(query, null);
                if (c1!=null && c1.getCount() > 0) {
                    if (c1.moveToFirst()) {
                        if (c1.getString(0).equals("Tpo")) {
                          //  Intent intent = new Intent(getApplicationContext(), Tpo.class);
                            Intent intent = new Intent(getApplicationContext(), Tpo.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (c1.getString(0).equals("Company admin")) {
                          //  Intent intent = new Intent(getApplicationContext(), Companyadmin.class);
                            Intent intent = new Intent(getApplicationContext(), Companyadmin.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (c1.getString(0).equals("Admin")) {
                            Intent intent = new Intent(getApplicationContext(), Admin.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        usernamedb= new Usernamedb(Signin.this);

        username= (EditText) findViewById(R.id.username_id);

        forgetPassword= (TextView) findViewById(R.id.forget_password_id);

        forgetPassword.setOnClickListener(this);

        signupnow= (TextView) findViewById(R.id.create_new_one_id);

        signupnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this, Signup.class));
            }
        });

        rdb= new ReferenceIddb(Signin.this);

      //  rdb.delete();

        us= new UserStatus(Signin.this);

     //   us.delete();

        progressDialog= new ProgressDialog(this);

        firebaseAuth= FirebaseAuth.getInstance();

        mAuth= FirebaseAuth.getInstance();

        email= (EditText) findViewById(R.id.email_id);
        password= (EditText) findViewById(R.id.password_id);
        signIn= (Button) findViewById(R.id.signin_btn_id);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        goToSignUp= (TextView) findViewById(R.id.create_new_one_id);
     //   signIn.setOnClickListener(this);
        goToSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_btn_id:
                Login();
                break;
            case R.id.create_new_one_id:
                startActivity(new Intent(this, Signup.class));
                break;
            case R.id.forget_password_id:
                reset();
                break;
        }
    }

    private void reset() {
        AlertDialog.Builder a11= new AlertDialog.Builder(Signin.this);
        a11.setTitle("Reset password");
        a11.setMessage("Are you sure to reset password");
        a11.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder a10= new AlertDialog.Builder(Signin.this);
                a10.setTitle("Provide your email id");
                a10.setCancelable(false);
                a10.setMessage("Please provide your email id to receive password reset link");
                a10.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog a101= a10.create();
                a101.show();
            }
        });

        a11.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog a1= a11.create();
        a1.show();

        String Email= email.getText().toString().trim();
        if (TextUtils.isEmpty(Email) || !isValidEmail(Email)) {
            email.setError("Please enter emailid to receive reset link");
        }
        else {
            firebaseAuth.sendPasswordResetEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Signin.this, "Password reset link is sent to your emailid", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Signin.this, "Error sending reset link due to: " + e.getMessage(), Toast.LENGTH_LONG).show();

                    System.out.println("Error sending reset link due to:" + e.getMessage());
                }
            });
        }
    }

    private void Login() {
        String getEmail= email.getText().toString().trim();
        String getPassword= password.getText().toString().trim();
        if (TextUtils.isEmpty(getEmail)) {
            email.setError("Please input your Email id!");
            return;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("Please input your Password!");
            return;
        }
        if (!isValidEmail(getEmail)) {
            email.setError("Please input valid email!");
            return;
        }
        if (getPassword.length()< 6) {
            password.setError("Password length must be > 5");
        }

        if (getEmail.trim().equals("pushpabose726@gmail.com") && getPassword.trim().equals("Im@12345678")) {
            Toast.makeText(this, "Hello Admin Welcome to the University recruitment system", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Admin.class);
            startActivity(intent);
            finish();
        }

        SQLiteDatabase db= us.getWritableDatabase();
        String query = "select * from user";
        Cursor c1 = db.rawQuery(query, null);

        if (c1!= null && c1.getCount() > 0) {
            if (c1.moveToFirst()) {
                status= c1.getString(0);
                System.out.println("From Signin Login() userStatus user is: " + status);
            }
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.signInWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    if (status!=null) {
                          //  if (user1!=null) {
                                if (status.equals("Tpo")) {
                                    System.out.println("From Signin Login() UserStatus is: " + status);
                                    SQLiteDatabase db11= usernamedb.getWritableDatabase();
                                    String query11 = "select * from username";
                                    Cursor c111 = db11.rawQuery(query11, null);
                                    if (c111!=null && c111.getCount() > 0) {
                                        if (c111.moveToFirst()) {
                                            usernameMatch= c111.getString(0);
                                            if (!usernameMatch.equals(username.getText().toString().trim())) {
                                                username.setError("Please input correct username");
                                            }
                                        }
                                    }
                                    if (usernameMatch.equals(username.getText().toString().trim())) {
                                        Intent intent = new Intent(getApplicationContext(), Tpo.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else if (status.equals("Company admin")) {
                                    System.out.println("From Signin Login() UserStatus is: " + status);

                                    SQLiteDatabase db11= usernamedb.getWritableDatabase();
                                    String query11 = "select * from username";
                                    Cursor c111 = db11.rawQuery(query11, null);
                                    if (c111!=null && c111.getCount() > 0) {
                                        if (c111.moveToFirst()) {
                                            usernameMatch= c111.getString(0);
                                            if (!usernameMatch.equals(username.getText().toString().trim())) {
                                                username.setError("Please input correct username");
                                            }
                                        }
                                    }
                                    if (usernameMatch.equals(username.getText().toString().trim())) {
                                        Intent intent = new Intent(getApplicationContext(), Tpo.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    Intent intent = new Intent(getApplicationContext(), Companyadmin.class);
                                    startActivity(intent);
                                    finish();
                                } else if (status.equals("Admin")) {
                                    System.out.println("From Signin Login() UserStatus is: " + status);
                                    Intent intent = new Intent(getApplicationContext(), Admin.class);
                                    startActivity(intent);
                                    finish();
                                }
                          //  }
                        }
                    else {
                        startActivity(new Intent(Signin.this, Profile.class));
                        finish();
                    }
                    }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(Signin.this, "Signin unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}