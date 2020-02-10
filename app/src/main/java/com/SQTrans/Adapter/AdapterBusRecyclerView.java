package com.SQTrans.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.SQTrans.ModelData.PemesananBus;
import com.SQTrans.PembayaranBusTransfer;
import com.SQTrans.ProsesPesan;
import com.SQTrans.R;
import com.SQTrans.RVRiwayat;
import com.SQTrans.Services.MainActivity;

import java.util.ArrayList;

public class AdapterBusRecyclerView extends RecyclerView.Adapter<AdapterBusRecyclerView.ViewHolder> {
    private ArrayList<PemesananBus> daftarBarang;
    private Context context;
    private FirebaseDataListener listener;
    private static AdapterBusRecyclerView instance;

    public AdapterBusRecyclerView(ArrayList<PemesananBus> barangs, Context ctx) {
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarBarang = barangs;
        context = ctx;
        listener = (RVRiwayat) ctx;
        instance = this;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvTitle, tvTanggal, tvDari, tvTujuan, tvQuantity, tvPenginapan;
        ImageButton ib_delete;
        Button ib_edit;

        ViewHolder(View v) {
            super( v );
            tvTitle = (TextView) v.findViewById( R.id.tvItemBus );
            ib_delete = v.findViewById( R.id.ib_delete );
            ib_edit = v.findViewById( R.id.ib_edit );
            tvDari = v.findViewById( R.id.tvDari );
            tvTujuan = v.findViewById( R.id.tvTujuan );
            tvTanggal = v.findViewById( R.id.tvTanggal );
            tvPenginapan = v.findViewById( R.id.tvItemLamaInap );
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_bus, parent, false );
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder( v );
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        /**
         *  Menampilkan data pada view
         */
        final String bigbus = daftarBarang.get( position ).getBigBus();
        final String mediumbus = daftarBarang.get( position ).getMediumBus();
        final String minibus = daftarBarang.get( position ).getminiBus();
        final String hiace = daftarBarang.get( position ).getHiAce();
        final String tanggal = daftarBarang.get( position ).getPergi();
        final String dari = daftarBarang.get( position ).getDari();
        final String tujuan = daftarBarang.get( position ).getDestinasi();
        final String penginapan = daftarBarang.get( position ).getHargaPenginapan();
        final String harga = daftarBarang.get( position ).getHarga();
        holder.ib_edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, PembayaranBusTransfer.class );
                intent.putExtra( "bigbus", bigbus );
                intent.putExtra( "mediumbus", mediumbus );
                intent.putExtra( "minibus", minibus );
                intent.putExtra( "hiace", hiace );
                intent.putExtra( "dari", dari );
                intent.putExtra( "harga", harga );
                intent.putExtra( "tujuan", tujuan );
                intent.putExtra( "tanggal", tanggal );
                intent.putExtra( "menginap", penginapan );
                context.startActivity( intent );
            }
        } );
        holder.ib_delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder( context );
                builder.setMessage( "Apakah anda yakin ingin menghapus!!!" )
                        .setPositiveButton( "Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listener.onDeleteData( daftarBarang.get( position ), position );
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

        holder.tvTitle.setText( tanggal );
        holder.tvDari.setText( dari );
        holder.tvTujuan.setText( tujuan );
        holder.tvPenginapan.setText( "Lama Penginapan" + " " + penginapan );
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item pada barang
         */
        return daftarBarang.size();
    }

    public interface FirebaseDataListener {
        void onDeleteData(PemesananBus pemesananBus, int position);
    }

    public void hapusHistori(final int position) {
        listener.onDeleteData( daftarBarang.get( position ), position );
    }

    public static AdapterBusRecyclerView getInstance() {
        return instance;
    }
}
