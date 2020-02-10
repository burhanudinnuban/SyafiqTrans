package com.SQTrans.Services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.SQTrans.Help;
import com.SQTrans.ModelData.PemesananBus;
import com.SQTrans.R;
import com.SQTrans.RVRiwayat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private String getUserId;
    private ImageView imgShop;
    private DatabaseReference getRefenence;
    protected boolean doubleBackPress = false;
    @SuppressLint("StaticFieldLeak")
    public static MainActivity instance;
    Fragment frg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        instance = this;
        getRefenence = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        getUserId = user.getUid();

        frg = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace( R.id.nav_host_fragment , frg );
        ft.attach(frg);
        ft.commit();

        ImageButton imgHelp = findViewById( R.id.imgHelp );
        imgShop = findViewById( R.id.imgKeranjang );

        imgHelp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), Help.class );
                startActivity( intent );
            }
        } );


        imgShop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), RVRiwayat.class );
                startActivity( intent );
                finish();
            }
        } );
    }

    public void cekData() {
        getRefenence.child( "pemesanan_bus" + getUserId ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    PemesananBus barang = noteDataSnapshot.getValue( PemesananBus.class );
                    assert barang != null;
                    barang.setKey( noteDataSnapshot.getKey() );
                    imgShop.setBackgroundResource( R.drawable.ic_new_shop );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println( databaseError.getDetails() + " " + databaseError.getMessage() );
            }
        } );
    }

    @Override
    protected void onPostResume() {
        cekData();
        super.onPostResume();
    }

    @Override
    public void onBackPressed(){
        if (doubleBackPress){
            finish();
            super.onBackPressed();
            return;
        }
        this.doubleBackPress = true;
        Toast.makeText( this, "Tekan Tombol 'Back' Sekali Lagi untuk Keluar", Toast.LENGTH_LONG ).show();
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                doubleBackPress=false;
            }
        }, 2000);
    }

    public static MainActivity getInstance(){
        return instance;
    }
}
