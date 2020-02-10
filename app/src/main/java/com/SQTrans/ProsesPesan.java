package com.SQTrans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class ProsesPesan extends AppCompatActivity {
    TextView tvDari;
    TextView tvTujuan;
    TextView tvTanggal;
    TextView tvHarga;
    TextView tvBigbus;
    TextView tvMediumBus;
    TextView tvMiniBus;
    TextView tvHiAce;

    String strDari1;
    String strTujuan1;
    String strTanggal1;
    String strQuantity1;
    String strMenginap;

    String strBigbus;
    String strMediumBus;
    String strMiniBus;
    String strHiace;
    String strHarga;

    Button btnPesan, btnNanti;
    private LinearLayout parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_proses_pesan );
        parent = findViewById( R.id.parent );
        tvBigbus = findViewById( R.id.tvBigbus );
        tvMediumBus = findViewById( R.id.tvMediumBus );
        tvMiniBus = findViewById( R.id.tvMiniBus );
        tvHiAce = findViewById( R.id.tvHiAce );
        tvDari = findViewById( R.id.tvDari );
        tvTujuan = findViewById( R.id.tvTujuan );
        tvTanggal = findViewById( R.id.tvTanggal );
        tvHarga = findViewById( R.id.tvHarga );
        btnPesan = findViewById( R.id.btnPesanBus );
        btnNanti = findViewById( R.id.btnNanti );

        strBigbus = getIntent().getStringExtra( "bigbus" );
        strMediumBus = getIntent().getStringExtra( "mediumbus" );
        strMiniBus = getIntent().getStringExtra( "minibus" );
        strHiace = getIntent().getStringExtra( "hiace" );
        strDari1 = getIntent().getStringExtra( "dari" );
        strQuantity1 = getIntent().getStringExtra( "quantity" );
        strTanggal1 = getIntent().getStringExtra( "tanggal" );
        strTujuan1 = getIntent().getStringExtra( "tujuan" );
        strMenginap = getIntent().getStringExtra( "menginap" );
        strHarga = getIntent().getStringExtra( "harga" );

        tvBigbus.setText( strBigbus );
        tvMediumBus.setText( strMediumBus);
        tvMiniBus.setText( strMiniBus );
        tvHiAce.setText( strHiace );
        tvDari.setText( strDari1 );
        tvTujuan.setText( strTujuan1 );
        tvTanggal.setText( strTanggal1 );
        tvHarga.setText( strHarga );

        btnNanti.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );

        btnPesan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), PembayaranBusTransfer.class );
                intent.putExtra( "dari", strDari1 );
                intent.putExtra( "tujuan", strTujuan1 );
                intent.putExtra( "tanggal", strTanggal1 );
                intent.putExtra( "bigbus", strBigbus );
                intent.putExtra( "mediumbus", strMediumBus );
                intent.putExtra( "minibus", strMiniBus );
                intent.putExtra( "hiace", strHiace );
                intent.putExtra( "menginap", strMenginap );
                intent.putExtra( "harga", strHarga );
                startActivity( intent );
                Snackbar snackbar = Snackbar.make( parent, "Pemesanan berhasil harap lakukan pembayaran", LENGTH_SHORT );
                snackbar.show();
                finish();
            }
        } );
    }
}
