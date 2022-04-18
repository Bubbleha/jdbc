package com.ryan.statement;

import com.ryan.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 10:26 上午
 */
public class PreparedStatementTest {

    @Test
    public void test1(){
        // String sql = "delete from customers where id = ?";
        String sql = "update `order` set order_name=? where order_id=?";
        update(sql, "黎明", 2);
    }

    /**
     * 通用的增删改
     */
    public static int update(String sql, Object ...args){
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //2.预编译语句
            statement = conn.prepareStatement(sql);
            //3.填充占位符
            for(int i=0; i < args.length; i++){
                statement.setObject(i+1, args[i]);
            }
            //4.执行
            // statement.execute();
            return statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //5.关闭资源
            JDBCUtils.closeResource(conn, statement, null);
        }
        return 0;
    }


    @Test
    public void insert (){
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();

            //2.预编译sql语句
            String sql = "insert into customers(name,email,birth) values(?,?,?)";
            statement = conn.prepareStatement(sql);

            //3.填充占位符
            statement.setString(1, "哪吒");
            statement.setString(2, "neza@qq.com");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse("2001-01-01");
            statement.setDate(3, new java.sql.Date(date.getTime()));

            //4.执行操作
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, statement, null);
        }

    }

    @Test
    public void testUpdate(){
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql
            String sql = "update customers set name=? where id=?";
            statement = conn.prepareStatement(sql);
            //3.填充占位符
            statement.setObject(1, "莫扎特");
            statement.setObject(2, 1);
            //4.执行
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //5.关闭资源
            JDBCUtils.closeResource(conn, statement, null);
        }

    }


}
