import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BmiScalePage extends Application {

    @Override
    public void start(Stage stage) {
        // Title for the page
        Label titleLabel = new Label("BMI Scale");
        titleLabel.setFont(new Font("Arial", 24));
        
        // Input fields for height and weight
        Label heightLabel = new Label("Enter your height (in meters):");
        TextField heightField = new TextField();
        
        Label weightLabel = new Label("Enter your weight (in kilograms):");
        TextField weightField = new TextField();
        
        // Button to calculate BMI
        Button calculateButton = new Button("Calculate BMI");
        calculateButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        
        // Label to display the result
        Label resultLabel = new Label("Your BMI will be displayed here.");
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Calculate BMI when the button is clicked
        calculateButton.setOnAction(e -> {
            try {
                double height = Double.parseDouble(heightField.getText());
                double weight = Double.parseDouble(weightField.getText());
                if (height > 0 && weight > 0) {
                    double bmi = weight / (height * height);
                    resultLabel.setText(String.format("Your BMI is: %.2f", bmi));
                    saveBmiToDatabase(height, weight, bmi);  // Save the result to the database
                } else {
                    resultLabel.setText("Please enter valid positive values.");
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid numbers.");
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
            heightLabel,
            heightField,
            weightLabel,
            weightField,
            calculateButton,
            resultLabel,
            backButton
        );

        // Set up the scene
        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("BMI Scale");
        stage.setScene(scene);
        stage.show();
    }

    private void saveBmiToDatabase(double height, double weight, double bmi) {
        String url = "jdbc:mysql://localhost:3306/fitness_tracker";  // Replace with your actual database name
        String user = "root";  // Replace with your MySQL username
        String password = "manu";  // Replace with your MySQL password

        String query = "INSERT INTO bmi_tracking (user_id, height, weight, bmi, bmi_date) VALUES (?, ?, ?, ?, NOW())";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Assuming a hardcoded user_id for now (you'll likely get this from the logged-in user)
            int userId = 1; // Replace with actual user ID dynamically

            stmt.setInt(1, userId);
            stmt.setDouble(2, height);
            stmt.setDouble(3, weight);
            stmt.setDouble(4, bmi);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
