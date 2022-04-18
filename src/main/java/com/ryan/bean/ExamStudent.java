package com.ryan.bean;

/**
 * @description:
 * @author: Bubble
 * @create: 2022-04-18 7:18 下午
 */
public class ExamStudent {
    int flowId;//流水号
    int type; //四六级
    String idCard; //身份证号
    String examCard; //学生证号
    String studentName; //学生姓名
    String location; //地址
    int grade; //成绩

    @Override
    public String toString() {
        return "ExamStudent{" +
                "flowId=" + flowId +
                ", type=" + type +
                ", idCard='" + idCard + '\'' +
                ", examCard='" + examCard + '\'' +
                ", studentName='" + studentName + '\'' +
                ", location='" + location + '\'' +
                ", grade=" + grade +
                '}';
    }

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
