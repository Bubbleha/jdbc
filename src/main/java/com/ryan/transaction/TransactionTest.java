package com.ryan.transaction;

import com.ryan.bean.UserTable;
import com.ryan.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 11:04 下午
 */
public class TransactionTest {
    @Test
    public void testTransactionSelect(){
        Connection conn = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            String sql1 = "select user,balance from user_table where user=?";
            conn.setAutoCommit(false);
            //2.查询当前连接隔离级别
            System.out.println(conn.getTransactionIsolation());
            //3.修改当前连接隔离级别
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            System.out.println(conn.getTransactionIsolation());
            //4.执行查询
            UserTable cc = query(conn, UserTable.class, sql1, "CC");
            System.out.println(cc);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            //5.关闭连接
            JDBCUtils.closeResource(conn, null, null);
        }
    }

    @Test
    public void testTransactionUpdate(){
        Connection conn = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            String sql = "update user_table set balance = ? where user=?";

            conn.setAutoCommit(false);
            //2.查询当前连接隔离级别
            System.out.println(conn.getTransactionIsolation());
            //3.修改当前连接隔离级别
            // conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            //4.执行修改
            update(conn, sql, 5000, "CC");
            Thread.sleep(20000);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            //5.关闭连接
            JDBCUtils.closeResource(conn, null, null);
        }
    }

    /**
     * 通用表的查询一条记录
     */
    public static  <T> T query(Connection conn, Class<T> clazz, String sql, Object... args) {
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

    @Test
    public void test1() {
        Connection conn = null;
        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            String sql1 = "update user_table set balance = balance - 100 where user=?";
            String sql2 = "update user_table set balance = balance + 100 where user=?";
            //2.开启事务
            System.out.println(conn.getAutoCommit());
            conn.setAutoCommit(false);
            //3.进行数据库操作
            update(conn, sql1, "AA");
            //模拟网络异常
            System.out.println(10 / 0);
            update(conn, sql2, "BB");
            System.out.println("转账成功");
            //4.若没有异常提交事务
            conn.commit();
        } catch (Exception e) {
            try {
                //5.发生异常,回滚事务
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                //6.恢复每次DML操作后自动提交功能
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //7.关闭连接
            JDBCUtils.closeResource(conn, null, null);
        }

    }


    /**
     * 通用的增删改
     */
    public static int update(Connection conn, String sql, Object... args) {

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
