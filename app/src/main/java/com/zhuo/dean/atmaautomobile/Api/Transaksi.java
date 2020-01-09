package com.zhuo.dean.atmaautomobile.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaksi {
    @SerializedName("KODE_TRANSAKSI")
    @Expose
    private Integer kODETRANSAKSI;
    @SerializedName("TANGGAL_TRANSAKSI")
    @Expose
    private String tANGGALTRANSAKSI;
    @SerializedName("KODE_CUST")
    @Expose
    private Integer kODECUST;
    @SerializedName("KODE_STATUS")
    @Expose
    private Integer kODESTATUS;
    @SerializedName("KODE_CABANG")
    @Expose
    private Integer kODECABANG;
    @SerializedName("KODE_KENDARAAN")
    @Expose
    private Integer kODEKENDARAAN;
    @SerializedName("CS")
    @Expose
    private Integer cS;
    @SerializedName("MONTIR")
    @Expose
    private Integer mONTIR;
    @SerializedName("KASIR")
    @Expose
    private Integer kASIR;
    @SerializedName("KODE_LUNAS")
    @Expose
    private Integer kODELUNAS;
    @SerializedName("KODE_PENJUALAN")
    @Expose
    private String kODEPENJUALAN;
    @SerializedName("DISKON")
    @Expose
    private Integer dISKON;

    public Integer getKODETRANSAKSI() {
        return kODETRANSAKSI;
    }

    public void setKODETRANSAKSI(Integer kODETRANSAKSI) {
        this.kODETRANSAKSI = kODETRANSAKSI;
    }

    public String getTANGGALTRANSAKSI() {
        return tANGGALTRANSAKSI;
    }

    public void setTANGGALTRANSAKSI(String tANGGALTRANSAKSI) {
        this.tANGGALTRANSAKSI = tANGGALTRANSAKSI;
    }

    public Integer getKODECUST() {
        return kODECUST;
    }

    public void setKODECUST(Integer kODECUST) {
        this.kODECUST = kODECUST;
    }

    public Integer getKODESTATUS() {
        return kODESTATUS;
    }

    public void setKODESTATUS(Integer kODESTATUS) {
        this.kODESTATUS = kODESTATUS;
    }

    public Integer getKODECABANG() {
        return kODECABANG;
    }

    public void setKODECABANG(Integer kODECABANG) {
        this.kODECABANG = kODECABANG;
    }

    public Integer getKODEKENDARAAN() {
        return kODEKENDARAAN;
    }

    public void setKODEKENDARAAN(Integer kODEKENDARAAN) {
        this.kODEKENDARAAN = kODEKENDARAAN;
    }

    public Integer getCS() {
        return cS;
    }

    public void setCS(Integer cS) {
        this.cS = cS;
    }

    public Integer getMONTIR() {
        return mONTIR;
    }

    public void setMONTIR(Integer mONTIR) {
        this.mONTIR = mONTIR;
    }

    public Integer getKASIR() {
        return kASIR;
    }

    public void setKASIR(Integer kASIR) {
        this.kASIR = kASIR;
    }

    public Integer getKODELUNAS() {
        return kODELUNAS;
    }

    public void setKODELUNAS(Integer kODELUNAS) {
        this.kODELUNAS = kODELUNAS;
    }

    public String getKODEPENJUALAN() {
        return kODEPENJUALAN;
    }

    public void setKODEPENJUALAN(String kODEPENJUALAN) {
        this.kODEPENJUALAN = kODEPENJUALAN;
    }

    public Integer getDISKON() {
        return dISKON;
    }

    public void setDISKON(Integer dISKON) {
        this.dISKON = dISKON;
    }
}
