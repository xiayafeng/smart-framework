package org.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装action信息
 */
public class Handler {
    //controller类
    private Class<?> controllerClass;
    //action方法
    private Method acitonMethod;

    public Handler(Class<?> controllerClass, Method acitonMethod) {
        this.controllerClass = controllerClass;
        this.acitonMethod = acitonMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getAcitonMethod() {
        return acitonMethod;
    }
}
