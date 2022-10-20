package com.hj.component.classMasterMenu;

import com.hj.component.MyJPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ClassExamManagePanel extends MyJPanel {
    Connection connection;

    public ClassExamManagePanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("班主任功能 > 学生信息");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        this.add(title);
    }

    @Override
    public String toString() {
        return "ClassExamManagePanel";
    }

}
