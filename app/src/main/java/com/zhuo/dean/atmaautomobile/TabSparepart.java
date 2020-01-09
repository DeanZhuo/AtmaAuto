package com.zhuo.dean.atmaautomobile;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuo.dean.atmaautomobile.Api.ApiClient;
import com.zhuo.dean.atmaautomobile.Api.Helper;
import com.zhuo.dean.atmaautomobile.Api.Sparepart;
import com.zhuo.dean.atmaautomobile.Api.SparepartAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabSparepart extends Fragment {
    private List<Sparepart> sparepartList = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private SparepartAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton sort, byStok, byPrice;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    Boolean isOpen = false;
    private AlertDialog dialog;
    private TextView textview_stock, textview_price;
    private String sorted="not";

    Comparator<Sparepart> comparePrice = new Comparator<Sparepart>() {
        @Override
        public int compare(Sparepart o1, Sparepart o2) {
            return o1.getHarga_jual().compareTo(o2.getHarga_jual());
        }
    };

    Comparator<Sparepart> compareStock = new Comparator<Sparepart>() {
        @Override
        public int compare(Sparepart o1, Sparepart o2) {
            return o1.getJumlah_Stok().compareTo(o2.getJumlah_Stok());
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_tab_sparepart, container, false);
        dialog = Helper.loadDialog(getActivity()).create();
        dialog.show();

        sort = view.findViewById(R.id.fab);
        byPrice = view.findViewById(R.id.fab1);
        byStok = view.findViewById(R.id.fab2);
        textview_stock = (TextView) view.findViewById(R.id.textview_stock);
        textview_price = (TextView) view.findViewById(R.id.textview_price);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_rotate_anticlock);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    textview_stock.setVisibility(View.INVISIBLE);
                    textview_price.setVisibility(View.INVISIBLE);
                    byStok.startAnimation(fab_close);
                    byPrice.startAnimation(fab_close);
                    sort.startAnimation(fab_anticlock);
                    byStok.setClickable(false);
                    byPrice.setClickable(false);
                    isOpen = false;
                } else {
                    textview_stock.setVisibility(View.VISIBLE);
                    textview_price.setVisibility(View.VISIBLE);
                    byPrice.startAnimation(fab_open);
                    byStok.startAnimation(fab_open);
                    sort.startAnimation(fab_clock);
                    byPrice.setClickable(true);
                    byStok.setClickable(true);
                    isOpen = true;
                }
            }
        });

        byPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sorted.equalsIgnoreCase("priceAsc")){
                    sorted = "priceDesc";
                    Toast.makeText(getActivity(), "Sort by Price Desc", Toast.LENGTH_SHORT).show();
                    Collections.sort(sparepartList, Collections.reverseOrder(comparePrice));
                    adapter = new SparepartAdapter(getActivity(), sparepartList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                } else {
                    sorted = "priceAsc";
                    Toast.makeText(getActivity(), "Sort by Price Asc", Toast.LENGTH_SHORT).show();
                    Collections.sort(sparepartList, comparePrice);
                    adapter = new SparepartAdapter(getActivity(), sparepartList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        byStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sorted.equalsIgnoreCase("stockAsc")){
                    sorted = "stockDesc";
                    Toast.makeText(getActivity(), "Sort by Stock Desc", Toast.LENGTH_SHORT).show();
                    Collections.sort(sparepartList, Collections.reverseOrder(compareStock));
                    adapter = new SparepartAdapter(getActivity(), sparepartList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                } else {
                    sorted = "stockAsc";
                    Toast.makeText(getActivity(), "Sort by Stock Asc", Toast.LENGTH_SHORT).show();
                    Collections.sort(sparepartList, compareStock);
                    adapter = new SparepartAdapter(getActivity(), sparepartList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        EditText editText = (EditText) view.findViewById(R.id.SpSearchText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        setRecyclerView();
        return view;
    }

    private void filter(String search) {
        ArrayList<Sparepart> filter = new ArrayList<>();

        for (Sparepart sparepart : sparepartList){
            if (sparepart.getNama_sparepart().toLowerCase().contains(search.toLowerCase())){
                filter.add(sparepart);
            }
        }
        adapter.filterList(filter);
    }

    private void setRecyclerView() {
        adapter = new SparepartAdapter(getActivity(), sparepartList);
        recyclerView = (RecyclerView) view.findViewById(R.id.SparepartView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setList();
        recyclerView.setAdapter(adapter);
    }

    private void setList() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<List<Sparepart>> listCall = apiClient.getSpareparts();
        listCall.enqueue(new Callback<List<Sparepart>>() {
            @Override
            public void onResponse(Call<List<Sparepart>> call, Response<List<Sparepart>> response) {
                try {
                    adapter.notifyDataSetChanged();
                    if(response.body() != null){
                        for(int i=0; i<response.body().size(); i++){
                            sparepartList.add(response.body().get(i));
                        }
                    }

                    adapter = new SparepartAdapter(getActivity(), sparepartList);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error response", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Sparepart>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Connection Error", Toast.LENGTH_SHORT).show();
                if (t instanceof IOException) {
                    Log.d("error sp", t.getMessage(), t);
                    // logging probably not necessary
                }
                else {
                    Log.d("error sp", t.getMessage(), t);
                    // todo log to some central bug tracking service
                }
            }
        });
        dialog.dismiss();
    }
}
