package com.ryan.statement;

import com.ryan.bean.Customer;
import com.ryan.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 2:10 下午
 */
public class CustomersQuery {


    @Test
    public void test3(){
        String sql = "select id,name,email,birth from customers where id<?";
        List<Customer> list = queryMulti(Customer.class, sql, 5);
        list.forEach(System.out::println);
    }

    /**
     * 通用查询多条记录
     */
    public static  <T> List<T> queryMulti(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句
            statement = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            //4.执行
            resultSet = statement.executeQuery();
            //5.获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //6.根据元数据获columnCount和columnLabel
            int columnCount = metaData.getColumnCount();
            ArrayList<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T t = clazz.newInstance();
                for (int j = 0; j < columnCount; j++) {
                    //6.1获取列值
                    Object columnVal = resultSet.getObject(j + 1);
                    //6.2获取列的别名
                    String columnLabel = metaData.getColumnLabel(j + 1);
                    //6.3利用反射给属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
        } finally {
            //7.关闭资源
            JDBCUtils.closeResource(conn, statement, resultSet);
        }
        return null;
    }

    @Test
    public void test2(){
        String sql = "select id,name,email,birth from customers where id =?";
        Customer customer = query(Customer.class, sql, 3);
        System.out.println(customer);
    }

    /**
     * 通用表的查询一条记录
     */
    public static  <T> T query(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句
            statement = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            //4.执行
            resultSet = statement.executeQuery();
            //5.获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //6.根据元数据获columnCount和columnLabel
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                T t = clazz.newInstance();
                for (int j = 0; j < columnCount; j++) {
                    //6.1获取列值
                    Object columnVal = resultSet.getObject(j + 1);
                    //6.2获取列的别名
                    String columnLabel = metaData.getColumnLabel(j + 1);
                    //6.3利用反射给属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnVal);
                }
                return t;
            }
        } catch (Exception e) {
        } finally {
            //7.关闭资源
            JDBCUtils.closeResource(conn, statement, resultSet);
        }
        return null;
    }

    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customers where nme=?";
            statement = conn.prepareStatement(sql);
            statement.setObject(1, "陈道明");
            //执行并返回结果集
            resultSet = statement.executeQuery();
            //处理结果集
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);
                // System.out.println("id= " + id "name" + name, "email" + email, "birth" + birth);
                Customer customer = new Customer();
                customer.setId(id);
                customer.setName(name);
                customer.setEmail(email);
                customer.setBirth(birth);
                System.out.println(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, statement, resultSet);
        }

    }
}
