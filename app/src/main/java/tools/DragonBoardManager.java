package tools;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.dragonboard.service.IDragonBoardListener;
import com.android.dragonboard.service.IDragonBoardService;


public class DragonBoardManager {


    private static final String TAG = DragonBoardManager.class.getName();

    private static final String SERVICE_PACKAGE_NAME = "com.android.dragonboard.service";
    private static final String SERVICE_CLASS_NAME = "com.android.dragonboard.service.DragonBoardService";

    private static DragonBoardManager sDragonBoardManager;

    private Context mContext;
    private boolean mInit;
    private IDragonBoardStatusListener mDragonBoardStatusListener;

    private IDragonBoardService mDragonBoardService;
    private ServiceConnection mServiceConnection;

    private IDragonBoardListener mDragonBoardListener = new DragonBoardListener();


    /**
     * 构造函数
     */
    private DragonBoardManager() {

    }

    public static final synchronized DragonBoardManager getInstance() {
        if (sDragonBoardManager == null) {
            sDragonBoardManager = new DragonBoardManager();
        }

        return sDragonBoardManager;
    }


    /**
     * 初始化 需要传入context和listener
     *
     * @param context  activity context用于bindservice
     * @param listener 用于返回状态
     */
    public void init(Context context, IDragonBoardStatusListener listener) {

    }


    /**
     * 绑定服务
     */
    private void bindService() {
        //bind service
        Intent intent = new Intent();
        intent.setClassName(SERVICE_PACKAGE_NAME, SERVICE_CLASS_NAME);
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected: ");
                mDragonBoardService = IDragonBoardService.Stub.asInterface(service);
				//TODO
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: ");
				//TODO
            }
        };

        if (mContext != null) {
            mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }


    public void unbindService() {
		//TODO
    }

    public void setStatus(int index, boolean light) {
        Log.d(TAG, "setStatus: index " + index + " light: " + light);
		//TODO
    }

    public void setFrequency(int index, int frequency) {
        Log.d(TAG, "setFrequency: index " + index + " frequency: " + frequency);
		//TODO
    }

    class DragonBoardListener extends IDragonBoardListener.Stub {

        @Override
        public void onChange(int index, int light) throws RemoteException {
            Log.d(TAG, "onChange: " + index + " status: " + light);
			//TODO
        }

        @Override
        public IBinder asBinder() {
            return this;
        }
    }
}
