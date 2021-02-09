package com.terry.lock;



import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */

	private ScreenObserver mScreenObserver;
	
	private int screenWidth;
	private int screenHeight;	
//	private float defaultX;
//	private float defaultY;
	private int earthImgSize = 50;
	private  ImageView airshipView;
	private  ImageView moonView;
	public  ImageView earthView;
	private  ImageView sunView;
	private  Bitmap sunImage;
//	private  Bitmap earthImage;
//	private  Bitmap airshipImage;
	private  Bitmap moonImage;
	private  boolean flag=false;
	public  ArrayList<Integer> imgList = new ArrayList<Integer>(); 
	public Handler handler;
	public int count, // 地球图片编号
			t, // 地球位置编号
			w, h, // imgWorld图像组件的宽和高
			r, // 圆形轨道半径
			x0, y0; // 圆形轨道中心坐标

	public static final double dlt =  (Math.PI / 256); //位置移动大小256最合适
	    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("--------------Main Activity onTouchEvent--------------");
		// TODO Auto-generated method stub
//		int[] location = new int[2];  
//		view.getLocationInWindow(location); //获取view组件的位置
		int x= (int)event.getX()-20;
		int y=(int)event.getY()-50; //为鼠标不在图标中心所以-50
		  LayoutParams Params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT
				  ,x, y);
		  if(flag){			  
			  airshipView.setLayoutParams(Params);
			  
			  //是否碰到月球
			  if (x >moonView.getLeft() &&  x < moonView.getLeft() +moonImage.getWidth()
							&& y >moonView.getTop() &&  y< moonView.getTop()+moonImage.getHeight()){				  
				  
//			  if(event.getAction()==MotionEvent.ACTION_UP){
					Toast.makeText(Main.this,"解锁",2000).show();
					flag=false;
//					unregisterReceiver(LockService.getLockReceiver());
					LockService.setFirstLockFlag(false);// 释放锁屏标记
					finish();
				  }
			  
//			  if(event.getAction()==MotionEvent.ACTION_UP){//恢复飞船位置
//				  LayoutParams Params2=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT
//						  ,(int)defaultX, (int)defaultY);
//				  airshipView.setLayoutParams(Params2);
//			  }
		  }
		  
		return super.onTouchEvent(event);
	}
	

	@Override
    public void onAttachedToWindow() {
//android底层系统把HOME键屏蔽了，但如果发现它是TYPE_KEYGUARD类的窗体，则不会过滤。所以把Activity修改成TYPE_KEYGUARD
//类就好了。 注意android 1.6 不支持屏蔽Home键
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD); 
        super.onAttachedToWindow();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            // TODO Auto-generated method stub
    	  Log.e(Constant.TAG, "onKeyDown:"+keyCode+"");
            switch(keyCode) {
            case KeyEvent.KEYCODE_HOME:
                    Log.e(Constant.TAG, "丫的，看你往哪里跑!");
                    return true;  //屏蔽按键           
            case KeyEvent.KEYCODE_CALL:
            	Log.e(Constant.TAG, "拔电话键");            	
            	return true;  //屏蔽按键
            case KeyEvent.KEYCODE_POWER:
            	Log.e(Constant.TAG, "电源键按下");
            		break;
			case KeyEvent.KEYCODE_ENDCALL:
				Log.e(Constant.TAG, "挂电话键");
				break;
}
            return false;
            
            
    }
    
//    private void doSomethingOnScreenOn() {
//        Log.i(Constant.TAG, "Screen is on");
//       }
//       private void doSomethingOnScreenOff() {
//        Log.i(Constant.TAG, "Screen is off");
//       }
       
//       @Override
//       protected void onDestroy() {
//        super.onDestroy();
//        //停止监听screen状态
//        mScreenObserver.stopScreenStateUpdate();
//       }

 



	@Override
    public void onCreate(Bundle savedInstanceState) {
       Log.d(Constant.TAG, "----------------onCreate----------------");
//       this.setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
       super.onCreate(savedInstanceState);
//       //设置全屏anndroid2.2 不可用
//       requestWindowFeature(Window.FEATURE_NO_TITLE);
//       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//       		WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
		// 获得屏幕尺寸
		DisplayMetrics dm = new DisplayMetrics();
		dm = this.getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
        setContentView(R.layout.main);
        airshipView=(ImageView)findViewById(R.id.image);        
        moonView=(ImageView)findViewById(R.id.moon);        

        
//        mScreenObserver = new ScreenObserver(this);
//        mScreenObserver.requestScreenStateUpdate(new ScreenStateListener() {
//         @Override
//         public void onScreenOn() {
//          doSomethingOnScreenOn();
//         }
//         @Override
//         public void onScreenOff() {
//          doSomethingOnScreenOff();
//         }
//        });
       
        
        /**
         * 启动开机重启服务service
         * http://www.cnblogs.com/xiaoQLu/archive/2012/07/17/2595294.html
         * 包含FLAG_ACTIVITY_NEW_TASK的Intent启动Activity的Task正在运行, 则不会为该Activity创建新的Task,
         */
        Intent i = new Intent(Main.this,OtherService.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(i);
	
	  //禁止Home键
      Main.this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
      
      
      /**启动锁屏service*/
      Intent intent=new Intent(Main.this,LockService.class);
      startService(intent);
        
        airshipView.setOnTouchListener(new OnTouchListener() {
        	
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("--------------ImageView onTouch--------------");
			  flag=true;
//			  defaultX = airshipView.getLeft();
//			  defaultY = airshipView.getTop();
			  
				return false;
			}
		});
        
        LockService.setFirstLockFlag(true); //设置锁屏标记
        
        sunView=(ImageView)findViewById(R.id.sunImage);        
        earthView=(ImageView)findViewById(R.id.earthImage);
        
//		  LayoutParams Params =new LayoutParams(50,50
//				  ,120, 200);
//		 earthImage.setLayoutParams(Params);
		 initImageList(); //读取图片		 
		 initParam();//初始化参数
		 handler = new Handler();
		 Load_DrawImage loadThread = new Load_DrawImage(150, 200,this);//开一条线程读取并绘图
		 handler.post(loadThread);
		 
		 //绕太阳自转
		 final Handler handler2 = new Handler();
		 final Main m = this;
		 Runnable r1 = new Runnable() {
			@Override
			public void run() {
				m.xySite();
				handler2.postDelayed(this, 40);
			}
		};
		
		handler2.post(r1);
        
	}
	
	

/**
 * 初始化图片列表、读取大小
 */
public void initImageList() {
		Log.i("Surface:", "Create");

		// 用反射机制来获取资源中的图片ID和尺寸
		Field[] fields = R.drawable.class.getDeclaredFields();
		for (Field field : fields) {
			if ("icon".indexOf(field.getName()) !=-1 || "moon".indexOf(field.getName()) !=-1
					|| "sun".indexOf(field.getName()) !=-1
					|| "bk".indexOf(field.getName()) !=-1
					|| "airship".indexOf(field.getName()) !=-1
					)// 除了icon之外的图片
			{
				System.out.println("icon or small_ball");
				continue;
			
			}
				int index = 0;
				try {
					index = field.getInt(R.drawable.class);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 保存图片ID
				imgList.add(index);
			
		}
		
//		earthImage = BitmapFactory.decodeResource(getResources(),R.drawable.earth000);                                                                                       
		sunImage = BitmapFactory.decodeResource(getResources(),R.drawable.sun);
//		airshipImage = BitmapFactory.decodeResource(getResources(),R.drawable.airship);
		moonImage = BitmapFactory.decodeResource(getResources(),R.drawable.moon);
		
}

/**
 * 初始化太阳与地球坐标、自转参数
 */
public void initParam() {
      t = -1;
	  x0 = screenWidth / 2 +90;/**y调太阳位置*/
	  y0 = screenHeight / 2 -170 /**x*/;
	  w = earthImgSize; // earthImage.getWidth();
	  h = earthImgSize;// earthImage.getHeight();
	  r = screenWidth / 2 -30;
	  int left = x0 - sunImage.getWidth() / 2;
	  int top = y0 - sunImage.getHeight() /2;	  
	   LayoutParams Params =new LayoutParams(sunImage.getWidth(),sunImage.getHeight()
				  ,top, left);
	   sunView.setLayoutParams(Params);

}

 /**
 * 计算地球自动的坐标
 */
public  void xySite() {
	  t = (t+1) % 512;
	  int left = (int) (Math.round(x0+r*Math.cos(t*dlt))-w / 2);
	  int top = (int) (Math.round(y0-r*Math.sin(t*dlt))-h / 2);
	   LayoutParams Params =new LayoutParams(earthImgSize, earthImgSize,
			   top,left );
	   earthView.setLayoutParams(Params);

}
	
}
