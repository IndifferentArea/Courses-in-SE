package com.hj;

import com.hj.component.LoginPanel;
import com.hj.component.MainPanel;
import com.hj.component.MyFrame;
import com.hj.component.MyJPanel;
import com.hj.component.classMasterMenu.ClassExamManagePanel;
import com.hj.component.classMasterMenu.StudentInfoPanel;
import com.hj.component.infoMenu.ChangePwdPanel;
import com.hj.component.infoMenu.InfoPanel;
import com.hj.component.managerMenu.*;
import com.hj.component.stuMenu.ViewScorePanel;
import com.hj.component.superMenu.AddPermissionPanel;
import com.hj.component.superMenu.AddRolePanel;
import com.hj.component.superMenu.PermissionManagePanel;
import com.hj.component.superMenu.RoleManagePanel;
import com.hj.component.teacherMenu.AddExamPanel;
import com.hj.component.teacherMenu.ExamManageTeacherPanel;
import com.hj.component.teacherMenu.ScoreUploadPanel;
import com.hj.customInterface.LoginInterface;
import com.hj.customInterface.TabSwitch;
import com.hj.util.MysqlUtil;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // 生成的与数据库的连接
    static Connection connection;
    static MyFrame myFrame;
    static LoginPanel loginPanel;
    static MainPanel mainPanel;
    static MyJPanel currentPanel;

    // 菜单组件
    static InfoPanel infoPanel;
    static ChangePwdPanel changePwdPanel;
    static ViewScorePanel viewScorePanel;
    static AddExamPanel addExamPanel;
    static ScoreUploadPanel scoreUploadPanel;
    static ExamManageTeacherPanel examManageTeacherPanel;
    static StudentInfoPanel studentInfoPanel;
    static ClassExamManagePanel classExamManagePanel;
    static AddUserPanel addUserPanel;
    static ExamManagePanel examManagePanel;
    static AddClassPanel addClassPanel;
    static ClassManagePanel classManagePanel;
    static StudentManagePanel studentManagePanel;
    static TeacherManagePanel teacherManagePanel;
    static AddRolePanel addRolePanel;
    static RoleManagePanel roleManagePanel;
    static AddPermissionPanel addPermissionPanel;
    static PermissionManagePanel permissionManagePanel;

    // 组件及对象映射表
    static Map<String, MyJPanel> map;

    //当前登录用户的id
    static String info_id;

    public static void main(String[] args) {
        connection = MysqlUtil.getConnection();

        // 生成登录页面,并将登录回调接口传入
        loginPanel = new LoginPanel(connection, new LoginImpl());
        map = new HashMap<>();

        // 菜单组件
        infoPanel = new InfoPanel(connection);
        map.put("InfoPanel", infoPanel);

        changePwdPanel = new ChangePwdPanel(connection);
        map.put("ChangePwdPanel", changePwdPanel);

        viewScorePanel = new ViewScorePanel(connection);
        map.put("ViewScorePanel", viewScorePanel);

        addExamPanel = new AddExamPanel(connection);
        map.put("AddExamPanel", addExamPanel);

        scoreUploadPanel = new ScoreUploadPanel(connection);
        map.put("ScoreUploadPanel", scoreUploadPanel);

        examManageTeacherPanel = new ExamManageTeacherPanel(connection);
        map.put("ExamManageTeacherPanel", examManageTeacherPanel);

        studentInfoPanel = new StudentInfoPanel(connection);
        map.put("StudentInfoPanel", studentInfoPanel);

        classExamManagePanel = new ClassExamManagePanel(connection);
        map.put("ClassExamManagePanel", classExamManagePanel);

        addUserPanel = new AddUserPanel(connection);
        map.put("AddUserPanel", addUserPanel);

        examManagePanel = new ExamManagePanel(connection);
        map.put("ExamManagePanel", examManagePanel);

        addClassPanel = new AddClassPanel(connection);
        map.put("AddClassPanel", addClassPanel);

        classManagePanel = new ClassManagePanel(connection);
        map.put("ClassManagePanel", classManagePanel);

        studentManagePanel = new StudentManagePanel(connection);
        map.put("StudentManagePanel", studentManagePanel);

        teacherManagePanel = new TeacherManagePanel(connection);
        map.put("TeacherManagePanel", teacherManagePanel);

        addRolePanel = new AddRolePanel(connection);
        map.put("AddRolePanel", addRolePanel);

        roleManagePanel = new RoleManagePanel(connection);
        map.put("RoleManagePanel", roleManagePanel);

        addPermissionPanel = new AddPermissionPanel(connection);
        map.put("AddPermissionPanel", addPermissionPanel);

        permissionManagePanel = new PermissionManagePanel(connection);
        map.put("PermissionManagePanel", permissionManagePanel);

        // 主框架
        myFrame = new MyFrame();

        myFrame.add(loginPanel);
    }

    /**
     * 登录回调函数的实现
     */
    static class LoginImpl implements LoginInterface {

        @Override
        /**
         * 登录回调函数
         * 跳转页面到mainPanel,并且进行一系列初始化
         *
         * @param id 当前登录的用户的id
         */
        public void loginSuccess(String id) {
            mainPanel = new MainPanel(connection, new TabSwitchImpl(), id);

            myFrame.remove(loginPanel);
            myFrame.setBounds(100, 100, 800, 500);
            myFrame.setLocationRelativeTo(null);

            myFrame.add(mainPanel);
            mainPanel.add(infoPanel);
            infoPanel.setId(id);
            info_id = id;
            infoPanel.init();
            infoPanel.updateUI();
            currentPanel = infoPanel;
        }
    }

    /**
     * 页面切换回调函数的实现
     */
    static class TabSwitchImpl implements TabSwitch {

        @Override
        /**
         * 页面切换回调函数
         * 根据返回的页面名称在map中查找需要跳转到的页面
         * @param tabName 切换到的页面的名称
         */
        public void switchTo(String tabName) {
            System.out.println("panel: " + map.get(tabName));
            mainPanel.remove(currentPanel);
            map.get(tabName).setId(info_id);
            map.get(tabName).init();
            currentPanel = map.get(tabName);
            mainPanel.add(map.get(tabName));
            mainPanel.updateUI();
        }
    }
}
