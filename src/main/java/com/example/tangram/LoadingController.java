package com.example.tangram;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoadingController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    private AnchorPane loadingPane;

    @FXML
    private ImageView back, tangif;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize() {

        Image image1 = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\loadingback.png");
        back.setImage(image1);

        Image image2 = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\loadgif2.gif");
        tangif.setImage(image2);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> progressBar.setProgress(0)),
                new KeyFrame(Duration.seconds(3), e -> progressBar.setProgress(1))
        );
        timeline.setOnFinished(event -> makeFadeOut());
        timeline.play();
    }

    private void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(loadingPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            try {
                switchToStory1();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }

    private void switchToStory1() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("story1.fxml"));
        scene = new Scene(root, 827, 603);
        stage = (Stage) loadingPane.getScene().getWindow();
        stage.setScene(scene);
    }
}
