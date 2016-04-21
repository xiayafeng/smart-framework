package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * servlet助手类
 */
public final class ServletHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);
    /**
     * 使每个线程独自拥有一份ServletHelper实例
     */
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_THREAD_LOCAL = new ThreadLocal<ServletHelper>();
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    /**
     * 初始化
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_THREAD_LOCAL.set(new ServletHelper(request, response));
    }
    /**
     * 销毁
     */
    public static void destroy() {
        SERVLET_HELPER_THREAD_LOCAL.remove();
    }
    /**
     * 获取request对象
     */
    public static HttpServletRequest getRequest (){
        return SERVLET_HELPER_THREAD_LOCAL.get().request;
    }
    /**
     * 获取response对象
     */
    public static HttpServletResponse getResponse () {
        return SERVLET_HELPER_THREAD_LOCAL.get().response;
    }
    /**
     * 获取session对象
     */
    public static HttpSession getSession () {
        return getRequest().getSession();
    }
    /**
     * 获取contexttext对象
     */
    public static ServletContext getServletContext () {
        return getRequest().getServletContext();
    }
    /**
     * 将属性放入request中
     */
    public static void setRequestAttribute(String key, Object value) {
        getRequest().setAttribute(key, value);
    }
    /**
     * 从request中获取属性
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String key) {
        return (T) getRequest().getAttribute(key);

    }
    /**
     * 从request中移除属性
     */
    public static void removeRequestAttribute (String key) {
        getRequest().removeAttribute(key);
    }
    /**
     * 发送重定向响应
     */
    public static void sendRedirect (String location) {
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            LOGGER.error("redirect failure", e);
        }
    }
    /**
     *将属性放入session中
     */
    public static void setSessionAttribute(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }
    /**
     * 从session中获取值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String key) {
        return (T) getRequest().getSession().getAttribute(key);
    }
    /**
     * 从session中移除属性
     */
    public static void removeSessionAttribute(String key) {
        getRequest().getSession().removeAttribute(key);
    }
    /**
     * 使session失效
     */
    public static void invalidateSession() {
        getRequest().getSession().invalidate();
    }




















}
