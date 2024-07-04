package JFX_test;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;

import Players.Joueur;

public class MainApp extends Application {

    private static ImageView imageView;
    private static Label infoLabel;

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

        VBox infoBox = new VBox(10);
        infoBox.setAlignment(Pos.CENTER);
        infoLabel = new Label();
        infoBox.getChildren().add(infoLabel);

        StackPane.setAlignment(infoBox, Pos.BOTTOM_CENTER);
        root.getChildren().add(infoBox);

        Scene scene = new Scene(root, 800, 600);

        // Lier les dimensions de l'ImageView à celles du StackPane
        imageView.fitWidthProperty().bind(root.widthProperty());
        imageView.fitHeightProperty().bind(root.heightProperty());
        imageView.setPreserveRatio(false);  // Ne pas préserver le ratio

        primaryStage.setTitle("Broadcast");
        primaryStage.initStyle(StageStyle.UNDECORATED); // Supprimer la barre de titre

        // Ajouter une icône à la fenêtre
        Image icon = new Image("file:resources/icon.png");
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true); // Mettre la fenêtre en plein écran
        primaryStage.show();
    }

    public static void displayJoueur(Joueur joueur) {
        try {
            File file = new File(joueur.getImgJoueur());
            Image image = new Image(file.toURI().toURL().toExternalForm());
            imageView.setImage(image);

            String joueurInfo = "Nom: " + joueur.getNom() + "\n"
                    + "Prénom: " + joueur.getPrenom() + "\n"
                    + "Âge: " + joueur.getAge() + "\n"
                    + "Nationalité: " + joueur.getNatio_acronyme() + "\n"
                    + "Taille: " + joueur.getTaille() + " cm\n"
                    + "Poids: " + joueur.getWeight() + " kg\n"
                    + "Main dominante: " + joueur.getMain() + "\n"
                    + "Date de naissance: " + joueur.getBirthDate() + "\n"
                    + "Lieu de naissance: " + joueur.getBirthPlace() + "\n"
                    + "Ville de résidence: " + joueur.getCityResidence() + "\n"
                    + "Classement: " + joueur.getRank() + "\n"
                    + "Total des gains: " + joueur.getPrizetotal();

            infoLabel.setText(joueurInfo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
