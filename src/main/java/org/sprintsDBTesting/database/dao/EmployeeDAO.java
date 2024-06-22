package org.sprintsDBTesting.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {
    private final Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertEmployee(String name, String email, String phoneNo, double salary, String birthDate, String hireDate, boolean isActive, String role, String description, int roleId, int departmentId) throws SQLException {
        String sql = "INSERT INTO employees (name, email, phone_no, salary, birth_date, hire_date, is_active, role, description, role_id, department_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phoneNo);
            stmt.setDouble(4, salary);
            stmt.setString(5, birthDate);
            stmt.setString(6, hireDate);
            stmt.setBoolean(7, isActive);
            stmt.setString(8, role);
            stmt.setString(9, description);
            stmt.setInt(10, roleId);
            stmt.setInt(11, departmentId);
            stmt.executeUpdate();
        }
    }

    public ResultSet getEmployeeByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM employees WHERE email = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, email);
        return stmt.executeQuery();
    }
}
