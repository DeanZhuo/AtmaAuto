package com.zhuo.dean.atmaautomobile.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiClient {
    @GET("cabang")
    Call<List<Cabang>> getCabang();

    @GET("sparepart/")
    Call<List<Sparepart>> getSpareparts();

    @GET("sparepart/{KODE_SPAREPART}")
    Call<Sparepart> detailSparepart (@Path("KODE_SPAREPART") int KSparepart);

    @GET("cusByNum/{number}")
    Call<Customer> getCustomer(@Path("number") String noTelp);

    @POST("kendaraanByNum")
    @FormUrlEncoded
    Call<Kendaraan> getKendaraan(@Field("PLAT") String plat);

    @POST("historyCustomer")
    @FormUrlEncoded
    Call<List<Transaksi>> getHistory(@Field("KODE_CUST") int KODE_CUST,
                                     @Field("KODE_KENDARAAN") int KODE_KENDARAAN);
}
