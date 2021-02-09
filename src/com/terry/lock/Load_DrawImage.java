package com.terry.lock;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
                                                                                                                       
//public class EarthView  { 
class Load_DrawImage extends Thread {   
/** Called when the activity is first created. */                                                                      
Button btnSingleThread, btnDoubleThread;                                                                               
SurfaceView sfv;                                                                                                       
SurfaceHolder sfh;                                                                                                     
ArrayList<Integer> imgList = new ArrayList<Integer>();                                                                 
int imgWidth, imgHeight;                                                                                               
Bitmap bitmap;//独立线程读取，独立线程绘图   

Main m ;
ImageView earthImage;
Handler handler;
  
//public EarthView(Context context,View v) {
//	mainActivity = (Main) context;	
//	earthImage = (ImageView) v;
//	if (imgList.size() == 0)
//		initImageList();
	
	
//	 Animation am = new TranslateAnimation (0,200,0,0);     // 动画开始到结束的执行时间(1000 = 1 秒)   
//	 am. setDuration (500 );    // 动画重复次数(-1 表示一直重复)   
//	 am. setRepeatCount ( -1 );    // 图片配置动画    // 
//	 earthImage. setAnimation (am);   
////	 frame.setAnimation(am);    // 动画开始   
//	 am. startNow ();
   
//}

/*                                                                                                                    
 * 读取并显示图片的线程                                                                                               
 */                                                                                                                   
//class Load_DrawImage extends Thread {                                                                                 
    int x, y;                                                                                                         
    int imgIndex = 0;                                                                                                 
                                                                                                                      
    public Load_DrawImage(int x, int y,Main main) {                                                                             
        this.x = x;                                                                                                   
        this.y = y;    
        this.imgList = main.imgList;
        this.handler = main.handler;
        this.earthImage = main.earthView;
        this.m = main;
    }                                                                                                                 
                                                                                                                      
    public void run() {                                                                                               
//        while (true) {   
//            Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x                                                 
//                    + imgWidth, this.y + imgHeight));                                                                 
//            Bitmap bmImg = BitmapFactory.decodeResource(mainActivity.getResources(),                                               
//                    imgList.get(imgIndex));                                                                           
//            c.drawBitmap(bmImg, this.x, this.y, new Paint());  
    	 
//	  	  m.t = (m.t+1) % 512;
//	  	  int left = (int) (Math.round(m.x0+m.r*Math.cos(m.t*m.dlt))-m.w / 2);
//	  	  int top = (int) (Math.round(m.y0-m.r*Math.sin(m.t*m.dlt))-m.h / 2);
//	  	   LayoutParams Params =new LayoutParams(top,left
//	  			   ,earthImage.getWidth(), earthImage.getHeight());
//	  	   earthImage.setLayoutParams(Params);
  	   
//    	  System.out.println("top:"+earthImage.getTop()+",left:"+earthImage.getLeft());
            earthImage.setBackgroundResource(imgList.get(imgIndex));
            imgIndex++;                                                                                               
            if (imgIndex == imgList.size())                                                                           
                imgIndex = 0;                                                                                         
                                                                                                                      
//            sfh.unlockCanvasAndPost(c);// 更新屏幕显示内容
        	this.handler.postDelayed(this, 40);
        	
//        }                                                                                                             
    }                                                                                                                 
//}

 


 
 
}                                                                                                                         
