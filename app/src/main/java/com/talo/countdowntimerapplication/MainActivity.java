package com.talo.countdowntimerapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnInstantNoodles;
    private ImageButton btnDrinking;
    private ImageButton btnFood;
    private ImageButton btnSettingTime;

    private static final int REQUEST_CODE_CHECK_GOOGLE_PLAY_SERVICE = 1000;
    //AD記得換上測試ID
    //AD記得換上測試ID
    //AD記得換上測試ID
    //AD記得換上測試ID
    //AD記得換上測試ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkGooglePlayVision();

        btnInstantNoodles = findViewById(R.id.btnInstantNoodles);
        btnInstantNoodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InstantNoodlesTimer.class);
                startActivity(intent);
            }
        });
        btnInstantNoodles.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.instant_noodles_unit_toast,
                        (ViewGroup) findViewById(R.id.instantNoodlesToast));

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER, 0, 200);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
                return true;
            }
        });

        btnDrinking = findViewById(R.id.btnDrinking);
        btnDrinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrinkTimer.class);
                startActivity(intent);
            }
        });
        btnDrinking.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.drink_unit_toast,
                        (ViewGroup) findViewById(R.id.drinkToast));

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER, 0, 200);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
                return true;
            }
        });

        btnFood = findViewById(R.id.btnFood);
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodTimer.class);
                startActivity(intent);
            }
        });
        btnFood.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.food_unit_toast,
                        (ViewGroup) findViewById(R.id.foodToast));
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER, 0, 200);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
                return true;
            }
        });
        btnSettingTime = findViewById(R.id.btnSettingTime);
        btnSettingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingTimer.class);
                startActivity(intent);
            }
        });
        btnSettingTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.setting_timer_toast,
                        (ViewGroup) findViewById(R.id.settingToast));
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP, 0, 150);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view);
                toast.show();
                return true;
            }
        });

        //廣告
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //end

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CHECK_GOOGLE_PLAY_SERVICE:
                if (resultCode == RESULT_CANCELED)
                    //使用者取消處理google play service問題，結束APP
                    showDlgGooglePlayServiceFailAndExitApp();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showDlgGooglePlayServiceFailAndExitApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error);
        builder.setMessage(R.string.APP_error_info);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setPositiveButton(R.string.btn_true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(MainActivity.this, R.string.updateAPP, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void checkGooglePlayVision() {
        GoogleApiAvailability google = GoogleApiAvailability.getInstance();
        int resultCode = google.isGooglePlayServicesAvailable(this);
        if (resultCode == ConnectionResult.SUCCESS) {
            Toast.makeText(this, R.string.new_vision, Toast.LENGTH_SHORT).show();
            return;
        }
        //能否自行排除
        if (google.isUserResolvableError(resultCode)) {
            google.showErrorDialogFragment(this, resultCode, REQUEST_CODE_CHECK_GOOGLE_PLAY_SERVICE);
            return;
        }
        showDlgGooglePlayServiceFailAndExitApp();
    }
}