import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        showMainWindow(primaryStage);
    }

    private void showMainWindow(Stage primaryStage) {
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #333;");
        backButton.setOnAction(e -> showLoginPage(primaryStage));

        Button foodTrackingButton = new Button("Food Tracking");
        Button activityTrackingButton = new Button("Activity Tracking");
        Button medicalConditionTrackingButton = new Button("Medical Condition Tracking");
        Button waterTrackingButton = new Button("Water Tracking");
        Button bmiScaleButton = new Button("BMI Scale");
        Button userProfileButton = new Button("User Profile");

        foodTrackingButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        activityTrackingButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        medicalConditionTrackingButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        waterTrackingButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        bmiScaleButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");
        userProfileButton.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px;");

        // Set up button actions to navigate to corresponding pages
        foodTrackingButton.setOnAction(e -> openFoodTrackingPage());
        activityTrackingButton.setOnAction(e -> openActivityTrackingPage());
        medicalConditionTrackingButton.setOnAction(e -> openMedicalConditionPage());
        waterTrackingButton.setOnAction(e -> openWaterTrackingPage());
        bmiScaleButton.setOnAction(e -> openBmiScalePage());
        userProfileButton.setOnAction(e -> openUserProfilePage());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(
                backButton,
                foodTrackingButton,
                activityTrackingButton,
                medicalConditionTrackingButton,
                waterTrackingButton,
                bmiScaleButton,
                userProfileButton
        );
        layout.setAlignment(Pos.CENTER);

        // Set background and scene without using additional properties
        StackPane root = new StackPane();
        Image image = new Image("file:/C:/XboxGames/image.jpg"); // Update the file path as needed
        BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);
        Background background = new Background(backgroundImage);
        root.setBackground(background);
        root.getChildren().add(layout);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Personal Fitness Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFoodTrackingPage() {
        // Open Food Tracking page logic here
        FoodTrackingPage foodTrackingPage = new FoodTrackingPage();
        Stage foodTrackingStage = new Stage();
        foodTrackingPage.start(foodTrackingStage);
    }

    private void openActivityTrackingPage() {
        // Open Activity Tracking page logic here
        ActivityTrackingPage activityTrackingPage = new ActivityTrackingPage();
        Stage activityTrackingStage = new Stage();
        activityTrackingPage.start(activityTrackingStage);
    }

    private void openMedicalConditionPage() {
        // Open Medical Condition page logic here
        MedicalConditionTrackingPage medicalConditionPage = new MedicalConditionTrackingPage();
        Stage medicalConditionStage = new Stage();
        medicalConditionPage.start(medicalConditionStage);
    }

    private void openWaterTrackingPage() {
        // Open Water Tracking page logic here
        WaterTrackingPage waterTrackingPage = new WaterTrackingPage();
        Stage waterTrackingStage = new Stage();
        waterTrackingPage.start(waterTrackingStage);
    }

    private void openBmiScalePage() {
        // Open BMI Scale page logic here
        BmiScalePage bmiScalePage = new BmiScalePage();
        Stage bmiScaleStage = new Stage();
        bmiScalePage.start(bmiScaleStage);
    }

    private void openUserProfilePage() {
        // Open User Profile page logic here
        UserProfilePage userProfilePage = new UserProfilePage();
        Stage userProfileStage = new Stage();
        userProfilePage.start(userProfileStage);
    }

    private void showLoginPage(Stage primaryStage) {
        // Logic to show login page
    }

    public static void main(String[] args) {
        launch(args);
    }
}
