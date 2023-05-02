import java.sql.*;
import java.sql.DriverManager;
import java.sql.Statement;


public class connection {
    private static Connection con;
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/jdbcBankApp";
            String user = "root";
            String password = "Bhawana1234@";

             con = DriverManager.getConnection(url, user, password);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;


    }
}
