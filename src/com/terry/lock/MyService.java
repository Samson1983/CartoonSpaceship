package com.terry.lock;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 未使用
 * @author Administrator
 *
 */
public class MyService extends Service {
	private ScreenOFFReceiver offReceiver;
	private ScreenONReceiver onReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Toast.makeText(MyService.this, "开启服务成功", Toast.LENGTH_LONG).show();
//		Toast.makeText(MyService.this, "ddf", Toast.LENGTH_LONG).show();
		super.onCreate();
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d("H3c", "in Server");
				//开机自动解锁
				KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
				KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
				keyguardLock.disableKeyguard();
				
				//注册闭屏广播
				IntentFilter mFilter01;
				mFilter01 = new IntentFilter("android.intent.action.SCREEN_OFF");
				offReceiver = new ScreenOFFReceiver();
				registerReceiver(offReceiver, mFilter01);
				
				//注册开屏广播
				IntentFilter mFilter02;
				mFilter02 = new IntentFilter("android.intent.action.SCREEN_ON");
				onReceiver = new ScreenONReceiver();
				registerReceiver(onReceiver, mFilter02);
			}
		}).start();
	}
	
	public class ScreenOFFReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.d("H3c","screen off");
		}
		
	}
	
	public class ScreenONReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("H3c","screen on");
			//开屏后解锁
			KeyguardManager keyguardManager = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
			KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
			keyguardLock.disableKeyguard();
		}
	}
}
