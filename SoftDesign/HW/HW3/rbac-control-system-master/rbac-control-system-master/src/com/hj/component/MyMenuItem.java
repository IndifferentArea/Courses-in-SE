package com.hj.component;

import javax.swing.*;

/**
 * 二级菜单项,用于权限管理以及页面跳转
 */
public class MyMenuItem extends JMenuItem {
    private String name;
    private String permission;

    public MyMenuItem(String label, String name, String permission) {
        super(label);
        this.name = name;
        this.permission = permission;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return name;
    }
}
