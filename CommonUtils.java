package com.example.ytz.utils;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2016/11/13 0013.
 *  公共的工具类
 */
public class CommonUtils {
    private static ThreadPoolExecutor sPoolExecutor= (ThreadPoolExecutor) Executors.newCachedThreadPool();
    //1、把耗时操作（任务）放到线程池里面执行
    public static void runInThread(Runnable task){
        sPoolExecutor.execute(task);
    }

    //2、创建公用的Handler
    private static Handler sHandler=new Handler();
    public static Handler getHandler(){
        return sHandler;
    }
    //3、把任务放到主线程中执行
    public static void myRunOnUIThread(Runnable task){
        sHandler.post(task);
    }
    //4、在任意线程里面打印Toast
    private static Toast sToast=null;
    public static void showToast(final Context context, final String text){
        //发送到主线程执行
        myRunOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (sToast==null){
                    sToast=Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
                sToast.setText(text);
                sToast.show();
            }
        });
    }
    //5、dp==>px
    public static float dpToPx(Context context,float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
    //6、sp==>px
    public static float spToPx(Context context,float sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.getResources().getDisplayMetrics());
    }
    //7、浮点数的估值方法
    public static Float evaluateFloat(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
    //8、颜色的估值方法
    public static int evaluateArgb(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                (int)((startB + (int)(fraction * (endB - startB))));
    }
    //10、优先显示昵称
    public static String priorityNameOrJid(String name,String jid){
        if (!TextUtils.isEmpty(name)){
            return name;
        }
        return jid;
    }
    //11、获取当前的时间
    public static String getCurrentTime(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }
    //计算状态栏的高度
    public static int getStatuBarHeight(View view){
        Rect rect=new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }
}

