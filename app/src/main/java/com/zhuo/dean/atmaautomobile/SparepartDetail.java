package com.zhuo.dean.atmaautomobile;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuo.dean.atmaautomobile.Api.ApiClient;
import com.zhuo.dean.atmaautomobile.Api.Helper;
import com.zhuo.dean.atmaautomobile.Api.Sparepart;

import java.io.InputStream;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SparepartDetail extends AppCompatActivity {
    TextView dKBarang, dTipe, dNama, dJual, dBeli, dMerk, dKPeletakan, dStok;
    int KSparepart, Jual, Beli, Stok;
    String KBarang, Tipe, Nama, Merk, KPeletakan;
    ImageView detGambar;
    Button detail;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparepart_detail);

        AlertDialog.Builder builder = Helper.loadDialog(SparepartDetail.this);
        dialog = builder.create();
        dialog.show();

        KSparepart = (int) getIntent().getIntExtra("id",1);

        setAtribut();
        setDetail(KSparepart);
    }

    private void setDetail(int kSparepart) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiSparepart = retrofit.create(ApiClient.class);
        Call<Sparepart> objectCall = apiSparepart.detailSparepart(kSparepart);
        objectCall.enqueue(new Callback<Sparepart>() {
            @Override
            public void onResponse(Call<Sparepart> call, Response<Sparepart> response) {
                int status = response.code();
                if (status == 404) {
                    Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_SHORT).show();
                }else if (status == 500){
                    Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                }else{
                    final Sparepart sparepart = response.body();
                    Log.d("sp", "onResponse: "+response.toString());
                    Log.d("sp", "onResponse: spare" + sparepart.getNama_sparepart());
                    Toast.makeText(getApplicationContext(), "Sparepart", Toast.LENGTH_SHORT).show();
                    KBarang = sparepart.getKode_barang();
                    Tipe = sparepart.getTipe_sparepart();
                    Nama = sparepart.getNama_sparepart();
                    Jual = sparepart.getHarga_jual();
                    Beli = sparepart.getHarga_beli();
                    Merk = sparepart.getMerk_sparepart();
                    KPeletakan = sparepart.getKode_peletakan();
                    Stok = sparepart.getJumlah_Stok();

                    dKBarang.setText(KBarang);
                    dTipe.setText(Tipe);
                    dNama.setText(Nama);
                    dJual.setText(String.valueOf(Jual));
                    dBeli.setText(String.valueOf(Beli));
                    dMerk.setText(Merk);
                    dKPeletakan.setText(KPeletakan);
                    dStok.setText(String.valueOf(Stok));

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
                                detGambar.setImageDrawable(drawable);
                            } else {
                                detGambar.setImageResource(R.drawable.logo);
                            }
                        }
                    }
                    new loadImage().execute();
                }
            }

            @Override
            public void onFailure(Call<Sparepart> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Network Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.dismiss();
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

    private void setAtribut() {
        dKBarang = (TextView) findViewById(R.id.SpDetKBarang);
        dTipe = (TextView) findViewById(R.id.SpDetTipe);
        dNama = (TextView) findViewById(R.id.SpDetNama);
        dJual = (TextView) findViewById(R.id.SpDetJual);
        dBeli = (TextView) findViewById(R.id.SpDetBeli);
        dMerk = (TextView) findViewById(R.id.SpDetMerk);
        dKPeletakan = (TextView) findViewById(R.id.SpDetKPeletakan);
        dStok = (TextView) findViewById(R.id.SpDetStok);

        detGambar = (ImageView) findViewById(R.id.SpDetGambar);
    }
}
