package com.ryan.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 10:28 上午
 */
public class JDBCUtils {

   private static DataSource dataSourceDruid = null;

    static {
        try {
            Properties properties = new Properties();
            InputStream is = new FileInputStream("src/main/resources/druid.properties");
            properties.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Connection getConnectionDruid(){
        try {
           return dataSourceDruid.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static DataSource dataSource =null;
    static {
        try {
            Properties properties = new Properties();
            InputStream is = ClassLoader.getSystemResourceAsStream("dbcp.properties");
            properties.load(is);
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnectionDBCP(){
        try {
           return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0");

    public static Connection getConnectionC3P0() throws SQLException {
        Connection connection = cpds.getConnection();
        return connection;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
        //1.加载配置文件
        InputStream is = ClassLoader.getSystemResourceAsStream("jdbc.properties");
        Properties properties = new Properties();

        properties.load(is);

        //2.获取配置信息
        String driverClass = properties.getProperty("driverClass");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        Connection conn = null;


        //3.加载驱动
        Class.forName(driverClass);

        //4.获取连接
        conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    public static void closeResource(Connection conn, Statement statement, ResultSet resultSet) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
