package com.hj.component.managerMenu;

import com.hj.component.MyJPanel;
import com.hj.component.ResultDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClassManagePanel extends MyJPanel {
    Connection connection;
    private List<ClassItem> classItemList;
    private JPanel classTable;

    public ClassManagePanel(Connection connection) {
        this.connection = connection;
        this.setLayout(null);
        int yOffset = 20;


        // 标题
        Font titleFont = new Font("宋体", Font.BOLD, 18);
        JLabel title = new JLabel("教务管理 > 班级管理");
        title.setFont(titleFont);
        title.setBounds(20, yOffset, 300, 20);

        // 班级表格
        classTable = new JPanel(new GridLayout(0, 6, 10, 3));

        this.add(classTable);
        this.add(title);
    }

    @Override
    /**
     * 初始化界面
     */
    public void init() {
        // 表格标题
        classTable.removeAll();
        JLabel classIdTitle = new JLabel("班级id");
        JLabel classNameTitle = new JLabel("班级名称");
        JLabel classStudentNumTitle = new JLabel("班级人数");
        JLabel classMasterNameTitle = new JLabel("班主任");
        JLabel classMasterEmailTitle = new JLabel("班主任邮箱");
        JLabel actionTitle = new JLabel("操作");
        classTable.add(classIdTitle);
        classTable.add(classNameTitle);
        classTable.add(classStudentNumTitle);
        classTable.add(classMasterNameTitle);
        classTable.add(classMasterEmailTitle);
        classTable.add(actionTitle);

        try {
            // 获取班级数据
            classItemList = new ArrayList<>();
            Statement statement = connection.createStatement();
            String getAllStudentSql = "select class_id,class_name,teacher_id,`name`,u_mail from t_class,t_teacher,t_user_info WHERE t_class.class_master=t_teacher.teacher_id and t_teacher.info_id=t_user_info.info_id";
            ResultSet rs = statement.executeQuery(getAllStudentSql);

            while (rs.next()) {
                classItemList.add(new ClassItem(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

            System.out.println(1);
            classTable.setBounds(20, 60, 700, classItemList.size() * 30);

            // 添加数据到表格中
            for (int i = 0; i < classItemList.size(); i++) {
                ClassItem classItem = classItemList.get(i);

                JLabel classId = new JLabel(classItem.classId);
                JLabel className = new JLabel(classItem.className);
                JLabel classStudentNum = new JLabel(classItem.studentNum);
                JLabel classMasterName = new JLabel(classItem.classMasterName);
                JLabel classMasterEmail = new JLabel(classItem.classMasterEmail);
                JButton action = new JButton("修改班主任");
                // 修改班主任按钮
                action.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ResultDialog("班级 " + classItem.className + " 班主任修改成功");
                    }
                });
                classTable.add(classId);
                classTable.add(className);
                classTable.add(classStudentNum);
                classTable.add(classMasterName);
                classTable.add(classMasterEmail);
                classTable.add(action);
            }
            classTable.updateUI();
            this.updateUI();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ClassManagePanel";
    }

    /**
     * 班级对象
     */
    public class ClassItem {
        String classId;
        String className;
        String studentNum;
        String classMasterId;
        String classMasterName;
        String classMasterEmail;

        public ClassItem(String classId, String className, String classMasterId, String classMasterName, String classMasterEmail) {
            this.classId = classId;
            this.className = className;
            this.classMasterId = classMasterId;
            this.classMasterName = classMasterName;
            this.classMasterEmail = classMasterEmail;
            Random r = new Random();
            studentNum = r.nextInt(30) + 20 + "";
        }
    }
}
