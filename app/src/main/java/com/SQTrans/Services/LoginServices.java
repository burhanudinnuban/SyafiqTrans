package com.SQTrans.Services;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.SQTrans.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class LoginServices extends AppCompatActivity {

    protected EditText etEmail, etPassword;
    protected Button btnLogin, btnLupaPassword;
    protected TextView tvLupaPassword, tvRegister;
    protected String strEmail, strPassword;
    private FirebaseAuth auth;
    private Dialog dialog;
    private RelativeLayout parent;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_services);
        progress = new ProgressDialog(this);
        dialog = new Dialog(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        parent = findViewById(R.id.parent);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvLupaPassword = findViewById(R.id.tvForgotPass);
        tvRegister = findViewById(R.id.tvRegister);

//        // Here, thisActivity is the current activity
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Permission is not granted
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                Toast.makeText(this, "Membutuhkan Izin Lokasi", Toast.LENGTH_SHORT).show();
//            } else {
//
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                        1);
//            }
//        } else {
//            // Permission has already been granted
//            Toast.makeText(this, "Izin Lokasi diberikan", Toast.LENGTH_SHORT).show();
//        }

        tvLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lupaPassword();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterServices.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPassword = etPassword.getText().toString().trim();
                strEmail = etEmail.getText().toString().trim();
                if (strEmail.length() == 0) {
                    etEmail.setError("Email Diperlukan.");
                    etEmail.requestFocus();
                } else if (strEmail.length() <= 5) {
                    etEmail.setError("email tidak memenuhi syarat.");
                    etEmail.requestFocus();
                } else if (strPassword.length() == 0) {
                    etPassword.setError("Password Diperlukan.");
                    etPassword.requestFocus();
                } else if (strPassword.length() <= 7) {
                    etPassword.setError("minimal password 7 karakter.");
                    etPassword.requestFocus();
                }else {
                    progress.setTitle("Loading");
                    progress.setMessage("Wait while loading...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                    auth.signInWithEmailAndPassword(strEmail, strPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            btnLogin.setEnabled( false );
                            Toast.makeText(LoginServices.this, "Selamat Datang" + " " + strEmail, Toast.LENGTH_SHORT).show();
                            finish();
                            progress.dismiss();
                        }
                    });
                    auth.signInWithEmailAndPassword(strEmail, strPassword).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar snackbar = Snackbar.make(parent, "Password atau Email salah silahkan coba lagi.", LENGTH_SHORT);
                            snackbar.show();
                            progress.dismiss();
                        }
                    });
                }
            }
        });
    }

    public void lupaPassword() {
        dialog.setContentView(R.layout.lupa_password);
        etEmail = dialog.findViewById(R.id.etEmail);
        btnLupaPassword = dialog.findViewById(R.id.btnLupaPassword);

        btnLupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = etEmail.getText().toString().trim();
                if (strEmail.length() == 0) {
                    etEmail.setError("Email Diperlukan.");
                    etEmail.requestFocus();
                } else if (!isEmailValid(strEmail)) {
                    Snackbar snackbar = Snackbar.make(parent, "Email salah atau email tidak sesuai. \n Harap memasukkan email valid dan benar.", LENGTH_SHORT);
                    snackbar.show();
                } else {
                    progress.setTitle("Loading");
                    progress.setMessage("Wait while loading...");
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                    auth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LoginServices.this, "Password telah dikirim ke email :" + " " + strEmail, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            progress.dismiss();
                        }
                    });
                    auth.sendPasswordResetEmail(strEmail).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar snackbar = Snackbar.make(parent, "Email tidak terdaftar. Silahkan coba lagi.", LENGTH_SHORT);
                            snackbar.show();
                            progress.dismiss();
                        }
                    });
                }
            }
        });

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher( email );

        return matcher.matches();
    }

    @Override
    protected void onPostResume() {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // User is logged in
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        super.onPostResume();
    }
}
