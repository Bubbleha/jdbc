package com.ryan.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-19 5:17 下午
 */
public class C3P0Test {

    /**
     * 方式二
     */
    @Test
    public void test2() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0");
        System.out.println(cpds.getConnection());
    }

    /**
     * 方式一
     */
    @Test
    public void testC3P0() throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:mysql://192.168.31.240:3306/test?useSSL=false" );
        cpds.setUser("root");
        cpds.setPassword("root");

        Connection conn = cpds.getConnection();
        System.out.println(conn);

        //销毁数据库连接池
        DataSources.destroy( cpds );
    }

}

