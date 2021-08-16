package com.example.marquee;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;




public class FirstActivityTest extends FirstActivity {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOnCreateOptionsMenu() {
    }

    @Test
    public void testOnCreate() {
        int s = findViewById(R.id.textView).getWidth();
        assertNull(s);
    }

    @Test
    public void testClass1() {
    }

    @Test
    public void testChangeLEDStatus() {
    }

    @Test
    public void testLight() {
    }

    @Test
    public void testLogText() {
    }

    @Test
    public void testOnResume() {
    }

    @Test
    public void testOnDestroy() {
    }
}