/*
 * Class DatabaseConnection
 * @author: Xuehua Lan
 * @version JavaSE-17
*/ 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/gmcc";
    private static final String USER = "root";
    private static final String PASSWORD = "test";
    
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    // Database Setup
    public static void createTables() throws SQLException {
        try (Connection connection = connect()) {
            String createUserTable = "CREATE TABLE IF NOT EXISTS user_profiles ("
                    + "userid INT AUTO_INCREMENT PRIMARY KEY,"
                    + "username VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "first_name VARCHAR(255),"
                    + "last_name VARCHAR(255),"
                    + "is_vip BOOLEAN NOT NULL"
                    + ")";
            
            String createPostTable = "CREATE TABLE IF NOT EXISTS social_media_posts ("
                    + "postid INT AUTO_INCREMENT PRIMARY KEY,"
                    + "userid INT NOT NULL,"
                    + "content TEXT,"
                    + "author VARCHAR(255),"
                    + "likes INT,"
                    + "shares INT,"
                    + "date_time TIMESTAMP"
                    + ")";
            
            try (Statement statement = connection.createStatement()) {
                statement.execute(createUserTable);
                statement.execute(createPostTable);
            }
        } catch (SQLException ex) {
        	throw new SQLException("Failed to create tables.", ex);
        }
    }
}