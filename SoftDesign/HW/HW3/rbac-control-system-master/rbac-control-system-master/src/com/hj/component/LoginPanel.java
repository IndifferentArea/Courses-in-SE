package com.hj.component;

import com.hj.customInterface.LoginInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 登录界面
 */
public class LoginPanel extends JPanel {
    Connection connection;
    LoginInterface loginInterface;

    public LoginPanel(Connection connection, LoginInterface loginInterface) {
        super(new FlowLayout(FlowLayout.CENTER));
        this.loginInterface = loginInterface;

        this.connection = connection;
        Box titleBox = Box.createHorizontalBox();
        titleBox.add(Box.createVerticalStrut(200));
        titleBox.add(Box.createHorizontalStrut(10));
        JLabel title = new JLabel("中学成绩管理系统");
        titleBox.add(title);
        Font font = new Font("宋体", Font.BOLD, 30);
        title.setFont(font);

        // 账号盒
        Box userNameBox = Box.createHorizontalBox();
        userNameBox.add(Box.createHorizontalStrut(10));
        userNameBox.add(Box.createVerticalStrut(30));

        // 密码盒
        Box passwdBox = Box.createHorizontalBox();
        passwdBox.add(Box.createHorizontalStrut(12));
        passwdBox.add(Box.createVerticalStrut(30));

        // 账号
        JLabel username = new JLabel("账号:");
        JTextField usernameText = new JTextField(20);

        // 密码
        JLabel password = new JLabel("密码:");
        JPasswordField passwordText = new JPasswordField(20);
        JLabel loginState = new JLabel();

        // 登录按钮
        JButton jb = new JButton("登录");

        // 登录按钮监听
        jb.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement statement = connection.createStatement();
                    String sqlStatement = "select count(*) from t_user_info where info_id=" + usernameText.getText() + " and passwd=" + passwordText.getText();
                    System.out.println(sqlStatement);
                    ResultSet rs = statement.executeQuery(sqlStatement);
                    rs.next();
                    boolean success = rs.getInt(1) == 1; // 判断是否登录成功

                    // 处理登录逻辑
                    if (success) {
                        // 登录成功
                        loginState.setText("登陆成功");
                        loginInterface.loginSuccess(usernameText.getText());
                    } else {
                        // 登录失败
                        loginState.setText("用户名或密码错误");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.add(titleBox);
        userNameBox.add(username);
        userNameBox.add(usernameText);
        passwdBox.add(password);
        passwdBox.add(passwordText);

        this.add(userNameBox);
        this.add(passwdBox);
        this.add(loginState);
        this.add(jb);
    }

}
