/*
 * Class Controller
 * Handles user interactions and updates the view based on the model's data
 * 
 * @author: Xuehua Lan
 * @version JavaSE-17
*/ 

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Controller {
    private Model model;
    private UserView view;
    private User user;
    
    /**
     * Constructor
     * @param model
     * @param view
     */
    public Controller(Model model, UserView view) {
        this.model = model;
        this.view = view;
    }
    
    /**
     * Constructor
     * @param model
     * @param view
     * @param user
     */
    public Controller(Model model, UserView view, User user) {
        this.model = model;
        this.view = view;
        this.user = user;
    }
    
    /**
     * 
     * Block space for Handling user lgoin
     * 1. handleLogin
     * 2. upgradeVIP
     * 
     */	

    /**
     * Handling Login Scene Login request event
     * @param e Switch to Dashboard Scene with login user
     * @see https://www.tabnine.com/code/java/classes/javafx.scene.control.ButtonType
     */
    public void handleLogin(Event e) {
        // Implement login, get username and password from login
        String username = view.getUsername();
        String password = view.getPassword();
        
        // Create user instance
        user = model.getUserByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
        	// Check VIP status
        	if (!user.getIsvip()) {
        		// User is not a VIP, provide option to subscribe
                Alert vipupgrade = new Alert(Alert.AlertType.CONFIRMATION);
                vipupgrade.setTitle("VIP Upgrade");
                vipupgrade.setHeaderText("Upgrade to VIP");
                vipupgrade.setContentText("Would you like to subscribe to the application for a monthly fee of $0?");
        		
                Optional<ButtonType> result = vipupgrade.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // User agrees to subscribe, update VIP status in the database
                	if (upgradeVIP(username)) {
                        // upgrade successful
                    	Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Upgrade Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Please log out and log in again to access VIP functionalities");
                        successAlert.showAndWait();
                        view.showLoginScene();
                	}
                	
                    // log out and log in again to access VIP functionalities
                    view.showLoginScene();
                } else {
                	view.showDashboardScene(user);
                }
        	} else {
        		view.showDashboardScene(user);
        	}
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Registration Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Username or Password incorrect");
            errorAlert.showAndWait();
        }
    }
    
    /**
     * upgrade user to vip provided with their username
     * @param username
     * @return true for successfully upgrade to VIP
     */
    private boolean upgradeVIP(String username) {
        try {
            boolean ifSuccessful = model.updateVIP(username);
            if (ifSuccessful) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * 
     * Block space for Handling user register
     * 1. handleRegister
     * 2. handleCancelRegist
     * 3. handleConfirmRegist
     * 4. isValidRegistrationInput
     * 5. registerUser
     * 
     */	
    
    /**
     * Handling Login Scene Register request event
     * @param e Switch to Register Scene
     */
    public void handleRegister(Event e) {
        view.showRegisterScene();
    }
    
    /**
     * Handling Register Scene Register cancel event
     * @param e Decide not register, switch back to Login Scene
     */
    public void handleCancelRegist(Event e) {
        view.showLoginScene();
    }
    
    /**
     * Handling Register Scene Register confirm event
     * @param e register a new user and return back to login page 
     */
    public void handleConfirmRegist(Event e) {
    	String username = view.getUsernameSetup();
        String password = view.getPasswordSetup();
        String firstname = view.getFirstnameSetup();
        String lastname = view.getLastnameSetup();
        
        if (isValidRegistrationInput(username, password, firstname, lastname)) {
            // check registration and input into database
            if (registerUser(username, password, firstname, lastname)) {
                // Registration successful
            	Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Registration Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Registration successful!");
                successAlert.showAndWait();
                view.showLoginScene();
            } else {
                // Registration failed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Registration Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Username already be taken.");
                errorAlert.showAndWait();
            }
        } else {
            // Invalid input, show an error message
             Alert errorAlert = new Alert(Alert.AlertType.ERROR);
             errorAlert.setTitle("Registration Error");
             errorAlert.setHeaderText(null);
             errorAlert.setContentText("Please fill in all fields.");
             errorAlert.showAndWait();
        }
    }
    
    /** 
     * Method to check if all filed are enter, logic check
     * @param username for new user setup
     * @param password for new user setup
     * @param firstname for new user setup
     * @param lastname for new user setup
     * @return true for all filed entered, false for not all filed are completed
     */
    private boolean isValidRegistrationInput(String username, String password, String firstName, String lastName) {
        return !username.isEmpty() && !password.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty();
    }
    
    /** 
     * Method to check if username are in used
     * @param username for new user setup
     * @param password for new user setup
     * @param firstname for new user setup
     * @param lastname for new user setup
     * @return true for successful register and recorded into user profile table
     */
    private boolean registerUser(String username, String password, String firstname, String lastname) {
        try {
            boolean ifSuccessful = model.registerUser(username, password, firstname, lastname);
            if (ifSuccessful) {
            	// Create a new user
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * 
     * Block space for Handling Edit Profile
     * 1. handleEditProfile
     * 2. handleCancelEdit
     * 3. handleCancelEdit
     * 4. isValidEditInput
     * 5. isUsernamTaken
     * 
     */	
    
    /**
     * Handling Dashboard Scene Edit Profile event
     * @param e wish to update profile, switch to Edit Profile Scene
     */
    public void handleEditProfile(Event e) {
    	view.showEditProfileScene(user);
    }
    
    /**
     * Handling Edit Profile Scene cancel Edit event
     * @param e decide not edit profile and switch back to Dashboard
     */
    public void handleCancelEdit(Event e) {
        view.showDashboardScene(user);
    }
    
    /**
     * Handling Edit Profile Scene confirm edit event
     * @param e confirm all the input for profile edit, and update in the user profile table
     */
    public void handleConfirmEdit(Event e) {
    	String currentUsername = user.getUsername();
    	String newusername = view.getUsernameEdit();
        String password = view.getPasswordEdit();
        String firstname = view.getFirstnameEdit();
        String lastname = view.getLastnameEdit();
        
        if (isValidEditInput(newusername, password, firstname, lastname)) {
            // check the filed to be amend and the filed not to be amended
        	
        	// Check if the new username exists
        	if (isUsernamTaken(newusername)) {
				// Update failed, username duplicate
			    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
			    errorAlert.setTitle("Registration Error");
			    errorAlert.setHeaderText(null);
			    errorAlert.setContentText("Username already be taken.");
			    errorAlert.showAndWait();
			} else {
				if(newusername.isEmpty()) {
	        		newusername = user.getUsername();
	        	}
				
	        	if (password.isEmpty()) {
	        		password = user.getPassword();
	        	}
	        	
	        	if (firstname.isEmpty()) {
	        		firstname = user.getFirstName();
	        	}
	        	
	        	if (lastname.isEmpty()) {
	        		lastname = user.getLastName();
	        	}
	        	
	            if (eidtUser(currentUsername, newusername, password, firstname, lastname)) {
	                // Edit successful
	            	Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	                successAlert.setTitle("Registration Success");
	                successAlert.setHeaderText(null);
	                successAlert.setContentText("Edit Profile successful!");
	                successAlert.showAndWait();
	                
	                // Edit successful, update to the latest and back to Dashboard
	                user = model.getUserByUsername(newusername);
	                view.showDashboardScene(user);
	            }
			}
        } else {
        	System.out.print("Please fill in to edit your profile.");
        }
    }
    
    /** 
     * Method to check if one of the filed are enter
     * @param username for new user setup
     * @param password for new user setup
     * @param firstname for new user setup
     * @param lastname for new user setup
     * @return true for all filed entered, false for not all filed are completed
     */
    private boolean isValidEditInput(String username, String password, String firstName, String lastName) {
        return !username.isEmpty() || !password.isEmpty() || !firstName.isEmpty() || !lastName.isEmpty();
    }
    
    /**
     * Check if username is in used
     * @param username
     * @return true if username is in used
     */
    private boolean isUsernamTaken(String username) {
        try {
            boolean ifSuccessful = model.isUsernameTaken(username);
            if (ifSuccessful) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    /** 
     * Method to check if username are duplicate
     * @param username for new user setup
     * @param password for new user setup
     * @param firstname for new user setup
     * @param lastname for new user setup
     * @return true for successful register, false for register failed
     */
    private boolean eidtUser(String currentusername, String username, String password, String firstname, String lastname) {
        boolean ifSuccessful = model.editUser(currentusername, username, password, firstname, lastname);
		if (ifSuccessful) {
			// Create a new user in the database using the Model class
		    return true;
		} else {
		    return false;
		}
    }
    
    
    /**
     * 
     * Block space for Handling Add Post
     * 1. handleAddPost
     * 2. handleCancelAdd
     * 3. handleConfirmAdd
     * 4. isValidPostAdd
     * 5. addPost
     * 
     */	
    
    /**
     * Handling Dashboard Scene Add Post event
     * @param e Switch to add post Scene to add a new post
     */
    public void handleAddPost(Event e) {
    	view.showAddPostScene();
    }
    
    /**
     * Handling Add Post Scene confirm Add Post event
     * @param e decide not to add new post, and return back to Dashboard Scene
     */
    public void handleCancelAdd(Event e) {
    	view.showDashboardScene(user);
    }
    
    
    /**
     * Handling Add Post Scene confirm Add Post event
     * @param e enter new post including content, authors, #likes, #shares, datetime and insert into social media post table
     */
    public void handleConfirmAdd(Event e) {
        
        try {
            // Try to parse the likesText to an integer
        	int userid = user.getUserid();
        	String content = view.getContent();
            String author = view.getAuthor();
            int likes = Integer.parseInt(view.getLikes());
            int shares = Integer.parseInt(view.getShares());
            String datetime = view.getDatetime();
            
            if (isValidPostAdd(content, author, datetime)) {
                // check registration and input into database
            	
                if (addPost(userid, content, author, likes, shares, datetime)) {
                    // Add post successful
                	Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Add Post Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Registration successful!");
                    successAlert.showAndWait();
                    view.showDashboardScene(user);
                } else {
                    // Registration failed
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Add Post Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Connection Error");
                    errorAlert.showAndWait();
                }
            } else {
                // Invalid input, show an error message
                 Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                 errorAlert.setTitle("Add Post  Error");
                 errorAlert.setHeaderText(null);
                 errorAlert.setContentText("Please fill in all fields.");
                 errorAlert.showAndWait();
            }
            
        } catch (NumberFormatException ex) {
        	// Invalid input, show an error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Registration Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Only enter interger value for likes and shares entry");
            errorAlert.showAndWait();
        }
    }
    
    /** 
     * Method to check if all filed are enter
     * @param content for the post
     * @param author for the post
     * @param datetime for the post
     * @return true for all filed entered, false for not all filed are completed
     */
    private boolean isValidPostAdd(String content, String author, String datetime) {
        // check for empty fields, likes and shares can be 0
        return !content.isEmpty() && !author.isEmpty() && !datetime.isEmpty();
    }
    
    /** 
     * Method to check if all filed are enter
     * @param userid identification
     * @param content for the post
     * @param author for the post
     * @param likes for the post
     * @param shares for the post
     * @param datetime for the post
     * @return userid, content, author, likes, shares, datetime
     */
    private boolean addPost(int userid, String content, String author, int likes, int shares, String datetime) {
        // Return true if registration is successful and insert new post into database
        try {
            boolean ifSuccessful = model.addPost(userid, content, author, likes, shares, datetime);
            if (ifSuccessful) {
            	// Create a new post in the database using the Model class
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    
    /**
     * 
     * Block space for Handling Retrieve Post
     * 1. handleRetrievePost
     * 2. handleCancelRetrieve
     * 3. handleConfirmRetrieve
     * 4. isPostidExist
     * 
     */	
    
    /**
     * Handling Dashboard Scene Retrieve Post event
     * @param e decide to retrieve a post, switch to Retrieve Post Scene
     */
    public void handleRetrievePost(Event e) {
    	view.showRetrievePostScene();
    }
    
    /**
     * Handling Retrieve Post Scene to cancel retrieve
     * @param e decide not to retrieve post
     */
    public void handleCancelRetrieve(Event e) {
    	view.showDashboardScene(user);
    }
    
    /**
     * Handle retrieve post Scene 
     * @param e retrieve the post by postid and display
     */
    public void handleConfirmRetrieve(Event e){
    	try {
            // Try to parse the likesText to an integer
    		String retrievePostid = view.getRetrievePostid();
            int postid = Integer.parseInt(retrievePostid);
            
            if (isPostidExist(postid)) {
            	
            	Post post = model.getPostByPostid(postid);
            	
            	String retrieveTest = new String();
            	retrieveTest = ("Postid " + postid+ "\n"
            			+ "Content " + post.getContent()+ "\n"
            			+ "Author " + post.getAuthor()+ "\n"
            			+ "Number of Likes " + post.getLikes() + "\n"
            			+ "Number of Shares " + post.getShares()+ "\n"
            			+ "Datetime " + post.getDateTime());
            	
            	Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Retrieve Post Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText(retrieveTest);
                successAlert.showAndWait();
                //Return to Dashboard
                view.showDashboardScene(user);
            } else {
                // Registration failed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Add Post Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("This postid is not exist");
                errorAlert.showAndWait();
            }
            
		} catch (NumberFormatException ex) {
        	// Invalid input, show an error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please only enter interger value");
            errorAlert.showAndWait();
		}
    }
    
    /**
     * check if postid exist in the social media post table
     * @param postid
     * @return
     */
    private boolean isPostidExist(int postid) {
        // Return true if registration is successful and insert new post into database
        try {
            boolean ifSuccessful = model.isPostidExist(postid);
            if (ifSuccessful) {
            	// Create a new post in the database using the Model class
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    
    /**
     * 
     * Block space for Handling Remove Post
     * 1. handleRemovePost
     * 2. handleCancelRemove
     * 3. handleConfirmRemove
     * 4. removePost
     * 
     */	
    
    /**
     * Handling Dashboard Scene Remove Post event
     * @param e decide to remove a post, Switch to Remove Post Scene
     */
    public void handleRemovePost(Event e) {
    	view.showRemovePostScene();
    }
    
    /**
     * Handling Remove Post Scene to cancel remove
     * @param e decide not to remove post
     */
    public void handleCancelRemove(Event e) {
    	view.showDashboardScene(user);
    }
    
    
    /**
     * Handling Remove post Scene
     * @param e input postid to be removed, return to Dashboard after
     */
    public void handleConfirmRemove(Event e){
    	try {
			String removePostid = view.getRemovePostid();
            int postid = Integer.parseInt(removePostid);
            
            if (isPostidExist(postid)) {
            	
            	if (removePost(postid)) {
                    // Registration successful
                	Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Registration Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Registration successful!");
                    successAlert.showAndWait();
                    view.showLoginScene();
                } else {
                    // Registration failed
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Registration Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Username already be taken.");
                    errorAlert.showAndWait();
                }
                    
            } else {
                // Registration failed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Add Post Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("This postid is not exist");
                errorAlert.showAndWait();
            }
            
		} catch (NumberFormatException ex) {
        	// Invalid input, show an error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please only enter interger value");
            errorAlert.showAndWait();
		}
    	// Return to Dashboard
    	view.showDashboardScene(user);
    }
    
    /** 
     * Method to check if username are duplicate
     * @param postid for new user setup
     * @param password for new user setup
     * @param firstname for new user setup
     * @param lastname for new user setup
     * @return true for successful register, false for register failed
     */
    private boolean removePost(int postid) {
        boolean ifSuccessful = model.deletePostByPostid(postid);
		if (ifSuccessful) {
			// Create a new user in the database using the Model class
		    return true;
		} else {
		    return false;
		}
    }

    
    /**
     * 
     * Block space for Handling Retrieve Top N Posts
     * 1. handleRetrieveTopPosts
     * 2. handleCancelTopPosts
     * 3. handleDisplayTopPosts
     * 4. handleRetrieveTopPostsAll
     * 5. handleRetrieveTopPostsUser
     * 6. handleCloseTopPosts
     * 
     */
    
    /**
     * Handling the Retrieve Top Posts Scene
     * @param e decide to retrieve top post, switch to retrieve top post scenes
     */
    public void handleRetrieveTopPosts(Event e) {
    	view.showRetrieveTopPostsScene();
    }
    
    /**
     * Handling the Retrieve Top Posts Scene
     * @param e decide not to retrieve top posts
     */
    public void handleCancelTopPosts(Event e) {
    	view.showDashboardScene(user);
    }
    
    /**
     * Handling the Display Top Posts Scene when completed
     * @param e close and return to Dashboard Scene
     */
    public void handleCloseTopPosts(Event e) {
    	view.showDashboardScene(user);
    }
    
    /**
     * Handling the entry of top N posts
     * @param e enter number of posts, and switch to the display scene
     */
    public void handleDisplayTopPosts(Event e) {
    	
    	HashMap<Integer, Post> topPosts = new HashMap<>();
    	
    	try {
    		String retrieveTopNPost = view.getTopNPost();
        	String retrieveUser = view.getRetrieveUser();
            int topNPost = Integer.parseInt(retrieveTopNPost);
            
            if (retrieveUser.isEmpty()) {
            	topPosts = handleRetrieveTopPostsUser(topNPost, user.getUserid());
            	view.displayTopPostsScene(topPosts);
            } else {
            	topPosts = handleRetrieveTopPostsAll(topNPost);
            	view.displayTopPostsScene(topPosts);
            }
            
		} catch (NumberFormatException ex) {
        	// Invalid input, show an error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please only enter interger value for top N post");
            errorAlert.showAndWait();
		}
    	
    	view.showDashboardScene(user);
    }
    
    /**
     * 
     * @param topNPost
     * @return
     */
    public HashMap<Integer, Post> handleRetrieveTopPostsAll(int topNPost) {
    	HashMap<Integer, Post> topPosts = model.retrieveTopPostsAll(topNPost);
        return topPosts;
    }
    
    /**
     * 
     * @param topNPost
     * @param userid
     * @return
     */
    public HashMap<Integer, Post> handleRetrieveTopPostsUser(int topNPost, int userid) {
    	HashMap<Integer, Post> topPosts = model.retrieveTopPostsUser(topNPost, userid);
        return topPosts;
    }
    
    
    /**
     * 
     * Block space for Handling Export Post
     * 1. handleExportPost
     * 2. handleCancelExport
     * 3. handleConfirmExport
     * 
     */
    
    /**
     * 
     * @param e
     */
    public void handleExportPost(Event e) {
    	view.showExportPostScene();
    }
    
    /**
     * 
     * @param e
     */
    public void handleCancelExport(Event e) {
    	view.showDashboardScene(user);
    }
    
    /**
     * 
     * @param e
     */
    public void handleConfirmExport(Event e) {
    	try {
            // Try to parse the likesText to an integer
    		String exportPostid = view.getExportPostid();
    		String exportName = view.getExportName();
    		String exportFolder = view.getExportFolder();
            int postid = Integer.parseInt(exportPostid);
            
            if (isPostidExist(postid)) {
            	
            	Post post = model.getPostByPostid(postid);
            	
            	File folder = new File(exportFolder);
                if (!folder.exists() || !folder.isDirectory()) {
                	Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Folder Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Folder does not exist or is invalid");
                    errorAlert.showAndWait();
                }
                
                File file = new File(folder, exportName + ".csv");
                if (file.exists()) {
                	Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("File Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("File already exist");
                    errorAlert.showAndWait();
                } else {
                    try (FileWriter writer = new FileWriter(file)) {
                    	
                        writer.write("Post ID,Author,Content,Likes,Shares,Date_Time\n");
                        writer.write(postid + "," + post.getAuthor() + "," + post.getContent() + ","
                                + post.getLikes() + "," + post.getShares() + "," + post.getDateTime() + "\n");
                        writer.close();
                        // Inform the user that the export was successful
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Export Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("The post had been exported");
                        successAlert.showAndWait();
                        view.showDashboardScene(user);
                    } catch (IOException e1) {
                    	Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Export Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Export Error");
                        errorAlert.showAndWait();
                    }
                }
            	
            	String retrieveTest = new String();
            	
            	Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Retrieve Post Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText(retrieveTest);
                successAlert.showAndWait();
                //Return to Dashboard
                view.showDashboardScene(user);
            } else {
                // Registration failed
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Add Post Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("This postid is not exist");
                errorAlert.showAndWait();
            }
            
		} catch (NumberFormatException ex) {
        	// Invalid input, show an error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Please only enter interger value");
            errorAlert.showAndWait();
		}
    	
    }
    
    /**
     * 
     * Block space for Shares Distribution
     * 1. handleSharesDistribution
     * 2. handleCancelImport
     * 3. handleConfirmImport
     * 
     */
    /**
     * 
     * @param e
     */
    public void handleSharesDistribution(Event e) {
    	if (!user.getIsvip()) {
    		// User is not a VIP, provide option to subscribe
    		Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Not VIP User");
            successAlert.setHeaderText(null);
            successAlert.setContentText("This is a VIP function");
            successAlert.showAndWait();
            view.showDashboardScene(user);
    	} else {
    		// code 
    	}
    }
    

    
    /**
     * 
     * Block space for Handling Bulk Import
     * 1. handleBulkImportPost
     * 2. handleCancelImport
     * 3. handleConfirmImport
     * 
     */
    
    /**
     * 
     * @param e
     */
    public void handleBulkImportPost(Event e) {
    	if (!user.getIsvip()) {
    		// User is not a VIP, provide option to subscribe
    		Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Not VIP User");
            successAlert.setHeaderText(null);
            successAlert.setContentText("This is a VIP function");
            successAlert.showAndWait();
            view.showDashboardScene(user);
    	} else {
            String importName = view.getImportName();
            String importFolder = view.getImportFolder();
            //bulkImportPosts(importFolder, importName);
    	}
    }
    
    /**
    public void bulkImportPosts(String importFolder, String importName) throws SQLException {
        File folder = new File(importFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Folder Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Folder does not exist or is invalid");
            errorAlert.showAndWait();
        }
        
        File file = new File(folder, importName + ".csv");
        if (!file.exists()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("File Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("File does not exist");
            errorAlert.showAndWait();
        }
        
        try {
            Scanner myReader = new Scanner(file);
            boolean isHeaderLine = true; // header
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                
                // skip the header line
                if (isHeaderLine) {
                    isHeaderLine = false;
                    continue;
                }
                
                // Split the line by comma
                String[] data = line.split(",");
                
                if (data.length != 6) {
                    System.out.println("Error parsing the line, skipping line: " + line);
                    continue;
                }
                
                try {
                    int postid = Integer.parseInt(data[0]);
                    String content = data[1];
                    String author = data[2];
                    int likes = Integer.parseInt(data[3]);
                    int shares = Integer.parseInt(data[4]);
                    String dateTime = data[5];
                    
                    if(bulkImport(postid, user.getUserid(), content, author, likes, shares, dateTime)) {
                        System.out.println("Successfully import line: " + line);
                    }
                    
                    
                } catch (NumberFormatException e) {
                    // file exception for integer parameters
                    System.out.println("Error parsing integer value, skipping line: " + line);
                }
            }
        
            myReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    private boolean bulkImport(int postid, int userid, String content, String author, int likes, int shares, String dateTime) throws SQLException {
        boolean ifSuccessful = model.bulkImport(postid, userid, content, author, likes, shares, dateTime);
        if (ifSuccessful) {
            // Create a new user in the database using the Model class
            return true;
        } else {
            return false;
        }
    }
    */
    
    /**
     * 
     * @param e
     */
    public void handleCancelImport(Event e) {
    	view.showDashboardScene(user);
    }
    
    /**
     * 
     * @param e
     */
    public void handleConfirmImport(Event e) {
    	//String importName = view.getImportName();
		//String importFolder = view.getImportFolder();
		view.showDashboardScene(user);
		//bulkImportPosts(importFolder, importName);
    }
    
    /**
     * 
     * Block space for Handling Logout
     * 1. handleLogout
     * 
     */
    
    /**
     * Handling Dashboard Scene Logout event
     * @param e decide to logout and return to login Scene
     */
    public void handleLogout(Event e) {
        view.showLoginScene();
    }

}