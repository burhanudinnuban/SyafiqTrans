package com.SQTrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ToolbarCustome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_toolbar_custome );
        ImageButton imgHelp = findViewById( R.id.imgHelp );
        ImageView imgShop = findViewById( R.id.imgKeranjang );

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
            }
        } );
    }
}
