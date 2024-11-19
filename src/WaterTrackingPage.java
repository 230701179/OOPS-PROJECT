import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;

public class WaterTrackingPage extends Application {

    private ObservableList<WaterIntakeItem> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // Title for the page
        Label titleLabel = new Label("Water Tracking");
        titleLabel.setFont(new Font("Arial", 24));

        // Water intake entry field
        Label intakeLabel = new Label("Enter Water Intake (ml):");
        TextField intakeField = new TextField();

        // Button to log water intake
        Button logButton = new Button("Log Water");
        logButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        
        // Label to show intake status
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Table for displaying water tracking history
        TableView<WaterIntakeItem> table = new TableView<>();
        TableColumn<WaterIntakeItem, String> dateColumn = new TableColumn<>("Date");
        TableColumn<WaterIntakeItem, String> intakeColumn = new TableColumn<>("Intake (ml)");
        
        // Adding columns to the table
        table.getColumns().add(dateColumn);
        table.getColumns().add(intakeColumn);
        
        // Load data from MySQL
        loadWaterData();

        // Setting items to the table
        table.setItems(data);

        // Action for the "Log Water" button
        logButton.setOnAction(e -> {
            String intake = intakeField.getText();
            if (!intake.isEmpty() && isNumeric(intake)) {
                // Save the water intake to the database
                logWaterIntake(intake);
                statusLabel.setText("Water logged: " + intake + " ml");
                loadWaterData(); // Reload data from the database
            } else {
                statusLabel.setText("Please enter a valid number.");
            }
        });

        // Back button to go to the previous page
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        backButton.setOnAction(e -> stage.close()); // Close the current page

        // Layout for the page
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
            titleLabel,
            intakeLabel,
            intakeField,
            logButton,
            statusLabel,
            table,
            backButton
        );

        // Set up the scene
        Scene scene = new Scene(layout, 500, 400);
        stage.setTitle("Water Tracking");
        stage.setScene(scene);
        stage.show();
    }

    // Helper method to check if the input is a number
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to load water intake data from the database
    private void loadWaterData() {
        data.clear(); // Clear the existing data
        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "root";
        String pass = "your_password";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT date, intake FROM water_intake ORDER BY date DESC";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    String date = rs.getString("date");
                    String intake = rs.getString("intake");
                    data.add(new WaterIntakeItem(date, intake));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to log water intake into the database
    private void logWaterIntake(String intake) {
        String url = "jdbc:mysql://localhost:3306/fitness_tracker";
        String user = "root";
        String pass = "manu";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            String query = "INSERT INTO water_intake (date, intake) VALUES (CURDATE(), ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(intake));
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Class for representing each water intake item
    public static class WaterIntakeItem {
        private String date;
        private String intake;

        public WaterIntakeItem(String date, String intake) {
            this.date = date;
            this.intake = intake;
        }

        public String getDate() {
            return date;
        }

        public String getIntake() {
            return intake;
        }
    }
}
