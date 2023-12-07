package com.shortcutads;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;

import com.bumptech.glide.Glide;
import com.shortcutads.retrofit.Const;
import com.shortcutads.retrofit.SessionManager;

import java.util.Collections;


public class CustomRoundView extends LinearLayout {

    private static final String TAG = "customad";
    private AdvertisementRoot.CustomAd customadnative;


    public CustomRoundView(Context context) {
        super(context);
        new CustomAdsSmall(getContext(), this);
    }

    public CustomRoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        new CustomAdsSmall(getContext(), this);
    }

    public CustomRoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        new CustomAdsSmall(getContext(), this);
    }


    @SuppressLint("NewApi")
    public CustomRoundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        new CustomAdsSmall(getContext(), this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Log.d("TAG_AA", "onWindowFocusChanged: has " + hasWindowFocus);

    }

    public class CustomAdsSmall {
        private final Context context;
        private final LinearLayout adLayout;
        SessionManager sessionManager;
        private ImageView imageview;


        public CustomAdsSmall(Context context, LinearLayout adLayout) {
            this.context = context;
            this.adLayout = adLayout;
            sessionManager = new SessionManager(context);

            if (sessionManager.getCustomAd() != null && sessionManager.getCustomAd().isRoundShow()) {
                if (sessionManager.getCustomNatvie() != null && !sessionManager.getCustomNatvie().isEmpty()) {
                    customadnative = sessionManager.getCustomAd();
                    initAd();
                }
            }

        }

        private void initAd() {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_native_round, null, false);

            this.imageview = view.findViewById(R.id.roundbanner);


            if (sessionManager.getCustomAd().isRoundShow()) {
                if (sessionManager.getCustomAd().getCustomRoundImage() != null && !sessionManager.getCustomAd().getCustomRoundImage().isEmpty()) {
                    Log.d(TAG, "initAd: asasasas");
                    Collections.shuffle(sessionManager.getCustomAd().getCustomRoundImage());
                    Glide.with(context).load(Const.BASE_URL_Avs + sessionManager.getCustomAd().getCustomRoundImage().get(0)).into(imageview);

                    if (sessionManager.getCustomAd().getCustomRoundImage().get(0) != null
                            && !sessionManager.getCustomAd().getCustomRoundImage().get(0).isEmpty()) {
                        Glide.with(context).load(Const.BASE_URL_Avs + sessionManager.getCustomAd().getCustomRoundImage().get(0)).into(imageview);

                        Log.d("TAG", "iomage:124" + sessionManager.getCustomAd().getCustomRoundImage().get(0));
                        adLayout.removeAllViews();
                        adLayout.addView(view);


                        imageview.setOnClickListener(view1 -> {

                            Log.d(TAG, "initAd: 127");
                            if (sessionManager.getCustomAd() != null &&
                                    sessionManager.getCustomAd().getCustomRoundLink() != null &&
                                    !sessionManager.getCustomAd().getCustomRoundLink().isEmpty()
                                    && !sessionManager.getCustomAd().getCustomRoundLink().equals("null")) {

                                Uri uri = Uri.parse(sessionManager.getCustomAd().getCustomRoundLink());
                                Log.d("TAG", "link:roungimg " + uri);
                                Log.d("TAG", "link:roungimg " + sessionManager.getCustomAd().getCustomRoundLink());

                                Intent intent = new Intent(getContext(), Intent.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                                CustomTabsIntent.Builder customTabsBuilder = new CustomTabsIntent.Builder();
                                customTabsBuilder.addMenuItem("Close", pendingIntent);
                                CustomTabsIntent customTabsIntent = customTabsBuilder.build();
                                customTabsIntent.intent.setPackage(Const.CHROME_PACKAGE_NAME);
                                customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                customTabsIntent.launchUrl(getContext(), uri);

                            }
                        });
                    }


                }
            }
        }
    }
}
