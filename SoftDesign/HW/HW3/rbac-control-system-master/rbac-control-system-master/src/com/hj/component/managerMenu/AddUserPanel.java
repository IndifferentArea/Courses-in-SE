package com.hj.component.managerMenu;

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

public class AddUserPanel extends MyJPanel {
    Connection connection;
    private RoleItem[] roleItems;
    private JComboBox roleSelect;


    public AddUserPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("教务管理 > 添加用户");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 40;

        // 姓名
        JLabel nameLabel = new JLabel("姓名: ");
        nameLabel.setBounds(70, yOffset, 60, 25);
        JTextField nameText = new JTextField(20);
        nameText.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 性别
        JLabel genderLabel = new JLabel("性别: ");
        genderLabel.setBounds(70, yOffset, 60, 30);
        JRadioButton male = new JRadioButton("男");
        male.setBounds(130, yOffset, 50, 30);
        JRadioButton female = new JRadioButton("女");
        female.setBounds(180, yOffset, 50, 30);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        yOffset += 50;

        // 角色
        JLabel roleLabel = new JLabel("角色: ");
        roleLabel.setBounds(70, yOffset, 60, 25);
        roleSelect = new JComboBox();
        roleSelect.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 添加按钮
        JButton addUserButton = new JButton("添加");
        addUserButton.setBounds(90, yOffset, 100, 25);

        // 为添加按钮设置监听事件
        addUserButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String gender = male.isSelected() ? "男" : "女";
                RoleItem role = (RoleItem) roleSelect.getSelectedItem();
                Map<String, String> map = new HashMap<>();
                String info_id = MysqlUtil.getNextId("t_user_info", "info_id");
                map.put("info_id", info_id);
                map.put("name", name);
                map.put("u_gender", gender);
                map.put("role_id", role.role_id);
                try {
                    Statement statement = connection.createStatement();
                    // 添加用户
                    String sqlStatement = MysqlUtil.Insert("t_user_info", map);
                    System.out.println(sqlStatement);
                    statement.executeUpdate(sqlStatement);

                    // 返回结果
                    ResultDialog resultDialog = new ResultDialog("添加成功");

                    // 如果是老师或者学生的话需要额外加一次
                    // 学生
                    if (role.role_id.equals("1")) {
                        map.clear();
                        map.put("stu_id", MysqlUtil.getNextId("t_student", "stu_id"));
                        map.put("info_id", info_id);
                        String insertStudentSql = MysqlUtil.Insert("t_student", map);
                        statement.executeUpdate(insertStudentSql);
                    }

                    // 老师
                    if (role.role_id.equals("2")) {
                        map.clear();
                        map.put("teacher_id", MysqlUtil.getNextId("t_teacher", "teacher_id"));
                        map.put("info_id", info_id);
                        String insertStudentSql = MysqlUtil.Insert("t_teacher", map);
                        statement.executeUpdate(insertStudentSql);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.add(nameLabel);
        this.add(nameText);
        this.add(genderLabel);
        this.add(male);
        this.add(female);
        this.add(roleLabel);
        this.add(roleSelect);
        this.add(addUserButton);

        this.add(title);
    }


    /**
     * 获取所有角色列表
     *
     * @return 角色列表
     */
    private RoleItem[] getRoleItems() {
        Statement statement = null;
        List<RoleItem> roleItemList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sqlStatement = MysqlUtil.Select("t_role", null);
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                roleItemList.add(new RoleItem(rs.getString(2), rs.getString(1)));
            }
            System.out.println(roleItemList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleItemList.toArray(new RoleItem[roleItemList.size()]);
    }

    @Override
    public void init() {
        roleItems = getRoleItems();
        for (RoleItem roleItem : roleItems) {
            roleSelect.addItem(roleItem);
        }
    }

    @Override
    public String toString() {
        return "AddUserPanel";
    }

    /**
     * 角色对象
     */
    class RoleItem {
        String role;
        String role_id;

        public RoleItem(String role, String role_id) {
            this.role = role;
            this.role_id = role_id;
        }

        @Override
        public String toString() {
            return role;
        }
    }
}
