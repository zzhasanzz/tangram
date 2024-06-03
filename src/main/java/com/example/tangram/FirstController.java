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

public class FirstController {

    @FXML
    private AnchorPane aboutPane;
    @FXML
    private ImageView background;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize() {
        Image image1 = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\about.png");
        background.setImage(image1);
    }

    public void next1Pressed(){
        makeFadeOut();
    }

    public void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(aboutPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            try {
                switchToStory1();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }
    public void switchToStory1() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loading.fxml"));
        scene = new Scene(root,827,603);
        stage = (Stage) aboutPane.getScene().getWindow();
        stage.setScene(scene);
        //stage.show();
    }


}
