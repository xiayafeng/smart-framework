package org.smart4j.framework.helper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库操作助手类
 */
public final class DatabaseHelper {
    //数据库配置
    private static final String driver = ConfigHelper.getJdbcDriver();
    private static final String url = ConfigHelper.getJdbcUrl();
    private static final String username = ConfigHelper.getJdbcUsername();
    private static final String password = ConfigHelper.getJdbcPassword();
    //用于放置数据库连接的局部线程变量（使每个线程拥有自己的连接）
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
    //日志记录
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    /**
     * 获取连接
     */
    public static Connection getConnection () {
        Connection conn = connContainer.get();
        try {
            if (conn == null) {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connContainer.set(conn);
        }
        return conn;
    }
    /**
     * 开启事务
     */
    public static void beginTransaction () {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {

            }
        }
    }
    /**
     * 提交事务
     */
    public static void commitTransaction () {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {

            }
        }
    }
    /**
     * 回滚事务
     */
    public static void rollbackTransaction () {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction failure", e);
                throw new RuntimeException(e);
            } finally {

            }
        }
    }












}
