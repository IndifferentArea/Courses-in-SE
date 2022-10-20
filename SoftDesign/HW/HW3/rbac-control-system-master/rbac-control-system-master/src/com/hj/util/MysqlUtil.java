package com.hj.util;

import java.sql.*;
import java.util.Map;

/**
 * 封装的关于Mysql的组件库
 * 包括的功能有新建连接,关闭连接
 * 以及生成一些简单的增删改查语句
 */
public class MysqlUtil {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet rs;

    /**
     * 生成于数据库的连接
     *
     * @return 连接对象
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/systemDesign?user=root&password=root";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭连接
     *
     * @throws SQLException
     */
    public static void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    /**
     * 生成插入语句
     *
     * @param tableName 插入的表的名称
     * @param map       键值对,即对应的是 "where key = value"
     * @return 生成的insert语句
     */
    public static String Insert(String tableName, Map<String, String> map) {
        StringBuilder prefix = new StringBuilder();
        StringBuilder suffix = new StringBuilder();
        int count = map.size();
        prefix.append("insert into " + tableName + " (");
        suffix.append(" values ( ");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            prefix.append(entry.getKey());
            suffix.append("\"" + entry.getValue() + "\"");
            if (count > 1) {
                prefix.append(", ");
                suffix.append(", ");
                count--;
            }
        }
        prefix.append(")");
        suffix.append(")");
        String ret = prefix.toString() + suffix;
        System.out.println(ret);
        return ret;
    }

    /**
     * 生成删除语句
     *
     * @param tableName 删除的表的名称
     * @param map       键值对,即对应的是"where key = value"
     * @return 生成的delete语句
     */
    public static String Delete(String tableName, Map<String, String> map) {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("delete from " + tableName + " where ");
        int count = map.size();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sqlStatement.append(entry.getKey() + " = " + entry.getValue());
            if (count > 1) {
                sqlStatement.append(" and ");
                count--;
            }
        }
        System.out.println(sqlStatement);
        return sqlStatement.toString();
    }

    /**
     * 生成更新语句
     *
     * @param tableName    需要更新的数据所在表的名称
     * @param conditionMap 条件键值对,即对应的是"where key = value"
     * @param valueMap     更新值键值对,即对应的是"set key = value"
     * @return 生成的更新语句
     */
    public static String Update(String tableName, Map<String, String> conditionMap, Map<String, String> valueMap) {
        StringBuilder prefix = new StringBuilder();
        prefix.append("update " + tableName + " set ");
        StringBuilder suffix = new StringBuilder();
        suffix.append(" where ");

        int count = valueMap.size();
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            prefix.append(entry.getKey() + " = \"" + entry.getValue() + "\"");
            if (count > 1) {
                count--;
                prefix.append(" , ");
            }
        }

        count = conditionMap.size();
        for (Map.Entry<String, String> entry : conditionMap.entrySet()) {
            suffix.append(entry.getKey() + " = \"" + entry.getValue() + "\"");
            if (count > 1) {
                count--;
                prefix.append(" , ");
            }
        }
        String ret = prefix.toString() + suffix;
        System.out.println(ret);
        return ret;
    }

    /**
     * 生成查询语句
     *
     * @param tableName 需要查询的表的名称
     * @param map       键值对,即"where key = value",当map为null时返回所有数据
     * @return 生成的sql语句
     */
    public static String Select(String tableName, Map<String, String> map) {
        if (map == null) {
            return "select * from " + tableName;
        }
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("select * from " + tableName + " where ");
        int count = map.size();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sqlStatement.append(entry.getKey() + " = \"" + entry.getValue() + "\"");
            if (count > 1) {
                count--;
                sqlStatement.append(" and ");
            }
        }
        System.out.println(sqlStatement);
        return sqlStatement.toString();
    }

    /**
     * 生成查询语句
     *
     * @param tableName 需要查询的表的名称
     * @param type      select语句需要返回的内容
     * @param map       键值对,即对应的是"where key = value"
     * @return 生成的sql语句
     */
    public static String Select(String tableName, String type, Map<String, String> map) {
        if (map == null) {
            return "select " + type + " from " + tableName;
        }
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("select " + type + " from " + tableName + " where ");
        int count = map.size();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sqlStatement.append(entry.getKey() + " = \"" + entry.getValue() + "\"");
            if (count > 1) {
                count--;
                sqlStatement.append(" and ");
            }
        }
        System.out.println(sqlStatement);
        return sqlStatement.toString();
    }

    /**
     * 获取下一个值是多少,用于获得下一个插入数据的主键值
     *
     * @param tableName 查询表的名称
     * @param idTitle   查询列的名称
     * @return 该插入的值
     */
    public static String getNextId(String tableName, String idTitle) {
        String ret = null;
        try {
            Statement statement = connection.createStatement();
            String sql = "select max(" + idTitle + ") +1 from " + tableName;
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            ret = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(ret);
        return ret;
    }


}
