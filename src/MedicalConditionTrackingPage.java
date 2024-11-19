import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;

public class MedicalConditionTrackingPage extends Application {

    @Override
    public void start(Stage stage) {
        // Title for the page
        Label titleLabel = new Label("Medical Condition Tracking");
        titleLabel.setFont(new Font("Arial", 24));

        // Input fields for condition name, severity, and medications
        Label conditionLabel = new Label("Enter medical condition:");
        TextField conditionField = new TextField();
        
        Label severityLabel = new Label("Enter severity (1-10):");
        TextField severityField = new TextField();
        
        Label medicationLabel = new Label("Enter medication (optional):");
        TextField medicationField = new TextField();
        
        // Button to log condition
        Button logConditionButton = new Button("Log Condition");
        logConditionButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        
        // Label to display the result
        Label resultLabel = new Label("Condition entry will be displayed here.");
        resultLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Action for the "Log Condition" button
        logConditionButton.setOnAction(e -> {
            String condition = conditionField.getText();
            String severity = severityField.getText();
            String medication = medicationField.getText();
            
            if (!condition.isEmpty() && !severity.isEmpty()) {
                // Save the condition to the database
                logConditionToDatabase(condition, severity, medication);
                resultLabel.setText("Logged: " + condition + " (Severity: " + severity + ")");
                if (!medication.isEmpty()) {
                    resultLabel.setText(resultLabel.getText() + " | Medication: " + medication);
                }
            } else {
                resultLabel.setText("Please fill in all fields.");
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
            conditionLabel,
            conditionField,
            severityLabel,
            severityField,
            medicationLabel,
            medicationField,
            logConditionButton,
            resultLabel,
            backButton
        );

        // Set up the scene
        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("Medical Condition Tracking");
        stage.setScene(scene);
        stage.show();
    }

    // Method to log the medical condition into the MySQL database
    private void logConditionToDatabase(String condition, String severity, String medication) {
        String url = "jdbc:mysql://localhost:3306/fitness_tracker";
        String user = "root";
        String pass = "manu";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO medical_conditions (condition_name, severity, medication) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, condition);
                stmt.setInt(2, Integer.parseInt(severity));
                stmt.setString(3, medication.isEmpty() ? null : medication);
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
