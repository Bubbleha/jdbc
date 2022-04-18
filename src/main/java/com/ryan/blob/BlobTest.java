package com.ryan.blob;

import com.ryan.bean.Customer;
import com.ryan.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.*;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 8:12 下午
 */
public class BlobTest {
    @Test
    public void testBlob1(){
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        InputStream is = null;
        FileOutputStream out = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id=?";
            prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setInt(1, 21);
            resultSet = prepareStatement.executeQuery();
            is = null;
            out = null;
            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Date birth = resultSet.getDate("birth");
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
                Blob photo = resultSet.getBlob("photo");
                is = photo.getBinaryStream();
                out = new FileOutputStream("daku.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }

            }
        } catch (Exception e) {

        } finally {
            try {
                is.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(conn, prepareStatement, resultSet);
        }
    }

    @Test
    public void testBlob(){
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        FileInputStream is = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into customers(name,email,birth,photo) values(?,?,?,?)";
            prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setString(1, "芭比裤");
            prepareStatement.setString(2, "babiku@qq.com");
            prepareStatement.setObject(3, "199-01-03");

            is = new FileInputStream("src/main/resources/nevergiveup.jpg");
            prepareStatement.setBlob(4, is);
            prepareStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(conn, prepareStatement, null);
        }


    }
}
