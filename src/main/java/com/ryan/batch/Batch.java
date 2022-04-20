package com.ryan.batch;

import com.ryan.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description:批量插入
 * @author: Bubble
 * @create: 2022-04-18 9:52 下午
 */
public class Batch {
    /**设置连接不允许自动提交
     * addBatch(),executeBatch(),clearBatch()
     */
    @Test
    public void testBatchInsert3() {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name) values(?)";
            statement = conn.prepareStatement(sql);
            //设置手动提交
            conn.setAutoCommit(false);
            for (int i = 1; i <= 20000; i++) {
                statement.setObject(1, "name_" + i);
                //攒sql
                statement.addBatch();

                if(i % 500 == 0){
                    //执行batch
                    statement.executeBatch();

                    //清空batch
                    statement.clearBatch();
                }
            }
            //提交
            conn.commit();
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为:" + (end -start));
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, statement, null);
        }

    }

    /**
     * addBatch(),executeBatch(),clearBatch()
     */
    @Test
    public void testBatchInsert2() {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name) values(?)";
            statement = conn.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                statement.setObject(1, "name_" + i);
                //攒sql
                statement.addBatch();

                if(i % 500 == 0){
                    //执行batch
                    statement.executeBatch();

                    //清空batch
                    statement.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为:" + (end -start));
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, statement, null);
        }

    }


    @Test
    public void testBatchInsert1() {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name) values(?)";
            statement = conn.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                statement.setObject(1, "name_" + i);
                statement.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为:" + (end -start));
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, statement, null);
        }

    }
}
