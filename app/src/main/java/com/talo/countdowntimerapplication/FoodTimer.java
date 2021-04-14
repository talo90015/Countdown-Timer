package com.talo.countdowntimerapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class FoodTimer extends AppCompatActivity {

    //可自定義時間
    private static final long START_TIME_MILLIS = 0;    //總時間秒數(millis)

    private TextView showCountDownTime;
    private Button btn_Start_and_Pause; //開始和暫停
    private Button btn_Restart;         //重製時間

    private Button btnSpaghetti;
    private Button btnCookies;
    private Button btnFrench_fries;

    private Toolbar toolbar;

    private CountDownTimer countDownTimer;

    private SoundPool soundPool;
    private int music;
    private int clickMusic;

    private boolean timerRunning;

    private long timeLeftInMillis = START_TIME_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_timer);
        init();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showCountDownTime = findViewById(R.id.showCountDownTime);   //顯示時間元件

        btnSpaghetti= findViewById(R.id.btnSpaghetti);   //煮義大利麵時間
        btnCookies = findViewById(R.id.btnCookies);   //烤餅乾時間
        btnFrench_fries = findViewById(R.id.btnFrench_fries);   //炸薯條食間

        btn_Start_and_Pause = findViewById(R.id.btn_Start_and_Pause);   //開始和暫停元件
        btn_Restart = findViewById(R.id.btn_Restart);   //重製按鈕

        btn_Start_and_Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
                if (timerRunning){
                    pauseTime();
                }else{
                    startTime();
                }
            }
        });

        btn_Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
                restartTime();
                btn_Start_and_Pause.setVisibility(View.INVISIBLE);
            }
        });
        updateCountTimeText();

        btnSpaghetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeftInMillis = 720000;
                showCountDownTime.setText("12:00");
                btn_Start_and_Pause.setVisibility(View.VISIBLE);

            }
        });

        btnCookies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeftInMillis = 600000;
                showCountDownTime.setText("10:00");
                btn_Start_and_Pause.setVisibility(View.VISIBLE);
            }
        });

        btnFrench_fries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeftInMillis = 420000;
                showCountDownTime.setText("07:00");
                btn_Start_and_Pause.setVisibility(View.VISIBLE);
            }
        });
    }

    //開始
    private void startTime(){
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
    private void pauseTime(){
        countDownTimer.cancel();
        timerRunning = false;
        btn_Start_and_Pause.setText(R.string.continues);
        btn_Restart.setVisibility(View.VISIBLE);    //暫停按下恢復顯示
    }
    //重製
    private void restartTime(){
        timeLeftInMillis = START_TIME_MILLIS;
        updateCountTimeText();
        btn_Restart.setVisibility(View.INVISIBLE);  //重製時間按下消失
        btn_Start_and_Pause.setVisibility(View.VISIBLE);
        btn_Start_and_Pause.setText(R.string.start);
    }
    //更新時間
    private void updateCountTimeText(){
        int minutes = (int) (timeLeftInMillis / 1000) / 60; //分
        int seconds = (int) (timeLeftInMillis / 1000) % 60; //秒

        //格式化時間
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        showCountDownTime.setText(timeLeftFormatted);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    private void init(){
        soundPool = new SoundPool.Builder().build();
        music = soundPool.load(this, R.raw.music, 1);
        clickMusic = soundPool.load(this, R.raw.click, 1);
    }
    private void play(){
        soundPool.play(music, 1, 1, 0, 1, 1);
    }
    private void clickButton(){
        soundPool.play(clickMusic, 1, 1, 0, 0, 1);
    }

}