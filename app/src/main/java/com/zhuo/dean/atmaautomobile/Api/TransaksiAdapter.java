package com.zhuo.dean.atmaautomobile.Api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuo.dean.atmaautomobile.R;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    private Context context;
    private List<Transaksi> transaksis;
    private String state;

    public TransaksiAdapter(Context context, List<Transaksi> transaksis) {
        this.context = context;
        this.transaksis = transaksis;
    }

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, viewGroup, false);
        final TransaksiAdapter.ViewHolder viewHolder = new TransaksiAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.ViewHolder viewHolder, int i) {
        final Transaksi transaksi = transaksis.get(i);
        viewHolder.tvpenjualan.setText("Kode: "+transaksi.getKODEPENJUALAN());
        viewHolder.tvtanggal.setText("Tanggal: "+transaksi.getTANGGALTRANSAKSI());
        if (transaksi.getKODESTATUS() == 1)
            state = "Selesai";
        else state = "Belum";
        viewHolder.tvstatus.setText("Status: "+state);
    }

    @Override
    public int getItemCount() {
        if (transaksis != null)
            return transaksis.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvpenjualan, tvtanggal, tvstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpenjualan = itemView.findViewById(R.id.historyKode);
            tvtanggal = itemView.findViewById(R.id.historyTanggal);
            tvstatus = itemView.findViewById(R.id.historyStatus);
        }
    }
}
