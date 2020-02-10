package com.SQTrans.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SQTrans.Adapter.AdapterPembayaranBus;
import com.SQTrans.ModelData.PembayaranBus;
import com.SQTrans.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private ArrayList<PembayaranBus> pembayaranBus;
    private CardView cd_NoData, cvListview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate( R.layout.fragment_dashboard, container, false );

        rvView = root.findViewById( R.id.rvPembayaran );
        rvView.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getContext() );
        rvView.setLayoutManager( layoutManager );

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String getUserId = user.getUid();
        cd_NoData = root.findViewById( R.id.cd_noData );
        cvListview = root.findViewById( R.id.cvListView );

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child( "pembayaran_bus" + getUserId ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pembayaranBus = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    PembayaranBus transfer = noteDataSnapshot.getValue( PembayaranBus.class );
                    assert transfer != null;
                    transfer.setKey( noteDataSnapshot.getKey() );

                    pembayaranBus.add( transfer );
                    cvListview.setVisibility( View.VISIBLE );
                    cd_NoData.setVisibility( View.GONE );
                }
                adapter = new AdapterPembayaranBus( pembayaranBus, getActivity() );
                rvView.setAdapter( adapter );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println( databaseError.getDetails() + " " + databaseError.getMessage() );
            }
        } );
        return root;
    }
}