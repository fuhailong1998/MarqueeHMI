package com.example.marquee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author leon
 */
public class FirstActivity extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    boolean power = false;
    boolean marqueeFlag = false;

    public void light(Boolean[] b, TextView[] t) {

            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case 0:
                        if (b[i]) {
                            if (power){
                                t[i].setBackgroundResource(R.drawable.lightred);
                            }
                        } else {
                            t[i].setBackgroundResource(R.drawable.light);
                        }
                        ;
                        break;
                    case 1:
                        if (b[i]) {
                            if (power){
                                t[i].setBackgroundResource(R.drawable.lightgreen);
                            }
                        } else {
                            t[i].setBackgroundResource(R.drawable.light);
                        }
                        ;
                        break;
                    case 2:
                        if (b[i]) {
                            if (power){
                                t[i].setBackgroundResource(R.drawable.lightblue);
                            }
                        } else {
                            t[i].setBackgroundResource(R.drawable.light);
                        }
                        ;
                        break;
                    case 3:
                        if (b[i]) {
                            if (power){
                                t[i].setBackgroundResource(R.drawable.lightup);
                            }
                        } else {
                            t[i].setBackgroundResource(R.drawable.light);
                        }
                        ;
                        break;
                    default:
                        break;
                }

            }

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView t1 = findViewById(R.id.textView4);
        TextView t2 = findViewById(R.id.textView6);
        TextView t3 = findViewById(R.id.textView5);
        TextView t4 = findViewById(R.id.textView7);
        TextView[] leds = {t1, t2, t3, t4};
        SeekBar s1 = findViewById(R.id.seekBar3);
        SeekBar s2 = findViewById(R.id.seekBar4);
        SeekBar s3 = findViewById(R.id.seekBar5);
        SeekBar s4 = findViewById(R.id.seekBar6);
        SeekBar[] freqs = {s1, s2, s3, s4};
        TextView ft1 = findViewById(R.id.textView);
        TextView ft2 = findViewById(R.id.textView14);
        TextView ft3 = findViewById(R.id.textView13);
        TextView ft4 = findViewById(R.id.textView16);
        TextView[] fts = {ft1, ft2, ft3, ft4};
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch powerBtn = findViewById(R.id.switch6);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch marqueeBtn = findViewById(R.id.switch1);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led1 = findViewById(R.id.switch2);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led2 = findViewById(R.id.switch3);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led3 = findViewById(R.id.switch4);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led4 = findViewById(R.id.switch5);
        Switch[] ss = {led1, led2, led3, led4};
        Boolean[] ledStatus = {false, false, false, false};



        Boolean[][] ledMarquee = {
                {true, false, false, false},
                {false, true, false, false},
                {false, false, true, false},
                {false, false, false, true},
        };



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this, "Good Bye!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        powerBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this, "Power On", Toast.LENGTH_SHORT).show();
                    power = true;
                    light(ledStatus, leds);
                } else {
                    Toast.makeText(FirstActivity.this, "Power Off", Toast.LENGTH_SHORT).show();
                    power = false;
                    for (int i = 0; i < 4; i++) {
                        leds[i].setBackgroundResource(R.drawable.light);
                    }
                }
            }
        });



        marqueeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this, "Marquee On", Toast.LENGTH_SHORT).show();
                    marqueeFlag = true;

                } else {
                    Toast.makeText(FirstActivity.this, "Marquee Off", Toast.LENGTH_SHORT).show();
                    marqueeFlag = false;

                }
            }
        });


        for (int i = 0; i < 4; i++) {

            int finalI = i;
            ss[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(FirstActivity.this, "LED"+ finalI +1+" On", Toast.LENGTH_SHORT).show();
                        ledStatus[finalI] = true;
                        if (power){
                            light(ledStatus, leds);
                        }
                    } else {
                        Toast.makeText(FirstActivity.this, "LED1"+ finalI +1+" Off", Toast.LENGTH_SHORT).show();
                        ledStatus[finalI] = false;
                        light(ledStatus, leds);
                    }
                }
            });
        }


        for (int i = 0; i < 4; i++) {
            int ii = i;
            freqs[i].setOnSeekBarChangeListener((new SeekBar.OnSeekBarChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    fts[ii].setText(""+i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            }));
        }
    }


}