/*
 * Class User
 * @author: Xuehua Lan
 * @version JavaSE-17
*/ 

public class User {
	private int userid; //primary Key
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isvip;

    /**
     * Default Constructor
     * @param userid: unique for user identification
     * @param username: 
     * @param password: 
     * @param firstName: 
     * @param lastName: 
     * @param isvip: 
     */
    public User(int userid, String username, String password, String firstName, String lastName, boolean isvip) {
        this.userid = userid;
    	this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isvip = isvip;
    }
    
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getter of userid
    public int getUserid() {
        return this.userid;
    }
    
    // Setter of userid
    public void setUserid(int userid) {
        this.userid = userid;
      }

    // Getter of username
    public String getUsername() {
        return this.username;
    }
    
    // Setter of username
    public void setUsername(String username) {
        this.username = username;
      }

    // Getter of password
    public String getPassword() {
        return this.password;
    }
    
    // Setter of password
    public void setPassword(String password) {
        this.password = password;
      }

    // Getter of firstName
    public String getFirstName() {
        return this.firstName;
    }
    
    // Setter of firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
      }

    // Getter of lastName
    public String getLastName() {
        return this.lastName;
    }
    
    // Setter of lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
      }

    // Getter of isvip
    public Boolean getIsvip() {
        return this.isvip;
    }
    
    // Setter of isvip
    public void setIsvip(Boolean isvip) {
        this.isvip = isvip;
      }

}