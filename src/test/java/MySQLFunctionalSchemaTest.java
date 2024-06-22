import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sprintsDBTesting.database.dao.DepartmentDAO;
import org.sprintsDBTesting.database.dao.EmployeeDAO;
import org.sprintsDBTesting.database.dao.RoleDAO;
import org.sprintsDBTesting.database.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class MySQLFunctionalSchemaTest {
    private Connection connection;
    private RoleDAO roleDAO;
    private DepartmentDAO departmentDAO;
    private EmployeeDAO employeeDAO;

    @BeforeEach
    public void setUp() throws Exception {
        connection = DatabaseConnection.getConnection();
        roleDAO = new RoleDAO(connection);
        departmentDAO = new DepartmentDAO(connection);
        employeeDAO = new EmployeeDAO(connection);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (connection != null && !connection.isClosed()) {
            try (Statement stmt = connection.createStatement()) {
                // Clean up test data
                stmt.execute("DELETE FROM employees");
                stmt.execute("DELETE FROM departments");
                stmt.execute("DELETE FROM roles");
            }
            connection.close();
        }
    }

    @Test
    public void testSchemaIntegrity() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            // Check roles table structure
            ResultSet resultSet = stmt.executeQuery("DESCRIBE roles");
            assertTrue(resultSet.next());
            assertEquals("id", resultSet.getString("Field"));
            assertTrue(resultSet.next());
            assertEquals("role_name", resultSet.getString("Field"));
            assertTrue(resultSet.next());
            assertEquals("salary", resultSet.getString("Field"));

            // Check departments table structure
            resultSet = stmt.executeQuery("DESCRIBE departments");
            assertTrue(resultSet.next());
            assertEquals("id", resultSet.getString("Field"));
            assertTrue(resultSet.next());
            assertEquals("department_name", resultSet.getString("Field"));

            // Check employees table structure
            resultSet = stmt.executeQuery("DESCRIBE employees");
            assertTrue(resultSet.next());
            assertEquals("id", resultSet.getString("Field"));
            assertTrue(resultSet.next());
            assertEquals("name", resultSet.getString("Field"));
            // Etc...
        }
    }

    @Test
    public void testInsertAndRetrieveRole() throws SQLException {
        roleDAO.insertRole("developer", 60000.00);
        ResultSet resultSet = roleDAO.getRoleByName("developer");
        assertTrue(resultSet.next());
        assertEquals("developer", resultSet.getString("role_name"));
        assertEquals(60000.00, resultSet.getDouble("salary"));
    }

    @Test
    public void testInsertAndRetrieveDepartment() throws SQLException {
        departmentDAO.insertDepartment("Engineering");
        ResultSet resultSet = departmentDAO.getDepartmentByName("Engineering");
        assertTrue(resultSet.next());
        assertEquals("Engineering", resultSet.getString("department_name"));
    }

    @Test
    public void testInsertAndRetrieveEmployee() throws SQLException {
        roleDAO.insertRole("developer", 60000.00);
        departmentDAO.insertDepartment("Engineering");

        int roleId = roleDAO.getRoleIdByName("developer");
        int departmentId = departmentDAO.getDepartmentIdByName("Engineering");

        employeeDAO.insertEmployee("John Doe", "john.doe@example.com", "1234567890", 75000.00, "1990-01-01", "2023-01-01 10:00:00", true, "developer", "A skilled developer", roleId, departmentId);
        ResultSet resultSet = employeeDAO.getEmployeeByEmail("john.doe@example.com");
        assertTrue(resultSet.next());
        assertEquals("John Doe", resultSet.getString("name"));
        assertEquals("john.doe@example.com", resultSet.getString("email"));
        assertEquals("1234567890", resultSet.getString("phone_no"));
        assertEquals(75000.00, resultSet.getDouble("salary"));
        assertEquals("1990-01-01", resultSet.getDate("birth_date").toString());
        assertEquals("2023-01-01 10:00:00.0", resultSet.getTimestamp("hire_date").toString());
        assertTrue(resultSet.getBoolean("is_active"));
        assertEquals("developer", resultSet.getString("role"));
        assertEquals("A skilled developer", resultSet.getString("description"));
    }


}
