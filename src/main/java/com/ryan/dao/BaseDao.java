package com.ryan.dao;

import com.ryan.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-19 2:57 下午
 */
public abstract class BaseDao<T> {

    public Class<T> clazz = null;

    {
        ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] arguments = parameterizedType.getActualTypeArguments();
        Class<T> aClass = (Class<T>) arguments[0];
        clazz = aClass;
    }

    /**
     * 查询特殊值
     */
    public <E> E querySp(Connection conn, String sql, Object... args) {
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        try {
            prepareStatement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                prepareStatement.setObject(i + 1, args[i]);
            }

            resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                return (E) resultSet.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, prepareStatement, resultSet);
        }
        return null;
    }

    /**
     * 通用查询多条记录
     */
    public  List<T> queryMulti(Connection conn, String sql, Object... args) {
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
            JDBCUtils.closeResource(null, statement, resultSet);
        }
        return null;
    }

    /**
     * 通用表的查询一条记录
     */
    public T query(Connection conn, String sql, Object... args) {
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
            JDBCUtils.closeResource(null, statement, resultSet);
        }
        return null;
    }

    /**
     * 通用的增删改
     */
    public int update(Connection conn, String sql, Object... args) {

        PreparedStatement statement = null;

        try {
            //1.预编译语句
            statement = conn.prepareStatement(sql);
            //2.填充占位符
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            //3.执行
            // statement.execute();
            return statement.executeUpdate();
        } catch (Exception e) {

        } finally {
            //4.关闭资源
            JDBCUtils.closeResource(null, statement, null);
        }

        return 0;
    }
}
