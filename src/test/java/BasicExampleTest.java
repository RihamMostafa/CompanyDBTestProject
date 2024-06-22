import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class BasicExampleTest {

    @Test
    public void checkDuplicate() throws SQLException {

        //1. start connection
        Connection connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/company_db_3"
                ,"company_db_3_user","1111");

        //2. run sql query
        ResultSet rs= connection.createStatement().executeQuery("""
                select email, count(*)
                from employees
                group by email
                having count(*)>1 ;
                """);

        // assert on the results
        assertFalse(rs.next());
        connection.close();

    }

    @Test
    public void checkEmpData() throws SQLException {

        Connection connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/company_db_3"
                ,"company_db_3_user","1111");

        ResultSet rs= connection.createStatement().executeQuery("select * from employees");

        rs.next();
        for (int i=0;  i<3; i++)
        {
            if(i==2)  System.out.println(rs.getString("id")+ " "+rs.getString("name"));
        }


    }

}
