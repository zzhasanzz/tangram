package com.example.tangram;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {
    @FXML
    private Button startButton, resumeButton, score;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView background, logo;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private int currentLevel; // Declare currentLevel here


    @FXML
    private void initialize() {
        Image image1 = new Image("F:\\testGit\\tangram\\src\\main\\resources\\images\\tangram_background.png");
        background.setImage(image1);

        Image image2 = new Image("F:\\testGit\\tangram\\src\\main\\resources\\images\\logo1.png");
        logo.setImage(image2);
    }

    public void startPressed() {
        resetProgress();
        currentLevel = 1;
        saveProgress();
        makeFadeOut();
    }

    public void resumePressed() {
        loadProgress();
        makeFadeOut();
    }

    public void scorePressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("score.fxml"));
        if (loader != null) {
            root = loader.load();
            scene = new Scene(root, 827, 603);
            stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }


    public void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            try {
                switchToLevel(currentLevel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }

    private void switchToLevel(int currentLevel) throws IOException {
        FXMLLoader loader = null;
        if (currentLevel == 1) {
            loader = new FXMLLoader(getClass().getResource("first-view.fxml"));
        } else if (currentLevel == 2) {
            loader = new FXMLLoader(getClass().getResource("horse.fxml"));
        } else if (currentLevel == 3) {
            loader = new FXMLLoader(getClass().getResource("tree.fxml"));
        } else if (currentLevel == 4) {
            loader = new FXMLLoader(getClass().getResource("mountain.fxml"));
        } else if (currentLevel == 5) {
            loader = new FXMLLoader(getClass().getResource("tan.fxml"));
        } else if (currentLevel == 6) {
            loader = new FXMLLoader(getClass().getResource("bird.fxml"));
        }
        else if (currentLevel == 7) {
            loader = new FXMLLoader(getClass().getResource("house.fxml"));
        }
        else loader = new FXMLLoader(getClass().getResource("ship.fxml"));
        if (loader != null) {
            root = loader.load();
            scene = new Scene(root, 827, 603);
            stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    private void saveProgress() {
        DbController dbController = DbController.getInstance();
        dbController.saveProgress(currentLevel);
    }

    private void loadProgress() {
        DbController dbController = DbController.getInstance();
        currentLevel = dbController.getProgress();
    }

    private void resetProgress() {
        DbController dbController = DbController.getInstance();
        dbController.resetProgress();
    }
}
