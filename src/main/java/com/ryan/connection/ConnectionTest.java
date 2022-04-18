package com.ryan.connection;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-17 10:49 下午
 */
public class ConnectionTest {

    @Test
    public void testConnection1() throws SQLException, IOException {
        Driver driver = new com.mysql.cj.jdbc.Driver();

        String url = "jdbc:mysql://192.168.31.240:3306/test?useSSL=false";
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties info = new Properties();
        // info.setProperty("user","root");
        // info.setProperty("password", "root");
        info.load(inputStream);
        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }

    @Test
    public void testConnection2() throws SQLException, IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String className = "com.mysql.cj.jdbc.Driver";
        Class<?> clazz = Class.forName(className);
        Driver driver = (Driver)clazz.newInstance();

        String url = "jdbc:mysql://192.168.31.240:3306/test?useSSL=false";
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties info = new Properties();
        // info.setProperty("user","root");
        // info.setProperty("password", "root");
        info.load(inputStream);
        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }

    @Test
    public void testConnection3()  {
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://192.168.31.240:3306/test?useSSL=false";
        String user = "root";
        String password = "root";
        Connection conn = null;

        try {
            //实例化
            Class<?> clazz = Class.forName(className);
            Driver driver = (Driver)clazz.newInstance();

            //注册驱动
            DriverManager.registerDriver(driver);

            //获取连接
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {

        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println(conn);

    }

    @Test
    public void testConnection4()  {
        String className = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://192.168.31.240:3306/test?useSSL=false";
        String user = "root";
        String password = "root";
        Connection conn = null;

        try {
            //实例化
            Class.forName(className);
            // Driver driver = (Driver)clazz.newInstance();
            //
            // //注册驱动
            // DriverManager.registerDriver(driver);

            //获取连接
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {

        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println(conn);

    }

    @Test
    public void testConnection5()  {
        // String className = "com.mysql.cj.jdbc.Driver";
        // String url = "jdbc:mysql://192.168.31.240:3306/test?useSSL=false";
        // String user = "root";
        // String password = "root";
        //加载配置文件
        InputStream in = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取配置信息
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String className = properties.getProperty("className");

        Connection conn = null;

        try {
            //实例化
            Class.forName(className);
            // Driver driver = (Driver)clazz.newInstance();
            //
            // //注册驱动
            // DriverManager.registerDriver(driver);

            //获取连接
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {

        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println(conn);

    }
}
