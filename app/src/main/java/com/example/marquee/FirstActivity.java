package com.example.marquee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
//        隐藏状态栏导航栏
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

    // 点灯

    public void light(Boolean[] b, TextView[] t) {

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    if (b[i]) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightred);
                        }
                    } else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 1:
                    if (b[i]) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightgreen);
                        }
                    } else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 2:
                    if (b[i]) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightblue);
                        }
                    } else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 3:
                    if (b[i]) {
                        if (power) {
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
        TextView[] leds = {findViewById(R.id.textView4), findViewById(R.id.textView6), findViewById(R.id.textView5), findViewById(R.id.textView7)};
        SeekBar[] freqs = {findViewById(R.id.seekBar3), findViewById(R.id.seekBar5), findViewById(R.id.seekBar4), findViewById(R.id.seekBar6)};
        TextView[] fts = {findViewById(R.id.textView), findViewById(R.id.textView13), findViewById(R.id.textView14), findViewById(R.id.textView16)};
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

        class Mhandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        leds[0].setBackgroundResource(R.drawable.lightred);
                        break;
                    case 2:
                        leds[0].setBackgroundResource(R.drawable.light);
                        break;
                }
            }
        }
        Boolean[][] ledMarquee = {
                {true, false, false, false},
                {false, true, false, false},
                {false, false, true, false},
                {false, false, false, true},
        };

        Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable() {

            Message msg = new Message();

            @Override
            public void run() {
                Log.d("TAG", "run: ");

                timerHandler.postDelayed(this, 500);
            }
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
                    new Thread() {
                        @Override
                        public void run() {
                            while (power) {
                                for (Boolean[] status : ledMarquee
                                ) {
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    light(status, leds);
                                }
                            }
                        }
                    }.start();

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
                        Toast.makeText(FirstActivity.this, "LED" + finalI + 1 + " On", Toast.LENGTH_SHORT).show();
                        ledStatus[finalI] = true;
                        if (power) {
                            light(ledStatus, leds);
                        }
                    } else {
                        Toast.makeText(FirstActivity.this, "LED1" + finalI + 1 + " Off", Toast.LENGTH_SHORT).show();
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
                    fts[ii].setText("" + i);

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (power) {
//                        switch (finalI1) {
//                            case 0:
//                                new Thread() {
//                                    @Override
//                                    public void run() {
//                                        while (power) {
//                                            try {
//                                                Thread.sleep(seekBar.getProgress());
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                            leds[finalI].setBackgroundResource(R.drawable.lightred);
//                                            try {
//                                                Thread.sleep(seekBar.getProgress());
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                            leds[finalI].setBackgroundResource(R.drawable.light);
//                                        }
//                                    }
//                                }.start();
//                                break;
//                            case 1:
//                                new Thread() {
//                                    @Override
//                                    public void run() {
//                                        while (power) {
//                                            try {
//                                                Thread.sleep(seekBar.getProgress());
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                            leds[finalI].setBackgroundResource(R.drawable.lightgreen);
//                                            try {
//                                                Thread.sleep(seekBar.getProgress());
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                            leds[finalI].setBackgroundResource(R.drawable.light);
//                                        }
//                                    }
//                                }.start();
//                                break;
//                            case 2:
//                                new Thread() {
//                                    @Override
//                                    public void run() {
//                                        while (power) {
//                                            try {
//                                                Thread.sleep(seekBar.getProgress());
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                            leds[finalI].setBackgroundResource(R.drawable.lightblue);
//                                            try {
//                                                Thread.sleep(seekBar.getProgress());
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                            leds[finalI].setBackgroundResource(R.drawable.light);
//                                        }
//                                    }
//                                }.start();
//                                break;
//                            case 3:
//                                new Thread() {
//                                    @Override
//                                    public void run() {
//                                        while (power) {
//                                            try {
//                                                Thread.sleep(seekBar.getProgress());
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                            leds[finalI].setBackgroundResource(R.drawable.lightup);
//                                            try {
//                                                Thread.sleep(seekBar.getProgress());
//                                            } catch (InterruptedException e) {
//                                                e.printStackTrace();
//                                            }
//                                            leds[finalI].setBackgroundResource(R.drawable.light);
//                                        }
//                                    }
//                                }.start();
//                                break;
//                            default:
//                                break;
//                        }


                        timerHandler.removeCallbacks(timerRunnable);
                        timerHandler.postDelayed(timerRunnable, 500);
                    }
                }
            }));
        }
    }
}