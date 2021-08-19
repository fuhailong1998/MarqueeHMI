package com.example.marquee;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
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


import com.android.dragonboard.service.IDragonBoardService;
import com.example.marqueeservice.LedStatus;
import com.example.marqueeservice.LedStatusService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author leon
 */
public class FirstActivity extends AppCompatActivity {
    private IDragonBoardService ledStatusService;
    ServiceConnection connection;
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


        connection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ledStatusService = IDragonBoardService.Stub.asInterface(service);;

                Log.d("TAG", "onServiceConnected: ");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.android.dragonboard.service","com.android.dragonboard.service.DragonBoardService");
        intent.setComponent(componentName);
        bindService(intent,connection,BIND_AUTO_CREATE);

//        bindService(new Intent(MainActivity.this, LedService.class), connection, Context.BIND_AUTO_CREATE);

    }

    boolean power = false;
    boolean marqueeFlag = false;

    private double lastx, lastY;

    public static boolean testClass() {
        return true;
    }


    public int[] changeLEDStatus(int[] led) {

        for (int i = 0; i < 4; i++) {
            if (led[i] != -1) {
                led[i] = 1 - led[i];
            }
        }
        return led;

    }
    // 点灯

    public void light(int[] b, TextView[] t) {

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    if (b[i] == 1) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightred);
                        }
                    } else if (b[i] == -1) {
                        break;
                    } else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 1:
                    if (b[i] == 1) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightgreen);
                        }
                    } else if (b[i] == -1) {
                        break;
                    } else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 2:
                    if (b[i] == 1) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightblue);
                        }
                    } else if (b[i] == -1) {
                        break;
                    } else {
                        t[i].setBackgroundResource(R.drawable.light);
                    }
                    ;
                    break;
                case 3:
                    if (b[i] == 1) {
                        if (power) {
                            t[i].setBackgroundResource(R.drawable.lightup);
                        }
                    } else if (b[i] == -1) {
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
    public void logText(String s) {
        TextView t = findViewById(R.id.textView10);
        t.setMovementMethod(ScrollingMovementMethod.getInstance());
        Date day = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        t.append(df.format(day) + " " + s + "\n");

        int line = t.getLineCount();
        if (line > 14) {
            int offset = (t.getLineCount() - 2) * t.getLineHeight();
            t.scrollTo(0, offset - t.getHeight() + t.getLineHeight());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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
        int[] ledStatus = {0, 0, 0, 0};
        int[][] ledMarquee = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
        };

        TextView floatWin = findViewById(R.id.floatWin);
        TextView wifiBtn = findViewById(R.id.textView11);
        TextView bltBtn = findViewById(R.id.textView12);

        @SuppressLint({"WrongViewCast", "UseSwitchCompatOrMaterialCode"}) Switch bluetooth = findViewById(R.id.switch8);
        @SuppressLint({"WrongViewCast", "UseSwitchCompatOrMaterialCode"}) Switch wifi = findViewById(R.id.switch7);

        bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                        try {
                            ledStatusService.setStatus(1, false);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }


                    Log.d("TAG", "onCheckedChanged: ");
                }

            }
        });

        final int[] nd = {0};


        floatWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nd[0] %2==1){
                    bluetooth.setVisibility(View.INVISIBLE);
                    wifi.setVisibility(View.INVISIBLE);
                }else {
                    bluetooth.setVisibility(View.VISIBLE);
                    wifi.setVisibility(View.VISIBLE);
                }
                nd[0] = nd[0] + 1;

            }
        });

        floatWin.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                double x = motionEvent.getRawX();
                double y = motionEvent.getRawY();

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        bluetooth.setVisibility(View.INVISIBLE);
                        wifi.setVisibility(View.INVISIBLE);
                        lastx = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_UP:

                        floatWin.performClick();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        double dx = x - lastx;
                        double dy = y - lastY;
                        Log.d("TAG", "onTouch: dx==" + dx + ",dy==" + dy);

                        floatWin.setTranslationX((float) (floatWin.getTranslationX() + dx));
                        floatWin.setTranslationY((float) (floatWin.getTranslationY() + dy));
                        lastx = x;
                        lastY = y;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


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

//        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
//        Inspection '线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。' options

        scheduleTaskExecutor = new ScheduledThreadPoolExecutor(5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        @SuppressLint("HandlerLeak") Handler mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                int ledid = msg.what;

                switch (ledid) {
                    case 0:
                        final int[] i = {0};
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                i[0] = i[0] + 1;
                                if (i[0] % 2 == 0) {
                                    leds[0].setBackgroundResource(R.drawable.light);
                                } else {
                                    leds[0].setBackgroundResource(R.drawable.lightred);
                                }
                            }
                        }, 0, msg.arg1, TimeUnit.MILLISECONDS);
                        break;
                    case 1:
                        final int[] ii = {0};
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                ii[0] = ii[0] + 1;
                                if (ii[0] % 2 == 0) {
                                    leds[1].setBackgroundResource(R.drawable.light);
                                } else {
                                    leds[1].setBackgroundResource(R.drawable.lightgreen);
                                }
                            }
                        }, 0, msg.arg1, TimeUnit.MILLISECONDS);
                        break;
                    case 2:
                        final int[] iii = {0};
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                iii[0] = iii[0] + 1;
                                if (iii[0] % 2 == 0) {
                                    leds[2].setBackgroundResource(R.drawable.light);
                                } else {
                                    leds[2].setBackgroundResource(R.drawable.lightblue);
                                }
                            }
                        }, 0, msg.arg1, TimeUnit.MILLISECONDS);
                        break;
                    case 3:
                        final int[] iiii = {0};
                        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                iiii[0] = iiii[0] + 1;
                                if (iiii[0] % 2 == 0) {
                                    leds[3].setBackgroundResource(R.drawable.light);
                                } else {
                                    leds[3].setBackgroundResource(R.drawable.lightup);
                                }
                            }
                        }, 0, msg.arg1, TimeUnit.MILLISECONDS);
                        break;
                    case 4:

                        final int[] iiiii = {0};

                        Runnable runnable1 = () -> {
                            boolean flag = true;
                            light(ledMarquee[(iiiii[0]) % 4], leds);
                            iiiii[0] = iiiii[0] + 1;

                        };

                        scheduleTaskExecutor.scheduleAtFixedRate(runnable1, 0, msg.arg1, TimeUnit.MILLISECONDS);


                        break;

                    default:
                        break;
                }


            }
        };


        marqueeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this, "Marquee On", Toast.LENGTH_SHORT).show();
                    marqueeFlag = true;
                    logText("Marquee is running.");
                    Message message = new Message();
                    message.what = 4;
                    message.arg1 = 1000;
                    mHandler.sendMessage(message);

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
                            logText("LED" + (finalI + 1) + " Lighted.");
                        }
                    } else {
                        Toast.makeText(FirstActivity.this, "LED1" + finalI + 1 + " Off", Toast.LENGTH_SHORT).show();
                        ledStatus[finalI] = 0;
                        light(ledStatus, leds);
                        if (power) {
                            logText("LED" + (finalI + 1) + " Went Out.");
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
                        if (ii < 4) {
                            logText("LED" + (ii + 1) + " is Running at " + seekBar.getProgress());
                        } else {
                            if (marqueeFlag) {
                                logText("Marquee is Running at " + seekBar.getProgress());
                            }
                        }
                        if (seekBar.getProgress() != 0) {
                            if (power) {
                                if (ii < 4) {
                                    Message message = new Message();
                                    message.what = ii;
                                    message.arg1 = seekBar.getProgress();
                                    mHandler.sendMessage(message);
                                } else {
                                    if (marqueeFlag) {
                                        Message message = new Message();
                                        message.what = ii;
                                        message.arg1 = seekBar.getProgress();
                                        mHandler.sendMessage(message);
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