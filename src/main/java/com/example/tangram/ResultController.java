package com.example.tangram;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultController {
    @FXML
    private Button next;
    @FXML
    private Polygon star1;
    @FXML
    private Polygon star2;
    @FXML
    private Polygon star3;
    @FXML
    private ImageView background;
    @FXML
    private ImageView sparkles1;
    @FXML
    private ImageView sparkles2;
    @FXML
    private Pane rootPane;
    @FXML
    private Label status;
    @FXML
    private Label levelStatus;

    private int time;
    private int level;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void initialize() {
        Image image = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\horseback.png");
        background.setImage(image);
        Image spark = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\win.gif");
        sparkles1.setImage(spark);
        sparkles2.setImage(spark);
    }

    public void setTime(int time) {
        this.time = time;
        status.setText("Time Elapsed: " + time + " seconds");
        updateStars(time);
    }

    public void setLevel(int level) {
        this.level = level;
        if (level == 7) levelStatus.setText("You reached the end of the story!");
        levelStatus.setText("You proceed to Level: " + level + 1);
    }

    private void updateStars(int time) {
        Image starTexture = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\shine.png", false);

        if (time <= 60) {
            star1.setFill(new ImagePattern(starTexture));
            star2.setFill(new ImagePattern(starTexture));
            star3.setFill(new ImagePattern(starTexture));
        } else if (time <= 120) {
            star1.setFill(new ImagePattern(starTexture));
            star2.setFill(new ImagePattern(starTexture));
        } else {
            star1.setFill(new ImagePattern(starTexture));
        }

    }

    @FXML
    public void nextPressed() throws IOException {
        switchToNextLevel(++level);
    }

    public void switchToNextLevel(int currentLevel) throws IOException {
        FXMLLoader loader = null;
        if (currentLevel == 1) {
            loader = new FXMLLoader(getClass().getResource("first-view.fxml"));
        } else if (currentLevel == 2) {
            loader = new FXMLLoader(getClass().getResource("horsestory1.fxml"));
        } else if (currentLevel == 3) {
            loader = new FXMLLoader(getClass().getResource("treestory.fxml"));
        } else if (currentLevel == 4) {
            loader = new FXMLLoader(getClass().getResource("mountainstory.fxml"));
        } else if (currentLevel == 5) {
            loader = new FXMLLoader(getClass().getResource("tanstory.fxml"));
        } else if (currentLevel == 6) {
            loader = new FXMLLoader(getClass().getResource("birdstory.fxml"));
        }
        else if (currentLevel == 7) {
            loader = new FXMLLoader(getClass().getResource("housestory.fxml"));
        }
        else if (currentLevel == 8) {
            loader = new FXMLLoader(getClass().getResource("ending.fxml"));
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

}
