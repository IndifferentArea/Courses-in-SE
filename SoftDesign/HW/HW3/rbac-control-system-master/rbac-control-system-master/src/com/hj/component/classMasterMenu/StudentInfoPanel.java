package com.hj.component.classMasterMenu;

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

public class StudentInfoPanel extends MyJPanel {
    Connection connection;
    private List<Student> studentList;
    private JPanel studentTable;

    public StudentInfoPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("班主任功能 > 学生信息");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);

        studentTable = new JPanel(new GridLayout(0, 6, 10, 3));

        this.add(studentTable);
        this.add(title);
    }

    @Override
    /**
     * 初始化页面组件
     */
    public void init() {
        studentTable.removeAll();
        JLabel studentIdTitle = new JLabel("学生id");
        JLabel studentNameTitle = new JLabel("学生姓名");
        JLabel studentGenderTitle = new JLabel("学生性别");
        JLabel studentEmailTitle = new JLabel("学生邮箱");
        JLabel studentAgeTitle = new JLabel("学生年龄");
        JLabel actionTitle = new JLabel("操作");
        studentTable.add(studentIdTitle);
        studentTable.add(studentNameTitle);
        studentTable.add(studentGenderTitle);
        studentTable.add(studentEmailTitle);
        studentTable.add(studentAgeTitle);
        studentTable.add(actionTitle);

        try {
            studentList = new ArrayList<>();
            Statement statement = connection.createStatement();
            // 获得所有学生信息
            String getStuInfoSql = "select t_user_info.info_id,t_user_info.`name`,t_user_info.u_gender,t_user_info.u_mail,t_user_info.u_age \n" +
                    "from t_user_info,t_student\n" +
                    "where t_user_info.info_id=t_student.info_id and\n" +
                    "t_student.class_id in \n" +
                    "(select class_id from t_teacher,t_class where info_id=124 and t_teacher.teacher_id=t_class.class_master)";
            ResultSet rs = statement.executeQuery(getStuInfoSql);

            while (rs.next()) {
                studentList.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            System.out.println(1);
            // 根据学生数量动态设置面板高度
            studentTable.setBounds(20, 60, 700, studentList.size() * 30);

            // 初始化组件数值
            for (int i = 0; i < studentList.size(); i++) {
                Student student = studentList.get(i);

                JLabel studentId = new JLabel(student.studentId);
                JLabel studentName = new JLabel(student.studentName);
                JLabel studentGender = new JLabel(student.studentGender);
                JLabel studentEmail = new JLabel(student.studentEmail);
                JLabel studentAge = new JLabel(student.studentAge);
                JButton action = new JButton("编辑信息");
                action.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ResultDialog("学生 " + student.studentName + " 信息修改成功");
                    }
                });
                studentTable.add(studentId);
                studentTable.add(studentName);
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
        return "StudentInfoPanel";
    }

    /**
     * 学生对象
     */
    public class Student {
        String studentId;
        String studentName;
        String studentGender;
        String studentEmail;
        String studentAge;

        public Student(String studentId, String studentName, String studentGender, String studentEmail, String studentAge) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.studentGender = studentGender;
            this.studentEmail = studentEmail;
            this.studentAge = studentAge;
        }
    }
}
