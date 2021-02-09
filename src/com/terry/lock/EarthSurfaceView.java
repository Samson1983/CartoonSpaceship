package com.terry.lock;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
                                                                                                                       
public class EarthSurfaceView  extends SurfaceView implements SurfaceHolder.Callback{                                                                        
/** Called when the activity is first created. */                                                                      
Button btnSingleThread, btnDoubleThread;                                                                               
SurfaceView sfv;                                                                                                       
SurfaceHolder sfh;                                                                                                     
ArrayList<Integer> imgList = new ArrayList<Integer>();                                                                 
int imgWidth, imgHeight;                                                                                               
Bitmap bitmap;//独立线程读取，独立线程绘图                                                                             
  
public EarthSurfaceView(Context context) {
    super(context);                                                             
    sfh =  this.getHolder(); 
    sfh.addCallback(this);
   
}

/*                                                                                                                    
 * 读取并显示图片的线程                                                                                               
 */                                                                                                                   
class Load_DrawImage extends Thread {                                                                                 
    int x, y;                                                                                                         
    int imgIndex = 0;                                                                                                 
                                                                                                                      
    public Load_DrawImage(int x, int y) {                                                                             
        this.x = x;                                                                                                   
        this.y = y;                                                                                                   
    }                                                                                                                 
                                                                                                                      
    public void run() {                                                                                               
        while (true) {   
        	try {
            Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x                                                 
                    + imgWidth, this.y + imgHeight));                                                                 
            Bitmap bmImg = BitmapFactory.decodeResource(getResources(),                                               
                    imgList.get(imgIndex));                                                                           
            c.drawBitmap(bmImg, this.x, this.y, new Paint());                                                         
            imgIndex++;                                                                                               
            if (imgIndex == imgList.size())                                                                           
                imgIndex = 0;                                                                                         
                                                                                                                      
            sfh.unlockCanvasAndPost(c);// 更新屏幕显示内容
            
            
				Thread.sleep(200);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
        }                                                                                                             
    }                                                                                                                 
}

@Override
public void surfaceChanged(SurfaceHolder holder, int format, int width,
		int height) {
	// TODO Auto-generated method stub
	
}

@Override
public void surfaceCreated(SurfaceHolder holder) {
		Log.i("Surface:", "Create");

		// 用反射机制来获取资源中的图片ID和尺寸
		Field[] fields = R.drawable.class.getDeclaredFields();
		for (Field field : fields) {
			if ("icon".indexOf(field.getName()) !=-1 || "small_ball".indexOf(field.getName()) !=-1)// 除了icon之外的图片
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
		// 取得图像大小
		Bitmap bmImg = BitmapFactory.decodeResource(getResources(),
				imgList.get(0));
		imgWidth = bmImg.getWidth();
		imgHeight = bmImg.getHeight();
		
		 new Load_DrawImage(150, 200).start();//开一条线程读取并绘图             
	
}

@Override
public void surfaceDestroyed(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	
}  

//@Override                                                                                                              
//public void onCreate(Bundle savedInstanceState) {                                                                      
//  super.onCreate(savedInstanceState);                                                                                
//  setContentView(R.layout.main);                                                                                     
////                                                                                                                     
////  btnSingleThread = (Button) this.findViewById(R.id.Button01);                                                       
////  btnDoubleThread = (Button) this.findViewById(R.id.Button02);                                                       
////  btnSingleThread.setOnClickListener(new ClickEvent());                                                              
////  btnDoubleThread.setOnClickListener(new ClickEvent());                                                              
//  sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01);                                                         
//  sfh = sfv.getHolder();     
//  sfh.addCallback(this);
////  sfh.addCallback(new MyCallBack());// 自动运行surfaceCreated以及surfaceChanged   
//  new Load_DrawImage(0, 0).start();//开一条线程读取并绘图              
//} 

//class ClickEvent implements View.OnClickListener {                                                                     
//                                                                                                                       
//    @Override                                                                                                          
//    public void onClick(View v) {                                                                                      
//                                                                                                                       
//        if (v == btnSingleThread) {                                                                                    
//            new Load_DrawImage(0, 0).start();//开一条线程读取并绘图                                                    
//        } else if (v == btnDoubleThread) {                                                                             
//            new LoadImage().start();//开一条线程读取                                                                   
//            new DrawImage(imgWidth + 10, 0).start();//开一条线程绘图                                                   
//        }                                                                                                              
//                                                                                                                       
//    }                                                                                                                  
//                                                                                                                       
//}                                                                                                                      
                                                                                                                       
//class MyCallBack implements SurfaceHolder.Callback {                                                                   
//                                                                                                                       
//    @Override                                                                                                          
//    public void surfaceChanged(SurfaceHolder holder, int format, int width,                                            
//            int height) {                                                                                              
//        Log.i("Surface:", "Change");                                                                                   
//                                                                                                                       
//    }                                                                                                                  
//                                                                                                                       
//    @Override                                                                                                          
//    public void surfaceCreated(SurfaceHolder holder) {                                                                 
////        Log.i("Surface:", "Create");                                                                                   
////                                                                                                                       
////        // 用反射机制来获取资源中的图片ID和尺寸                                                                        
////        Field[] fields = R.drawable.class.getDeclaredFields();                                                         
////        for (Field field : fields) {                                                                                   
////            if (!"icon".equals(field.getName()))// 除了icon之外的图片                                                  
////            {                                                                                                          
////                int index = 0;                                                                                         
////                try {                                                                                                  
////                    index = field.getInt(R.drawable.class);                                                            
////                } catch (IllegalArgumentException e) {                                                                 
////                    // TODO Auto-generated catch block                                                                 
////                    e.printStackTrace();                                                                               
////                } catch (IllegalAccessException e) {                                                                   
////                    // TODO Auto-generated catch block                                                                 
////                    e.printStackTrace();                                                                               
////                }                                                                                                      
////                // 保存图片ID                                                                                          
////                imgList.add(index);                                                                                    
////            }                                                                                                          
////        }                                                                                                              
////        // 取得图像大小                                                                                                
////        Bitmap bmImg = BitmapFactory.decodeResource(getResources(),                                                    
////                imgList.get(0));                                                                                       
////        imgWidth = bmImg.getWidth();                                                                                   
////        imgHeight = bmImg.getHeight();                                                                                 
//    }                                                                                                                  
//                                                                                                                       
//    @Override                                                                                                          
//    public void surfaceDestroyed(SurfaceHolder holder) {                                                               
//        Log.i("Surface:", "Destroy");                                                                                  
//                                                                                                                       
//     }                                                                                                                 
//                                                                                                                       
// }                                                                                                                     
                                                                                                                       
                                                                                                                
                                                                                                                       
 /*                                                                                                                    
  * 只负责绘图的线程                                                                                                   
  */                                                                                                                   
// class DrawImage extends Thread {                                                                                      
//     int x, y;                                                                                                         
//                                                                                                                       
//     public DrawImage(int x, int y) {                                                                                  
//         this.x = x;                                                                                                   
//         this.y = y;                                                                                                   
//     }                                                                                                                 
//                                                                                                                       
//     public void run() {                                                                                               
//         while (true) {                                                                                                
//             if (bitmap != null) {//如果图像有效                                                                       
//                 Canvas c = sfh.lockCanvas(new Rect(this.x, this.y, this.x                                             
//                         + imgWidth, this.y + imgHeight));                                                             
//                                                                                                                       
//                 c.drawBitmap(bitmap, this.x, this.y, new Paint());                                                    
//                                                                                                                       
//                 sfh.unlockCanvasAndPost(c);// 更新屏幕显示内容                                                        
//             }                                                                                                         
//         }                                                                                                             
//     }                                                                                                                 
// };                                                                                                                    
                                                                                                                       
 /*                                                                                                                    
  * 只负责读取图片的线程                                                                                               
  */                                                                                                                   
// class LoadImage extends Thread {                                                                                      
//     int imgIndex = 0;                                                                                                 
//                                                                                                                       
//     public void run() {                                                                                               
//         while (true) {                                                                                                
//             bitmap = BitmapFactory.decodeResource(getResources(),                                                     
//                     imgList.get(imgIndex));                                                                           
//             imgIndex++;                                                                                               
//             if (imgIndex == imgList.size())//如果到尽头则重新读取                                                     
//                 imgIndex = 0;                                                                                         
//         }                                                                                                             
//     }                                                                                                                 
// }; 
 
}                                                                                                                         
