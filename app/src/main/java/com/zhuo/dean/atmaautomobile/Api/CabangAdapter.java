package com.zhuo.dean.atmaautomobile.Api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuo.dean.atmaautomobile.R;
import com.zhuo.dean.atmaautomobile.TabInfo;

import java.util.List;

public class CabangAdapter extends RecyclerView.Adapter<CabangAdapter.ViewHolder> {
    private Context context;
    private List<Cabang> cabangList;

    public CabangAdapter(Context context, List<Cabang> cabangList) {
        this.context = context;
        this.cabangList = cabangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cabang_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Cabang cabang = cabangList.get(i);
         viewHolder.nama.setText(cabang.getnAMACABANG());
         viewHolder.alamat.setText(cabang.getaLAMATCABANG());
         viewHolder.noTelp.setText(cabang.getnOTELPCABANG());
    }

    @Override
    public int getItemCount() {
        if (cabangList != null)
            return cabangList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, alamat, noTelp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNama);
            alamat = itemView.findViewById(R.id.tvAlamat);
            noTelp = itemView.findViewById(R.id.tvNoTelp);
        }
    }
}
