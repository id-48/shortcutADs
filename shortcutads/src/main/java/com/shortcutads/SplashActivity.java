package com.shortcutads;

import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.AudienceNetworkAds;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.shortcutads.retrofit.Const;
import com.shortcutads.retrofit.GamesRoot;
import com.shortcutads.retrofit.RestResponce;
import com.shortcutads.retrofit.RetrofitBuilder;
import com.shortcutads.retrofit.SessionManager;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Text101", "onCreate: oncreate sdk splace");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AudienceNetworkAds.initialize(this);
        sessionManager = new SessionManager(this);

        getGames();

    }


    private void getGames() {
        Call<GamesRoot> call = RetrofitBuilder.ads().getGames(Const.DEVKEY);
        call.enqueue(new Callback<GamesRoot>() {
            @Override
            public void onResponse(Call<GamesRoot> call, Response<GamesRoot> response) {
                if (response.code() == 200 && response.body().isStatus()) {
                    sessionManager.saveGames(response.body());
                    Log.d("TAG", "gamesze: " + response.body().getGame().size());
                }
            }

            @Override
            public void onFailure(Call<GamesRoot> call, Throwable t) {
                Log.d("TAG", "onFailure: 3 " + t);

            }
        });
    }


    public void getData(String appkey, ResponceLisner responceLisner, String backgroundColor, String buttonColor, String btnTextColor, String textTitleColor, String textBodyColor) {


        Call<RestResponce> call = RetrofitBuilder.ads().getApp(appkey, Const.DEVKEY);

        call.enqueue(new Callback<RestResponce>() {
            @Override
            public void onResponse(Call<RestResponce> call, Response<RestResponce> response) {
                if (response.code() == 200 && response.body() != null && response.body().getMessage() != null) {

                    Log.d("spash>>>>", "onResponse: ===============");

                    byte[] data = Base64.decode(response.body().getMessage(), Base64.DEFAULT);


                    try {
                        String text = new String(data, "UTF-8");

                        JsonParser parser = new JsonParser();
                        JsonElement mJson = parser.parse(text);


                        AdvertisementRoot.AppData host = new Gson().fromJson(mJson, AdvertisementRoot.AppData.class);
                        Log.e("TAG", "onResponse111: " + host.toString());

                        sessionManager.saveBooleanValue(Const.Avs_Downloded, true);
                        sessionManager.saveAds(host.getApp().getAds());
                        sessionManager.saveApp(host.getApp());
                        sessionManager.saveSetting(host.getSetting());
                        sessionManager.saveCustomADs(host.getCustomAd());
                        sessionManager.saveCustomNativeADs(host.getCustomNativeAd());

                        sessionManager.saveStringValue(Const.OneSingnal, host.getSetting().getOneSignalId());

                        sessionManager.saveStringValue(Const.backgroundColor, backgroundColor);
                        sessionManager.saveStringValue(Const.buttonColor, buttonColor);
                        sessionManager.saveStringValue(Const.btnTextColor, btnTextColor);
                        sessionManager.saveStringValue(Const.textTitleColor, textTitleColor);
                        sessionManager.saveStringValue(Const.textBodyColor, textBodyColor);

                        if (sessionManager.getSetting().isIsShortVideoShow()) {
                            Log.d("TAG", "isloaderrrrr  77");
                            AppManage.getInstance(SplashActivity.this).loadAds();
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                responceLisner.sucsses();
                            }
                        }, 5000);


                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<RestResponce> call, Throwable t) {

            }
        });


    }

    public interface ResponceLisner {
        void sucsses();

        void fail();
    }


}