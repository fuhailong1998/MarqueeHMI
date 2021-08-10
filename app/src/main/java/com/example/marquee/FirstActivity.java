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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author leon
 */
public class FirstActivity extends AppCompatActivity {

    private ScheduledExecutorService scheduleTaskExecutor;
    //多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中之一没有捕获抛出的异常，其它任务便会自动终止运行，使用ScheduledExecutorService则没有这个问题。
    //ScheduledExecutorService,是基于线程池设计的定时任务类,每个调度任务都会分配到线程池中的一个线程去执行,也就是说,任务是并发执行,互不影响。
    //只有当调度任务来的时候,ScheduledExecutorService才会真正启动一个线程,其余时间ScheduledExecutorService都是出于轮询任务的状态。

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
        SeekBar[] freqs = {findViewById(R.id.seekBar3), findViewById(R.id.seekBar5), findViewById(R.id.seekBar4), findViewById(R.id.seekBar6), findViewById(R.id.seekBar2)};
        TextView[] fts = {findViewById(R.id.textView), findViewById(R.id.textView13), findViewById(R.id.textView14), findViewById(R.id.textView16), findViewById(R.id.textView9)};
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

        @SuppressLint("HandlerLeak") Handler mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        Log.d("TAG", "handleMessage: "+msg.arg1);
                        leds[msg.what].setBackgroundResource(R.drawable.lightred);
                        break;
                    case 1:
                        Log.d("TAG", "handleMessage: "+msg.arg1);
                        leds[msg.what].setBackgroundResource(R.drawable.lightgreen);
                        break;
                    case 2:
                        Log.d("TAG", "handleMessage: "+msg.arg1);
                        leds[msg.what].setBackgroundResource(R.drawable.lightblue);
                        break;
                    case 3:
                        Log.d("TAG", "handleMessage: "+msg.arg1);
                        leds[msg.what].setBackgroundResource(R.drawable.lightup);
                        scheduleTaskExecutor.schedule(new Runnable() {
                            @Override
                            public void run() {
                                leds[msg.what].setBackgroundResource(R.drawable.light);
                                Log.d("TAG", "run: ????????????");
                            }
                        }, 500, TimeUnit.MICROSECONDS);
                        break;
                    default:
                        Log.d("TAG", "handleMessage: "+msg.arg1);

                        break;
                }


            }
        };
        scheduleTaskExecutor = new ScheduledThreadPoolExecutor(5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        marqueeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this, "Marquee On", Toast.LENGTH_SHORT).show();
                    marqueeFlag = true;
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = 1;
                                mHandler.sendMessage(message);
                            }
                        }, 0, 500, TimeUnit.MICROSECONDS);

                } else {
                    Toast.makeText(FirstActivity.this, "Marquee Off", Toast.LENGTH_SHORT).show();
                    marqueeFlag = false;

                }
            }
        });

//        LED开关
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
//        检测Switch滑动
        for (int i = 0; i < 5; i++) {
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
                        scheduleTaskExecutor.schedule(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = ii;
                                message.arg1 = seekBar.getProgress();
                                mHandler.sendMessage(message);
                            }
                        },0,TimeUnit.SECONDS);
                    }
                }
            }));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scheduleTaskExecutor != null && !scheduleTaskExecutor.isShutdown()) {
            scheduleTaskExecutor.shutdown();
        }
    }
}