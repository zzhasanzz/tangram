package com.example.tangram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ScoreController {

    @FXML
    private TableView<Score> scoreTableView;
    @FXML
    private TableColumn<Score, Integer> levelColumn;
    @FXML
    private TableColumn<Score, Integer> scoreColumn;
    @FXML
    private Button backButton;
    @FXML
    private ImageView background;

    @FXML
    private void initialize() {

        Image image = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\highscores.png");
        background.setImage(image);
        // Initialize the TableView columns
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Load scores from the database
        DbController dbController = DbController.getInstance();
        List<Score> scores = dbController.getScores();

        // Convert the list to an observable list
        ObservableList<Score> scoreObservableList = FXCollections.observableArrayList(scores);

        // Bind the observable list to the TableView
        scoreTableView.setItems(scoreObservableList);
    }

    @FXML
    private void backPressed() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Tangram.class.getResource("opening-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 827, 603);

        // Get the current stage
        Stage stage = (Stage) scoreTableView.getScene().getWindow();  // Assuming scoreTableView is a node in your current scene

        // Set the new scene
        stage.setTitle("Tangram");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
