package com.talo.countdowntimerapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SettingTimer extends AppCompatActivity {

    //可自定義時間
    private long START_TIME_MILLIS = 0;    //總時間秒數(millis)

    private TextView showCountDownTime;
    private Button btn_Start_and_Pause; //開始和暫停
    private Button btn_Restart;         //重製時間
    private Toolbar toolbar;

    private CountDownTimer countDownTimer;

    private SoundPool soundPool;
    private int music;
    private int clickMusic;

    private boolean timerRunning;

    private long timeLeftInMillis = START_TIME_MILLIS;

    //時間設定框
    private ImageButton btnSetting;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_timer);
        init();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showCountDownTime = findViewById(R.id.showCountDownTime);   //顯示時間元件

        btn_Start_and_Pause = findViewById(R.id.btn_Start_and_Pause);   //開始和暫停元件
        btn_Restart = findViewById(R.id.btn_Restart);   //重製按鈕

        btn_Start_and_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
                if (timerRunning) {
                    pauseTime();
                } else {
                    startTime();
                }
            }
        });

        btn_Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
                restartTime();
            }
        });
        updateCountTimeText();

        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(btnSettingClick);
    }

    //開始
    private void startTime() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountTimeText();
            }

            @Override
            public void onFinish() {
                showCountDownTime.setText("00:00");
                timerRunning = false;
                btn_Start_and_Pause.setText(R.string.start);
                btn_Start_and_Pause.setVisibility(View.INVISIBLE);  //開始按下消失
                btn_Restart.setVisibility(View.VISIBLE);
                play();
            }
        }.start();

        timerRunning = true;
        btn_Start_and_Pause.setText(R.string.pause);
        btn_Restart.setVisibility(View.INVISIBLE);  //按下開始消失
    }

    //暫停
    private void pauseTime() {
        countDownTimer.cancel();
        timerRunning = false;
        btn_Start_and_Pause.setText(R.string.continues);
        btn_Restart.setVisibility(View.VISIBLE);    //暫停按下恢復顯示
    }

    //重製
    private void restartTime() {
        timeLeftInMillis = START_TIME_MILLIS;
        updateCountTimeText();
        btn_Restart.setVisibility(View.INVISIBLE);  //重製時間按下消失
        btn_Start_and_Pause.setVisibility(View.VISIBLE);
        btn_Start_and_Pause.setText(R.string.start);

    }

    //更新時間
    private void updateCountTimeText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60; //分
        int seconds = (int) (timeLeftInMillis / 1000) % 60; //秒

        //格式化時間
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        showCountDownTime.setText(timeLeftFormatted);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    private void init() {
        soundPool = new SoundPool.Builder().build();
        music = soundPool.load(this, R.raw.music, 1);
        clickMusic = soundPool.load(this, R.raw.click, 1);
    }

    private void play() {
        soundPool.play(music, 1, 1, 0, 1, 1);
    }
    private void clickButton(){
        soundPool.play(clickMusic, 1, 1, 0, 0, 1);
    }

    //設定倒數時間
    private View.OnClickListener btnSettingClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog = new Dialog(SettingTimer.this);
            dialog.setTitle("設定倒數時間");
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.countdown_timer_setting);

            final NumberPicker numMinutes = dialog.findViewById(R.id.numMinutes);
            final NumberPicker numSecond = dialog.findViewById(R.id.numSecond);
            //最小值
            numMinutes.setMinValue(0);
            numSecond.setMinValue(0);

            numMinutes.setMaxValue(99);
            numSecond.setMaxValue(59);

            numMinutes.setValue(0);
            numSecond.setValue(0);


            Button btnTimer = dialog.findViewById(R.id.btnTimer);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int min = numMinutes.getValue();
                    int sec = numSecond.getValue();
                    int getMin = min * 60 * 1000;
                    int getSec = sec * 1000;
                    START_TIME_MILLIS = getMin + getSec;
                    timeLeftInMillis = START_TIME_MILLIS;
                    //Toast.makeText(SettingTimer.this,  String.valueOf(START_TIME_MILLIS), Toast.LENGTH_SHORT).show();
                    String settingTimer = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                    showCountDownTime.setText(settingTimer);
                    dialog.dismiss();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    };

}