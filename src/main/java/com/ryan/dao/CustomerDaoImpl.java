package com.ryan.dao;

import com.ryan.bean.Customer;
import com.ryan.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-19 3:31 下午
 */
public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDao{
    @Override
    public void insert(Connection conn, Customer customer) {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        update(conn, sql, customer.getName(), customer.getEmail(), customer.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Customer customer) {
        String sql = "update customers set name = ?,email = ?,birth = ? where id = ?";
        update(conn, sql, customer.getName(), customer.getEmail(), customer.getBirth(), customer.getId());
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql = "select id,name,email,birth from customers where id = ?";
        Customer customer = query(conn, sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection conn) {
        String sql = "select id,name,email,birth from customers";
        List<Customer> list = queryMulti(conn, sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql= "select count(*) from customers";
        return querySp(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customers";
        return querySp(conn, sql);
    }
}
