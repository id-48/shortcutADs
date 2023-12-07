package com.shortcutads;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdsManager;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.shortcutads.retrofit.Const;
import com.shortcutads.retrofit.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class InitNativeAds {
    Context context;
    SessionManager sessionManager;

    public InitNativeAds(Context context) {
        this.context = context;
        sessionManager = new SessionManager(context);
    }


    public void showBigNative(NativeAd nativeAd, ViewGroup viewGroup) {
        if (nativeAd == null) return;


        View view = LayoutInflater.from(context).inflate(R.layout.google_native_big, null, false);
        NativeAdView adView = view.findViewById(R.id.ad_view);

        adView.setMediaView(adView.findViewById(R.id.ad_media));
        adView.getMediaView().setVisibility(View.GONE);
        adView.setHeadlineView(adView.findViewById(R.id.ad_title));
        adView.setBodyView(adView.findViewById(R.id.ad_description));
        adView.setCallToActionView(adView.findViewById(R.id.btnApply));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));

        ((CardView) view.findViewById(R.id.card)).setCardBackgroundColor(Color.parseColor(sessionManager.getStringValue(Const.backgroundColor)));
        ((TextView) view.findViewById(R.id.ad_title)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.textTitleColor)));
        ((TextView) view.findViewById(R.id.ad_description)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.textBodyColor)));
        ((TextView) view.findViewById(R.id.btnApply)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.btnTextColor)));
        ((TextView) view.findViewById(R.id.btnApply)).getBackground().setTint(Color.parseColor(sessionManager.getStringValue(Const.buttonColor)));
        ((TextView) view.findViewById(R.id.txtad)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.btnTextColor)));
        ((TextView) view.findViewById(R.id.txtad)).getBackground().setTint(Color.parseColor(sessionManager.getStringValue(Const.buttonColor)));

        viewGroup.removeAllViews();
        viewGroup.addView(view, 0);
        populateNativeAdView(nativeAd, adView);

    }

    public void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        if (nativeAd.getHeadline() != null && !nativeAd.getHeadline().isEmpty()) {

            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        }
        if (nativeAd.getBody() != null && !nativeAd.getBody().isEmpty()) {

            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() != null && !nativeAd.getCallToAction().isEmpty()) {

            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getMediaContent() != null) {

            ((MediaView) adView.getMediaView()).setMediaContent(nativeAd.getMediaContent());
            adView.getMediaView().setVisibility(View.VISIBLE);
        } else {

        }

        NativeAd.Image icon = nativeAd.getIcon();
        if (icon == null) {
            adView.getIconView().setVisibility(View.GONE);
        }
        if (icon != null && icon.getDrawable() != null && adView.getIconView() != null) {
            Glide.with(this.context.getApplicationContext()).load(icon.getDrawable()).into((ImageView) adView.getIconView());
            adView.getIconView().setVisibility(View.VISIBLE);
        } else {
            Log.d("TAG", "populateNativeAdView: image null ma.li ");
            try {
                adView.getIconView().setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        adView.setNativeAd(nativeAd);

    }


    public void showBannnerNative(NativeAd nativeAd, ViewGroup viewGroup) {
        if (nativeAd == null) return;
        if (this.context != null) {
            View view = LayoutInflater.from(this.context).inflate(R.layout.google_native_banner, null, false);
            NativeAdView adView = view.findViewById(R.id.ad_view);

            adView.setHeadlineView(adView.findViewById(R.id.ad_title));
            adView.setBodyView(adView.findViewById(R.id.ad_description));
            adView.setCallToActionView(adView.findViewById(R.id.btnApply));
            adView.setMediaView(adView.findViewById(R.id.ad_media));

            ((CardView) view.findViewById(R.id.card)).setCardBackgroundColor(Color.parseColor(sessionManager.getStringValue(Const.backgroundColor)));
            ((TextView) view.findViewById(R.id.ad_title)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.textTitleColor)));
            ((TextView) view.findViewById(R.id.ad_description)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.textBodyColor)));
            ((TextView) view.findViewById(R.id.btnApply)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.btnTextColor)));
            ((TextView) view.findViewById(R.id.btnApply)).getBackground().setTint(Color.parseColor(sessionManager.getStringValue(Const.buttonColor)));
            ((TextView) view.findViewById(R.id.txtad)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.btnTextColor)));
            ((TextView) view.findViewById(R.id.txtad)).getBackground().setTint(Color.parseColor(sessionManager.getStringValue(Const.buttonColor)));


            viewGroup.removeAllViews();
            viewGroup.addView(view, 0);
            populateNativeAdView2(nativeAd, adView);

        }
    }

    private void populateNativeAdView2(NativeAd nativeAd, NativeAdView adView) {
        if (nativeAd.getHeadline() != null && !nativeAd.getHeadline().isEmpty()) {

            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        }
        if (nativeAd.getBody() != null && !nativeAd.getBody().isEmpty()) {

            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() != null && !nativeAd.getCallToAction().isEmpty()) {

            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }


        if (nativeAd.getMediaContent() != null) {

            ((MediaView) adView.getMediaView()).setMediaContent(nativeAd.getMediaContent());
            adView.getMediaView().setVisibility(View.VISIBLE);
        } else {
            adView.getMediaView().setVisibility(View.GONE);
        }

        adView.setNativeAd(nativeAd);

    }

    public void showFbNative(NativeAdsManager facebookNativeBannerAd, ViewGroup viewGroup) {
        com.facebook.ads.NativeAd nativeAd = facebookNativeBannerAd.nextNativeAd();
        if (this.context != null) {

            NativeAdLayout nativeAdLayout = new NativeAdLayout(context);
            viewGroup.removeAllViews();
            if (nativeAd != null) {
                View view = LayoutInflater.from(context).inflate(R.layout.fb_native_big, null, false);

                ((CardView) view.findViewById(R.id.card)).setCardBackgroundColor(Color.parseColor(sessionManager.getStringValue(Const.backgroundColor)));
                ((TextView) view.findViewById(R.id.ad_headlinefb)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.textTitleColor)));
                ((TextView) view.findViewById(R.id.ad_bodyfb)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.textBodyColor)));
                ((TextView) view.findViewById(R.id.ad_call_to_actionfb)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.btnTextColor)));
                ((TextView) view.findViewById(R.id.ad_call_to_actionfb)).getBackground().setTint(Color.parseColor(sessionManager.getStringValue(Const.buttonColor)));
                ((TextView) view.findViewById(R.id.txtad)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.btnTextColor)));
                ((TextView) view.findViewById(R.id.txtad)).getBackground().setTint(Color.parseColor(sessionManager.getStringValue(Const.buttonColor)));


                nativeAdLayout.addView(view);
                NativeAdLayout linearLayout = view.findViewById(R.id.ad_choices_container);
                linearLayout.removeAllViews();
                com.facebook.ads.MediaView mvAdMedia;
                ImageView ivAdIcon;
                TextView tvAdTitle;
                TextView tvAdBody;
                TextView btnAdCallToAction;
                mvAdMedia = (com.facebook.ads.MediaView) view.findViewById(R.id.ad_choices_containerfb);
                ivAdIcon = (ImageView) view.findViewById(R.id.ad_iconfb);
                tvAdTitle = (TextView) view.findViewById(R.id.ad_headlinefb);
                tvAdBody = (TextView) view.findViewById(R.id.ad_bodyfb);
                btnAdCallToAction = (TextView) view.findViewById(R.id.ad_call_to_actionfb);

                tvAdTitle.setText(nativeAd.getAdvertiserName());
                tvAdBody.setText(nativeAd.getAdBodyText());
                btnAdCallToAction.setText("  " + nativeAd.getAdCallToAction());
                btnAdCallToAction.setVisibility(
                        nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
                AdOptionsView adChoicesView = new AdOptionsView(context,
                        nativeAd, nativeAdLayout);


                List<View> clickableViews = new ArrayList<>();

                clickableViews.add(mvAdMedia);
                clickableViews.add(btnAdCallToAction);

                nativeAd.registerViewForInteraction(view, mvAdMedia, ivAdIcon, clickableViews);

                linearLayout.addView(adChoicesView, 0);
                viewGroup.addView(nativeAdLayout, 0);


            }
        }
    }

    public void showFbNativeBanner(NativeAdsManager facebookNativeBannerAd, ViewGroup viewGroup) {

        com.facebook.ads.NativeAd nativeAd = facebookNativeBannerAd.nextNativeAd();

        if (this.context != null) {
            if (nativeAd == null) {
                return;
            }

            NativeAdLayout nativeAdLayout = new NativeAdLayout(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the Ad view.  The layout referenced is the one you created in the last step.
            View adView = inflater.inflate(R.layout.fb_native_banner, nativeAdLayout, false);

            ((CardView) adView.findViewById(R.id.card)).setCardBackgroundColor(Color.parseColor(sessionManager.getStringValue(Const.backgroundColor)));
            ((TextView) adView.findViewById(R.id.ad_headlinefb)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.textTitleColor)));
            ((TextView) adView.findViewById(R.id.ad_bodyfb)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.textBodyColor)));
            ((TextView) adView.findViewById(R.id.ad_call_to_actionfb)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.btnTextColor)));
            ((TextView) adView.findViewById(R.id.ad_call_to_actionfb)).getBackground().setTint(Color.parseColor(sessionManager.getStringValue(Const.buttonColor)));
            ((TextView) adView.findViewById(R.id.txtad)).setTextColor(Color.parseColor(sessionManager.getStringValue(Const.btnTextColor)));
            ((TextView) adView.findViewById(R.id.txtad)).getBackground().setTint(Color.parseColor(sessionManager.getStringValue(Const.buttonColor)));


            nativeAdLayout.addView(adView);


            NativeAdLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);

            adChoicesContainer.removeAllViews();

            TextView nativeAdTitle = adView.findViewById(R.id.ad_headlinefb);
            TextView nativeAdBody = adView.findViewById(R.id.ad_bodyfb);
            com.facebook.ads.MediaView nativeAdIconView = adView.findViewById(R.id.ad_media);
            TextView nativeAdCallToAction = adView.findViewById(R.id.ad_call_to_actionfb);

            nativeAdCallToAction.setVisibility(
                    nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);

            nativeAdTitle.setText(nativeAd.getAdvertiserName());
            nativeAdBody.setText(nativeAd.getAdBodyText());

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(nativeAdTitle);
            clickableViews.add(nativeAdCallToAction);
            nativeAd.registerViewForInteraction(adView, nativeAdIconView, clickableViews);


            viewGroup.removeAllViews();
            viewGroup.addView(nativeAdLayout, 0);
        }
    }
}
