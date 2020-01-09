package com.zhuo.dean.atmaautomobile.Api;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Sparepart {
    @SerializedName("KODE_SPAREPART")
    @Expose
    private Integer Kode_sparepart;
    @SerializedName("KODE_BARANG")
    @Expose
    private String Kode_barang;
    @SerializedName("TIPE_SPAREPART")
    @Expose
    private String Tipe_sparepart;
    @SerializedName("NAMA_SPAREPART")
    @Expose
    private String Nama_sparepart;
    @SerializedName("HARGA_JUAL")
    @Expose
    private Integer Harga_jual;
    @SerializedName("HARGA_BELI")
    @Expose
    private Integer Harga_beli;
    @SerializedName("MERK_SPAREPART")
    @Expose
    private String Merk_sparepart;
    @SerializedName("KODE_PELETAKAN")
    @Expose
    private String Kode_peletakan;
    @SerializedName("JUMLAH_STOK")
    @Expose
    private Integer Jumlah_Stok;
    @SerializedName("GAMBAR")
    @Expose
    private String Gambar;

    public String getKode_barang() {
        return Kode_barang;
    }

    public Integer getHarga_jual() {
        return Harga_jual;
    }

    public Integer getHarga_beli() {
        return Harga_beli;
    }

    public String getMerk_sparepart() {
        return Merk_sparepart;
    }

    public String getKode_peletakan() {
        return Kode_peletakan;
    }

    public String getGambar() {
        return Gambar;
    }

    public Integer getKode_sparepart() {
        return Kode_sparepart;
    }

    public String getTipe_sparepart() {
        return Tipe_sparepart;
    }

    public String getNama_sparepart() {
        return Nama_sparepart;
    }

    public Integer getJumlah_Stok() {
        return Jumlah_Stok;
    }
}
