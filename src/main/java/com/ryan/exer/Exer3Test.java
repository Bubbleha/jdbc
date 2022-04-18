package com.ryan.exer;

import com.ryan.bean.ExamStudent;
import com.ryan.statement.CustomersQuery;

import java.util.Scanner;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 7:16 下午
 */
public class Exer3Test {
    public static void main(String[] args) {
        String idSql = "select FlowID `flowId`,Type `type`,IDCard `idCard`,ExamCard`examCard`,StudentName `studentName`,Location `location`,Grade `grade` from examstudent where IDCard=?";
        String examSql = "select FlowID `flowId`,Type `type`,IDCard `idCard`,ExamCard`examCard`,StudentName `studentName`,Location `location`,Grade `grade` from examstudent where ExamCard=?";
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择您要输入的类型:\n a:准备考证号 \n b:身份证号");
        String s = scanner.next();
        if("a".equals(s)){
            System.out.println("请输入准考证号:");
            String examCard = scanner.next();
            ExamStudent e = CustomersQuery.query(ExamStudent.class, examSql, examCard);
            if(e == null){
                System.out.println("查无此人!请重新进入程序");
            }else {
                System.out.println("=========查询结果=========");
                System.out.println("流水号:\t\t" + e.getFlowId() + "\n" + "四级/六级\t" +
                        e.getType() +"\n" +"身份证号:\t" + e.getIdCard() +"\n" + "准考证号:\t" +
                        e.getExamCard() +"\n学生姓名:\t" + e.getStudentName() + "\n区域:\t\t" +
                        e.getLocation() + "\n成绩:\t\t" + e.getGrade());
            }
        }else if ("b".equals(s)){
            System.out.println("请输入身份证号:");
            String idCard = scanner.next();
            ExamStudent e1 = CustomersQuery.query(ExamStudent.class, idSql, idCard);
            if(e1 == null){
                System.out.println("查无此人!请重新进入程序");
            }else {
                System.out.println("=========查询结果=========");
                System.out.println("流水号:\t\t" + e1.getFlowId() + "\n" + "四级/六级\t" +
                        e1.getType() +"\n" +"身份证号:\t" + e1.getIdCard() +"\n" + "准考证号:\t" +
                        e1.getExamCard() +"\n学生姓名:\t" + e1.getStudentName() + "\n区域:\t\t" +
                        e1.getLocation() + "\n成绩:\t\t" + e1.getGrade());
            }

        }else {
            System.out.println("输入有误!请重新进入程序");
        }

    }
}
