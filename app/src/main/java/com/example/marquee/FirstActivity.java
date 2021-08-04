package com.example.marquee;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "REMOVE", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

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
        if (actionbar != null){
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


        Button button1 = findViewById(R.id.button);
//        button1.setBackgroundColor(0x66544121);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this,"Good Bye!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch power_btn = findViewById(R.id.switch6);
        power_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this,"Power On",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FirstActivity.this,"Power Off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch marquee_btn = findViewById(R.id.switch1);
        marquee_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this,"Marquee On",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FirstActivity.this,"Marquee Off",Toast.LENGTH_SHORT).show();
                }
            }
        });


        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led1 = findViewById(R.id.switch2);
        led1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this,"LED1 On",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FirstActivity.this,"LED1 Off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led2 = findViewById(R.id.switch3);
        led2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this,"LED2 On",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FirstActivity.this,"LED2 Off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led3 = findViewById(R.id.switch4);
        led3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this,"LED3 On",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FirstActivity.this,"LED3 Off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch led4 = findViewById(R.id.switch5);
        led4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(FirstActivity.this,"LED4 On",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FirstActivity.this,"LED4 Off",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}