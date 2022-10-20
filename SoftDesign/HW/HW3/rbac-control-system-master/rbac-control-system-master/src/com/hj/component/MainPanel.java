package com.hj.component;

import com.hj.customInterface.TabSwitch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 主界面框架
 */
public class MainPanel extends JPanel {
    Connection connection;
    TabSwitch tabSwitch;
    String info_id;

    public MainPanel(Connection connection, TabSwitch tabSwitch, String info_id) {
        super(new BorderLayout());
        this.connection = connection;
        this.tabSwitch = tabSwitch;
        this.info_id = info_id;

        // 菜单栏组件
        JMenuBar jMenuBar = new JMenuBar();

        // 个人信息
        MyMenu InfoMenu = new MyMenu("个人信息", "个人信息");
        MyMenuItem info = new MyMenuItem("我的信息", "InfoPanel", "修改基础信息");
        MyMenuItem changePwd = new MyMenuItem("修改密码", "ChangePwdPanel", "修改密码");
        InfoMenu.add(info);
        InfoMenu.add(changePwd);

        // 学生功能
        MyMenu StuMenu = new MyMenu("学生功能", "学生功能");
        MyMenuItem viewScore = new MyMenuItem("查看成绩", "ViewScorePanel", "查看成绩");
        StuMenu.add(viewScore);

        // 老师功能
        MyMenu teacherMenu = new MyMenu("老师功能", "老师功能");
        MyMenuItem addExam = new MyMenuItem("添加单元测试", "AddExamPanel", "添加测试");
        MyMenuItem scoreUpload = new MyMenuItem("成绩上传", "ScoreUploadPanel", "上传成绩");
        MyMenuItem examManageTeacher = new MyMenuItem("单元测试管理", "ExamManageTeacherPanel", "管理测试");
        teacherMenu.add(addExam);
        teacherMenu.add(scoreUpload);
        teacherMenu.add(examManageTeacher);

        // 班主任功能
        MyMenu classMasterMenu = new MyMenu("班主任功能", "班主任功能");
        MyMenuItem studentInfo = new MyMenuItem("本班学生信息", "StudentInfoPanel", "查看学生");
        MyMenuItem classExamManage = new MyMenuItem("本班考试信息", "ClassExamManagePanel", "查看考试");
        classMasterMenu.add(studentInfo);
        classMasterMenu.add(classExamManage);

        // 教务功能
        MyMenu managerMenu = new MyMenu("教务管理", "教务功能");
        MyMenuItem addUser = new MyMenuItem("添加用户", "AddUserPanel", "添加用户");
        MyMenuItem examManage = new MyMenuItem("考试安排", "ExamManagePanel", "安排考试");
        MyMenuItem addClass = new MyMenuItem("添加班级", "AddClassPanel", "添加班级");
        MyMenuItem classManage = new MyMenuItem("班级管理", "ClassManagePanel", "管理班级");
        MyMenuItem studentManage = new MyMenuItem("学生管理", "StudentManagePanel", "管理学生");
        MyMenuItem teacherManage = new MyMenuItem("教师管理", "TeacherManagePanel", "管理教师");
        managerMenu.add(addUser);
        managerMenu.add(examManage);
        managerMenu.add(addClass);
        managerMenu.add(classManage);
        managerMenu.add(studentManage);
        managerMenu.add(teacherManage);

        // 后台管理
        MyMenu superMenu = new MyMenu("后台管理", "后台功能");
        MyMenuItem addRole = new MyMenuItem("添加角色", "AddRolePanel", "添加角色");
        MyMenuItem roleManage = new MyMenuItem("角色管理", "RoleManagePanel", "管理角色");
        MyMenuItem addPermission = new MyMenuItem("添加权限", "AddPermissionPanel", "添加权限");
        MyMenuItem permissionManage = new MyMenuItem("权限管理", "PermissionManagePanel", "管理权限");
        superMenu.add(addRole);
        superMenu.add(roleManage);
        superMenu.add(addPermission);
        superMenu.add(permissionManage);

        jMenuBar.add(InfoMenu);
        jMenuBar.add(StuMenu);
        jMenuBar.add(teacherMenu);
        jMenuBar.add(classMasterMenu);
        jMenuBar.add(managerMenu);
        jMenuBar.add(superMenu);

//        StuMenu.setEnabled(false);
//        classMasterMenu.setEnabled(false);
//        managerMenu.setEnabled(false);
//        superMenu.setEnabled(false);

        // 构造所有菜单栏的列表,方便权限管理
        List<MyMenuItem> itemList = new ArrayList<>();
        itemList.add(info);
        itemList.add(changePwd);
        itemList.add(viewScore);
        itemList.add(addExam);
        itemList.add(scoreUpload);
        itemList.add(examManageTeacher);
        itemList.add(studentInfo);
        itemList.add(classExamManage);
        itemList.add(addUser);
        itemList.add(examManage);
        itemList.add(addClass);
        itemList.add(classManage);
        itemList.add(studentManage);
        itemList.add(teacherManage);
        itemList.add(addRole);
        itemList.add(roleManage);
        itemList.add(addPermission);
        itemList.add(permissionManage);

        // 为其设置回调函数,点击之后通知main函数切换页面
        for (int i = 0; i < itemList.size(); i++) {
            JMenuItem menuItem = itemList.get(i);
            menuItem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabSwitch.switchTo(menuItem.getName());
                }
            });
        }

        // 获取当前用户的权限并disable菜单栏的对应按钮
        try {
            Statement statement = connection.createStatement();
            String getPermissionSql = "select permission_name from t_user_info,t_permission,t_role_permission where info_id=" + info_id + " and t_user_info.role_id=t_role_permission.role_id and t_role_permission.permission_id=t_permission.permission_id";
            ResultSet rs = statement.executeQuery(getPermissionSql);
            Set<String> permissionSet = new HashSet<>();
            while (rs.next()) {
                permissionSet.add(rs.getString(1));
            }
            System.out.println(permissionSet.toString());
            // 禁用该用户没有的权限
            for (MyMenuItem menuItem : itemList) {
                if (!permissionSet.contains(menuItem.getPermission())) {
                    menuItem.setEnabled(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        this.add(jMenuBar, BorderLayout.NORTH);
        this.setVisible(true);
    }

}
