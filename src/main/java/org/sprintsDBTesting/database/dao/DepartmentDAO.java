package org.sprintsDBTesting.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentDAO {
    private final Connection connection;

    public DepartmentDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertDepartment(String departmentName) throws SQLException {
        String sql = "INSERT INTO departments (department_name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, departmentName);
            stmt.executeUpdate();
        }
    }

    public ResultSet getDepartmentByName(String departmentName) throws SQLException {
        String sql = "SELECT * FROM departments WHERE department_name = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, departmentName);
        return stmt.executeQuery();
    }

    public int getDepartmentIdByName(String departmentName) throws SQLException {
        ResultSet resultSet = getDepartmentByName(departmentName);
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        throw new SQLException("Department not found");
    }
}
