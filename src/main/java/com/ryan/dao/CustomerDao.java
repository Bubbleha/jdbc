package com.ryan.dao;

import com.mysql.cj.jdbc.exceptions.ConnectionFeatureNotAvailableException;
import com.ryan.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-19 3:14 下午
 */
public interface CustomerDao {
    /**
     * 将customer对象保存数据库中
     * @param conn
     * @param customer
     */
    void insert(Connection conn, Customer customer);

    /**
     * 根据用户id删除数据
     * @param conn
     * @param id
     */
    void deleteById(Connection conn, int id);

    /**
     * 更新customer对象在数据库中数据
     * @param conn
     * @param customer
     */
    void update(Connection conn, Customer customer);

    /**
     * 根据用户id查询用户信息
     * @param conn
     * @param id
     * @return
     */
    Customer getCustomerById(Connection conn, int id);

    /**
     * 查询整张表的用户信息
     * @param conn
     * @return
     */
    List<Customer> getAll(Connection conn);

    /**
     * 统计用户数
     * @param conn
     * @return
     */
    Long getCount(Connection conn);

    /**
     * 获取最大生日
     * @param conn
     * @return
     */
    Date getMaxBirth(Connection conn);
}
