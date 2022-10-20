package com.hj.component.stuMenu;

import com.hj.component.MyJPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewScorePanel extends MyJPanel {
    Connection connection;
    private JPanel scoreTable;
    private String info_id;

    public ViewScorePanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("学生功能 > 成绩查询");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);

        // 成绩表格
        scoreTable = new JPanel(new GridLayout(0, 4, 10, 3));

        this.add(scoreTable);
        this.add(title);
    }

    @Override
    /**
     * 初始化函数
     */
    public void init() {
        scoreTable.removeAll();
        // 添加表头
        JLabel examNameLabel = new JLabel("考试名称");
        JLabel examTimeLabel = new JLabel("考试时间");
        JLabel examTypeLabel = new JLabel("考试类型");
        JLabel scoreLabel = new JLabel("成绩");
        scoreTable.add(examNameLabel);
        scoreTable.add(examTimeLabel);
        scoreTable.add(examTypeLabel);
        scoreTable.add(scoreLabel);

        // 获取该学生成绩列表
        Score[] scoreList = getScore();
        scoreTable.setBounds(20, 60, 700, scoreList.length * 30);

        // 将成绩添加到表格中
        for (int i = 0; i < scoreList.length; i++) {
            Score scoreItem = scoreList[i];
            JLabel examName = new JLabel(scoreItem.examName);
            JLabel examTime = new JLabel(scoreItem.examTime);
            JLabel examType = new JLabel(scoreItem.examType);
            JLabel score = new JLabel(scoreItem.score);

            scoreTable.add(examName);
            scoreTable.add(examTime);
            scoreTable.add(examType);
            scoreTable.add(score);
        }
        scoreTable.updateUI();
        this.updateUI();
    }

    /**
     * 根据info_id获取该学生所有考试成绩
     *
     * @return 考试成绩列表
     */
    private Score[] getScore() {
        Statement statement = null;
        List<Score> scoreList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sqlStatement = "select exam_name,exam_time,exam_type,score from t_student,t_exam,t_score where t_student.info_id=" + info_id + " and t_score.stu_id=t_student.stu_id and t_score.exam_id=t_exam.exam_id";
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                scoreList.add(new Score(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            System.out.println(scoreList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreList.toArray(new Score[scoreList.size()]);
    }

    @Override
    public String toString() {
        return "ViewScorePanel";
    }

    @Override
    public void setId(String info_id) {
        this.info_id = info_id;
    }

    /**
     * 成绩对象
     */
    public class Score {
        String examName;
        String examTime;
        String examType;
        String score;

        public Score(String examName, String examTime, String examType, String score) {
            this.examName = examName;
            this.examTime = examTime;
            this.examType = examType;
            this.score = score;
        }
    }
}
