package com.fei.aspectjdemo;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import androidx.fragment.app.Fragment;

/**
 * @ClassName: SectionAspect
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2021-02-06 16:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-02-06 16:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
//@Aspect 标志切面的处理类
//@Pointcut标志切点是谁，后面跟符合切点的规则。
@Aspect
public class SectionAspect {

//    @Pointcut
//            ("execution(* com.game.xiangxuemytest.aspectj.AspectJActivity.aspectTest(..))")
//    public void pointActionMethod() {}
//
//    @Before("pointActionMethod()")
//    public void testBefore(){
//        Log.i("AspectJActivity","AspectUtil method1 Before");
//    }

    /**
     * 找到@CheckNet切点
     */
    @Pointcut("execution(@com.fei.aspectjdemo.CheckNet * *(..))")
    public void checkBehavior() {
        Log.e("tag", "checkBehavior");
    }

    Long time;

    @Before("checkBehavior()")
    public void checkBefore() {
        time = System.currentTimeMillis();
        Log.e("tag", "checkBefore");
    }

    /**
     * 处理切点
     */
    @Around("checkBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("tag", "checkNet");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        CheckNet annotation = methodSignature.getMethod().getAnnotation(CheckNet.class);
        if (annotation != null) {
            Object object = joinPoint.getThis();
            Context context = getContext(object);
            if (context != null) {
                //可以在这里处理业务
                if (!isNetworkAvailable(context)) {
                    // 3.没有网络不要往下执行
                    Toast.makeText(context, "请检查您的网络", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        }
        return joinPoint.proceed();
    }

    @After("checkBehavior()")
    public void checkAfter() {
        Log.e("tag", "checkAfter");
        long l = System.currentTimeMillis() - time;
        Log.e("tag", "运行时间" + l);
    }


    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
