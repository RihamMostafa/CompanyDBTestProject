package org.sprintsDBTesting.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
    private final Connection connection;

    public RoleDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertRole(String roleName, double salary) throws SQLException {
        String sql = "INSERT INTO roles (role_name, salary) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, roleName);
            stmt.setDouble(2, salary);
            stmt.executeUpdate();
        }
    }

    public ResultSet getRoleByName(String roleName) throws SQLException {
        String sql = "SELECT * FROM roles WHERE role_name = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, roleName);
        return stmt.executeQuery();
    }

    public int getRoleIdByName(String roleName) throws SQLException {
        ResultSet resultSet = getRoleByName(roleName);
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        throw new SQLException("Role not found");
    }
}
