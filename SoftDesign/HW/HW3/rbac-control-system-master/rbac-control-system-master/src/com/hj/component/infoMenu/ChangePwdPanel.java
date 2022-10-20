package com.hj.component.infoMenu;

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
import java.util.HashMap;
import java.util.Map;

public class ChangePwdPanel extends MyJPanel {
    Connection connection;
    private String info_id;


    public ChangePwdPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("个人信息 > 修改密码");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 40;

        // 原密码
        JLabel oldPwdLabel = new JLabel("原密码: ");
        oldPwdLabel.setBounds(70, yOffset, 60, 25);
        JPasswordField oldPwdText = new JPasswordField(20);
        oldPwdText.setBounds(130, yOffset, 150, 25);
        yOffset += 50;

        // 新密码
        JLabel newPwdLabel = new JLabel("新密码: ");
        newPwdLabel.setBounds(70, yOffset, 60, 25);
        JPasswordField newPwdText = new JPasswordField(20);
        newPwdText.setBounds(130, yOffset, 150, 25);
        yOffset += 50;

        // 确认密码
        JLabel confirmPwdLabel = new JLabel("确认密码: ");
        confirmPwdLabel.setBounds(55, yOffset, 60, 25);
        JPasswordField confirmPwdText = new JPasswordField(20);
        confirmPwdText.setBounds(130, yOffset, 150, 25);
        yOffset += 50;

        // 添加按钮
        JButton updateInfoButton = new JButton("修改");
        updateInfoButton.setBounds(90, yOffset, 100, 25);

        /**
         * 添加修改密码监听事件
         * 首先需要看两次输入的新密码是否一致,如果一致再去查找数据库中旧密码是否正确
         * 如果旧密码也正确,则更新密码,否则提示错误
         */
        updateInfoButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPwd = oldPwdText.getText();
                String newPwd = newPwdText.getText();
                String confirmPwd = confirmPwdText.getText();
                // 判断新密码是否一致
                if (!newPwd.equals(confirmPwd)) {
                    new ResultDialog("两次输入的密码不一致");
                    return;
                }

                try {
                    Statement statement = connection.createStatement();

                    // 查询输入旧密码是否正确
                    Map<String, String> conditionMap = new HashMap<>();
                    conditionMap.put("info_id", info_id);
                    conditionMap.put("passwd", oldPwd);
                    String confirmOldPwd = MysqlUtil.Select("t_user_info", "count(*)", conditionMap);
                    ResultSet confirmOldPwdRs = statement.executeQuery(confirmOldPwd);
                    confirmOldPwdRs.next();
                    if (!"1".equals(confirmOldPwdRs.getString(1))) {
                        new ResultDialog("旧密码错误");
                        return;
                    }
                    conditionMap.remove("passwd");

                    //更新密码
                    Map<String, String> valueMap = new HashMap<>();
                    valueMap.put("passwd", newPwd);
                    String sqlStatement = MysqlUtil.Update("t_user_info", conditionMap, valueMap);
                    statement.executeUpdate(sqlStatement);

                    //返回结果
                    ResultDialog resultDialog = new ResultDialog("修改成功");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.add(oldPwdLabel);
        this.add(oldPwdText);
        this.add(newPwdLabel);
        this.add(newPwdText);
        this.add(confirmPwdLabel);
        this.add(confirmPwdText);
        this.add(updateInfoButton);

        this.add(title);
    }

    public void setId(String id) {
        this.info_id = id;
    }

    @Override
    public String toString() {
        return "InfoPanel";
    }
}
