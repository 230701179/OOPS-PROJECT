import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fitness_tracker";  // Database URL
    private static final String USER = "root";  // MySQL username
    private static final String PASSWORD = "manu";  // MySQL password

    // Method to establish a connection to the database
    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }

    // Main method - Entry point of the application
    public static void main(String[] args) {
        // Attempt to establish a connection
        Connection connection = connect();

        // Check if the connection was successful
        if (connection != null) {
            System.out.println("Connection to the database established successfully!");
            // Close the connection after use (optional for this demo, but always good practice)
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
