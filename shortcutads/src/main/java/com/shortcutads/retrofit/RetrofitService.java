package com.shortcutads.retrofit;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


public interface RetrofitService {

    @GET("app/appData")
    Call<RestResponce> getApp(@Query("appKey") String appkey, @Header("key") String devkey);

    @GET("/game")
    Call<GamesRoot> getGames(@Header("key") String key);

    @GET("json")
    Call<IpAddressRoot> getIp();

}
