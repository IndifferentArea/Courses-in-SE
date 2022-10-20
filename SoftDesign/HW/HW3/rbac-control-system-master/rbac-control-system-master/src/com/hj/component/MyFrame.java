package com.hj.component;

import com.hj.util.MysqlUtil;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * 主框架
 */
public class MyFrame extends JFrame {
    public MyFrame() {
        this.setTitle("RBAC测试");
        this.setBounds(350, 150, 320, 400);
        this.setLocationRelativeTo(null); // 设置该页面屏幕居中
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 关闭窗口时关闭与数据库的连接
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    // 关闭于数据库的连接
                    MysqlUtil.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
