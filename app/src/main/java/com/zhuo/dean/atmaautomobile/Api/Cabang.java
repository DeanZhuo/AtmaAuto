package com.zhuo.dean.atmaautomobile.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cabang {
    @SerializedName("KODE_CABANG")
    @Expose
    private Integer kODECABANG;
    @SerializedName("NAMA_CABANG")
    @Expose
    private String nAMACABANG;
    @SerializedName("ALAMAT_CABANG")
    @Expose
    private String aLAMATCABANG;
    @SerializedName("NO_TELP_CABANG")
    @Expose
    private String nOTELPCABANG;

    public String getnAMACABANG() {
        return nAMACABANG;
    }

    public String getaLAMATCABANG() {
        return aLAMATCABANG;
    }

    public String getnOTELPCABANG() {
        return nOTELPCABANG;
    }
}
