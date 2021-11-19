package com.alien.ioclib.utils;

import android.app.Activity;
import android.view.View;

import com.alien.ioclib.annotation.ContentView;
import com.alien.ioclib.annotation.EventBase;
import com.alien.ioclib.annotation.InjectView;
import com.alien.ioclib.proxy.EventProxyHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class InjectUtil {
    public static void inject(Activity activity) {
        injectLayout(activity);
        injectView(activity);
        injectEvents(activity);
    }

    private static void injectLayout(Activity activity) {
        Class<?> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int id = contentView.value();
            activity.setContentView(id);
        }
    }

    private static void injectView(Activity activity) {
        Class<?> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView == null) {
                continue;
            }
            int id = injectView.value();
            View view = activity.findViewById(id);
            field.setAccessible(true);
            try {
                field.set(activity, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 获取所有的方法
        // getDeclaredMethod*()获取的是类自身声明的所有方法，包含public、protected和private方法。
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 获取方法上对应的注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType == null) {
                    continue;
                }
                // 获取的注解上的注解
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    continue;
                }
                // 事件要素
                // "setOnClickListener"
                String listenerSetter = eventBase.listenerSetter();
                // View.OnClickListener.class
                Class<?> listenerType = eventBase.listenerType();
                // "onClick"
                String listenerMethod = eventBase.listenerMethod();
                // 方法拦截的对应关系
                HashMap<String, Method> methodMap = new HashMap<>();
                methodMap.put(listenerMethod, method);
                //  通过方法拿到事件源
                try {
                    // 拿到注解内方法 int[] value()
                    Method mt = annotationType.getDeclaredMethod("value");
                    // 拿到对应的值 数组
                    int[] ids = (int[]) mt.invoke(annotation);
                    if (ids == null) {
                        continue;
                    }
                    for (int id : ids) {
                        View view = activity.findViewById(id);
                        if (view == null) {
                            continue;
                        }
                        // 得到 setOnClickListener
                        // getMethod*()获取的是类的所有共有方法，这就包括自身的所有public方法，和从基类继承的、从接口实现的所有public方法。
                        Method listenerSetterMethod = view.getClass().getMethod(
                                listenerSetter, listenerType);
                        // 得到View.OnClickListener
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class[]{listenerType}, new EventProxyHandler(activity, methodMap));
                        listenerSetterMethod.invoke(view, proxy);
                    }
                } catch (NoSuchMethodException | IllegalAccessException |
                        InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}