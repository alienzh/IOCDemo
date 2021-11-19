package com.alien.ioclib.proxy;

import android.app.Activity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class EventProxyHandler implements InvocationHandler {

    private Activity activity;
    private HashMap<String, Method> methodMap;

    public EventProxyHandler(Activity activity, HashMap<String, Method> methodMap) {
        this.activity = activity;
        this.methodMap = methodMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method method1 = methodMap.get(method.getName());
        if (method1 != null) {
            return method1.invoke(activity, args);
        }
        return method.invoke(proxy, args);
    }
}
