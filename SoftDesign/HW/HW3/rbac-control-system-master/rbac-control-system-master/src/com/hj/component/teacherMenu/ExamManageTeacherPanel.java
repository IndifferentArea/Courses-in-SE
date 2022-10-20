package com.hj.component.teacherMenu;

import com.hj.component.MyJPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ExamManageTeacherPanel extends MyJPanel {
    Connection connection;

    public ExamManageTeacherPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("老师功能 > 单元测试管理");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 50;

        // 班级名称
        JLabel examWarningLabel = new JLabel("对不起,您还未发布任何考试 ");
        examWarningLabel.setBounds(70, yOffset, 160, 25);

        this.add(examWarningLabel);
        this.add(title);
    }

    @Override
    public String toString() {
        return "ExamManageTeacherPanel";
    }
}
