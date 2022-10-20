package com.hj.component.teacherMenu;

import com.hj.component.MyJPanel;
import com.hj.component.ResultDialog;
import com.hj.util.MysqlUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddExamPanel extends MyJPanel {
    Connection connection;
    private JComboBox examTypeSelect;
    private JComboBox classSelect;
    private JComboBox courseSelect;

    public AddExamPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("老师功能 > 添加考试");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 40;

        // 班级名称
        JLabel classNameLabel = new JLabel("班级名称: ");
        classNameLabel.setBounds(70, yOffset, 60, 25);
        classSelect = new JComboBox();
        classSelect.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 考试科目
        JLabel examCourseLabel = new JLabel("考试科目: ");
        examCourseLabel.setBounds(70, yOffset, 60, 25);
        courseSelect = new JComboBox();
        courseSelect.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 考试类型
        JLabel examTypeLabel = new JLabel("考试类型: ");
        examTypeLabel.setBounds(70, yOffset, 60, 25);
        examTypeSelect = new JComboBox();
        examTypeSelect.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 考试名称
        JLabel examNameLabel = new JLabel("考试名称: ");
        examNameLabel.setBounds(70, yOffset, 60, 25);
        JTextField examNameText = new JTextField(20);
        examNameText.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 考试时间
        JLabel examTimeLabel = new JLabel("考试时间: ");
        examTimeLabel.setBounds(70, yOffset, 60, 25);
        JTextField examTimeText = new JTextField(20);
        examTimeText.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 添加按钮
        JButton addExamButton = new JButton("添加");
        addExamButton.setBounds(90, yOffset, 100, 25);

        // 为添加考试按钮设置监听器
        addExamButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取所有相关信息
                Class classItem = (Class) classSelect.getSelectedItem();
                Course course = (Course) courseSelect.getSelectedItem();
                String examType = (String) examTypeSelect.getSelectedItem();
                String examName = examNameText.getText();
                String examTime = examTimeText.getText();

                Map<String, String> map = new HashMap<>();
                String exam_id = MysqlUtil.getNextId("t_exam", "exam_id");
                map.put("exam_id", exam_id);
                map.put("course_id", course.courseId);
                map.put("exam_name", examName);
                map.put("exam_type", examType);
                map.put("exam_time", examTime);
                try {
                    Statement statement = connection.createStatement();
                    // 增加考试信息
                    String insertExamSql = MysqlUtil.Insert("t_exam", map);
                    statement.executeUpdate(insertExamSql);

                    // 增加班级考试信息
                    map.clear();
                    map.put("class_exam_id", MysqlUtil.getNextId("t_class_exam", "class_exam_id"));
                    map.put("class_id", classItem.classId);
                    map.put("exam_id", exam_id);
                    String insertClassExamSql = MysqlUtil.Insert("t_class_exam", map);
                    statement.executeUpdate(insertClassExamSql);

                    //返回结果
                    new ResultDialog("考试添加成功");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.add(classNameLabel);
        this.add(classSelect);
        this.add(examCourseLabel);
        this.add(courseSelect);
        this.add(examTypeLabel);
        this.add(examTypeSelect);
        this.add(examNameLabel);
        this.add(examNameText);
        this.add(examTimeLabel);
        this.add(examTimeText);
        this.add(addExamButton);

        this.add(title);
    }

    /**
     * 从数据字典中获取所有考试类型
     *
     * @return 考试类型列表
     */
    private String[] getExamType() {
        Statement statement = null;
        java.util.List<String> examTypeList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sqlStatement = "select value from t_data_dictionary where attr='考试类型'";
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                examTypeList.add(rs.getString(1));
            }
            System.out.println(examTypeList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return examTypeList.toArray(new String[examTypeList.size()]);
    }

    /**
     * 获取所有班级信息
     *
     * @return 班级列表
     */
    private Class[] getAllClass() {
        Statement statement = null;
        List<Class> classList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sqlStatement = "select class_id,class_name from t_class";
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                classList.add(new Class(rs.getString(1), rs.getString(2)));
            }
            System.out.println(classList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classList.toArray(new Class[classList.size()]);
    }

    /**
     * 获取所有课程信息
     *
     * @return 课程列表
     */
    private Course[] getAllCourse() {
        Statement statement = null;
        List<Course> courseList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sqlStatement = "select course_id,course_name from t_course";
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                courseList.add(new Course(rs.getString(1), rs.getString(2)));
            }
            System.out.println(courseList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList.toArray(new Course[courseList.size()]);
    }

    @Override
    /**
     * 初始化页面组件
     */
    public void init() {
        // 获取考试类型列表
        String[] examTypeList = getExamType();
        for (String type : examTypeList) {
            examTypeSelect.addItem(type);
        }

        // 获取班级列表
        Class[] classList = getAllClass();
        for (Class classItem : classList) {
            classSelect.addItem(classItem);
        }

        // 获取课程列表
        Course[] courseList = getAllCourse();
        for (Course courseItem : courseList) {
            courseSelect.addItem(courseItem);
        }
    }

    @Override
    public String toString() {
        return "ExamManagePanel";
    }

    /**
     * 课程对象
     */
    public class Course {
        String courseId;
        String courseName;

        public Course(String courseId, String courseName) {
            this.courseId = courseId;
            this.courseName = courseName;
        }

        @Override
        public String toString() {
            return courseName;
        }

    }

    /**
     * 班级对象
     */
    public class Class {
        String classId;
        String className;

        public Class(String classId, String className) {
            this.classId = classId;
            this.className = className;
        }

        @Override
        public String toString() {
            return className;
        }
    }
}
