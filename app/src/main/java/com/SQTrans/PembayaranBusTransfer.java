package com.SQTrans;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.SQTrans.Adapter.AdapterBusRecyclerView;
import com.SQTrans.ModelData.PembayaranBus;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class PembayaranBusTransfer extends AppCompatActivity {

    private RelativeLayout parent;
    private DatabaseReference database;
    @SuppressLint("StaticFieldLeak")
    public static PembayaranBusTransfer intansce;
    int a;
    TextView tvDari, tvTujuan, tvTanggal, tvHarga, tvBigbus, tvMediumBus, tvMiniBus, tvHiAce;
    private ProgressDialog progress;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pembayaran_bus );
        database = FirebaseDatabase.getInstance().getReference();
        TextView tvJumlahUang = findViewById( R.id.tvJumlahUang );
        Button btnTransferNow = findViewById( R.id.btnTransferNow );
        Button btnNanti = findViewById( R.id.btnNanti );
        parent = findViewById( R.id.parent );
        progress = new ProgressDialog(this);
        intansce = this;
        tvBigbus = findViewById( R.id.tvBigbus );
        tvMediumBus = findViewById( R.id.tvMediumBus );
        tvMiniBus = findViewById( R.id.tvMiniBus );
        tvHiAce = findViewById( R.id.tvHiAce );
        tvDari = findViewById( R.id.tvDari );
        tvTujuan = findViewById( R.id.tvTujuan );
        tvTanggal = findViewById( R.id.tvTanggal );
        tvHarga = findViewById( R.id.tvHarga );
        TextView lamamenginap = findViewById( R.id.lamaMenginap );

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        final String getUserId = user.getUid();

        final String dari = getIntent().getStringExtra( "dari" );
        final String tujuan = getIntent().getStringExtra( "tujuan" );
        final String tanggal = getIntent().getStringExtra( "tanggal" );
        final String quantity = getIntent().getStringExtra( "quantity" );
        final String bigbus = getIntent().getStringExtra( "bigbus" );
        final String mediumbus = getIntent().getStringExtra( "mediumbus" );
        final String minibus = getIntent().getStringExtra( "minibus" );
        final String hiace = getIntent().getStringExtra( "hiace" );
        final String jumlahUang = getIntent().getStringExtra( "harga" );
        final String penginapan = getIntent().getStringExtra( "menginap" );
        final String status = "sukses";

        tvBigbus.setText( bigbus );
        tvMediumBus.setText( mediumbus);
        tvMiniBus.setText( minibus );
        tvHiAce.setText( hiace );
        tvDari.setText( dari );
        tvTujuan.setText( tujuan );
        tvTanggal.setText( tanggal );
        tvJumlahUang.setText( jumlahUang );
        lamamenginap.setText( "Menginap" + " " + "Selama"+ " " + penginapan );

        final String output = tvJumlahUang.getText().toString();

        btnNanti.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        btnTransferNow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder( PembayaranBusTransfer.this );
                builder.setMessage( "Apakah Anda Yakin!!!!" )
                        .setCancelable( false )
                        .setPositiveButton( "Transfer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, final int i) {
                                progress.setTitle("Loading");
                                progress.setMessage("Wait while loading...");
                                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                progress.show();
                                PembayaranBus pembayaranBus = new PembayaranBus( dari, tujuan, tanggal, output, bigbus, mediumbus, minibus, hiace, quantity, status, penginapan );
                                Log.d( "TAG", "onClick: " + penginapan );
                                database.child("pembayaran_bus"+getUserId).push().setValue(pembayaranBus).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar snackbar = Snackbar.make(parent, "Pembayaran berhasil.", LENGTH_SHORT);
                                        snackbar.show();
                                        progress.dismiss();
                                    }
                                });

                                final Handler handler = new Handler();
                                handler.postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                AdapterBusRecyclerView.getInstance().hapusHistori( a );
                                                finish();
                                            }
                                        }, 1000L);

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
        });
    }

    public static PembayaranBusTransfer getInstance(){
        return intansce;
    }
}