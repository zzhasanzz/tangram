package com.example.tangram;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Story2Controller implements Initializable {
    @FXML
    private Label s2Label;

    @FXML
    private AnchorPane story2Pane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button nexts2Button, skips2Button;

    @FXML
    private ImageView king, back;

    private static final String textToShow1 = "Tan was overjoyed with his creation and wanted to show it to the king to prove his skill.But getting to the king's palace was no easy task. Tan had to embark on a long journey. He first boarded a ship to cross the vast sea.";

    private static final String textToShow2 = "Now, let us try to make the ship using the given shapes in which Tan travelled!!!";

    private boolean isFirstText = true;

    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("F:\\testGit\\tangram\\src\\main\\resources\\images\\6.png");
        back.setImage(image);

        Image image1 = new Image("F:\\testGit\\tangram\\src\\main\\resources\\images\\king_2074820.png");
        king.setImage(image1);

        animateText(textToShow1);
    }

    private void animateText(String textToShow) {
        Timeline timeline = new Timeline();
        final double durationInSeconds = 3.0;
        final int numFramesPerLetter = 2;
        final double frameDuration = durationInSeconds / (textToShow.length() * numFramesPerLetter);

        for (int i = 0; i < textToShow.length(); i++) {
            for (int j = 0; j <= numFramesPerLetter; j++) {
                final int index = i;
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * frameDuration + j * frameDuration / numFramesPerLetter), e -> {
                    s2Label.setText(textToShow.substring(0, index + 1));
                });
                timeline.getKeyFrames().add(keyFrame);
            }
        }

        timeline.play();
    }

    public void onNexts2ButtonPressed() {
        if (isFirstText) {
            animateText(textToShow2);
            isFirstText = false;
        }
        else {
            makeFadeOut();
        }
    }

    public void makeFadeOut(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(story2Pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            try {
                switchToShip();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }
    public void switchToShip() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ship.fxml"));
        scene = new Scene(root,827,603);
        stage = (Stage) story2Pane.getScene().getWindow();
        stage.setScene(scene);
        //stage.show();
    }

    public void onSkips2ButtonPressed() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ship.fxml"));
        scene = new Scene(root,827,603);
        stage = (Stage) story2Pane.getScene().getWindow();
        stage.setScene(scene);
    }


}
