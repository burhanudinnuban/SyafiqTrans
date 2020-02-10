package com.SQTrans;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.SQTrans.ModelData.PemesananBus;
import com.SQTrans.Services.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class PesanBus extends AppCompatActivity {
    TextView tvBus, tvDari, tvTujuan, tvTanggal, tvHarga, tvBigbus, tvMediumBus, tvMiniBus, tvHiAce;
    Button btnPesan, btnNanti;
    String strDari1, strTujuan1, strTanggal1, strQuantity1, strMenginap,
            banten, bandung, puncakBogor, jakarta, bogor, malang,
            hargaTujuan, hargaPenginapan, sukabumi, bali, tangkubanPerahu, cipanas,
            cibodas, ciwidey, dufan, tmii, strBigbus, strMediumBus, strMiniBus, strHiace;
    private DatabaseReference database;
    private LinearLayout parent;
    private ProgressDialog progress;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pesan_bus );

        database = FirebaseDatabase.getInstance().getReference();
        progress = new ProgressDialog(this);
        parent = findViewById( R.id.parent );
        tvBus = findViewById( R.id.tvBus );
        tvDari = findViewById( R.id.tvDari );
        tvTujuan = findViewById( R.id.tvTujuan );
        tvTanggal = findViewById( R.id.tvTanggal );
        tvHarga = findViewById( R.id.tvHarga );
        tvBigbus = findViewById( R.id.tvBigbus );
        tvMediumBus = findViewById( R.id.tvMediumBus );
        tvMiniBus = findViewById( R.id.tvMiniBus );
        tvHiAce = findViewById( R.id.tvHiAce );
        btnPesan = findViewById( R.id.btnPesanBus );
        btnNanti = findViewById( R.id.btnNanti );
        DecimalFormat decimalFormat = new DecimalFormat( ",###" );

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        final String getUserId = user.getUid();

        strBigbus = getIntent().getStringExtra( "bigbus" );
        strMediumBus = getIntent().getStringExtra( "mediumbus" );
        strMiniBus = getIntent().getStringExtra( "minibus" );
        strHiace = getIntent().getStringExtra( "hiace" );
        strDari1 = getIntent().getStringExtra( "dari" );
        strQuantity1 = getIntent().getStringExtra( "quantity" );
        strTanggal1 = getIntent().getStringExtra( "tanggal" );
        strTujuan1 = getIntent().getStringExtra( "tujuan" );
        strMenginap = getIntent().getStringExtra( "menginap" );

        tvBigbus.setText( strBigbus + "pcs" );
        tvMediumBus.setText( strMediumBus + "pcs" );
        tvMiniBus.setText( strMiniBus + "pcs" );
        tvHiAce.setText( strHiace + "pcs" );
        tvDari.setText( strDari1 );
        tvTujuan.setText( strTujuan1 );
        tvTanggal.setText( strTanggal1 );

        banten = "750000";
        bandung = "650000";
        puncakBogor = "5000000";
        jakarta = "100000";
        bogor = "500000";
        malang = "2000000";
        sukabumi = "650000";
        bali = "7500000";
        tmii = "200000";
        tangkubanPerahu = "650000";
        cipanas = "600000";
        cibodas = "625000";
        ciwidey = "650000";
        dufan = "300000";


        switch (strMenginap) {
            case "Tidak Menginap":
                hargaPenginapan = "0";
                break;
            case "1 hari":
                hargaPenginapan = "1000000";
                break;
            case "2 hari":
                hargaPenginapan = "2000000";
                break;
            case "3 hari":
                hargaPenginapan = "3000000";
                break;
            case "4 hari":
                hargaPenginapan = "4000000";
                break;
            case "5 hari":
                hargaPenginapan = "5000000";
                break;
            case "6 hari":
                hargaPenginapan = "6000000";
                break;
            case "7 hari":
                hargaPenginapan = "7000000";
                break;
            case "8 hari":
                hargaPenginapan = "8000000";
                break;
            case "9 hari":
                hargaPenginapan = "9000000";
                break;
            case "10 hari":
                hargaPenginapan = "10000000";
                break;
        }

        switch (strTujuan1) {
            case "Banten":
                hargaTujuan = banten;
                break;
            case "Bandung":
                hargaTujuan = bandung;
                break;
            case "Puncak Bogor":
                hargaTujuan = puncakBogor;
                break;
            case "Jakarta":
                hargaTujuan = jakarta;
                break;
            case "Bogor":
                hargaTujuan = bogor;
                break;
            case "Malang":
                hargaTujuan = malang;
                break;
            case "Sukabumi":
                hargaTujuan = sukabumi;
                break;
            case "Bali":
                hargaTujuan = bali;
                break;
            case "Taman Mini Indonesia Indah":
                hargaTujuan = tmii;
                break;
            case "Tangkuban Perahu":
                hargaTujuan = tangkubanPerahu;
                break;
            case "Cipanas":
                hargaTujuan = cipanas;
                break;
            case "Cibodas":
                hargaTujuan = cibodas;
                break;
            case "Ciwidey":
                hargaTujuan = ciwidey;
                break;
            case "Dunia Fantasi Ancol":
                hargaTujuan = dufan;
                break;
        }

        double dHargaTujuan = Double.parseDouble( hargaTujuan );
        double dhargaPenginapan = Double.parseDouble( hargaPenginapan );

        double dbigbus = Double.parseDouble( strBigbus );
        double dmediumbus = Double.parseDouble( strMediumBus );
        double dminibus = Double.parseDouble( strMiniBus );
        double dhiace = Double.parseDouble( strHiace );

        double hasilbigbus = bigbus( dbigbus );
        double hasilmediumbus = mediumbus( dmediumbus );
        double hasilminibus = minibus( dminibus );
        double hasilhiace = hiace( dhiace );

        double hasilBus = hasil( hasilbigbus, hasilmediumbus, hasilminibus, hasilhiace, dHargaTujuan, dhargaPenginapan );
        tvHarga.setText( "Rp" + " " + decimalFormat.format( hasilBus ) );

        btnNanti.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        btnPesan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String bigbus1 = tvBigbus.getText().toString().trim();
                final String mediumbus1 = tvMediumBus.getText().toString().trim();
                final String minibus1 = tvMiniBus.getText().toString().trim();
                final String hiace1 = tvHiAce.getText().toString().trim();
                final String hargahasil = tvHarga.getText().toString().trim();
                AlertDialog.Builder builder = new AlertDialog.Builder( PesanBus.this );
                builder.setMessage( "Apakah Anda Yakin" )
                        .setCancelable( false )
                        .setPositiveButton( "Pesan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progress.setTitle("Loading");
                                progress.setMessage("Wait while loading...");
                                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                                progress.show();
                                PemesananBus pemesananBus = new PemesananBus( strDari1, strTujuan1, strTanggal1, hargahasil, bigbus1, mediumbus1, minibus1, hiace1, strMenginap );
                                database.child( "pemesanan_bus" + getUserId ).push().setValue( pemesananBus ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar snackbar = Snackbar.make( parent, "Pemesanan berhasil harap lakukan pembayaran", LENGTH_SHORT );
                                        snackbar.show();
                                        Intent intent = new Intent( getApplicationContext(), RVRiwayat.class );
                                        startActivity( intent );
                                        finish();
                                        progress.dismiss();
                                        MainActivity.getInstance().finish();
                                    }
                                } );
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
    }

    public double hasil(double a, double b, double c, double d, double e, double f) {
        return a + b + c + d + e + f;
    }

    public double bigbus(double b) {
        return 1750000 * b;
    }

    public double mediumbus(double b) {
        return 1400000 * b;
    }

    public double minibus(double b) {
        return 500000 * b;
    }

    public double hiace(double b) {
        return 250000 * b;
    }


}
