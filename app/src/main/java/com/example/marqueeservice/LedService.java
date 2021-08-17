package com.example.marqueeservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leon
 */
public class LedService extends Service {

    private final Binder mBinder = new LedStatusService.Stub() {
        @Override
        public void changeLedStatus(LedStatus ledStatus) throws RemoteException {
            Log.d("TAG", "changeLedStatus: "+ledStatus);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    public LedService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "Service_onStartCommand executed!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");

    return mBinder;
    }
}