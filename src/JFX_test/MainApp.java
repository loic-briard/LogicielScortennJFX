package JFX_test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;

public class MainApp extends Application {

    private static ImageView imageView;

    public static void main(String[] args) {
        // Lancer la fenêtre Swing
        SwingUtilities.invokeLater(() -> new SwingApp());

        // Lancer l'application JavaFX
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        imageView = new ImageView();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, 400, 300);

        // Lier les dimensions de l'ImageView à celles du StackPane
        imageView.fitWidthProperty().bind(root.widthProperty());
        imageView.fitHeightProperty().bind(root.heightProperty());
        imageView.setPreserveRatio(false);  // Ne pas préserver le ratio

        primaryStage.setTitle("Fenêtre JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void changeImage(String imagePath) {
        try {
            File file = new File(imagePath);
            Image image = new Image(file.toURI().toURL().toExternalForm());
            imageView.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
