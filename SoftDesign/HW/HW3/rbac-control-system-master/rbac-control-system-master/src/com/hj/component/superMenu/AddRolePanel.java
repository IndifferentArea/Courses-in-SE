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

public class AddRolePanel extends MyJPanel {
    Connection connection;

    public AddRolePanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("后台管理 > 添加角色");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 40;

        // 角色名称
        JLabel roleNameLabel = new JLabel("角色名称: ");
        roleNameLabel.setBounds(70, yOffset, 60, 25);
        JTextField roleNameText = new JTextField(20);
        roleNameText.setBounds(130, yOffset, 200, 25);
        yOffset += 50;

        // 角色描述
        JLabel roleDescLabel = new JLabel("角色描述: ");
        roleDescLabel.setBounds(70, yOffset, 60, 25);
        JTextArea roleDescText = new JTextArea(4, 20);
        roleDescText.setBounds(130, yOffset, 200, 80);
        roleDescText.setLineWrap(true);
        yOffset += 110;

        // 添加按钮
        JButton addRoleButton = new JButton("添加");
        addRoleButton.setBounds(90, yOffset, 100, 25);

        // 为添加角色按钮添加监听器
        addRoleButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = null;
                String roleName = roleNameText.getText();
                String roleDesc = roleDescText.getText();
                Map<String, String> map = new HashMap<>();
                map.put("role_name", roleName);
                map.put("role_desc", roleDesc);
                System.out.println(id + " " + roleName + " " + roleDesc);
                try {
                    Statement statement = connection.createStatement();
                    // 获取接下来角色的id
                    map.put("role_id", MysqlUtil.getNextId("t_role", "role_id"));

                    // 插入该角色信息
                    String insertRoleSql = MysqlUtil.Insert("t_role", map);
                    statement.executeUpdate(insertRoleSql);

                    //返回结果
                    new ResultDialog("添加成功");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.add(roleNameLabel);
        this.add(roleNameText);
        this.add(roleDescLabel);
        this.add(roleDescText);

        this.add(addRoleButton);
        this.add(title);
    }

    @Override
    public String toString() {
        return "AddRolePanel";
    }
}
