package com.SQTrans.ui.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.SQTrans.R;
import com.SQTrans.Services.LoginServices;
import com.SQTrans.Services.RegisterServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class ProfileFragment extends Fragment {

    private Button btnGantiPassword;
    private Button btnGantiEmail;
    private Button btnLogin;
    private EditText etEmail, etEmailConfirm, etPassword, etPasswordConfirm;
    private String strEmail;
    private String strEmailConfirm;
    private String strPassword;
    private String strPasswordConfirm;
    private FirebaseAuth auth;
    private Dialog dialog;
    private RelativeLayout parent;
    private FirebaseUser user;
    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_notifications, container, false );

        TextView tvemail = root.findViewById( R.id.tvEmailUser );
        btnGantiPassword = root.findViewById( R.id.btnGantiPassword );
        btnGantiEmail = root.findViewById( R.id.btnGantiEmail );
        Button btnLogout = root.findViewById( R.id.btnLogout );
        Button btnHapusAkun = root.findViewById( R.id.btnHapusUser );
        parent = root.findViewById( R.id.parent );
        dialog = new Dialog( Objects.requireNonNull( getContext() ) );
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressBar = root.findViewById( R.id.pbLoading );
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        assert firebaseUser != null;
        String email = firebaseUser.getEmail();
        tvemail.setText( email );
        auth = FirebaseAuth.getInstance();

        btnGantiPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
            }
        } );

        btnGantiEmail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity1();
            }
        } );

        btnLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView( R.layout.login_activity );
                etEmail = dialog.findViewById( R.id.etEmail );
                etPassword = dialog.findViewById( R.id.etPassword );
                btnLogin = dialog.findViewById( R.id.btnLogin );

                progressBar = dialog.findViewById( R.id.pbLoading );
                btnLogin.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility( View.VISIBLE );
                        strEmail = etEmail.getText().toString().trim();
                        strPassword = etPassword.getText().toString().trim();
                        if (strEmail.length() == 0) {
                            etEmail.setError( "Email Diperlukan." );
                            etEmail.requestFocus();
                        } else if (strEmail.length() <= 5) {
                            etEmail.setError( "email tidak memenuhi syarat." );
                            etEmail.requestFocus();
                        } else if (strPassword.length() == 0) {
                            etPassword.setError( "Password Diperlukan." );
                            etPassword.requestFocus();
                        } else if (strPassword.length() <= 7) {
                            etPassword.setError( "minimal password 7 karakter." );
                            etPassword.requestFocus();
                        } else if (isEmailValid( strEmail )) {
                            Snackbar snackbar = Snackbar.make( parent, "Email salah atau email tidak sesuai. \n Harap memasukkan email valid dan benar.", LENGTH_SHORT );
                            snackbar.show();
                        } else {
                            auth.signInWithEmailAndPassword( strEmail, strPassword ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressBar.setVisibility( View.GONE );
                                    dialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder( getContext() )
                                            .setTitle( "Keluar Akun" )
                                            .setMessage( "Apakah Anda yakin!!!" )
                                            .setPositiveButton( "Keluar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                    progressBar.setVisibility( View.VISIBLE );
                                                    try {
                                                        progressBar.setVisibility( View.GONE );
                                                        auth.signOut();
                                                        Intent intent = new Intent( getActivity(), LoginServices.class );
                                                        startActivity( intent );
                                                        Objects.requireNonNull( getActivity() ).finish();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            } )
                                            .setNegativeButton( "Batal", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            } );
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                }
                            } );
                            auth.signInWithEmailAndPassword( strEmail, strPassword ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility( View.GONE );
                                    Snackbar snackbar = Snackbar.make( parent, "Password atau Email salah silahkan coba lagi.", LENGTH_SHORT );
                                    snackbar.show();
                                }
                            } );
                        }
                    }
                } );

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                    dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                    dialog.show();
                }
            }
        } );

        btnHapusAkun.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog.setContentView( R.layout.login_activity );
                    etEmail = dialog.findViewById( R.id.etEmail );
                    etPassword = dialog.findViewById( R.id.etPassword );
                    btnLogin = dialog.findViewById( R.id.btnLogin );
                    progressBar = dialog.findViewById( R.id.pbLoading );

                    btnLogin.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setVisibility( View.VISIBLE );
                            strEmail = etEmail.getText().toString().trim();
                            strPassword = etPassword.getText().toString().trim();
                            if (strEmail.length() == 0) {
                                etEmail.setError( "Email Diperlukan." );
                                etEmail.requestFocus();
                            } else if (strEmail.length() <= 5) {
                                etEmail.setError( "email tidak memenuhin syarat." );
                                etEmail.requestFocus();
                            } else if (strPassword.length() == 0) {
                                etPassword.setError( "Password Diperlukan." );
                                etPassword.requestFocus();
                            } else if (strPassword.length() <= 7) {
                                etPassword.setError( "minimal password 7 karakter." );
                                etPassword.requestFocus();
                            } else if (isEmailValid( strEmail )) {
                                Snackbar snackbar = Snackbar.make( parent, "Email salah atau email tidak sesuai. \n Harap memasukkan email valid dan benar.", LENGTH_SHORT );
                                snackbar.show();
                            } else {
                                auth.signInWithEmailAndPassword( strEmail, strPassword ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        progressBar.setVisibility( View.GONE );
                                        dialog.dismiss();
                                        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() )
                                                .setTitle( "Hapus Akun" )
                                                .setMessage( "Apakah Anda yakin!!!" )
                                                .setPositiveButton( "Hapus", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        progressBar.setVisibility( View.VISIBLE );
                                                        if (user != null) {
                                                            user.delete()
                                                                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Toast.makeText( getContext(), "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT ).show();
                                                                                Intent intent = new Intent( getActivity(), RegisterServices.class );
                                                                                startActivity( intent );
                                                                                Objects.requireNonNull( getActivity() ).finish();
                                                                                progressBar.setVisibility( View.GONE );
                                                                            } else {
                                                                                Toast.makeText( getContext(), "Failed to delete your account!", Toast.LENGTH_SHORT ).show();
                                                                                progressBar.setVisibility( View.GONE );
                                                                            }
                                                                        }
                                                                    } );
                                                        }
                                                    }
                                                } )
                                                .setNegativeButton( "Batal", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                } );
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                } );
                                auth.signInWithEmailAndPassword( strEmail, strPassword ).addOnFailureListener( new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility( View.GONE );
                                        Snackbar snackbar = Snackbar.make( parent, "Password atau Email salah silahkan coba lagi.", LENGTH_SHORT );
                                        snackbar.show();
                                    }
                                } );
                            }
                        }
                    } );

                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                        dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                        dialog.show();
                    }
                }


        } );

        if (progressBar != null) {
            progressBar.setVisibility( View.GONE );
        }
        return root;
    }

    private void loginActivity() {
        dialog.setContentView( R.layout.login_activity );
        etEmail = dialog.findViewById( R.id.etEmail );
        etPassword = dialog.findViewById( R.id.etPassword );
        btnLogin = dialog.findViewById( R.id.btnLogin );

        progressBar = dialog.findViewById( R.id.pbLoading );
        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility( View.VISIBLE );
                strEmail = etEmail.getText().toString().trim();
                strPassword = etPassword.getText().toString().trim();
                if (strEmail.length() == 0) {
                    etEmail.setError( "Email Diperlukan." );
                    etEmail.requestFocus();
                } else if (strEmail.length() <= 5) {
                    etEmail.setError( "email tidak memenuhi syarat." );
                    etEmail.requestFocus();
                } else if (strPassword.length() == 0) {
                    etPassword.setError( "Password Diperlukan." );
                    etPassword.requestFocus();
                } else if (strPassword.length() <= 7) {
                    etPassword.setError( "minimal password 7 karakter." );
                    etPassword.requestFocus();
                } else if (isEmailValid( strEmail )) {
                    Snackbar snackbar = Snackbar.make( parent, "Email salah atau email tidak sesuai. \n Harap memasukkan email valid dan benar.", LENGTH_SHORT );
                    snackbar.show();
                } else {
                    auth.signInWithEmailAndPassword( strEmail, strPassword ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility( View.GONE );
                            dialog.dismiss();
                            gantiPassword();
                        }
                    } );
                    auth.signInWithEmailAndPassword( strEmail, strPassword ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility( View.GONE );
                            Snackbar snackbar = Snackbar.make( parent, "Password atau Email salah silahkan coba lagi.", LENGTH_SHORT );
                            snackbar.show();
                        }
                    } );
                }
            }
        } );

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            dialog.show();
        }
    }

    private void loginActivity1() {
        dialog.setContentView( R.layout.login_activity );
        etEmail = dialog.findViewById( R.id.etEmail );
        etPassword = dialog.findViewById( R.id.etPassword );
        btnLogin = dialog.findViewById( R.id.btnLogin );
        progressBar = dialog.findViewById( R.id.pbLoading );

        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility( View.VISIBLE );
                strEmail = etEmail.getText().toString().trim();
                strPassword = etPassword.getText().toString().trim();
                if (strEmail.length() == 0) {
                    etEmail.setError( "Email Diperlukan." );
                    etEmail.requestFocus();
                } else if (strEmail.length() <= 5) {
                    etEmail.setError( "email tidak memenuhin syarat." );
                    etEmail.requestFocus();
                } else if (strPassword.length() == 0) {
                    etPassword.setError( "Password Diperlukan." );
                    etPassword.requestFocus();
                } else if (strPassword.length() <= 7) {
                    etPassword.setError( "minimal password 7 karakter." );
                    etPassword.requestFocus();
                } else if (isEmailValid( strEmail )) {
                    Snackbar snackbar = Snackbar.make( parent, "Email salah atau email tidak sesuai. \n Harap memasukkan email valid dan benar.", LENGTH_SHORT );
                    snackbar.show();
                } else {
                    auth.signInWithEmailAndPassword( strEmail, strPassword ).addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility( View.GONE );
                            dialog.dismiss();
                            gantiEmail();
                        }
                    } );
                    auth.signInWithEmailAndPassword( strEmail, strPassword ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility( View.GONE );
                            Snackbar snackbar = Snackbar.make( parent, "Password atau Email salah silahkan coba lagi.", LENGTH_SHORT );
                            snackbar.show();
                        }
                    } );
                }
            }
        } );

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            dialog.show();
        }
    }

    private boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile( regExpn, Pattern.CASE_INSENSITIVE );
        Matcher matcher = pattern.matcher( email );

        return !matcher.matches();
    }

    private void gantiPassword() {
        dialog.setContentView( R.layout.ganti_password );
        etPassword = dialog.findViewById( R.id.etNewPassword );
        etPasswordConfirm = dialog.findViewById( R.id.etNewPasswordConfirm );
        btnGantiPassword = dialog.findViewById( R.id.btnGantiPassword );

        btnGantiPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPassword = etPassword.getText().toString().trim();
                strPasswordConfirm = etPasswordConfirm.getText().toString().trim();
                if (strPassword.length() == 0) {
                    etPassword.setError( "Password Baru Diperlukan." );
                    etPassword.requestFocus();
                } else if (strPasswordConfirm.length() == 0) {
                    etPasswordConfirm.setError( "Konfirmasi Password Baru Diperlukan." );
                    etPasswordConfirm.requestFocus();
                } else if (!strPassword.equals( strPasswordConfirm )) {
                    Snackbar snackbar = Snackbar.make( parent, "Password tidak sama. Silahkan coba lagi", LENGTH_SHORT );
                    snackbar.show();
                } else {
                    user.updatePassword( strPassword )
                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText( getContext(), "Password is updated!", Toast.LENGTH_SHORT ).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText( getContext(), "Failed to update password!", Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            } );
                }
            }
        } );

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            dialog.show();
        }
    }

    private void gantiEmail() {
        dialog.setContentView( R.layout.ganti_email );
        etEmail = dialog.findViewById( R.id.etNewEmail );
        etEmailConfirm = dialog.findViewById( R.id.etNewEmailConfirm );
        btnGantiEmail = dialog.findViewById( R.id.btnGantiEmail );

        btnGantiEmail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = etEmail.getText().toString().trim();
                strEmailConfirm = etEmailConfirm.getText().toString().trim();

                if (strEmail.length() == 0) {
                    etEmail.setError( "Email Diperlukan." );
                    etEmail.requestFocus();
                } else if (strEmailConfirm.length() == 0) {
                    etEmailConfirm.setError( "Email Konfirmasi Diperlukan." );
                    etEmailConfirm.requestFocus();
                } else if (!strEmail.equals( strEmailConfirm )) {
                    Snackbar snackbar = Snackbar.make( parent, "Email tidak sama. Silahkan coba lagi", LENGTH_SHORT );
                    snackbar.show();
                } else {
                    user.updateEmail( strEmail )
                            .addOnCompleteListener( new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText( getContext(), "Email address is updated.", Toast.LENGTH_LONG ).show();
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText( getContext(), "Failed to update email!", Toast.LENGTH_LONG ).show();
                                    }
                                }
                            } );
                }
            }
        } );

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            dialog.show();
        }
    }
}