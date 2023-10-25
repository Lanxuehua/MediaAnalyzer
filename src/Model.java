/*
 * Class Model
 * Handles database interactions
 * Encapsulates methods for retrieving users, editing profiles, creating and deleting posts, and retrieving post data
 * 
 * @author: Xuehua Lan
 * @version JavaSE-17
*/ 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Model {
    private Connection connection;
    
    /**
     * Constructor
     * @param connection
     */
    public Model(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * check if username exist
     * @param username username that would like to be applied
     * @return true if username already been used
     * @throws SQLException
     */
    public boolean isUsernameTaken(String username) throws SQLException {
        String query = "SELECT username FROM user_profiles WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
    
    /**
     * Getter - get user information based on username
     * @param username
     * @return user (userid, username, password, firstname, lastname, isvip)
     */
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user_profiles WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve user data from the result set
                    int userid = resultSet.getInt("userid");
                    String password = resultSet.getString("password");
                    String firstname = resultSet.getString("first_name");
                    String lastname = resultSet.getString("last_name");
                    boolean isvip = resultSet.getBoolean("is_vip");
                    //create a User object
                    return new User(userid, username, password, firstname, lastname, isvip);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
        	 // Database errors
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * New User registration updated into user profile table
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @return true for 1 new user is added to the User Profile
     * @throws SQLException
     */
    public boolean registerUser(String username, String password, String firstName, String lastName) throws SQLException {
        try {
            // Check if the username already been used
            if (isUsernameTaken(username)) {
                return false;
            } else {
            	
            	String insertQuery = "INSERT INTO user_profiles (username, password, first_name, last_name, is_vip) VALUES (?, ?, ?, ?, ?)";
                
                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, firstName);
                    statement.setString(4, lastName);
                    statement.setBoolean(5, false); // Initially, the user is not VIP

                    int rowsAffected = statement.executeUpdate();
                    return rowsAffected > 0;
                }
            }
            
        } catch (SQLException ex) {
       	 	// Database errors
            ex.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * Update user vip to true status
     * @param userid user that would like to update to vip
     * @return true if user vip had been updated
     * @throws SQLException
     */
    public boolean updateVIP(String username) throws SQLException {
        String query = "UPDATE user_profiles SET is_vip = ? WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, true);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }
    
    
    /**
     * Update user profile for username, password, firstname, and lastname
     * @param currentusername current username in used
     * @param username new username the user would like to update
     * @param password new password the user would like to update
     * @param firstname first name the user would like to update
     * @param lastname last name the user would like to update
     * @return true if 1 line of user profile had been updated
     */
    public boolean editUser(String currentusername, String username, String password, String firstname, String lastname) {
        try {
            // Check if the current username exists
            if (!isUsernameTaken(currentusername)) {
                // Username doesn't exist then no editable user
                return false;
            } else {
            	// Update the user's profile in database
                String updateQuery = "UPDATE user_profiles SET username = ?, password = ?, first_name = ?, last_name = ? WHERE username = ?";
                PreparedStatement statement = connection.prepareStatement(updateQuery);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, firstname);
                statement.setString(4, lastname);
                statement.setString(5, currentusername);

                int rowsAffected = statement.executeUpdate();
                statement.close();
                return rowsAffected > 0;
            }
            
        } catch (SQLException ex) {
        	// Database errors
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Check if post exist with postid
     * @param postid provided postid to check for existing post
     * @return return true if postid exist
     * @throws SQLException
     */
    boolean isPostidExist(int postid) throws SQLException {
        String query = "SELECT postid FROM social_media_posts WHERE postid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, postid);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
    
    /**
     * adding new post to the social media posts table
     * @param userid record the who enter the post
     * @param content the content of the post
     * @param author provided author name of the post, can be different to the user's full name
     * @param likes number of likes for this post
     * @param shares number of shares for this post
     * @param datetime the date of this post
     * @return return true if the new post had been added to the social media table
     * @throws SQLException
     */
    public boolean addPost(int userid, String content, String author, int likes, int shares, String datetime) throws SQLException {
        try {
            String insertQuery = "INSERT INTO social_media_posts (username, content, author, likes, shares, datetime) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            	statement.setInt(1, userid);
                statement.setString(2, content);
                statement.setString(3, author);
                statement.setInt(4, likes);
                statement.setInt(5, shares);
                statement.setString(6, datetime);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
                }
            
        } catch (SQLException ex) {
       	 	// Database errors
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieve post by postid and create a Post post to store the information
     * @param postid provide postid to locate existing post
     * @return return post (postid, userid, content, author, likes, shares, datetime)
     * @throws SQLException
     */
    public Post getPostByPostid(int postid){
        String query = "SELECT * FROM social_media_posts WHERE postid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, postid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve user data from the result set and create a User object
                    int userid = resultSet.getInt("userid");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date_time = resultSet.getString("date_time");
                    // create new Post to store the post information
                    return new Post(postid, userid, content, author, likes, shares, date_time);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
       	 	// Database errors
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * remove the post by postid
     * @param postid provided postid for post to be removed
     * @return true for successfully delete the post with provided postid
     */
    public boolean deletePostByPostid(int postid) {
        String query = "DELETE FROM social_media_posts WHERE postid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, postid);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
       	 	// Database errors
            e.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * Retrieve top N likes posts and return
     * @param topN number of top N posts would like to retrieve
     * @return Top N posts with most likes
     */
    public HashMap<Integer, Post> retrieveTopPostsAll(int topN) {
        HashMap<Integer, Post> topPosts = new HashMap<>();
        String query = "SELECT * FROM social_media_posts ORDER BY likes DESC LIMIT ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, topN);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int postid = resultSet.getInt("postid");
                    int userId = resultSet.getInt("userid");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date_time = resultSet.getString("date_time");
                    // create new Post for each retrieve post
                    Post post = new Post(postid, userId, content, author, likes, shares, date_time);
                    topPosts.put(postid, post);
                }
            }
        } catch (SQLException e) {
       	 	// Database errors
            e.printStackTrace();
        }
        return topPosts;
    }
    
    /**
     * Retrieve top N likes posts by userid and return
     * @param topN number of top N posts would like to retrieve
     * @param userid top N posts of userid's posts
     * @return Top N posts with most likes by defined userid
     */
    public HashMap<Integer, Post> retrieveTopPostsUser(int topN, int userid) {
        HashMap<Integer, Post> topPosts = new HashMap<>();
        String query = "SELECT * FROM social_media_posts WHERE userid = ? ORDER BY likes DESC LIMIT ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userid);
            statement.setInt(2, topN);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int postid = resultSet.getInt("postid");
                    int userId = resultSet.getInt("userid");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String date_time = resultSet.getString("date_time");
                    // create new Post for each retrieve post
                    Post post = new Post(postid, userId, content, author, likes, shares, date_time);
                    topPosts.put(postid, post);
                }
            }
        } catch (SQLException e) {
       	 	// Database errors
            e.printStackTrace();
        }
        return topPosts;
    }
    
    public boolean bulkImport(int postid, int userid, String content, String author, int likes, int shares, String dateTime) throws SQLException {
        try {
        	String insertQuery = "INSERT INTO social_media_posts (postid, userid, content, author, likes, shares, dateTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        	
        	try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, postid);
                statement.setInt(2, userid);
                statement.setString(3, content);
                statement.setString(4, author);
                statement.setInt(5, likes);
                statement.setInt(6, shares);
                statement.setString(7, dateTime);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
        	}
    	} catch (SQLException ex) {
       	 	// Database errors
            ex.printStackTrace();
            return false;
        }
    }
    
}