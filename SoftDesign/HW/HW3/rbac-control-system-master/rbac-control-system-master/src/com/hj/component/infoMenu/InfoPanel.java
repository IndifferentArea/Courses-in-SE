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

public class InfoPanel extends MyJPanel {
    Connection connection;
    private String info_id;
    private String roleName;
    private String name;
    private String gender;
    private String email;
    private String age;

    private JLabel idText;
    private JLabel nameText;
    private JRadioButton male;
    private JRadioButton female;
    private JLabel roleText;
    private JTextField emailText;
    private JTextField ageText;

    public InfoPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("个人信息 > 我的信息");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 40;

        // 学号
        JLabel idLabel = new JLabel("学工号: ");
        idLabel.setBounds(70, yOffset, 60, 25);
        idText = new JLabel(info_id);
        idText.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 姓名
        JLabel nameLabel = new JLabel("姓名: ");
        nameLabel.setBounds(70, yOffset, 60, 25);
        nameText = new JLabel(name);
        nameText.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 性别
        JLabel genderLabel = new JLabel("性别: ");
        genderLabel.setBounds(70, yOffset, 60, 30);
        male = new JRadioButton("男");
        male.setSelected("男".equals(gender));
        male.setBounds(130, yOffset, 50, 30);
        female = new JRadioButton("女");
        female.setSelected("女".equals(gender));
        female.setBounds(180, yOffset, 50, 30);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        yOffset += 50;

        // 角色
        JLabel roleLabel = new JLabel("角色: ");
        roleLabel.setBounds(70, yOffset, 60, 25);
        roleText = new JLabel(roleName);
        roleText.setBounds(130, yOffset, 120, 25);
        yOffset += 50;


        // 邮箱
        JLabel emailLabel = new JLabel("邮箱: ");
        emailLabel.setBounds(70, yOffset, 60, 25);
        emailText = new JTextField(20);
        emailText.setBounds(130, yOffset, 150, 25);
        yOffset += 50;

        // 年龄
        JLabel ageLabel = new JLabel("年龄: ");
        ageLabel.setBounds(70, yOffset, 60, 25);
        ageText = new JTextField(20);
        ageText.setBounds(130, yOffset, 150, 25);
        yOffset += 50;

        // 添加按钮
        JButton updateInfoButton = new JButton("修改");
        updateInfoButton.setBounds(90, yOffset, 100, 25);

        // 为更新按钮添加监听事件
        updateInfoButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gender = male.isSelected() ? "男" : "女";
                Map<String, String> conditionMap = new HashMap<>();
                conditionMap.put("info_id", info_id);
                Map<String, String> valueMap = new HashMap<>();
                valueMap.put("u_gender", gender);
                valueMap.put("u_mail", emailText.getText());
                valueMap.put("u_age", ageText.getText());
                System.out.println(info_id + "" + name + " " + gender + " " + email);
                try {
                    Statement statement = connection.createStatement();
                    String sqlStatement = MysqlUtil.Update("t_user_info", conditionMap, valueMap);
                    statement.executeUpdate(sqlStatement);

                    //返回结果
                    ResultDialog resultDialog = new ResultDialog("修改成功");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.add(idLabel);
        this.add(idText);
        this.add(nameLabel);
        this.add(nameText);
        this.add(genderLabel);
        this.add(male);
        this.add(female);
        this.add(roleLabel);
        this.add(roleText);
        this.add(emailLabel);
        this.add(emailText);
        this.add(ageLabel);
        this.add(ageText);
        this.add(updateInfoButton);

        this.add(title);
    }

    /**
     * 初始化函数,根据当前登录用户的info_id获取其所有信息,并更新页面
     */
    public void init() {
        try {
            Statement statement = connection.createStatement();
            // 获取所有基本信息
            Map<String, String> map = new HashMap<>();
            map.put("info_id", info_id);
            String getInfoSql = MysqlUtil.Select("t_user_info", map);
            ResultSet rs = statement.executeQuery(getInfoSql);
            rs.next();

            info_id = rs.getString(1);
            name = rs.getString(4);
            gender = rs.getString(5);
            email = rs.getString(6);
            age = rs.getString(7);

            //获取roleName
            String roleId = rs.getString(2);
            map.remove("info_id");
            map.put("role_id", roleId);
            String getRoleNameSql = MysqlUtil.Select("t_role", "role_name", map);
            ResultSet rs1 = statement.executeQuery(getRoleNameSql);
            rs1.next();
            roleName = rs1.getString(1);

            idText.setText(info_id);
            nameText.setText(name);
            male.setSelected("男".equals(gender));
            female.setSelected("女".equals(gender));
            roleText.setText(roleName);
            emailText.setText(email);
            ageText.setText(age);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setId(String id) {
        this.info_id = id;
    }

    @Override
    public String toString() {
        return "InfoPanel";
    }
}
