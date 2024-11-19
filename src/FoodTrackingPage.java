import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;

public class FoodTrackingPage extends Application {

    @Override
    public void start(Stage stage) {
        // Title for the page
        Label titleLabel = new Label("Food Tracking");
        titleLabel.setFont(new Font("Arial", 24));

        // Input fields for food type, quantity, and time
        Label foodLabel = new Label("Enter food item:");
        TextField foodField = new TextField();
        
        Label quantityLabel = new Label("Enter quantity (grams):");
        TextField quantityField = new TextField();
        
        Label timeLabel = new Label("Enter time of consumption:");
        TextField timeField = new TextField();
        
        // Button to log food
        Button logFoodButton = new Button("Log Food");
        logFoodButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");

        // Label to display the result
        Label resultLabel = new Label("Food entry will be displayed here.");
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Action for the "Log Food" button
        logFoodButton.setOnAction(e -> {
            String foodItem = foodField.getText();
            String quantity = quantityField.getText();
            String time = timeField.getText();
            
            if (!foodItem.isEmpty() && !quantity.isEmpty() && !time.isEmpty()) {
                // Save food entry to MySQL database
                saveFoodEntry(foodItem, Integer.parseInt(quantity), time);
                resultLabel.setText("Logged: " + foodItem + " (" + quantity + "g) at " + time);
            } else {
                resultLabel.setText("Please fill in all fields.");
            }
        });

        // Back button to go to the previous page
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        backButton.setOnAction(e -> stage.close());  // Close the current page, modify this as per your navigation logic

        // Layout for the page
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
            titleLabel,
            foodLabel,
            foodField,
            quantityLabel,
            quantityField,
            timeLabel,
            timeField,
            logFoodButton,
            resultLabel,
            backButton
        );

        // Set up the scene
        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("Food Tracking");
        stage.setScene(scene);
        stage.show();
    }

    private void saveFoodEntry(String foodItem, int quantity, String time) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/fitness_tracker";  // Replace with your actual database name
        String user = "root";  // Replace with your MySQL username
        String password = "manu";  // Replace with your MySQL password

        String query = "INSERT INTO food_tracking (user_id, food_name, quantity, consumption_time, date) VALUES (?, ?, ?, ?, CURDATE())";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Assuming a hardcoded user_id for now (you'll likely get this from the logged-in user)
            int userId = 1;

            stmt.setInt(1, userId);
            stmt.setString(2, foodItem);
            stmt.setInt(3, quantity);
            stmt.setString(4, time);  // Assuming time is in a valid format (HH:mm)

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
