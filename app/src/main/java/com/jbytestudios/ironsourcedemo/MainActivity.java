package com.jbytestudios.ironsourcedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.ads.MobileAds;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.jbytestudios.ironsourcedemo.constants.IronSourceConstants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //Android fields
    private Button btnBannerAd;
    private Button btnInterstitialAd;
    private FrameLayout bannerAdContainer;

    //IS fields
    private IronSourceBannerLayout banner;

    //Objects and primitives
    private boolean isBannerAdShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFields();
        initGoogleAdmob();
        initListenersAndSDK();
        initBtnListeners();
    }

    private void initFields(){
        btnBannerAd = findViewById(R.id.btn_banner_ad);
        btnInterstitialAd = findViewById(R.id.btn_interstitial_ad);
        bannerAdContainer = findViewById(R.id.is_banner_ad_container);
    }

    private void initGoogleAdmob(){
        MobileAds.initialize(this, initializationStatus -> {
        });
    }

    private void initListenersAndSDK(){
        banner = IronSource.createBanner(this, new ISBannerSize(320, 90));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        bannerAdContainer.addView(banner, 0, layoutParams);
        banner.setBannerListener(new BannerListener() {
            @Override
            public void onBannerAdLoaded() {
                banner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onBannerAdLoadFailed(IronSourceError ironSourceError) {
                Log.e(TAG,
                        "There's an error. Error message: " + ironSourceError.getErrorMessage() +
                        " | Error code: " + ironSourceError.getErrorCode() +
                        " | toString(): " + ironSourceError.toString());
            }

            @Override
            public void onBannerAdClicked() {

            }

            @Override
            public void onBannerAdScreenPresented() {

            }

            @Override
            public void onBannerAdScreenDismissed() {

            }

            @Override
            public void onBannerAdLeftApplication() {

            }
        });
        IronSource.loadBanner(banner);
        //banner.setVisibility(View.INVISIBLE);

        IronSource.setInterstitialListener(new InterstitialListener() {
            @Override
            public void onInterstitialAdReady() {

            }

            @Override
            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {

            }

            @Override
            public void onInterstitialAdOpened() {

            }

            @Override
            public void onInterstitialAdClosed() {

            }

            @Override
            public void onInterstitialAdShowSucceeded() {

            }

            @Override
            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {

            }

            @Override
            public void onInterstitialAdClicked() {

            }
        });
        IronSource.loadInterstitial();
        IronSource.isInterstitialReady();

        //SDK init
        IronSource.init(this, IronSourceConstants.IS_APP_KEY, IronSource.AD_UNIT.BANNER, IronSource.AD_UNIT.INTERSTITIAL);
        IntegrationHelper.validateIntegration(this);
    }

    private void initBtnListeners(){
        btnBannerAd.setOnClickListener(view -> {
            //Init banner ad
            if (!isBannerAdShowing){
                banner.setVisibility(View.VISIBLE);
                btnBannerAd.setText(getResources().getString(R.string.hide_banner_ad));
                isBannerAdShowing = true;
            }else{
                banner.setVisibility(View.INVISIBLE);
                btnBannerAd.setText(getResources().getString(R.string.show_banner_ad));
                isBannerAdShowing = false;
            }

        });
        btnInterstitialAd.setOnClickListener(view -> {
            //Init Interstitial ad
            IronSource.showInterstitial();
        });
    }

    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }

}