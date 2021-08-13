package com.example.marquee;

import static org.junit.Assert.*;

import android.annotation.SuppressLint;
import android.app.Person;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


public class FirstActivityTest {

    @Rule
    public ActivityTestRule<FirstActivity> mainActivityActivityTestRule = new ActivityTestRule<FirstActivity>(FirstActivity.class);
    private FirstActivity firstActivity = null;

    @Before
    public void setUp() throws Exception {
        firstActivity = mainActivityActivityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test Over.");
    }

    @Test
    public void onCreateOptionsMenu() {
    }

    @Test
    public void onCreate() {

    }

    @Test
    public void changeLEDStatus() {

        int[] status = firstActivity.changeLEDStatus(new int[]{1,0,-1,1});
        assertArrayEquals(status,new int[]{0,1,-1,0});

    }

    @Test
    public void light() {
        firstActivity.power = true;
        TextView[] leds = {firstActivity.findViewById(R.id.textView4), firstActivity.findViewById(R.id.textView6),
                firstActivity.findViewById(R.id.textView5), firstActivity.findViewById(R.id.textView7)};
        int[] ledStatus = {1, 1, 0, 0};

        final Drawable.ConstantState drawable = leds[0].getBackground().getConstantState();
        final Drawable.ConstantState drawable2 = leds[1].getBackground().getConstantState();

        firstActivity.light(ledStatus,leds);

        final Drawable.ConstantState drawable1 = leds[0].getBackground().getConstantState();
        final Drawable.ConstantState drawable3 = leds[1].getBackground().getConstantState();

        assertEquals(drawable,drawable1);
        assertNotEquals(drawable2,drawable3);
    }

    @Test
    public void logText() {
        TextView t = firstActivity.findViewById(R.id.textView10);
        assertNotNull(t.getText());
    }

    @Test
    public void onResume() {
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch powerBtn
                = firstActivity.findViewById(R.id.switch6);
        powerBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            assertFalse(isChecked);
            assertFalse(true);
        });
    }

    @Test
    public void onDestroy() {

    }
}