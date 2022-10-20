package com.hj.component.superMenu;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleManagePanel extends MyJPanel {
    Connection connection;
    private List<RoleItem> roleItemList;
    private JPanel roleTable;

    public RoleManagePanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("后台管理 > 角色管理");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);

        // 角色表格
        roleTable = new JPanel(new GridLayout(0, 4, 10, 3));

        this.add(roleTable);
        this.add(title);
    }

    @Override
    /**
     * 初始化页面
     */
    public void init() {
        roleTable.removeAll();

        //添加表头
        JLabel roleIdTitle = new JLabel("角色id");
        JLabel roleNameTitle = new JLabel("角色名称");
        JLabel roleDescTitle = new JLabel("角色描述");
        JLabel actionTitle = new JLabel("操作");
        roleTable.add(roleIdTitle);
        roleTable.add(roleNameTitle);
        roleTable.add(roleDescTitle);
        roleTable.add(actionTitle);

        // 获取所有角色列表
        try {
            roleItemList = new ArrayList<>();
            Statement statement = connection.createStatement();
            String getAllRoleSql = MysqlUtil.Select("t_role", null);
            ResultSet rs = statement.executeQuery(getAllRoleSql);

            while (rs.next()) {
                roleItemList.add(new RoleItem(rs.getString(1), rs.getString(2), rs.getString(3)));
            }

            roleTable.setBounds(20, 60, 600, roleItemList.size() * 30);

            // 将角色列表的数据添加到表格中,并设置删除的监听器
            for (int i = 0; i < roleItemList.size(); i++) {
                RoleItem roleItem = roleItemList.get(i);

                JLabel role_id = new JLabel(roleItem.role_id);
                JLabel role_name = new JLabel(roleItem.role_name);
                JLabel role_desc = new JLabel(roleItem.role_desc);
                JButton deleteRole = new JButton("删除角色");

                deleteRole.setSize(40, 25);
                deleteRole.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Statement statement = connection.createStatement();
                            Map<String, String> map = new HashMap<>();
                            map.put("role_id", role_id.getText());
                            String deleteRoleSql = MysqlUtil.Delete("t_role", map);
                            statement.executeUpdate(deleteRoleSql);
                            new ResultDialog("删除成功");
                            init();

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                roleTable.add(role_id);
                roleTable.add(role_name);
                roleTable.add(role_desc);
                roleTable.add(deleteRole);
            }
            roleTable.updateUI();
            this.updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "RoleManagePanel";
    }

    /**
     * 角色对象
     */
    public class RoleItem {
        String role_id;
        String role_name;
        String role_desc;

        public RoleItem(String role_id, String role_name, String role_desc) {
            this.role_id = role_id;
            this.role_name = role_name;
            this.role_desc = role_desc;
        }

        @Override
        public String toString() {
            return "RoleItem{" +
                    "role_id='" + role_id + '\'' +
                    ", role_name='" + role_name + '\'' +
                    ", role_desc='" + role_desc + '\'' +
                    '}';
        }
    }
}
