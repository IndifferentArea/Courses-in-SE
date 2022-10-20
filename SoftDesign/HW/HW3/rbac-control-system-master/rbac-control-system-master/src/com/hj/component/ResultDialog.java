package com.hj.component;

import javax.swing.*;
import java.awt.*;

/**
 * 封装的结果相应组件
 */
public class ResultDialog extends JDialog {
    public ResultDialog(String result) {
        JPanel resultPanel = new JPanel();
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setTitle("结果");
        this.setBounds(100, 100, 250, 150);
        this.setLocationRelativeTo(null);
        JLabel resultLabel = new JLabel(result);
        resultPanel.add(resultLabel);
        this.add(resultPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
}
