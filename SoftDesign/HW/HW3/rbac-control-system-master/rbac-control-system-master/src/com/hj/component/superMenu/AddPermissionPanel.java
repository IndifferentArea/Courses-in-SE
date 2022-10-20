package com.hj.component.superMenu;

import com.hj.component.MyJPanel;
import com.hj.component.ResultDialog;
import com.hj.util.MysqlUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class AddPermissionPanel extends MyJPanel {
    Connection connection;
    private JPanel roleTable;

    public AddPermissionPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("后台管理 > 添加权限");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 60;

        // 添加操作对象
        JLabel objLabel = new JLabel("操作对象: ");
        objLabel.setBounds(70, yOffset, 60, 25);
        JTextField objText = new JTextField(20);
        objText.setBounds(130, yOffset, 150, 25);
        JButton objButton = new JButton("添加");
        objButton.setBounds(320, yOffset, 80, 25);
        yOffset += 50;

        // 添加操作
        JLabel actionLabel = new JLabel("操作名称: ");
        actionLabel.setBounds(70, yOffset, 60, 25);
        JTextField actionText = new JTextField(20);
        actionText.setBounds(130, yOffset, 150, 25);
        JButton actionButton = new JButton("添加");
        actionButton.setBounds(320, yOffset, 80, 25);

        // 为添加操作对象按钮设置监听器
        objButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement statement = connection.createStatement();
                    Map<String, String> map = new HashMap<>();
                    map.put("obj_id", MysqlUtil.getNextId("t_object", "obj_id"));
                    map.put("obj_name", objText.getText());
                    // 将该操作对象添加到数据库中
                    String insertObj = MysqlUtil.Insert("t_object", map);
                    statement.executeUpdate(insertObj);

                    //返回结果
                    new ResultDialog("操作对象添加成功");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 为添加操作按钮设置监听器
        actionButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement statement = connection.createStatement();
                    Map<String, String> map = new HashMap<>();
                    map.put("action_id", MysqlUtil.getNextId("t_action", "action_id"));
                    map.put("action_name", actionText.getText());
                    // 将该操作添加到数据库中
                    String insertObj = MysqlUtil.Insert("t_action", map);
                    statement.executeUpdate(insertObj);

                    //返回结果
                    new ResultDialog("操作添加成功");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.add(objLabel);
        this.add(objText);
        this.add(objButton);
        this.add(actionLabel);
        this.add(actionText);
        this.add(actionButton);

        this.add(title);
    }

    @Override
    public String toString() {
        return "AddPermissionPanel";
    }
}
