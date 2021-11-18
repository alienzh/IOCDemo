package com.alien.ioclib.utils;

import android.app.Activity;
import android.view.View;

import com.alien.ioclib.annotation.ContentView;
import com.alien.ioclib.annotation.InjectView;

import java.lang.reflect.Field;

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
    }
}
