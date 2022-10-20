package com.hj.component.teacherMenu;

import com.hj.component.MyJPanel;
import com.hj.component.ResultDialog;
import com.hj.util.MysqlUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreUploadPanel extends MyJPanel {
    Connection connection;
    private JComboBox examSelect;
    private JComboBox studentSelect;

    public ScoreUploadPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("老师功能 > 上传成绩");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 40;

        // 考试名称
        JLabel examNameLabel = new JLabel("考试名称: ");
        examNameLabel.setBounds(70, yOffset, 60, 25);
        examSelect = new JComboBox();
        examSelect.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 学生姓名
        JLabel stuNameLabel = new JLabel("考试名称: ");
        stuNameLabel.setBounds(70, yOffset, 60, 25);
        studentSelect = new JComboBox();
        studentSelect.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 成绩
        JLabel scoreLabel = new JLabel("考试名称: ");
        scoreLabel.setBounds(70, yOffset, 60, 25);
        JTextField scoreText = new JTextField(20);
        scoreText.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 为考试选择下拉框设置监听器
        examSelect.addItemListener(new ItemListener() {
            @Override
            /**
             * 当选中的考试发生变化时,更新该考试对应的所有学生的信息
             */
            public void itemStateChanged(ItemEvent e) {
                studentSelect.removeAllItems();
                Exam exam = (Exam) examSelect.getSelectedItem();

                // 根据考试Id获取参加该考试的所有学生
                Student[] studentList = getStudent(exam.classId);

                // 为学生选择框添加学生数据
                for (Student stu : studentList) {
                    studentSelect.addItem(stu);
                }
            }
        });

        // 添加按钮
        JButton addScoreButton = new JButton("添加");
        addScoreButton.setBounds(90, yOffset, 100, 25);

        // 为添加按钮设置监听器
        addScoreButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取所有相关信息
                Student stu = (Student) studentSelect.getSelectedItem();
                Exam exam = (Exam) examSelect.getSelectedItem();
                String score = scoreText.getText();

                Map<String, String> map = new HashMap<>();
                String score_id = MysqlUtil.getNextId("t_score", "score_id");
                map.put("score_id", score_id);
                map.put("stu_id", stu.stuId);
                map.put("exam_id", exam.examId);
                map.put("score", score);
                try {
                    Statement statement = connection.createStatement();
                    // 增加成绩信息
                    String insertScoreSql = MysqlUtil.Insert("t_score", map);
                    statement.executeUpdate(insertScoreSql);

                    //返回结果
                    new ResultDialog("成绩添加成功");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });


        this.add(examNameLabel);
        this.add(examSelect);
        this.add(stuNameLabel);
        this.add(studentSelect);
        this.add(scoreLabel);
        this.add(scoreText);
        this.add(addScoreButton);
        this.add(examSelect);

        this.add(title);
    }

    /**
     * 获取该老师所有考试
     *
     * @return 该老师的所有考试列表
     */
    private Exam[] getExam() {
        Statement statement;
        List<Exam> examList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sqlStatement = "select t_exam.exam_id,exam_name,t_teacher_class.class_id from t_exam,t_teacher,t_teacher_class,t_class_exam where t_teacher.info_id=127 and t_teacher.teacher_id=t_teacher_class.teacher_id and t_teacher_class.class_id=t_class_exam.class_id and t_class_exam.exam_id=t_exam.exam_id";
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                examList.add(new Exam(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return examList.toArray(new Exam[examList.size()]);
    }

    /**
     * 获取参加该考试的所有学生
     *
     * @return 参加该考试的所有学生
     */
    public Student[] getStudent(String classId) {
        Statement statement;
        List<Student> studentList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sqlStatement = "select t_student.stu_id,t_user_info.`name` from t_student,t_user_info where t_student.class_id=" + classId + " and t_student.info_id=t_user_info.info_id";
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                studentList.add(new Student(rs.getString(1), rs.getString(2)));
            }
            System.out.println(studentList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList.toArray(new Student[studentList.size()]);
    }

    @Override
    /**
     * 初始化页面组件
     */
    public void init() {
        // 获取所有考试列表
        Exam[] examList = getExam();
        for (Exam exam : examList) {
            examSelect.addItem(exam);
        }
    }

    @Override
    public String toString() {
        return "ScoreUploadPanel";
    }

    /**
     * 考试对象
     */
    public class Exam {
        String examId;
        String examName;
        String classId;

        public Exam(String examId, String examName, String classId) {
            this.examId = examId;
            this.examName = examName;
            this.classId = classId;
        }

        @Override
        public String toString() {
            return examName;
        }

    }


    /**
     * 学生对象
     */
    public class Student {
        String stuId;
        String stuName;

        public Student(String stuId, String stuName) {
            this.stuId = stuId;
            this.stuName = stuName;
        }


        @Override
        public String toString() {
            return stuName;
        }
    }

}
