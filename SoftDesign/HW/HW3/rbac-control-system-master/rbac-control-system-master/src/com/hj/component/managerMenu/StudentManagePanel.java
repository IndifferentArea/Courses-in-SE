package com.hj.component.managerMenu;

import com.hj.component.MyJPanel;
import com.hj.component.ResultDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentManagePanel extends MyJPanel {
    Connection connection;
    private List<Student> studentList;
    private JPanel studentTable;

    public StudentManagePanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("教务管理 > 学生管理");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);

        // 学生信息表格
        studentTable = new JPanel(new GridLayout(0, 7, 10, 3));

        this.add(studentTable);
        this.add(title);
    }

    @Override
    /**
     * 初始化页面
     */
    public void init() {
        studentTable.removeAll();
        // 添加表头
        JLabel studentIdTitle = new JLabel("学生id");
        JLabel studentNameTitle = new JLabel("学生姓名");
        JLabel studentClassTitle = new JLabel("学生班级");
        JLabel studentGenderTitle = new JLabel("学生性别");
        JLabel studentEmailTitle = new JLabel("学生邮箱");
        JLabel studentAgeTitle = new JLabel("学生年龄");
        JLabel actionTitle = new JLabel("操作");
        studentTable.add(studentIdTitle);
        studentTable.add(studentNameTitle);
        studentTable.add(studentClassTitle);
        studentTable.add(studentGenderTitle);
        studentTable.add(studentEmailTitle);
        studentTable.add(studentAgeTitle);
        studentTable.add(actionTitle);

        // 获取所有学生信息列表
        try {
            studentList = new ArrayList<>();
            Statement statement = connection.createStatement();
            String getAllStudentSql = "select * from t_student,t_user_info,t_class where t_student.info_id=t_user_info.info_id and t_student.class_id=t_class.class_id";
            ResultSet rs = statement.executeQuery(getAllStudentSql);

            while (rs.next()) {
                studentList.add(new Student(rs.getString(1), rs.getString(7), rs.getString(12), rs.getString(8), rs.getString(9), rs.getString(10)));
            }

            System.out.println(1);
            studentTable.setBounds(20, 60, 700, studentList.size() * 30);

            // 将信息添加到表格中
            for (int i = 0; i < studentList.size(); i++) {
                Student student = studentList.get(i);

                JLabel studentId = new JLabel(student.studentId);
                JLabel studentName = new JLabel(student.studentName);
                JLabel studentClass = new JLabel(student.studentClass);
                JLabel studentGender = new JLabel(student.studentGender);
                JLabel studentEmail = new JLabel(student.studentEmail);
                JLabel studentAge = new JLabel(student.studentAge);
                JButton action = new JButton("修改班级");
                action.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ResultDialog("学生 " + student.studentName + " 班级修改成功");
                    }
                });
                studentTable.add(studentId);
                studentTable.add(studentName);
                studentTable.add(studentClass);
                studentTable.add(studentGender);
                studentTable.add(studentEmail);
                studentTable.add(studentAge);
                studentTable.add(action);
            }
            studentTable.updateUI();
            this.updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "StudentManagePanel";
    }

    /**
     * 学生对象
     */
    public class Student {
        String studentId;
        String studentName;
        String studentClass;
        String studentGender;
        String studentEmail;
        String studentAge;

        public Student(String studentId, String studentName, String studentClass, String studentGender, String studentEmail, String studentAge) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.studentClass = studentClass;
            this.studentGender = studentGender;
            this.studentEmail = studentEmail;
            this.studentAge = studentAge;
        }
    }
}
