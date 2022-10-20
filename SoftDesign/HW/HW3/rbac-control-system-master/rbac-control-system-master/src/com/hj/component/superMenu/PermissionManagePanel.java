package com.hj.component.superMenu;

import com.hj.component.MyJPanel;
import com.hj.util.MysqlUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionManagePanel extends MyJPanel {
    Connection connection;
    private JComboBox roleSelect;
    private JPanel makePermissionTable; // 设置权限面板
    private JPanel assignPermissionTable;  // 分配权限面板
    private JCheckBox[][] checkBoxes; //设置权限面板勾选框
    private Integer state = 0; // 0表示正在设置权限,1表示正在为角色分配权限

    public PermissionManagePanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("后台管理 > 权限管理");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 50;

        // 设置权限提醒文字
        JLabel text = new JLabel("您正在设置权限");
        text.setBounds(20, yOffset, 120, 25);

        // 分配权限提醒文字
        JLabel preText = new JLabel("您正在为角色 ");
        preText.setBounds(20, yOffset, 80, 25);
        roleSelect = new JComboBox();
        roleSelect.setBounds(105, yOffset, 100, 25);

        // 为下拉框添加监听器,当选择的对象发生变化时,更新下方权限框的内容
        roleSelect.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setAssignPermissionTable();
            }
        });
        // 分配权限提醒文字
        JLabel sufText = new JLabel("分配权限");
        sufText.setBounds(220, yOffset, 60, 25);

        // 默认展示设置权限部分内容
        preText.setVisible(false);
        roleSelect.setVisible(false);
        sufText.setVisible(false);

        // 切换页面按钮
        JButton changeSate = new JButton("分配权限");
        changeSate.setBounds(600, yOffset, 100, 25);

        // 切换页面按钮监听事件,点击后更换页面并更新当前数据
        changeSate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = state == 0 ? 1 : 0;//设置另一状态
                boolean flag = state == 0; //flag为true表示当前展示的是makePermissionTable,为false表示展示的是assignPermissionTable
                if (flag)
                    setMakePermissionTable();
                else
                    setAssignPermissionTable();
                changeSate.setText(flag ? "分配权限" : "设置权限");
                preText.setVisible(!flag);
                roleSelect.setVisible(!flag);
                sufText.setVisible(!flag);
                assignPermissionTable.setVisible(!flag);

                text.setVisible(flag);
                makePermissionTable.setVisible(flag);
            }
        });

        yOffset += 40;
        // 分配权限表格
        assignPermissionTable = new JPanel(new GridLayout(0, 5, 10, 3));
        assignPermissionTable.setBounds(20, yOffset, 600, 400);
        assignPermissionTable.setVisible(false);

        // 设置权限表格
        makePermissionTable = new JPanel(new GridLayout(0, 4, 10, 3));
        makePermissionTable.setBounds(20, yOffset, 700, 400);

        this.add(text);
        this.add(preText);
        this.add(sufText);
        this.add(assignPermissionTable);
        this.add(makePermissionTable);
        this.add(roleSelect);
        this.add(changeSate);
        this.add(title);
    }

    @Override
    /**
     * 从数据库获取数据初始化面板组件
     */
    public void init() {
        Role[] roleList = getRole();
        for (Role role : roleList) {
            roleSelect.addItem(role);
        }
        setMakePermissionTable();
    }


    /**
     * 分配权限界面初始化
     */
    public void setAssignPermissionTable() {
        assignPermissionTable.removeAll();

        // 获取当前角色的所有权限
        RolePermission[] rolePermissionList = getRolePermission();

        // 获取已经设置好的所有权限
        Permission[] permissionList = getPermission();

        // 根据权限的数量设置高度
        assignPermissionTable.setBounds(20, 110, 700, (permissionList.length + 4) / 5 * 30);

        // 初始化选择框
        JCheckBox[] checkBoxes = new JCheckBox[permissionList.length];
        int index = 0;

        // 为所有选择框设置监听器
        for (Permission permission : permissionList) {
            checkBoxes[index] = new JCheckBox(permission.permissionName);
            JCheckBox perCheck = checkBoxes[index];
            index++;

            perCheck.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Permission per = permission; // 当前对应权限的信息
                    try {
                        Statement statement = connection.createStatement();
                        Role role = (Role) roleSelect.getSelectedItem();
                        String changePermissionSql;
                        Map<String, String> map = new HashMap<>();
                        map.clear();
                        map.put("permission_id", permission.permissionId);
                        map.put("role_id", role.roleId);
                        if (perCheck.isSelected()) {
                            // 为当前角色新增该权限
                            changePermissionSql = MysqlUtil.Insert("t_role_permission", map);
                        } else {
                            // 删除当前角色的该权限
                            changePermissionSql = MysqlUtil.Delete("t_role_permission", map);
                        }
                        statement.executeUpdate(changePermissionSql);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            assignPermissionTable.add(perCheck);
        }

        // 选中已经被分配给该角色的权限
        for (RolePermission rolePermission : rolePermissionList) {
            for (int i = 0; i < permissionList.length; i++) {
                if (permissionList[i].permissionId.equals(rolePermission.permissionId)) {
                    System.out.println(permissionList[i].permissionName);
                    checkBoxes[i].setSelected(true);
                    break;
                }
            }
        }

        assignPermissionTable.updateUI();
    }


    /**
     * 设置权限界面初始化
     */
    public void setMakePermissionTable() {

        makePermissionTable.removeAll();

        // 获取所有操作对象列表
        Obj[] objList = getObj();

        // 获取所有操作动作列表
        Action[] actionList = getAction();

        // 获取已经分配好的所有权限
        Permission[] permissionList = getPermission();

        // 为设置表格设置样式
        makePermissionTable.setLayout(new GridLayout(0, objList.length + 1, 10, 3));
        makePermissionTable.setBounds(20, 110, 700, (actionList.length + 1) * 30);

        // 第一行为所有的操作对象
        makePermissionTable.add(new JLabel()); //占位
        for (Obj obj : objList) {
            makePermissionTable.add(new JLabel(obj.objName));
        }

        // 初始化选择框并添加监听对象,在监听到勾选状态发生改变时上传数据到数据库
        checkBoxes = new JCheckBox[actionList.length][objList.length];
        for (int i = 0; i < actionList.length; i++) {
            makePermissionTable.add(new JLabel(actionList[i].actionName));
            for (int j = 0; j < checkBoxes[i].length; j++) {
                checkBoxes[i][j] = new JCheckBox();
                makePermissionTable.add(checkBoxes[i][j]);
                Obj obj = objList[j];
                Action action = actionList[i];
                JCheckBox checkBox = checkBoxes[i][j];
                checkBox.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String objId = obj.objId;
                        String objName = obj.objName;
                        String actionId = action.actionId;
                        String actionName = action.actionName;
                        try {
                            Statement statement = connection.createStatement();
                            String changePermissionSql;
                            Map<String, String> map = new HashMap<>();
                            map.clear();
                            map.put("obj_id", objId);
                            map.put("action_id", actionId);

                            if (checkBox.isSelected()) {
                                // 新增对应的权限
                                String permission_id = MysqlUtil.getNextId("t_permission", "permission_id");
                                map.put("permission_id", permission_id);
                                map.put("permission_name", actionName + objName);
                                changePermissionSql = MysqlUtil.Insert("t_permission", map);
                            } else {
                                // 删除对应的权限
                                changePermissionSql = MysqlUtil.Delete("t_permission", map);
                            }
                            statement.executeUpdate(changePermissionSql);

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        System.out.println(actionName + objName);
                    }
                });
            }
        }

        // 设置默认选中的权限组合
        for (Permission permission : permissionList) {
            checkBoxes[Integer.parseInt(permission.actionId) - 1][Integer.parseInt(permission.objId) - 1].setSelected(true);
        }
    }

    /**
     * 获取当前角色的权限操作
     *
     * @return 当前对象的权限列表
     */
    private RolePermission[] getRolePermission() {
        List<RolePermission> permissionList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            Role role = (Role) roleSelect.getSelectedItem();
            String getRoleSql = "select permission_id,role_id from t_role_permission where role_id = " + role.roleId;
            ResultSet rs = statement.executeQuery(getRoleSql);
            while (rs.next()) {
                permissionList.add(new RolePermission(rs.getString(1), rs.getString(2)));
            }
            System.out.println(permissionList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissionList.toArray(new RolePermission[permissionList.size()]);
    }


    /**
     * 获得已经设置好的所有权限
     *
     * @return 设置好的权限列表
     */
    public Permission[] getPermission() {
        List<Permission> permissionList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getRoleSql = "select permission_id,permission_name,obj_id,action_id from t_permission";
            ResultSet rs = statement.executeQuery(getRoleSql);
            while (rs.next()) {
                permissionList.add(new Permission(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            System.out.println(permissionList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return permissionList.toArray(new Permission[permissionList.size()]);
    }

    /**
     * 获取所有角色
     *
     * @return 所有角色列表
     */
    public Role[] getRole() {
        List<Role> roleList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getRoleSql = "select role_id,role_name from t_role";
            ResultSet rs = statement.executeQuery(getRoleSql);
            while (rs.next()) {
                roleList.add(new Role(rs.getString(1), rs.getString(2)));
            }
            System.out.println(roleList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleList.toArray(new Role[roleList.size()]);
    }

    /**
     * 获取所有操作对象
     *
     * @return 操作对象列表
     */
    public Obj[] getObj() {
        List<Obj> objList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getRoleSql = "select obj_id,obj_name from t_object";
            ResultSet rs = statement.executeQuery(getRoleSql);
            while (rs.next()) {
                objList.add(new Obj(rs.getString(1), rs.getString(2)));
            }
            System.out.println(objList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objList.toArray(new Obj[objList.size()]);
    }

    /**
     * 获取所有操作
     *
     * @return 操作列表
     */
    public Action[] getAction() {
        List<Action> actionList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String getRoleSql = "select action_id,action_name from t_action";
            ResultSet rs = statement.executeQuery(getRoleSql);
            while (rs.next()) {
                actionList.add(new Action(rs.getString(1), rs.getString(2)));
            }
            System.out.println(actionList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actionList.toArray(new Action[actionList.size()]);
    }

    @Override
    public String toString() {
        return "PermissionManagePanel";
    }

    /**
     * 角色权限对象
     */
    public class RolePermission {
        String permissionId;
        String role_id;

        public RolePermission(String permissionId, String role_id) {
            this.permissionId = permissionId;
            this.role_id = role_id;
        }

        @Override
        public String toString() {
            return "RolePermission{" +
                    "permissionId='" + permissionId + '\'' +
                    ", role_id='" + role_id + '\'' +
                    '}';
        }
    }

    /**
     * 权限对象
     */
    public class Permission {
        String permissionName;
        String permissionId;
        String objId;
        String actionId;

        public Permission(String permissionId, String permissionName, String objId, String actionId) {
            this.permissionName = permissionName;
            this.permissionId = permissionId;
            this.objId = objId;
            this.actionId = actionId;
        }

        @Override
        public String toString() {
            return permissionName;
        }
    }

    /**
     * 角色对象
     */
    public class Role {
        String roleName;
        String roleId;

        public Role(String roleId, String roleName) {
            this.roleName = roleName;
            this.roleId = roleId;
        }

        @Override
        public String toString() {
            return roleName;
        }
    }

    /**
     * 操作对象对象
     */
    public class Obj {
        String objId;
        String objName;

        public Obj(String objId, String objName) {
            this.objId = objId;
            this.objName = objName;
        }

        @Override
        public String toString() {
            return objName;
        }
    }

    /**
     * 操作对象
     */
    public class Action {
        String actionId;
        String actionName;

        public Action(String actionId, String actionName) {
            this.actionId = actionId;
            this.actionName = actionName;
        }

        @Override
        public String toString() {
            return actionName;
        }
    }
}
