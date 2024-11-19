import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;

public class UserProfilePage extends Application {

    @Override
    public void start(Stage stage) {
        // Title for the page
        Label titleLabel = new Label("User Profile");
        titleLabel.setFont(new Font("Arial", 24));

        // User information fields
        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();
        
        Label emailLabel = new Label("Email Address:");
        TextField emailField = new TextField();
        
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        
        // Profile picture (for simplicity, use a placeholder)
        Label pictureLabel = new Label("Profile Picture:");
        Button changePicButton = new Button("Change Picture");

        // Button to save updated profile
        Button saveButton = new Button("Save Profile");
        saveButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");

        // Label to show save status
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Action for the "Save Profile" button
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            
            // Insert or update user profile in the database
            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                saveUserProfile(name, email, password);
                statusLabel.setText("Profile updated successfully!");
            } else {
                statusLabel.setText("Please fill in all fields.");
            }
        });

        // Back button to go to the previous page
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        backButton.setOnAction(e -> stage.close());  // Close the current page

        // Layout for the page
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
            titleLabel,
            nameLabel,
            nameField,
            emailLabel,
            emailField,
            passwordLabel,
            passwordField,
            pictureLabel,
            changePicButton,
            saveButton,
            statusLabel,
            backButton
        );

        // Set up the scene
        Scene scene = new Scene(layout, 400, 350);
        stage.setTitle("User Profile");
        stage.setScene(scene);
        stage.show();
    }

    private void saveUserProfile(String name, String email, String password) {
        String url = "jdbc:mysql://localhost:3306/fitness_tracker";
        String user = "root";
        String pass = "manu";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO user_profiles (full_name, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);  // You should hash the password in a real app
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
