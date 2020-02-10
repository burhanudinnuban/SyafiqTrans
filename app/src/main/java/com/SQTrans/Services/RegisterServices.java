package com.SQTrans.Services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SQTrans.ModelData.DataUser;
import com.SQTrans.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class RegisterServices extends AppCompatActivity {

    protected EditText etNotelepon, etEmail, etnamaLengkap, etPassword, etPasswordConfirm;
    protected Button btnRegister;
    protected CheckBox chkRegister;
    protected TextView tvSnK, tvLogin;
    protected String strNoTelepon, strEmail, strNamaLengkap, strPassword, strPasswordConfirm;
    protected RelativeLayout parent;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register_services );
        database = FirebaseDatabase.getInstance().getReference();
        progress = new ProgressDialog( this );
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        etNotelepon = findViewById( R.id.etNoTelepon );
        etEmail = findViewById( R.id.etEmail );
        etnamaLengkap = findViewById( R.id.etNamaLengkap );
        etPassword = findViewById( R.id.etPassword );
        etPasswordConfirm = findViewById( R.id.etPasswordConfirm );
        btnRegister = findViewById( R.id.btnRegister );
        chkRegister = findViewById( R.id.chkSK_Regist );
        tvSnK = findViewById( R.id.tvReadSK_Regist );
        parent = findViewById( R.id.parent );
        tvLogin = findViewById( R.id.tvLogin );

        tvLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), LoginServices.class );
                startActivity( intent );
            }
        } );

        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strNoTelepon = etNotelepon.getText().toString().trim();
                strEmail = etEmail.getText().toString().trim();
                strNamaLengkap = etnamaLengkap.getText().toString().trim();
                strPassword = etPassword.getText().toString().trim();
                strPasswordConfirm = etPasswordConfirm.getText().toString().trim();

                if (strNoTelepon.length() == 0) {
                    etNotelepon.setError( "Nomor Telepon Diperlukan." );
                    etNotelepon.requestFocus();
                } else if (strNoTelepon.length() <= 9) {
                    etNotelepon.setError( "minimal nomor telepon 10 angka." );
                    etNotelepon.requestFocus();
                } else if (strNamaLengkap.length() == 0) {
                    etnamaLengkap.setError( "Nama Lengkap Diperlukan." );
                    etnamaLengkap.requestFocus();
                } else if (strEmail.length() == 0) {
                    etEmail.setError( "Email Diperlukan." );
                    etEmail.requestFocus();
                } else if (strPassword.length() == 0) {
                    etPassword.setError( "Password Diperlukan." );
                    etPassword.requestFocus();
                } else if (strPassword.length() <= 7) {
                    etPassword.setError( "minimal password 7 karakter." );
                    etPassword.requestFocus();
                } else if (strPasswordConfirm.length() == 0) {
                    etPasswordConfirm.setError( "Password Confirm Diperlukan." );
                    etPasswordConfirm.requestFocus();
                } else if (!chkRegister.isChecked()) {
                    Snackbar snackbar = Snackbar.make( parent, "Harap Mencentang Syarat dan Ketentuan.", Snackbar.LENGTH_LONG );
                    snackbar.show();
                } else if (!strPassword.equals( strPasswordConfirm )) {
                    Snackbar snackbar = Snackbar.make( parent, "Password tidak sesuai, harap perisksa kembali.", Snackbar.LENGTH_LONG );
                    snackbar.show();
                } else if (isEmailValid( strEmail )) {
                    progress.setTitle( "Loading" );
                    progress.setMessage( "Wait while loading..." );
                    progress.setCancelable( false ); // disable dismiss by tapping outside of the dialog
                    progress.show();
                    Toast.makeText( getApplicationContext(), "Sukses", Toast.LENGTH_SHORT ).show();
                    DataUser dataUser = new DataUser( strNamaLengkap, strEmail, strNoTelepon );
                    auth.createUserWithEmailAndPassword( strEmail, strPassword );
                    auth.createUserWithEmailAndPassword( strEmail, strPassword ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar snackbar = Snackbar.make( parent, "Pendaftaran gagal. Harap memasukkan data yang valid dan benar.", LENGTH_SHORT );
                            snackbar.show();
                            progress.dismiss();
                        }
                    } );
                    database.child( "data_user" ).push().setValue( dataUser ).addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent( getApplicationContext(), LoginServices.class );
                            startActivity( intent );
                            Snackbar snackbar = Snackbar.make( parent, "Registrasi Berhasil Silahkan Login.", LENGTH_SHORT );
                            snackbar.show();
                            progress.dismiss();
                            finish();
                        }
                    } );

                } else if (!isEmailValid( strEmail )) {
                    Snackbar snackbar = Snackbar.make( parent, "Email salah atau email tidak sesuai. \n Harap memasukkan email valid dan benar.", LENGTH_SHORT );
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make( parent, "Password salah silahkan coba lagi.", LENGTH_SHORT );
                    snackbar.show();
                }
            }
        } );
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile( regExpn, Pattern.CASE_INSENSITIVE );
        Matcher matcher = pattern.matcher( email );

        return matcher.matches();
    }
}
