package com.ryan.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-20 9:23 上午
 */
public class Druid {
    @Test
    public void testDruid() {
        try {
            Properties properties = new Properties();
            InputStream is = new FileInputStream("src/main/resources/druid.properties");
            properties.load(is);
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
