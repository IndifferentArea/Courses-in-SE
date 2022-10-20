package com.hj.component;

import javax.swing.*;

/**
 * 所有一级菜单之间的父组件,用于权限管理
 */
public class MyMenu extends JMenu {
    private String permission;

    public MyMenu(String label, String permission) {
        super(label);
        this.permission = permission;
    }

    @Override
    public String toString() {
        return permission;
    }
}
