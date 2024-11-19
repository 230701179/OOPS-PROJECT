import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.sql.*;

public class ActivityTrackingPage extends Application {

    @Override
    public void start(Stage stage) {
        // Title or text for the page title
        Button backButton = new Button("Back to Main");
        backButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #333;");
        backButton.setOnAction(e -> stage.close());  // This closes the current page, you may want to return to the main window

        // Buttons for activity tracking features
        Button trackActivityButton = new Button("Track New Activity");
        trackActivityButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        trackActivityButton.setOnAction(e -> openTrackActivityWindow());

        Button activityHistoryButton = new Button("View Activity History");
        activityHistoryButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        
        // Layout for the page
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(backButton, trackActivityButton, activityHistoryButton);

        // Set the scene for the page
        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("Activity Tracking");
        stage.setScene(scene);
        stage.show();
    }

    private void openTrackActivityWindow() {
        // Open a new window to track a new activity
        Stage stage = new Stage();
        VBox layout = new VBox(15);
        
        // Inputs for activity type, duration, distance
        Label activityLabel = new Label("Enter activity type (e.g., Running):");
        TextField activityField = new TextField();
        
        Label durationLabel = new Label("Enter duration (minutes):");
        TextField durationField = new TextField();
        
        Label distanceLabel = new Label("Enter distance (km):");
        TextField distanceField = new TextField();

        Button saveButton = new Button("Save Activity");
        saveButton.setOnAction(e -> {
            String activityType = activityField.getText();
            String duration = durationField.getText();
            String distance = distanceField.getText();
            
            if (!activityType.isEmpty() && !duration.isEmpty() && !distance.isEmpty()) {
                saveActivity(activityType, Integer.parseInt(duration), Double.parseDouble(distance));
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Activity saved successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
            }
        });

        layout.getChildren().addAll(activityLabel, activityField, durationLabel, durationField, distanceLabel, distanceField, saveButton);
        Scene scene = new Scene(layout, 300, 250);
        stage.setTitle("Track New Activity");
        stage.setScene(scene);
        stage.show();
    }

    private void saveActivity(String activityType, int duration, double distance) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/fitness_tracker";  // Replace with your actual database name
        String user = "root";  // Replace with your MySQL username
        String password = "manu";  // Replace with your MySQL password

        String query = "INSERT INTO activity_tracking (user_id, activity_type, duration, distance, activity_date) VALUES (?, ?, ?, ?, NOW())";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Assuming a hardcoded user_id for now (you'll likely get this from the logged-in user)
            int userId = 1; // You can replace this with the actual user ID dynamically

            stmt.setInt(1, userId);
            stmt.setString(2, activityType);
            stmt.setInt(3, duration);
            stmt.setDouble(4, distance);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
