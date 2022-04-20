package com.ryan.datasource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-20 8:52 上午
 */
public class DBCPTest {

    /**
     * 方式二
     * @throws Exception
     */
    @Test
    public void testDBCP2() throws Exception {
        Properties properties = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream("dbcp.properties");
        properties.load(is);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        System.out.println(dataSource.getConnection());
    }

    /**
     * 方式一
     * @throws SQLException
     */
    @Test
    public void testDBCP1() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.31.240:3306/test?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setInitialSize(0);
        dataSource.setMaxIdle(8);
        dataSource.setMinIdle(8);
        System.out.println(dataSource.getConnection());

    }
}
