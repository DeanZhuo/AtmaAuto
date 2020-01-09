package com.zhuo.dean.atmaautomobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhuo.dean.atmaautomobile.Api.ApiClient;
import com.zhuo.dean.atmaautomobile.Api.Customer;
import com.zhuo.dean.atmaautomobile.Api.Helper;
import com.zhuo.dean.atmaautomobile.Api.Kendaraan;
import com.zhuo.dean.atmaautomobile.Api.Transaksi;
import com.zhuo.dean.atmaautomobile.Api.TransaksiAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  TabData extends Fragment {

    private View view;
    private int noCust, noKendaraan;
    private TransaksiAdapter adapter;
    private List<Transaksi> transaksiList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_tab_data, container, false);

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_history_data);
        dialog.setCancelable(true);

        FloatingActionButton action = view.findViewById(R.id.floatingActionButton);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        Button search = (Button)dialog.findViewById(R.id.HistoryButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg) {
                dialog.dismiss();
                transaksiList.clear();
                EditText notelp = dialog.findViewById(R.id.HistoryNo);
                String NoTelp = notelp.getText().toString();
                EditText plat = dialog.findViewById(R.id.HistoryPlat);
                String Plat = plat.getText().toString();

                Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                ApiClient apiClient = retrofit.create(ApiClient.class);
                Call<Customer> customerCall = apiClient.getCustomer(NoTelp);
                customerCall.enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        if (response.code() == 404){
                            Toast.makeText(getActivity(), "Customer Not Found", Toast.LENGTH_SHORT).show();
                            noCust = 0;
                        } else if (response.code() == 200){
                            noCust = response.body().getkODECUST();
                        } else {
                            noCust = 0;
                            Log.d("customer", "onResponse: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        Log.d("customer", "onFailure: "+t.getMessage());
                    }
                });

                Call<Kendaraan> kendaraanCall = apiClient.getKendaraan(Plat);
                kendaraanCall.enqueue(new Callback<Kendaraan>() {
                    @Override
                    public void onResponse(Call<Kendaraan> call, Response<Kendaraan> response) {
                        if (response.code() == 404){
                            Toast.makeText(getActivity(), "Kendaraan Not Found", Toast.LENGTH_SHORT).show();
                            noKendaraan = 0;
                        } else if (response.code() == 200){
                            noKendaraan = response.body().getKODEKENDARAAN();
                        } else {
                            noKendaraan = 0;
                            Log.d("kendaraan", "onResponse: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Kendaraan> call, Throwable t) {
                        Log.d("kendaraan", "onFailure: "+t.getMessage());
                    }
                });

                setAdapterList(noCust, noKendaraan);
            }
        });

        return view;
    }

    private void setAdapterList(int noCust, int noKendaraan) {
        adapter = new TransaksiAdapter(getActivity(), transaksiList);
        recyclerView = (RecyclerView) view.findViewById(R.id.historyView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setList(noCust, noKendaraan);
        recyclerView.setAdapter(adapter);
    }

    private void setList(int noCust, int noKendaraan) {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<List<Transaksi>> clientHistory  = apiClient.getHistory(noCust,noKendaraan);
        clientHistory.enqueue(new Callback<List<Transaksi>>() {
            @Override
            public void onResponse(Call<List<Transaksi>> call, Response<List<Transaksi>> response) {
                try {
                    adapter.notifyDataSetChanged();
                    if(response.body() != null){
                        for(int i=0; i<response.body().size(); i++){
                            transaksiList.add(response.body().get(i));
                        }
                    }

                    adapter = new TransaksiAdapter(getActivity(), transaksiList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Success "+response.code(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error response", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Transaksi>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Connection Error", Toast.LENGTH_SHORT).show();
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("error tr", t.getMessage(), t);
                    // logging probably not necessary
                }
                else {
                    Toast.makeText(getActivity(), "conversion issue! big problems :( ", Toast.LENGTH_SHORT).show();
                    Log.d("error tr", t.getMessage(), t);
                    // todo log to some central bug tracking service
                }
            }
        });
    }
}
