package com.ryan.exer;

import com.ryan.statement.PreparedStatementTest;

import java.util.Scanner;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 6:58 下午
 */
public class Exer2Test {

    public static void main(String[] args) {
        String sql = "insert into examstudent(Type,IDCard,ExamCard,StudentName,Location,Grade) values(?,?,?,?,?,?)";
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入考生的详细信息:");
        System.out.println("Type:");
        int type = scanner.nextInt();
        System.out.println("IDCard:");
        String idCard = scanner.next();
        System.out.println("ExamCard:");
        String examCard = scanner.next();
        System.out.println("StudentName:");
        String studentName = scanner.next();
        System.out.println("Location:");
        String location = scanner.next();
        System.out.println("Grade");
        int grade = scanner.nextInt();
        int update = PreparedStatementTest.update(sql, type, idCard, examCard, studentName, location, grade);
        if(update == 0){
            System.out.println("信息录入失败");
        }else {
            System.out.println("信息录入成功");
        }

    }
}
