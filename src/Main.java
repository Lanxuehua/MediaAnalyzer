/*
 * Class Main
 * Initialize Database, create MVC, set up initial scene
 * @author: Xuehua Lan
 * @version JavaSE-17
*/ 

import java.sql.Connection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to Data Analytics Hub");

        // Create the database connection
		try {
			//
			Connection connection = DatabaseConnection.connect();
			
		    // Initialize the database and create tables
		    DatabaseConnection.createTables();

		    // Create the model, view, and controller
		    Model model = new Model(connection);
		    UserView view = new UserView(primaryStage);
		    Controller controller = new Controller(model, view);

		    // Set the controller reference in the view
		    view.setController(controller);

		    // Set up the initial scene
	        view.initializeLoginScene();
	        view.showLoginScene();

		    primaryStage.show();
		    
		} catch (SQLException e) {
		    // Handle database connection errors
		    e.printStackTrace();
		}
    }
}