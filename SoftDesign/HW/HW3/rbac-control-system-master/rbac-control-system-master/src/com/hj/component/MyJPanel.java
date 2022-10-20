package com.hj.component;

import javax.swing.*;

/**
 * 所有面板的父组件,方便设置一些公共函数调用
 */
public class MyJPanel extends JPanel {
    private String info_id;

    public void setId(String info_id) {
        this.info_id = info_id;
    }

    public void init() {
    }
}
