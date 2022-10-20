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

public class AddClassPanel extends MyJPanel {
    Connection connection;
    private JComboBox classMasterSelect;

    public AddClassPanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;

        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("教务管理 > 添加班级");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);
        yOffset += 40;

        // 班级名称
        JLabel nameLabel = new JLabel("班级名称: ");
        nameLabel.setBounds(70, yOffset, 60, 25);
        JTextField nameText = new JTextField(20);
        nameText.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 班主任
        JLabel classMasterLabel = new JLabel("班主任: ");
        classMasterLabel.setBounds(70, yOffset, 60, 25);
        classMasterSelect = new JComboBox();
        classMasterSelect.setBounds(130, yOffset, 120, 25);
        yOffset += 50;

        // 添加按钮
        JButton addClassButton = new JButton("添加");
        addClassButton.setBounds(90, yOffset, 100, 25);

        // 为添加按钮设置监听事件
        addClassButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = nameText.getText();
                Teacher classMaster = (Teacher) classMasterSelect.getSelectedItem();
                Map<String, String> map = new HashMap<>();
                map.put("class_id", MysqlUtil.getNextId("t_class", "class_id"));
                map.put("class_name", className);
                map.put("class_master", classMaster.teacher_id);
                try {
                    Statement statement = connection.createStatement();

                    // 添加班级
                    String insertClassSql = MysqlUtil.Insert("t_class", map);
                    statement.executeUpdate(insertClassSql);

                    //返回结果
                    ResultDialog resultDialog = new ResultDialog("班级添加成功");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        this.add(nameLabel);
        this.add(nameText);
        this.add(classMasterLabel);
        this.add(classMasterSelect);
        this.add(addClassButton);

        this.add(title);
    }


    /// 获取教师列表
    private Teacher[] getTeachers() {
        Statement statement = null;
        List<Teacher> teacherList = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String sqlStatement = "select * from t_teacher,t_user_info where t_teacher.info_id = t_user_info.info_id";
            ResultSet rs = statement.executeQuery(sqlStatement);
            while (rs.next()) {
                teacherList.add(new Teacher(rs.getString(1), rs.getString(7)));
            }
            System.out.println(teacherList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherList.toArray(new Teacher[teacherList.size()]);
    }

    @Override
    /**
     * 初始化函数,获取所有教师的列表并添加到选择框中
     */
    public void init() {
        Teacher[] teacherList = getTeachers();
        for (Teacher teacher : teacherList) {
            classMasterSelect.addItem(teacher);
        }
    }

    @Override
    public String toString() {
        return "AddClassPanel";
    }

    /**
     * 教师对象
     */
    public class Teacher {
        String teacher_id;
        String teacher_name;

        public Teacher(String teacher_id, String teacher_name) {
            this.teacher_id = teacher_id;
            this.teacher_name = teacher_name;
        }

        @Override
        public String toString() {
            return teacher_name;
        }
    }
}
