package com.zhuo.dean.atmaautomobile.Api;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuo.dean.atmaautomobile.R;
import com.zhuo.dean.atmaautomobile.SparepartDetail;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SparepartAdapter extends RecyclerView.Adapter<SparepartAdapter.ViewHolder> {
    private Context context;
    private List<Sparepart> sparepartList;

    public SparepartAdapter(Context context, List<Sparepart> sparepartList) {
        this.context = context;
        this.sparepartList = sparepartList;
    }

    @NonNull
    @Override
    public SparepartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sparepart_item, viewGroup, false);
        final SparepartAdapter.ViewHolder viewHolder = new SparepartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SparepartAdapter.ViewHolder viewHolder, int i) {
        final Sparepart sparepart = sparepartList.get(i);
        viewHolder.kodeBarang.setText("Kode Barang"+sparepart.getKode_barang());
        viewHolder.nama.setText("Nama Sparepart: "+sparepart.getNama_sparepart());
        viewHolder.stok.setText("Jumlah stok: "+sparepart.getJumlah_Stok());
        viewHolder.tipe.setText("Tipe Sparepart: "+sparepart.getTipe_sparepart());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SparepartDetail.class);
                intent.putExtra("id",sparepart.getKode_sparepart());
                context.startActivity(intent);
            }
        });

        class loadImage extends AsyncTask<Integer, Void, Integer> {
            Drawable drawable;

            @Override
            protected Integer doInBackground(Integer... integers) {
                String link = Helper.BASE_URL_IMAGE + sparepart.getGambar();
                drawable = LoadImageFromApi(link);
                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if (drawable != null){
                    viewHolder.imgView.setImageDrawable(drawable);
                } else {
                    viewHolder.imgView.setImageResource(R.drawable.logo);
                }
            }
        }
        new loadImage().execute();
    }

    private Drawable LoadImageFromApi(String link) {
        try {
            InputStream inputStream = (InputStream) new URL(link).getContent();
            Drawable drawable = Drawable.createFromStream(inputStream, "src name");
            return drawable;
        } catch (Exception e) {
            System.out.println("Exec = "+e.getMessage());
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (sparepartList != null)
            return sparepartList.size();
        else
            return 0;
    }

    public void filterList(ArrayList<Sparepart> filter) {
        sparepartList = filter;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView kodeBarang, nama, stok, tipe;
        public ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kodeBarang = itemView.findViewById(R.id.spShowKodeBarang);
            nama = itemView.findViewById(R.id.spShowNamaBarang);
            stok = itemView.findViewById(R.id.spShowStok);
            tipe = itemView.findViewById(R.id.spShowTipe);
            imgView = itemView.findViewById(R.id.spShowGambar);
        }
    }
}
