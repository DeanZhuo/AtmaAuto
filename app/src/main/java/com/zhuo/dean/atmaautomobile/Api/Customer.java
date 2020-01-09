package com.zhuo.dean.atmaautomobile.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("KODE_CUST")
    @Expose
    private Integer kODECUST;
    @SerializedName("NAMA_CUST")
    @Expose
    private String nAMACUST;
    @SerializedName("NO_TELP_CUST")
    @Expose
    private String nOTELPCUST;

    public Integer getkODECUST() {
        return kODECUST;
    }

    public String getnAMACUST() {
        return nAMACUST;
    }

    public String getnOTELPCUST() {
        return nOTELPCUST;
    }
}
