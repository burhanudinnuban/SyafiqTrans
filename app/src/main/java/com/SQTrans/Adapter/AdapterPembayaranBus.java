package com.SQTrans.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.SQTrans.ModelData.PembayaranBus;
import com.SQTrans.R;

import java.util.ArrayList;

public class AdapterPembayaranBus extends RecyclerView.Adapter<AdapterPembayaranBus.ViewHolder> {
    private ArrayList<PembayaranBus> transferBus;
    private Context context;

    public AdapterPembayaranBus(ArrayList<PembayaranBus> bayar, Context ctx) {
        transferBus = bayar;
        context = ctx;
    }

    @NonNull
    @Override
    public AdapterPembayaranBus.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.history_transfer, parent, false );
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterPembayaranBus.ViewHolder holder, int position) {
        final String transferbus = transferBus.get( position ).getHarga();
        final String dari = transferBus.get( position ).getDari();
        final String tujuan = transferBus.get( position ).getDestinasi();
        final String tanggal = transferBus.get( position ).getPergi();
        final String status = transferBus.get( position ).getStatus();

        holder.tvTanggalTf.setText( tanggal );
        holder.tvStatus.setText( status );
        holder.tvJumlahtf.setText( transferbus );
        holder.tvTujuan.setText( tujuan );
        holder.tvDari.setText( dari );

        holder.tvDetail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );
    }

    @Override
    public int getItemCount() {
        return transferBus.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggalTf, tvDari, tvTujuan, tvJumlahtf, tvStatus, tvDetail;

        ViewHolder(View itemView) {
            super( itemView );
            tvDari = itemView.findViewById( R.id.etDari );
            tvTujuan = itemView.findViewById( R.id.etDestinasi );
            tvJumlahtf = itemView.findViewById( R.id.etJumlahPembayaran );
            tvStatus = itemView.findViewById( R.id.tvStatusTransfer );
            tvTanggalTf = itemView.findViewById( R.id.tvTanggalTransfer );
            tvDetail = itemView.findViewById( R.id.tvDetail );
        }
    }
}
