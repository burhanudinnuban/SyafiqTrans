package com.SQTrans.ui.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.SQTrans.PesanBus;
import com.SQTrans.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class HomeFragment extends Fragment {
    private TextView etDari;
    private TextView etDestinasi;
    private TextView etPergi;
    private TextView tvViewBigBus;
    private TextView tvViewMediumBus;
    private TextView tvViewMiniBus;
    private TextView tvViewHiace;
    private Spinner sMenginap;
    private String strDari;
    private String strDestinasi;
    private String strPergi;
    private String strMenginap;
    private String strBigbus;
    private String strMediumBus;
    private String strMiniBus;
    private String strHiAce;
    private RelativeLayout parent;
    private Context ctx;
    private Dialog myDialog;
    private final Calendar myCalendar = Calendar.getInstance();
    private int counterBigbus = 0, counterMediumBus = 0, counterMiniBus = 0, counterHiace = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_home, container, false );
        // variable yang merefers ke Firebase Realtime Database
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String getUserId = user.getUid();

        Log.d( "TAG 2", "onCreateView: " + getUserId );
        Button btnLihatHarga = root.findViewById( R.id.btnHarga );
        etDari = root.findViewById( R.id.etDari );
        etDestinasi = root.findViewById( R.id.etDestinasi );
        etPergi = root.findViewById( R.id.etPergi );
        sMenginap = root.findViewById( R.id.sLamaMenginap );
        parent = root.findViewById( R.id.parent );
        tvViewBigBus = root.findViewById( R.id.tvBigbus );
        tvViewMediumBus = root.findViewById( R.id.tvMediumBus );
        tvViewMiniBus = root.findViewById( R.id.tvMiniBus );
        tvViewHiace = root.findViewById( R.id.tvHiace );
        ImageButton imgViewBigBus = root.findViewById( R.id.imgBigBusView );
        ImageButton imgViewMediumBus = root.findViewById( R.id.imgMediumBus );
        ImageButton imgViewMiniBus = root.findViewById( R.id.imgMiniBus );
        ImageButton imgViewHiace = root.findViewById( R.id.imgHiAce );
        Button imgMinBigbus = root.findViewById( R.id.btnMinBigBus );
        Button imgPlusBigbus = root.findViewById( R.id.btnPlusBigBus );
        Button imgMinMediumBus = root.findViewById( R.id.btnMinMediumBus );
        Button imgPlusMediumBus = root.findViewById( R.id.btnPlusMediumBus );
        Button imgMinMiniBus = root.findViewById( R.id.btnMinMiniBus );
        Button imgPlusMiniBus = root.findViewById( R.id.btnPlusMiniBus );
        Button imgMinHiace = root.findViewById( R.id.btnMinHiace );
        Button imgPlusHiace = root.findViewById( R.id.btnPlusHiace );

        imgMinBigbus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterBigbus>0) {
                    counterBigbus = counterBigbus - 1;
                    tvViewBigBus.setText( String.valueOf( counterBigbus ) );
                }else if (counterBigbus==0){
                    Toast.makeText( getContext(), "Batas sudah mencapai minimum pemesanan", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        imgPlusBigbus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterBigbus<=9) {
                    counterBigbus = counterBigbus + 1;
                    tvViewBigBus.setText( String.valueOf( counterBigbus ) );
                }else if (counterBigbus==10){
                    Toast.makeText( getContext(), "Batas sudah mencapai maximal pemesanan", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        imgMinMediumBus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterMediumBus>0) {
                    counterMediumBus = counterMediumBus - 1;
                    tvViewMediumBus.setText( String.valueOf( counterMediumBus ) );
                }else if (counterMediumBus==0){
                    Toast.makeText( getContext(), "Batas sudah mencapai minimum pemesanan", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        imgPlusMediumBus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterMediumBus<=9) {
                    counterMediumBus = counterMediumBus + 1;
                    tvViewMediumBus.setText( String.valueOf( counterMediumBus ) );
                }else if (counterMediumBus==10){
                    Toast.makeText( getContext(), "Batas sudah mencapai maximal pemesanan", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        imgMinMiniBus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterMiniBus>0) {
                    counterMiniBus = counterMiniBus - 1;
                    tvViewMiniBus.setText( String.valueOf( counterMiniBus ) );
                }else if (counterMiniBus==0){
                    Toast.makeText( getContext(), "Batas sudah mencapai minimum pemesanan", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        imgPlusMiniBus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterMiniBus<=9) {
                    counterMiniBus = counterMiniBus + 1;
                    tvViewMiniBus.setText( String.valueOf( counterMiniBus ) );
                }else if (counterMiniBus==10){
                    Toast.makeText( getContext(), "Batas sudah mencapai maximal pemesanan", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        imgMinHiace.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterHiace>0) {
                    counterHiace = counterHiace - 1;
                    tvViewHiace.setText( String.valueOf( counterHiace ) );
                }else if (counterHiace==0){
                    Toast.makeText( getContext(), "Batas sudah mencapai minimum pemesanan", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        imgPlusHiace.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterHiace<=9) {
                    counterHiace = counterHiace + 1;
                    tvViewHiace.setText( String.valueOf( counterHiace ) );
                }else if (counterHiace==10){
                    Toast.makeText( getContext(), "Batas sudah mencapai maximal pemesanan", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        imgViewBigBus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBigBus();
            }
        } );
        imgViewMediumBus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMediumBus();
            }
        } );
        imgViewMiniBus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMiniBus();
            }
        } );
        imgViewHiace.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupHiace();
            }
        } );

        ctx = getActivity();

        etDari.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make( parent, "Untuk saat ini pemberangkatan hanya bisa dilakukan pada PO.SQTrans di cibinong", LENGTH_SHORT );
                snackbar.show();
            }
        } );

        etDestinasi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupLocation();
            }
        } );

        btnLihatHarga.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strDari = etDari.getText().toString().trim();
                strDestinasi = etDestinasi.getText().toString().trim();
                strPergi = etPergi.getText().toString().trim();
                strMenginap = sMenginap.getSelectedItem().toString().trim();
                strBigbus = tvViewBigBus.getText().toString().trim();
                strHiAce = tvViewHiace.getText().toString().trim();
                strMediumBus = tvViewMediumBus.getText().toString().trim();
                strMiniBus = tvViewMiniBus.getText().toString().trim();

                if (strDari.length() == 0) {
                    Snackbar snackbar = Snackbar.make( parent, "Alamat Pemberangkatan Tidak Boleh Kosong", LENGTH_SHORT );
                    snackbar.show();
                } else if (strDestinasi.length() == 0) {
                    Snackbar snackbar = Snackbar.make( parent, "Alamat Tujuan Pemberangkatan Tidak Boleh Kosong", LENGTH_SHORT );
                    snackbar.show();
                } else if (strPergi.length() == 0) {
                    Snackbar snackbar = Snackbar.make( parent, "Tanggal Pemberangkatan Tidak Boleh Kosong", LENGTH_SHORT );
                    snackbar.show();
                } else if (strBigbus.equals( "0" ) && strMediumBus.equals( "0" ) && strMiniBus.equals( "0" ) && strHiAce.equals( "0" )){
                    Snackbar snackbar = Snackbar.make( parent, "Bus Tidak Boleh Kosong", LENGTH_SHORT );
                    snackbar.show();
                }else if (strMenginap.equals( "Pilih Lama Menginap" )) {
                    Snackbar snackbar = Snackbar.make( parent, "Penginapan Tidak Boleh Kosong", LENGTH_SHORT );
                    snackbar.show();
                }else {
                    Intent intent = new Intent( getContext(), PesanBus.class );
                    intent.putExtra( "dari", strDari );
                    intent.putExtra( "tujuan", strDestinasi );
                    intent.putExtra( "tanggal", strPergi );
                    intent.putExtra( "menginap", strMenginap );
                    intent.putExtra( "bigbus", strBigbus );
                    intent.putExtra( "mediumbus", strMediumBus );
                    intent.putExtra( "minibus", strMiniBus );
                    intent.putExtra( "hiace", strHiAce );
                    startActivity( intent );
                }
            }
        } );

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set( Calendar.YEAR, year );
                myCalendar.set( Calendar.MONTH, monthOfYear );
                myCalendar.set( Calendar.DAY_OF_MONTH, dayOfMonth );
                updateLabel();
            }

        };

        etPergi.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog( Objects.requireNonNull( getContext() ), date, myCalendar
                        .get( Calendar.YEAR ), myCalendar.get( Calendar.MONTH ),
                        myCalendar.get( Calendar.DAY_OF_MONTH ) ).show();
            }
        } );
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat( myFormat, Locale.US );
        Date currentTime = Calendar.getInstance().getTime();
        etPergi.setText( sdf.format( currentTime ) );

        return root;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        String formatDay = "dd";
        String formatMonth = "MM";
        String formatyear = "yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat( myFormat, Locale.US );
        SimpleDateFormat day = new SimpleDateFormat( formatDay, Locale.US );
        SimpleDateFormat month = new SimpleDateFormat( formatMonth, Locale.US );
        SimpleDateFormat year = new SimpleDateFormat( formatyear, Locale.US );
        Date currentTime = Calendar.getInstance().getTime();

        String getDay = day.format( myCalendar.getTime() );
        String getMonth = month.format( myCalendar.getTime() );
        String getYear = year.format( myCalendar.getTime() );
        String day1 = day.format( currentTime );
        String month1 = month.format( currentTime );
        String year1 = year.format( currentTime );

        Double getDay2 = Double.parseDouble( getDay );
        Double getMonth2 = Double.parseDouble( getMonth );
        Double getYear2 = Double.parseDouble( getYear );
        Double Day2 = Double.parseDouble( day1 );
        Double Month2 = Double.parseDouble( month1 );
        Double Year2 = Double.parseDouble( year1 );

        if (getDay2<Day2 || getMonth2<Month2 || getYear2<Year2){
            Toast.makeText( ctx, "Tanggal telah kadaluarsa harap gunakan tanggal lain", Toast.LENGTH_SHORT ).show();
        }else {
            etPergi.setText( sdf.format( myCalendar.getTime() ) );
        }

    }

    private void popupLocation() {
        myDialog = new Dialog( Objects.requireNonNull( getContext() ) );
        myDialog.setContentView( R.layout.popup_location );
        myDialog.setCanceledOnTouchOutside( false );

        Button btnBatal = myDialog.findViewById( R.id.btnBatal );
        TextView tvBandung = myDialog.findViewById( R.id.tvBandung );
        TextView tvBanten = myDialog.findViewById( R.id.tvBanten );
        TextView tvBogor = myDialog.findViewById( R.id.tvBogor );
        TextView tvJakarta = myDialog.findViewById( R.id.tvJakarta );
        TextView tvPuncakBogor = myDialog.findViewById( R.id.tvPuncakBogor );
        TextView tvMalang = myDialog.findViewById( R.id.tvMalang );
        TextView tvSukabumi = myDialog.findViewById( R.id.tvSukabumi );
        TextView tvBali = myDialog.findViewById( R.id.tvBali );
        TextView tvTMII = myDialog.findViewById( R.id.tvTMII );
        TextView tvTangkubanPerahu = myDialog.findViewById( R.id.tvTangkubanPerahu );
        TextView tvCipanas = myDialog.findViewById( R.id.tvCipanas );
        TextView tvCibodas = myDialog.findViewById( R.id.tvCibodas );
        TextView tvCiwidey = myDialog.findViewById( R.id.tvCiwidey );
        TextView tvDufan = myDialog.findViewById( R.id.tvDufan );

        final String strBandung = tvBandung.getText().toString();
        final String strBanten = tvBanten.getText().toString();
        final String strBogor = tvBogor.getText().toString();
        final String strJakarta = tvJakarta.getText().toString();
        final String strMalang = tvMalang.getText().toString();
        final String strPuncakBogor = tvPuncakBogor.getText().toString();
        final String strSukabumi = tvSukabumi.getText().toString();
        final String strBali = tvBali.getText().toString();
        final String strTMII = tvTMII.getText().toString();
        final String strTangkubanPerahu = tvTangkubanPerahu.getText().toString();
        final String strCipanas = tvCipanas.getText().toString();
        final String strCibodas = tvCibodas.getText().toString();
        final String strCiwidey = tvCiwidey.getText().toString();
        final String strDufan = tvDufan.getText().toString();


        tvBandung.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strBandung );
                myDialog.dismiss();
            }
        } );

        tvMalang.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strMalang );
                myDialog.dismiss();
            }
        } );

        tvPuncakBogor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strPuncakBogor );
                myDialog.dismiss();
            }
        } );

        tvJakarta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strJakarta );
                myDialog.dismiss();
            }
        } );

        tvBanten.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strBanten );
                myDialog.dismiss();
            }
        } );

        tvBogor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strBogor );
                myDialog.dismiss();
            }
        } );

        tvSukabumi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strSukabumi );
                myDialog.dismiss();
            }
        } );

        tvBali.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strBali );
                myDialog.dismiss();
            }
        } );

        tvTMII.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strTMII );
                myDialog.dismiss();
            }
        } );

        tvTangkubanPerahu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strTangkubanPerahu );
                myDialog.dismiss();
            }
        } );

        tvCipanas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strCipanas );
                myDialog.dismiss();
            }
        } );

        tvCibodas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strCibodas );
                myDialog.dismiss();
            }
        } );

        tvCiwidey.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strCiwidey );
                myDialog.dismiss();
            }
        } );

        tvDufan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etDestinasi.setText( strDufan );
                myDialog.dismiss();
            }
        } );

        btnBatal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();

            }
        } );

        if (myDialog.getWindow() != null) {
            myDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            myDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            myDialog.show();
        }
    }

    private void popupBigBus(){
        myDialog = new Dialog( Objects.requireNonNull( getContext() ) );
        myDialog.setContentView( R.layout.popupbus );
        myDialog.setCanceledOnTouchOutside( false );
        ImageView imgcancelbus, imgviewbus;
        imgcancelbus = myDialog.findViewById( R.id.imgCancelViewBus );
        imgviewbus = myDialog.findViewById( R.id.imgViewBus );

        imgviewbus.setImageResource( R.drawable.big_bus );
        imgcancelbus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        } );

        if (myDialog.getWindow() != null) {
            myDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            myDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            myDialog.show();
        }
    }

    private void popupMediumBus(){
        myDialog = new Dialog( Objects.requireNonNull( getContext() ) );
        myDialog.setContentView( R.layout.popupbus );
        myDialog.setCanceledOnTouchOutside( false );
        ImageView imgcancelbus, imgviewbus;
        imgcancelbus = myDialog.findViewById( R.id.imgCancelViewBus );
        imgviewbus = myDialog.findViewById( R.id.imgViewBus );
        imgviewbus.setImageResource( R.drawable.medium_bus );
        imgcancelbus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        } );

        if (myDialog.getWindow() != null) {
            myDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            myDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            myDialog.show();
        }
    }

    private void popupMiniBus(){
        myDialog = new Dialog( Objects.requireNonNull( getContext() ) );
        myDialog.setContentView( R.layout.popupbus );
        myDialog.setCanceledOnTouchOutside( false );
        ImageView imgcancelbus, imgviewbus;
        imgcancelbus = myDialog.findViewById( R.id.imgCancelViewBus );
        imgviewbus = myDialog.findViewById( R.id.imgViewBus );

        imgviewbus.setImageResource( R.drawable.mini_bus );
        imgcancelbus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        } );

        if (myDialog.getWindow() != null) {
            myDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            myDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            myDialog.show();
        }
    }

    private void popupHiace(){
        myDialog = new Dialog( Objects.requireNonNull( getContext() ) );
        myDialog.setContentView( R.layout.popupbus );
        myDialog.setCanceledOnTouchOutside( false );
        ImageView imgcancelbus, imgviewbus;
        imgcancelbus = myDialog.findViewById( R.id.imgCancelViewBus );
        imgviewbus = myDialog.findViewById( R.id.imgViewBus );

        imgviewbus.setImageResource( R.drawable.hiace );
        imgcancelbus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        } );

        if (myDialog.getWindow() != null) {
            myDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
            myDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            myDialog.show();
        }
    }

}