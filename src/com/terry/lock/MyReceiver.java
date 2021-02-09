package com.terry.lock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.Toast;

/**
 * 开机自启动
 * @author Administrator
 *
 */
public class MyReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context,OtherService.class);
		context.startService(i);
		Intent b=new Intent(context,Main.class);
		b.setAction(Constant.MAIN_ACTION);
		b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(b);
	}

}
