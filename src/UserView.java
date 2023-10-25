/*
 * Class UserView
 * Handle User interface, including scenes for the login, dashboard, and edit profile views.
 * 
 * @author: Xuehua Lan
 * @version JavaSE-17
*/ 

import java.sql.SQLException;
import java.util.HashMap;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserView {
	
	private Controller controller;
	
    private Stage primaryStage;
    private Scene loginScene;
    private Scene registerScene;
    private Scene dashboardScene;
    private Scene editProfileScene;
    private Scene addPostScene;
    private Scene retrievePostScene;
    private Scene removePostScene;
    private Scene retrieveTopPostsScene;
    private Scene displayTopPostsScene; 
    private Scene exportPostScene;
    private Scene sharesDistributionScene;
    private Scene bulkImportPostsScene;

    private TextField usernameInput;
    private PasswordField passwordInput;
    private Button loginButton;
    private Button registerButton;
    
    private TextField usernameSetup;
    private PasswordField passwordSetup;
    private TextField firstnameSetup;
    private TextField lastnameSetup;
    private Button confirmRegistButton;
    private Button cancelRegistButton;
    
    private Label dashboardLabel;
    private Button editProfileButton;
    private Button addPostButton;
    private Button retrievePostButton;
    private Button removePostButton;
    private Button retrieveTopPostsButton;
    private Button exportPostButton;
    private Button sharesDistributionButton;
    private Button bulkImportPostsButton;
    private Button logoutButton;

    private TextField usernameEdit;
    private PasswordField passwordEdit;
    private TextField firstnameEdit;
    private TextField lastnameEdit;
    private Button confirmEditButton;
    private Button cancelEditButton;
    
    private TextField content;
    private TextField author;
    private TextField likes;
    private TextField shares;
    private TextField datetime;
    private Button confirmAddButton;
    private Button cancelAddButton;
    
    private TextField retrievePostid;
    private Button confirmRetrievePostButton;
    private Button cancelRetrievePostButton;
    
    private TextField removePostid;
    private Button confirmRemovePostButton;
    private Button cancelRemovePostButton;
    
    private TextField retrieveTopNPost;
    private TextField retrieveUser;
    private Button completeRetrieveTopButton;
    private Button closeRetrieveTopButton;
    
    private TextField exportPostid;
    private TextField exportName;
    private TextField exportFolder;
    private Button confirmExportPostButton;
    private Button cancelExportPostButton;
    
    private TextField importName;
    private TextField importFolder;
    private Button confirmImportPostButton;
    private Button cancelImportPostButton;
    
    public UserView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Initialize the login, register, and dashboard scene
        initializeLoginScene();
        initializeViewRegister();
        initializeViewDashboard();
        initializeViewEditProfile();
        
        // Set the initial scene to the login scene
        primaryStage.setScene(loginScene);
    }
    
    /** 
     * Set Controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    /** 
     * Login Scene - Main Page
     * Set content and style of the Login Scene
     */
    public void initializeLoginScene() {
        
        usernameInput = new TextField();
        passwordInput = new PasswordField();
        loginButton = new Button("Login");
        registerButton = new Button("Register");
        
        // Create the layout for the login scene
        VBox loginLayout = new VBox(10);
        loginLayout.setSpacing(10);
        loginLayout.setPadding(new Insets(20));
        Label loginLabel = new Label("User Login");
        loginLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        loginLayout.getChildren().addAll(loginLabel, new Label("Username:"), usernameInput, new Label("Password:"), passwordInput, loginButton, registerButton);
        loginScene = new Scene(loginLayout, 800, 600);
        
        // Set event handlers for buttons
        loginButton.setOnAction(e -> {
            controller.handleLogin(e);
        });
        
        registerButton.setOnAction(e -> {
            controller.handleRegister(e);
        });
    }

    // Set and show Scene for Login Page
    public void showLoginScene() {
        primaryStage.setScene(loginScene);
    }
    
    /** 
     * Register Scene
     * Set content and style of the Register Scene
     */
    public void initializeViewRegister() {
    	
        usernameSetup = new TextField();
        passwordSetup = new PasswordField();
        firstnameSetup = new TextField();
        lastnameSetup = new TextField();
        confirmRegistButton = new Button("Confirm");
        cancelRegistButton = new Button("cancel");
        
        // Create the layout for the register scene
        VBox registerLayout = new VBox(10);
        registerLayout.setSpacing(10);
        registerLayout.setPadding(new Insets(20));
        Label registLabel = new Label("User Registration");
        registLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        registerLayout.getChildren().addAll(registLabel, new Label("Username:"), usernameSetup, new Label("Password:"), passwordSetup
                , new Label("First Name:"), firstnameSetup, new Label("Last Name:"), lastnameSetup
                , confirmRegistButton, cancelRegistButton);
        registerScene = new Scene(registerLayout, 600, 400); // set the scene
        
        // Set event handlers for buttons
        confirmRegistButton.setOnAction(e -> {
            controller.handleConfirmRegist(e);
        });
        
        cancelRegistButton.setOnAction(e -> {
            controller.handleCancelRegist(e);
        });
    }
    
    // Set and show Scene for Register page
    public void showRegisterScene() {
    	primaryStage.setScene(registerScene);
    }
    
    /** 
     * Dashboard Scene
     * Set content and style of the Dashboard Scene
     */
    public void initializeViewDashboard() {
        
        editProfileButton = new Button("Edit Profile");
        addPostButton = new Button("Add Post");
        retrievePostButton = new Button("Retrieve Post");
        removePostButton = new Button("Remove Post");
        retrieveTopPostsButton = new Button("Retrieve Top Posts");
        exportPostButton = new Button("Export Post");
        sharesDistributionButton = new Button("Shares Distribution");
        bulkImportPostsButton = new Button ("Bulk Import Posts");
        logoutButton = new Button("Logout");
        
        // Create the layout for the dashboard
        VBox dashboardLayout = new VBox(10);
        dashboardLayout.setSpacing(10);
        dashboardLayout.setPadding(new Insets(20));
        dashboardLabel = new Label();
        dashboardLabel.setId("welcomeLabel");
        dashboardLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        dashboardLayout.getChildren().addAll(dashboardLabel
        		, editProfileButton
        		, addPostButton
        		, retrievePostButton
        		, removePostButton
        		, retrieveTopPostsButton
        		, exportPostButton
        		, sharesDistributionButton
        		, bulkImportPostsButton
        		, logoutButton);
        dashboardScene = new Scene(dashboardLayout, 600, 400);

        // Set event handlers for the buttons (implement your logic)
        editProfileButton.setOnAction(e -> {
        	controller.handleEditProfile(e);
        });

        addPostButton.setOnAction(e -> {
        	controller.handleAddPost(e);
        });

        retrievePostButton.setOnAction(e -> {
        	controller.handleRetrievePost(e);
        });

        removePostButton.setOnAction(e -> {
        	controller.handleRemovePost(e);
        });

        retrieveTopPostsButton.setOnAction(e -> {
        	controller.handleRetrieveTopPosts(e);
        });

        exportPostButton.setOnAction(e -> {
        	controller.handleExportPost(e);
        });
        
        sharesDistributionButton.setOnAction(e -> {
        	controller.handleSharesDistribution(e);
        });
        
        bulkImportPostsButton.setOnAction(e -> {
        	controller.handleBulkImportPost(e);
        });

        logoutButton.setOnAction(e -> {
        	controller.handleLogout(e);
        });
    }
    
    /**
     * Set and show Scene for Dashboard page
     * @param username
     */
    public void showDashboardScene(User user) {
    	System.out.print(user.getFirstName());
    	dashboardLabel.setText("Welcome, " + user.getFirstName() + " " + user.getLastName());
    	primaryStage.setScene(dashboardScene);
    }
    
    /** 
     * Edit Profile Scene
     * Set content and style of the Edit Profile Scene
     */
    public void initializeViewEditProfile() {
    	
    	usernameEdit = new TextField();
        passwordEdit = new PasswordField();
        firstnameEdit = new TextField();
        lastnameEdit = new TextField();
        confirmEditButton = new Button("Confirm");
        cancelEditButton = new Button("cancel");
        
        // Create the layout for the register scene
        VBox EditProfileLayout = new VBox(10);
        EditProfileLayout.setSpacing(10);
        EditProfileLayout.setPadding(new Insets(20));
        Label EditProfileLabel = new Label("Please update your profile");
        EditProfileLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        EditProfileLayout.getChildren().addAll(EditProfileLabel
        		, new Label("New Username:"), usernameEdit
        		, new Label("Password:"), passwordEdit
                , new Label("First Name:"), firstnameEdit
                , new Label("Last Name:"), lastnameEdit
                , confirmEditButton, cancelEditButton);
        editProfileScene = new Scene(EditProfileLayout, 600, 400); // set the scene
        
        // Set event handlers for buttons
        confirmEditButton.setOnAction(e -> {
        	controller.handleConfirmEdit(e);
        });
        
        cancelEditButton.setOnAction(e -> {
            controller.handleCancelEdit(e);
        });
    }
    
    /**
     * Set and show Scene for Edit Profile page
     * @param username
     */
    public void showEditProfileScene(User user) {
    	primaryStage.setScene(editProfileScene);
    }
    
    /** 
     * Add Post Scene
     * Set content and style of the add post Scene
     */
    public void initializeViewAddPost() {
    	
    	content = new TextField();
    	author = new TextField();
    	likes = new TextField();
    	shares = new TextField();
    	datetime  = new TextField();
        confirmAddButton = new Button("Confirm");
        cancelAddButton = new Button("cancel");
        
        // Create the layout for the register scene
        VBox AddPostLayout = new VBox(10);
        AddPostLayout.setSpacing(10);
        AddPostLayout.setPadding(new Insets(20));
        Label AddPostLabel = new Label("Add Post");
        AddPostLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        AddPostLayout.getChildren().addAll(AddPostLabel
        		, new Label("Please provide the post content:"), content
        		, new Label("Please provide the post author:"), author
                , new Label("Please provide the number of likes of the post:"), likes
                , new Label("Please provide the number of shares of the post:"), shares
                , new Label("Please provide the date and time of the post in the format of DD/MM/YYYY HH:MM:"), datetime
                , confirmAddButton, cancelAddButton);
        addPostScene = new Scene(AddPostLayout, 600, 400); // set the scene
        
        // Set event handlers for buttons
        confirmAddButton.setOnAction(e -> {
        	controller.handleConfirmAdd(e);
        });
        
        cancelAddButton.setOnAction(e -> {
            controller.handleCancelAdd(e);
        });
    }
    
    public void showAddPostScene() {
    	initializeViewAddPost();
    	primaryStage.setScene(addPostScene);
    }
    
    /** 
     * Retrieve Post Scene
     * Set content and style
     */
    public void initializeViewRetrievePost() {
    	
    	retrievePostid = new TextField();
    	confirmRetrievePostButton = new Button("Confirm");
    	cancelRetrievePostButton = new Button("Cancel");
        
        // Create the layout for the Scene
        VBox layout = new VBox(10);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        Label label = new Label("Retrieve Post");
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        layout.getChildren().addAll(label
        		, new Label("Please provide the postid you want to retrieve:"), retrievePostid
        		, confirmRetrievePostButton, cancelRetrievePostButton);
        retrievePostScene = new Scene(layout, 300, 200); // set the scene
        
        // Set event handlers for buttons
        confirmRetrievePostButton.setOnAction(e -> {
        	controller.handleConfirmRetrieve(e);
        });
        
        cancelRetrievePostButton.setOnAction(e -> {
        	controller.handleCancelRetrieve(e);
        });
        
    }
    
    public void showRetrievePostScene() {
    	initializeViewRetrievePost();
    	primaryStage.setScene(retrievePostScene);
    }
    
    /** 
     * Remove Post Scene
     * Set content and style
     */
    public void initializeViewRemovePost() {
    	
    	removePostid = new TextField();
    	confirmRemovePostButton = new Button("Confirm");
    	cancelRemovePostButton = new Button("Cancel");
        
        // Create the layout for the register scene
        VBox layout = new VBox(10);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        Label label = new Label("Remove Post");
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        layout.getChildren().addAll(label
        		, new Label("Please provide the postid you want to remove:"), removePostid
        		, confirmRemovePostButton, cancelRemovePostButton);
        removePostScene = new Scene(layout, 300, 200); // set the scene
        
        // Set event handlers for buttons
        confirmRemovePostButton.setOnAction(e -> {
        	controller.handleConfirmRemove(e);
        });
        
        cancelRemovePostButton.setOnAction(e -> {
        	controller.handleCancelRemove(e);
        });
        
    }
    
    public void showRemovePostScene() {
    	initializeViewRemovePost();
    	primaryStage.setScene(removePostScene);
    }
    
    /** 
     * Retrieve Top most likes Post Scene
     * Set content and style
     */
    public void initializeViewRetrieveTopPosts() {
    	
    	retrieveTopNPost = new TextField();
    	retrieveUser = new TextField();
    	completeRetrieveTopButton = new Button("Confirm");
    	cancelRetrievePostButton = new Button("Cancel");
        
        // Create the layout for the register scene
        VBox layout = new VBox(10);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        Label label = new Label("Retrieve Top Most Likes Posts");
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        layout.getChildren().addAll(label
        		, new Label("Please enter number of top most like posts:"), retrieveTopNPost
        		, new Label("Who (userid) you want to retrieve, leave blank for all"), retrieveUser
        		, completeRetrieveTopButton, cancelRetrievePostButton);
        retrieveTopPostsScene = new Scene(layout, 600, 400); // set the scene
        
        // Set event handlers for buttons
        completeRetrieveTopButton.setOnAction(e -> {
        	controller.handleDisplayTopPosts(e);
        });

        cancelRetrievePostButton.setOnAction(e -> {
        	controller.handleCancelTopPosts(e);
        });
        
    }
    
    public void showRetrieveTopPostsScene() {
    	initializeViewRetrieveTopPosts();
    	primaryStage.setScene(retrieveTopPostsScene);
    }
    
    /** 
     * Display Top most liks Post Scene
     * Set content and style
     */
    public void displayTopPostsScene(HashMap<Integer, Post> topPosts) {
        
    	// Create the layout for the register scene
        VBox layout = new VBox(10);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        for (Post post : topPosts.values()) {
            VBox topNPost = new VBox(10); // Create a layout for each post
            topNPost.setSpacing(10);
            Label contentLabel = new Label("Content: " + post.getContent());
            Label authorLabel = new Label("Author: " + post.getAuthor());
            Label likesLabel = new Label("Likes: " + post.getLikes());
            Label sharesLabel = new Label("Shares: " + post.getShares());
            Label dateLabel = new Label("Shares: " + post.getDateTime());
            topNPost.getChildren().addAll(contentLabel, authorLabel, likesLabel, sharesLabel, dateLabel);
            layout.getChildren().addAll(topNPost);
        }
        displayTopPostsScene = new Scene(layout, 600, 400);
        primaryStage.setScene(removePostScene);

        // Set event handlers for buttons
        closeRetrieveTopButton.setOnAction(e -> {
        	controller.handleCloseTopPosts(e);
        });
    }
    
    /** 
     * Export Post Scene
     * Set content and style
     */
    public void initializeExportPostButton() {
    	
    	exportPostid = new TextField();
    	exportName = new TextField();
    	exportFolder = new TextField();
    	confirmExportPostButton = new Button("Confirm");
    	cancelExportPostButton = new Button("Cancel");
        
        // Create the layout for the register scene
        VBox layout = new VBox(10);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        Label label = new Label("Export Post");
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        layout.getChildren().addAll(label
        		, new Label("Enter export postid"), exportPostid
        		, new Label("Enter export file name"), exportName
        		, new Label("Enter export folder name"), exportFolder
        		, completeRetrieveTopButton, cancelExportPostButton);
        exportPostScene = new Scene(layout, 600, 400); // set the scene
        
        // Set event handlers for buttons
        confirmExportPostButton.setOnAction(e -> {
        	controller.handleConfirmExport(e);
        });
        
     // Set event handlers for buttons
        cancelExportPostButton.setOnAction(e -> {
        	controller.handleCancelExport(e);
        });
        
    }
    
    public void showExportPostScene() {
    	initializeExportPostButton();
    	primaryStage.setScene(exportPostScene);
    }
    
    
    /** 
     * Import Post Scene
     * Set content and style
     */
    public void initializeImportPostButton() {
    	
    	importName = new TextField();
    	importFolder = new TextField();
    	confirmImportPostButton = new Button("Confirm");
    	cancelImportPostButton = new Button("Cancel");
        
        // Create the layout for the register scene
        VBox layout = new VBox(10);
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        Label label = new Label("Bulk Import Post");
        label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        layout.getChildren().addAll(label
        		, new Label("Enter export file name"), importName
        		, new Label("Enter export folder name"), importFolder
        		, confirmImportPostButton, cancelImportPostButton);
        bulkImportPostsScene = new Scene(layout, 600, 400); // set the scene
        
        // Set event handlers for buttons
        confirmImportPostButton.setOnAction(e -> {
        	controller.handleConfirmImport(e);
        });
        
     // Set event handlers for buttons
        cancelImportPostButton.setOnAction(e -> {
        	controller.handleCancelImport(e);
        });
        
    }
    
    public void showImportPostScene() {
    	initializeImportPostButton();
    	primaryStage.setScene(bulkImportPostsScene);
    }
    

    /**
     * Text field Getter
     * @return username for login
     */
    public String getUsername() {
        return usernameInput.getText();
    }
    
    /**
     * Text field Getter
     * @return password for login
     */
    public String getPassword() {
        return passwordInput.getText();
    }
    
    /**
     * Text field Getter
     * @return username for register
     */
    public String getUsernameSetup() {
        return usernameSetup.getText();
    }
    
    /**
     * Text field Getter
     * @return password for register
     */
    public String getPasswordSetup() {
        return passwordSetup.getText();
    }
    
    /**
     * Text field Getter
     * @return firstname for register
     */
    public String getFirstnameSetup() {
        return firstnameSetup.getText();
    }
    
    /**
     * Text field Getter
     * @return lastname for register
     */
    public String getLastnameSetup() {
        return lastnameSetup.getText();
    }
    
    /**
     * Text field Getter
     * @return username for register
     */
    public String getUsernameEdit() {
        return usernameEdit.getText();
    }
    
    /**
     * Text field Getter
     * @return password for register
     */
    public String getPasswordEdit() {
        return passwordEdit.getText();
    }
    
    /**
     * Text field Getter
     * @return firstname for register
     */
    public String getFirstnameEdit() {
        return firstnameEdit.getText();
    }
    
    /**
     * Text field Getter
     * @return lastname for register
     */
    public String getLastnameEdit() {
        return lastnameEdit.getText();
    }

    /**
     * Text field Getter
     * @return content for add post
     */
    public String getContent() {
        return content.getText();
    }

    /**
     * Text field Getter
     * @return author for add post
     */
    public String getAuthor() {
        return author.getText();
    }

    /**
     * Text field Getter
     * @return likes for add post
     */
    public String getLikes() {
        return likes.getText();
    }

    /**
     * Text field Getter
     * @return shares for add post
     */
    public String getShares() {
        return shares.getText();
    }

    /**
     * Text field Getter
     * @return datetime for add post
     */
    public String getDatetime() {
        return datetime.getText();
    }
    
    /**
     * Text field Getter
     * @return postid for retrieve post
     */
    public String getRetrievePostid() {
        return retrievePostid.getText();
    }

    /**
     * Text field Getter
     * @return postid for remove post
     */
    public String getRemovePostid() {
        return removePostid.getText();
    }

    /**
     * Text field Getter
     * @return postid for retrieve n post
     */
    public String getTopNPost() {
        return retrieveTopNPost.getText();
    }

    /**
     * Text field Getter
     * @return retrieve someone's post
     */
    public String getRetrieveUser() {
        return retrieveUser.getText();
    }

    /**
     * Text field Getter
     * @return retrieve someone's post
     */
    public String getExportPostid() {
        return exportPostid.getText();
    }

    /**
     * Text field Getter
     * @return retrieve someone's post
     */
    public String getExportName() {
        return exportName.getText();
    }

    /**
     * Text field Getter
     * @return retrieve someone's post
     */
    public String getExportFolder() {
        return exportFolder.getText();
    }
    
    /**
     * Text field Getter
     * @return retrieve someone's post
     */
    public String getImportName() {
        return importName.getText();
    }

    /**
     * Text field Getter
     * @return retrieve someone's post
     */
    public String getImportFolder() {
        return importFolder.getText();
    }
}