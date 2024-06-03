package com.example.tangram;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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

public class EndingController implements Initializable {

    @FXML
    private Label hs1Label;

    @FXML
    private AnchorPane hstory1Pane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button nexths1Button;

    @FXML
    private ImageView tan;
    @FXML
    private ImageView back;

    private static final double slideDuration = 2.0;

    private static final String textToShow1 = "Tan felt down but he did not give up. He took a look at the broken pieces and saw an opportunity. Using the seven glass fragments, Tan invented a new game named TANGRAM.";

    private static final String textToShow2 = "Tan's game became a huge hit in the kingdom, bringing joy to everyone. The king rewarded Tan upon knowing of his journey. From that day on, Tan became known as the inventor who turned setbacks into success, inspiring others to never stop dreaming and creating.!";

    private boolean isFirstText = true;

    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\6.png");
        back.setImage(image);

        Image image1 = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\burma_4265582.png");
        tan.setImage(image1);
        animateText(textToShow1);

        double paneWidth = hstory1Pane.getBoundsInLocal().getWidth();
        tan.setTranslateX(paneWidth);
        TranslateTransition slideTransition = new TranslateTransition(Duration.seconds(slideDuration), tan);
        slideTransition.setToX(0);

        slideTransition.play();
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
                    hs1Label.setText(textToShow.substring(0, index + 1));
                });
                timeline.getKeyFrames().add(keyFrame);
            }
        }

        timeline.play();
    }

    @FXML
    public void onNexths1ButtonPressed() {
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
        fadeTransition.setNode(hstory1Pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            try {
                switchToStory2();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }
    public void switchToStory2() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("opening-view.fxml"));
        scene = new Scene(root,827,603);
        stage = (Stage) hstory1Pane.getScene().getWindow();
        stage.setScene(scene);
        //stage.show();
    }
}
