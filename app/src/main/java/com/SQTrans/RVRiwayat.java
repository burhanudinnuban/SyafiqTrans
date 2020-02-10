package com.SQTrans;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.SQTrans.Adapter.AdapterBusRecyclerView;
import com.SQTrans.ModelData.PemesananBus;
import com.SQTrans.Services.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVRiwayat extends AppCompatActivity implements AdapterBusRecyclerView.FirebaseDataListener {
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private ArrayList<PemesananBus> daftarBarang = new ArrayList<>();
    String getUserId;
    protected CardView cd_NoData, cvListview;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_rvriwayat );
        rvView = findViewById( R.id.rv_item_bus );
        rvView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        rvView.setLayoutManager( layoutManager );

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        getUserId = user.getUid();
        cd_NoData = findViewById( R.id.cd_noData );
        cvListview = findViewById( R.id.cvListView );

        database = FirebaseDatabase.getInstance().getReference();

        cd_NoData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekData();
            }
        } );
        cekData();
    }

    @Override
    public void onDeleteData(PemesananBus barang, final int position) {
        if (database != null) {
            database.child( "pemesanan_bus" + getUserId ).child( barang.getKey() ).removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finish();
                    startActivity( new Intent( getApplicationContext(), RVRiwayat.class ) );
                }
            } );

        }
    }

    public void cekData() {
        progress.show();
        database.child( "pemesanan_bus" + getUserId ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progress.dismiss();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    PemesananBus barang = noteDataSnapshot.getValue( PemesananBus.class );
                    assert barang != null;
                    barang.setKey( noteDataSnapshot.getKey() );
                    daftarBarang.add( barang );

                    if (daftarBarang != null) {
                        cvListview.setVisibility( View.VISIBLE );
                        cd_NoData.setVisibility( View.GONE );
                    } else {
                        cvListview.setVisibility( View.GONE );
                        cd_NoData.setVisibility( View.VISIBLE );
                    }
                }

                adapter = new AdapterBusRecyclerView( daftarBarang, RVRiwayat.this );
                rvView.setAdapter( adapter );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println( databaseError.getDetails() + " " + databaseError.getMessage() );
            }
        } );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
        startActivity( intent );
        finish();
        super.onBackPressed();
    }
}
