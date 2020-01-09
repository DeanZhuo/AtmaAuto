package com.zhuo.dean.atmaautomobile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhuo.dean.atmaautomobile.Api.ApiClient;
import com.zhuo.dean.atmaautomobile.Api.Cabang;
import com.zhuo.dean.atmaautomobile.Api.CabangAdapter;
import com.zhuo.dean.atmaautomobile.Api.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabInfo extends Fragment{
    private List<Cabang> cabangList = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private CabangAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_tab_info, container, false);
        dialog = Helper.loadDialog(getActivity()).create();

        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        adapter = new CabangAdapter(getActivity(), cabangList);
        recyclerView = (RecyclerView) view.findViewById(R.id.cabangView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setList();
        recyclerView.setAdapter(adapter);
    }

    private void setList() {
        dialog.show();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<List<Cabang>> listCall = apiClient.getCabang();
        listCall.enqueue(new Callback<List<Cabang>>() {
            @Override
            public void onResponse(Call<List<Cabang>> call, Response<List<Cabang>> response) {
                try {
                    adapter.notifyDataSetChanged();
                    if(response.body() != null){
                        for(int i=0; i<response.body().size(); i++){
                            cabangList.add(response.body().get(i));
                        }
                    }

                    adapter = new CabangAdapter(getActivity(), cabangList);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error response", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Cabang>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Connection Error", Toast.LENGTH_SHORT).show();
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("error cabang", t.getMessage(), t);
                    // logging probably not necessary
                }
                else {
                    Toast.makeText(getActivity(), "conversion issue! big problems :( ", Toast.LENGTH_SHORT).show();
                    Log.d("error cabang", t.getMessage(), t);
                    // todo log to some central bug tracking service
                }
            }
        });
        dialog.dismiss();
    }

}
