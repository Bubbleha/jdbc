package com.ryan.exer;

import com.ryan.statement.PreparedStatementTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 4:59 下午
 */
public class Exer1Test {
    public static void main(String[] args) throws ParseException {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入姓名:");
        String name = scanner.nextLine();
        System.out.println("请输入邮箱:");
        String email = scanner.nextLine();
        System.out.println("请输入出生年月:");
        String birth = scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(birth);
        int update = PreparedStatementTest.update(sql, name, email, new java.sql.Date(date.getTime()));
        if(update == 0){
            System.out.println("操作失败");
        }else {
            System.out.println("操作成功");
        }
    }


}
