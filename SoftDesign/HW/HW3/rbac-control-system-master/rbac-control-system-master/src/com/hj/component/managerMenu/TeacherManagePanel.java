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

public class TeacherManagePanel extends MyJPanel {
    Connection connection;
    private List<Teacher> teacherList;
    private JPanel teacherTable;

    public TeacherManagePanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("教务管理 > 老师管理");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);

        // 老师表格
        teacherTable = new JPanel(new GridLayout(0, 6, 10, 3));

        this.add(teacherTable);
        this.add(title);
    }

    @Override
    /**
     * 初始化函数
     */
    public void init() {
        teacherTable.removeAll();
        // 初始化表头
        JLabel teacherIdTitle = new JLabel("老师id");
        JLabel teacherNameTitle = new JLabel("老师姓名");
        JLabel teacherEmailTitle = new JLabel("邮箱");
        JLabel teacherCourseTitle = new JLabel("科目");
        JLabel teacherClassTitle = new JLabel("任课班级");
        JLabel actionTitle = new JLabel("操作");
        teacherTable.add(teacherIdTitle);
        teacherTable.add(teacherNameTitle);
        teacherTable.add(teacherEmailTitle);
        teacherTable.add(teacherCourseTitle);
        teacherTable.add(teacherClassTitle);
        teacherTable.add(actionTitle);

        // 获取老师信息
        try {
            teacherList = new ArrayList<>();
            Statement statement = connection.createStatement();
            String getAllStudentSql = "select t_user_info.info_id,t_user_info.`name`,t_user_info.u_mail,t_class.class_name,t_course.course_name " +
                    "from t_teacher,t_user_info,t_class,t_teacher_class,t_course " +
                    "where t_teacher.info_id=t_user_info.info_id and t_teacher.course_id=t_course.course_id and t_teacher_class.teacher_id=t_teacher.teacher_id and t_teacher_class.class_id=t_class.class_id";
            ResultSet rs = statement.executeQuery(getAllStudentSql);

            while (rs.next()) {
                teacherList.add(new Teacher(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(3)));
            }

            teacherTable.setBounds(20, 60, 700, teacherList.size() * 30);

            // 添加老师信息到表格中
            for (int i = 0; i < teacherList.size(); i++) {
                Teacher teacher = teacherList.get(i);

                JLabel teacherId = new JLabel(teacher.teacherId);
                JLabel teacherName = new JLabel(teacher.teacherName);
                JLabel teacherClass = new JLabel(teacher.teacherClass);
                JLabel teacherCourse = new JLabel(teacher.teacherCourse);
                JLabel teacherEmail = new JLabel(teacher.teacherEmail);
                JButton action = new JButton("修改任课班级");
                action.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ResultDialog("老师 " + teacher.teacherName + " 任课班级删除成功");
                    }
                });
                teacherTable.add(teacherId);
                teacherTable.add(teacherName);
                teacherTable.add(teacherEmail);
                teacherTable.add(teacherCourse);
                teacherTable.add(teacherClass);
                teacherTable.add(action);
            }
            teacherTable.updateUI();
            this.updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "TeacherManagePanel";
    }

    /**
     * 教师对象
     */
    public class Teacher {
        String teacherId;
        String teacherName;
        String teacherClass;
        String teacherCourse;
        String teacherEmail;

        public Teacher(String teacherId, String teacherName, String teacherClass, String teacherCourse, String teacherEmail) {
            this.teacherId = teacherId;
            this.teacherName = teacherName;
            this.teacherClass = teacherClass;
            this.teacherCourse = teacherCourse;
            this.teacherEmail = teacherEmail;
        }
    }
}
