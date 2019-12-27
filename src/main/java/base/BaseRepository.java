package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class BaseRepository {

    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static final String connectionString = "jdbc:mysql://localhost/kadictionary?"
        + "user=javauser&password=&useSSL=false&useUnicode=yes&characterEncoding=UTF-8";

    protected Connection connect = null;
    protected Statement statement = null;
    protected ResultSet resultSet = null;

    protected void openConnection() throws Exception {
        Class.forName(jdbcDriver);
        connect = DriverManager.getConnection(connectionString);
        statement = connect.createStatement();
    }

    protected void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception ignored) {
        }
    }
}
