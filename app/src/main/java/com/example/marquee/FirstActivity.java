package com.example.marquee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
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

import java.text.SimpleDateFormat;
import java.util.Date;
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


    public int[] changeLEDStatus(int[] led){

        for (int i = 0; i < 4; i++) {
            if (led[i]!=-1){
                led[i]=1-led[i];
            }
        }
        return led;

    }
    // 点灯

    public void light(int[] b, TextView[] t) {

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    if (b[i]==1) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightred);
                        }
                    } else if (b[i]==-1){
                        break;
                    }else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 1:
                    if (b[i]==1) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightgreen);
                        }
                    } else if (b[i]==-1){
                        break;
                    } else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 2:
                    if (b[i]==1) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightblue);
                        }
                    } else if (b[i]==-1){
                        break;
                    } else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 3:
                    if (b[i]==1) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightup);
                        }
                    } else if (b[i]==-1){
                        break;
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

    @SuppressLint("SetTextI18n")
    public void logText(String s){
        TextView t = findViewById(R.id.textView10);
        t.setMovementMethod(ScrollingMovementMethod.getInstance());
        Date day=new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        t.append(df.format(day)+" "+s+"\n");

        int line = t.getLineCount();
        if (line > 14) {
            int offset = (t.getLineCount()-2) * t.getLineHeight();
            t.scrollTo(0, offset - t.getHeight() + t.getLineHeight());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView[] leds = {findViewById(R.id.textView4), findViewById(R.id.textView6),
                findViewById(R.id.textView5), findViewById(R.id.textView7)};
        SeekBar[] freqs = {findViewById(R.id.seekBar3), findViewById(R.id.seekBar5),
                findViewById(R.id.seekBar4), findViewById(R.id.seekBar6),
                findViewById(R.id.seekBar2)};
        TextView[] fts = {findViewById(R.id.textView), findViewById(R.id.textView13),
                findViewById(R.id.textView14), findViewById(R.id.textView16),
                findViewById(R.id.textView9)};
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch powerBtn
                = findViewById(R.id.switch6);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch marqueeBtn
                = findViewById(R.id.switch1);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led1 = findViewById(R.id.switch2);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led2 = findViewById(R.id.switch3);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led3 = findViewById(R.id.switch4);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led4 = findViewById(R.id.switch5);
        Switch[] ss = {led1, led2, led3, led4};
        int[]ledStatus = {0, 0, 0, 0};
        int[][] ledMarquee = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
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
                    logText("Power On");
                    light(ledStatus, leds);

                } else {
                    Toast.makeText(FirstActivity.this, "Power Off", Toast.LENGTH_SHORT).show();
                    power = false;
                    logText("Power Off");
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
                int ledid = msg.what;

                switch (ledid){
                    case 0:
                        leds[msg.what].setBackgroundResource(R.drawable.lightred);
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                light(changeLEDStatus(ledStatus),leds);
                            }
                        }, 0,msg.arg1, TimeUnit.MILLISECONDS);
                        break;
                    case 1:
                        leds[msg.what].setBackgroundResource(R.drawable.lightgreen);
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                light(changeLEDStatus(ledStatus),leds);
                            }
                        }, 0,msg.arg1, TimeUnit.MILLISECONDS);
                        break;
                    case 2:
                        leds[msg.what].setBackgroundResource(R.drawable.lightblue);
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                light(changeLEDStatus(ledStatus),leds);
                            }
                        }, 0,msg.arg1, TimeUnit.MILLISECONDS);
                        break;
                    case 3:
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("TAG", "run: "+leds[3].getBackground().toString());
                                leds[3].setBackgroundResource(R.drawable.lightup);
                            }
                        }, 0,msg.arg1, TimeUnit.MILLISECONDS);
                        break;
                    case 4:
                            final int[] iii = {0};
                            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                                @Override
                                public void run() {
                                    iii[0] = iii[0] +1;
                                    light(ledMarquee[(iii[0])%4],leds);
                                    Log.d("TAG", "run: 11111111111111111111111111");
                                }
                            }, 0,msg.arg1, TimeUnit.MILLISECONDS);
                            break;

                    default:
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
                    logText("Marquee is running.");
                    scheduleTaskExecutor.schedule(
                    new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = 4;
                                message.arg1 = 1000;
                                mHandler.sendMessage(message);
                            }
                        },0,TimeUnit.SECONDS);

                } else {
                    Toast.makeText(FirstActivity.this, "Marquee Off", Toast.LENGTH_SHORT).show();
                    marqueeFlag = false;
                    logText("Marquee stopped.");
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
                        ledStatus[finalI] = 1;
                        if (power) {
                            light(ledStatus, leds);
                            logText("LED"+(finalI+1)+" Lighted.");
                        }
                    } else {
                        Toast.makeText(FirstActivity.this, "LED1" + finalI + 1 + " Off", Toast.LENGTH_SHORT).show();
                        ledStatus[finalI] = 0;
                        light(ledStatus, leds);
                        if (power){
                            logText("LED"+(finalI+1)+" Went Out.");
                        }
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
                        if (ii<4){
                            logText("LED"+(ii+1)+" is Running at "+seekBar.getProgress());
                        }else {
                            if (marqueeFlag){
                                logText("Marquee is Running at "+seekBar.getProgress());
                            }
                        }
                        if (seekBar.getProgress()!=0){
                            if (power){
                                if (ii<4){
                                    scheduleTaskExecutor.schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            Message message = new Message();
                                            message.what = ii;
                                            message.arg1 = seekBar.getProgress();
                                            mHandler.sendMessage(message);
                                        }
                                    },0,TimeUnit.SECONDS);
                                }else {
                                    if (marqueeFlag){
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

                            }

                        }
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