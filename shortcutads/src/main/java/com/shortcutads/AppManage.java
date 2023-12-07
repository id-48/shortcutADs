package com.shortcutads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdsManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.shortcutads.retrofit.SessionManager;

import java.util.ArrayList;

public class AppManage {
    private static final String TAG = "appmanage";
    public static String nativeGoogle1 = "";
    public static String nativeFacebook = "";
    public static String interstitialGoogle1 = "";
    public static String interstitialFacebook = "";
    public static String bannerGoogle1 = "";
    public static String bannerFacebook = "";
    public static InterstitialAd interstitial1;
    public static InterstitialAd interstitial2;
    public static MyCallback myCallback;
    public static AdView googleBannerAd1;
    public static boolean isGoogleBannerLoaded = false;
    public static SessionManager sessionManager;
    public static CustomDialogClass customDialogClass;
    private static Context context;
    private static AppManage mInstance;
    public ArrayList<Object> banner = new ArrayList<>();
    public ArrayList<Object> nativeAds = new ArrayList<>();
    private com.facebook.ads.InterstitialAd fbinterstitialAd1;
    private NativeAdsManager facebookNativeBannerAd;

    private boolean facebookBannerAdLoaded = false;

    private AdView googleBannerAd2;
    private boolean isGoogleBannerLoaded2 = false;
    private com.facebook.ads.AdView facebookBannerAd;

    public AppManage(Context context) {
        this.context = context;
    }

    public static AppManage getInstance(Context contexts) {

        context = contexts;
        if (mInstance == null) {
            mInstance = new AppManage(contexts);
        }
        sessionManager = new SessionManager(contexts);

        customDialogClass = new CustomDialogClass(contexts, R.style.customStyle);
        customDialogClass.setCancelable(false);


        bannerGoogle1 = "";
        nativeGoogle1 = "";
        interstitialGoogle1 = "";


        bannerFacebook = "";
        nativeFacebook = "";
        interstitialFacebook = "";


        return mInstance;
    }

    public void loadAds() {

        Log.d("123123", "loadAdmobinterstial==:  load ad ");
        sessionManager = new SessionManager(context);
        loadAdmobNativeBanner();
        loadAdmobInterstitial();
        loadAdmobBanner();
    }

    public void showNativeBannerAd(Context context, ViewGroup viewGroup, int adLevel) {
        if (nativeAds.size() != 0) {
            Log.d(TAG, "showBannerAd: " + nativeAds.size());
            if (nativeAds.get(0) instanceof NativeAd) {
                new InitNativeAds(context).showBannnerNative((NativeAd) nativeAds.get(0), viewGroup);
                nativeAds.remove(0);
            } else if (nativeAds.get(0) instanceof NativeAdsManager) {
                new InitNativeAds(context).showFbNativeBanner((NativeAdsManager) nativeAds.get(0), viewGroup);
                nativeAds.remove(0);
            }
        } else {
            new AdLoader.Builder(context, nativeGoogle1).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                // com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener
                public void onNativeAdLoaded(NativeAd nativeAd) {
                    Log.e("banner_log", "onNativeAdLoaded: banner show");
                    new InitNativeAds(context).showBannnerNative(nativeAd, viewGroup);
                }
            }).withAdListener(new AdListener() { // from class: com.pesonal.adsdk.AppManage.12
                @Override // com.google.android.gms.ads.AdListener
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    showFbNative(viewGroup);
                }
            }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build().loadAd(new AdRequest.Builder().build());

        }
        Log.d(TAG, "showBannerAd: asas " + nativeAds.size());


    }


    private void showFbNative(ViewGroup viewGroup) {
        NativeAdsManager mNativeAdsManager = new NativeAdsManager(context, nativeFacebook, 1);
        this.facebookNativeBannerAd = mNativeAdsManager;
        mNativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                Log.d(TAG, "onAdsLoaded: fb big show");
                new InitNativeAds(context).showFbNative(facebookNativeBannerAd, viewGroup);
            }


            @Override
            public void onAdError(AdError adError) {
                Log.e(TAG, "AdError: " + adError.getErrorMessage());
            }
        });

        mNativeAdsManager.loadAds(NativeAdBase.MediaCacheFlag.ALL);

    }


    private void showFbNativeBanner(ViewGroup viewGroup) {
        NativeAdsManager mNativeAdsManager = new NativeAdsManager(context, nativeFacebook, 1);
        this.facebookNativeBannerAd = mNativeAdsManager;
        mNativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                Log.d(TAG, "onAdsLoaded: fb banner show");
                new InitNativeAds(context).showFbNativeBanner(facebookNativeBannerAd, viewGroup);
            }


            @Override
            public void onAdError(AdError adError) {
                Log.e(TAG, "AdError: " + adError.getErrorMessage());
            }
        });

        mNativeAdsManager.loadAds(NativeAdBase.MediaCacheFlag.ALL);

    }


    public void loadAdmobNativeBanner() {


        new AdLoader.Builder(context, nativeGoogle1).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                Log.e("banner_log", "onNativeAdLoaded: banner load");

                nativeAds.add(nativeAd);
            }
        }).withAdListener(new AdListener() { // from class: com.pesonal.adsdk.AppManage.12
            @Override // com.google.android.gms.ads.AdListener
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                loadFbNativeBanner();


            }
        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build().loadAd(new AdRequest.Builder().build());

    }


    public void loadFbNativeBanner() {
        NativeAdsManager mNativeAdsManager = new NativeAdsManager(context, nativeFacebook, 1);
        this.facebookNativeBannerAd = mNativeAdsManager;
        mNativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                Log.d(TAG, "onAdsLoaded: fb banner load");
                nativeAds.add(facebookNativeBannerAd);
            }


            @Override
            public void onAdError(AdError adError) {
                Log.e(TAG, "AdError: " + adError.getErrorMessage());
            }
        });

        mNativeAdsManager.loadAds(NativeAdBase.MediaCacheFlag.ALL);

    }


    public void loadAdmobBanner() {
        AdView adView = new AdView(context);
        this.googleBannerAd1 = adView;
        adView.setAdSize(AdSize.BANNER);


        this.googleBannerAd1.setAdUnitId(bannerGoogle1);

        this.googleBannerAd1.loadAd(new AdRequest.Builder().build());
        this.googleBannerAd1.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                googleBannerAd1 = null;

                loadGoogleBanner2();
            }

            @Override
            public void onAdLoaded() {
                banner.add(adView);

                Log.d(TAG, "onAdLoaded: sdsddssd123 " + banner.size());
                AppManage.this.isGoogleBannerLoaded = true;
            }
        });


    }

    private void loadGoogleBanner2() {
        AdView adView = new AdView(context);
        this.googleBannerAd2 = adView;
        adView.setAdSize(AdSize.BANNER);

        this.googleBannerAd2.setAdUnitId("bannerGoogle2");

        this.googleBannerAd2.loadAd(new AdRequest.Builder().build());
        this.googleBannerAd2.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                googleBannerAd2 = null;
                loadFbBanner();
            }

            @Override
            public void onAdLoaded() {
                AppManage.this.isGoogleBannerLoaded2 = true;
                banner.add(adView);
            }
        });
    }

    private void loadFbBanner() {
        facebookBannerAd = new com.facebook.ads.AdView(context, AppManage.bannerFacebook, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                facebookBannerAdLoaded = false;
                facebookBannerAd = null;
            }

            @Override
            public void onAdLoaded(Ad ad) {
                banner.add(facebookBannerAd);
                facebookBannerAdLoaded = true;
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        facebookBannerAd.loadAd(facebookBannerAd.buildLoadAdConfig().withAdListener(adListener).build());

    }

    public void showBannerAd(ViewGroup viewGroup) {
        if (banner.size() != 0) {
            Log.d(TAG, "showBannerAd: " + banner.size());
            if (banner.get(0) instanceof AdView) {
                viewGroup.removeAllViews();
                try {
                    viewGroup.addView((AdView) banner.get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                banner.remove(0);
            } else if (banner.get(0) instanceof com.facebook.ads.AdView) {
                viewGroup.removeAllViews();
                viewGroup.addView((com.facebook.ads.AdView) banner.get(0));
                banner.remove(0);
            }
        } else {
            AdView adView = new AdView(context);
            this.googleBannerAd1 = adView;
            adView.setAdSize(AdSize.BANNER);

            this.googleBannerAd1.setAdUnitId(bannerGoogle1);

            this.googleBannerAd1.loadAd(new AdRequest.Builder().build());
            this.googleBannerAd1.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    googleBannerAd1 = null;
                    showFbBanner(viewGroup);
                }

                @Override
                public void onAdLoaded() {
                    viewGroup.removeAllViews();
                    viewGroup.addView(googleBannerAd1);

                }
            });


        }

        Log.d(TAG, "showBannerAd: asas " + banner.size());


    }


    private void showFbBanner(ViewGroup viewGroup) {
        facebookBannerAd = new com.facebook.ads.AdView(context, AppManage.bannerFacebook, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                facebookBannerAdLoaded = false;
                facebookBannerAd = null;
            }

            @Override
            public void onAdLoaded(Ad ad) {
                viewGroup.removeAllViews();
                viewGroup.addView(facebookBannerAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        facebookBannerAd.loadAd(facebookBannerAd.buildLoadAdConfig().withAdListener(adListener).build());
    }


    public void loadFbInterstitial() {
        if (this.fbinterstitialAd1 == null) {
            this.fbinterstitialAd1 = new com.facebook.ads.InterstitialAd(context, interstitialFacebook);
        }
        if (!this.fbinterstitialAd1.isAdLoaded()) {
            com.facebook.ads.InterstitialAd interstitialAd = this.fbinterstitialAd1;
            interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(new AbstractAdListener() {
                @Override
                public void onAdLoaded(Ad ad) {
                    super.onAdLoaded(ad);
                    Log.d(TAG, "onAdLoaded: 162");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    super.onError(ad, adError);
                    Log.d(TAG, "onError:168 " + ad.toString());
                    Log.d(TAG, "onError: 169" + adError.toString());
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    Log.d(TAG, "onInterstitialDismissed: sdsddssd");
                    interstitialCallBack();
                    super.onInterstitialDismissed(ad);
                }
            }).build());
        }

    }

    public void interstitialCallBack() {

        Log.d("123123", "loadAdmobinterstial: call back");
        MyCallback myCallback2 = myCallback;
        if (myCallback2 != null) {
            myCallback2.callbackCall();
            myCallback = null;
        }
    }

    public void showInterstitialAd(Context context, MyCallback myCallback, ViewGroup viewGroup, int level) {
        this.myCallback = myCallback;
        if (interstitial1 != null || interstitial2 != null || fbinterstitialAd1 != null && fbinterstitialAd1.isAdLoaded()) {
            if (interstitial1 != null) {

                interstitial1.show((Activity) context);
            } else if (interstitial2 != null) {
                interstitial2.show((Activity) context);
            } else if (fbinterstitialAd1 != null) {
                fbinterstitialAd1.show();
            }
        } else {
            customDialogClass.show();

            com.google.android.gms.ads.interstitial.InterstitialAd.load(context, interstitialGoogle1, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() { // from class: com.pesonal.adsdk.AppManage.16
                public void onAdLoaded(com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                    interstitial1 = interstitialAd;
                    customDialogClass.dismiss();

                    interstitial1.show((Activity) context);

                    Log.e("my_log", "onAdLoaded");
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Log.e("my_log", "The ad was dismissed.");
                            interstitial1 = null;
                            interstitialCallBack();

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d("my_log", "The ad was shown.");
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    Log.e("my_log", loadAdError.getMessage());
                    interstitial1 = null;

                    showInterstitialAd2(viewGroup, myCallback);

                }
            });

            interstitial1 = null;
            loadAdmobInterstitial();

            showInterstitialAd2(viewGroup, myCallback);
        }

        interstitialCallBack();

    }

    private void showInterstitialAd2(ViewGroup viewGroup, MyCallback myCallback) {
        this.myCallback = myCallback;

        if (interstitial2 != null) {
            interstitial2.show((Activity) context);
        } else {
            com.google.android.gms.ads.interstitial.InterstitialAd.load(context, "interstitialGoogle2", new AdRequest.Builder().build(), new InterstitialAdLoadCallback() { // from class: com.pesonal.adsdk.AppManage.16
                public void onAdLoaded(com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                    interstitial2 = interstitialAd;
                    customDialogClass.dismiss();
                    interstitial2.show((Activity) context);

                    Log.e("my_log", "onAdLoaded");
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Log.e("my_log", "The ad was dismissed.");
                            interstitial2 = null;
                            interstitialCallBack();

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            Log.d("my_log", "The ad was shown.");
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    Log.e("my_log", loadAdError.getMessage());
                    interstitial2 = null;
                    showFbInterstitial(viewGroup, myCallback);

                }
            });
        }

        interstitial2 = null;
        showFbInterstitial(viewGroup, myCallback);
    }

    private void showFbInterstitial(ViewGroup viewGroup, MyCallback myCallback) {
        this.myCallback = myCallback;

        Log.d(TAG, "showFbInterstitial: sdsdsdds");

        if (fbinterstitialAd1 != null && fbinterstitialAd1.isAdLoaded()) {
            fbinterstitialAd1.show();
            Log.d(TAG, "showFbInterstitial: sdsdssd2");
        } else {
            Log.d(TAG, "showFbInterstitial: sdsdsdds3");
            com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(context, interstitialFacebook);
            interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(new AbstractAdListener() {
                @Override
                public void onAdLoaded(Ad ad) {
                    super.onAdLoaded(ad);
                    interstitialAd.show();
                    customDialogClass.dismiss();

                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    super.onError(ad, adError);
                    Log.d(TAG, "onError:168 " + ad.toString());
                    Log.d(TAG, "onError: 169" + adError.toString());

                    fbinterstitialAd1 = null;
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    super.onInterstitialDismissed(ad);
                    fbinterstitialAd1 = null;
                    interstitialCallBack();
                }
            }).build());
        }

        fbinterstitialAd1 = null;
        loadFbInterstitial();


    }


    public void loadAdmobInterstitial() {

        com.google.android.gms.ads.interstitial.InterstitialAd.load(context, interstitialGoogle1, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() { // from class: com.pesonal.adsdk.AppManage.16
            public void onAdLoaded(com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                interstitial1 = interstitialAd;
                Log.e("my_log", "onAdLoaded");

                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.e("my_log", "The ad was dismissed.");
                        interstitial1 = null;
                        loadAdmobInterstitial();
                        interstitialCallBack();


                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Log.d("my_log", "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.e("my_log", loadAdError.getMessage());
                interstitial1 = null;
            }
        });


    }


    public interface MyCallback {
        void callbackCall();
    }

}


