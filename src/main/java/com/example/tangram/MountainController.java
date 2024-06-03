package com.example.tangram;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MountainController {
    @FXML
    private Pane rootPane;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Polygon large1;
    @FXML
    private Polygon large2;
    @FXML
    private Polygon small1;
    @FXML
    private Polygon small2;
    @FXML
    private Polygon mid;
    @FXML
    private Polygon parallelogram;
    @FXML
    private Polygon puzzle;
    @FXML
    private ImageView background;
    @FXML
    private Button nextButton;
    @FXML
    private Label timerLabel;

    private int currentLevel = 3;
    private Timeline timeline;
    private int secondsElapsed;

    private double startX;
    private double startY;

    private final double TARGET_AREA = 40000;
    private final double TOLERANCE = 600;

    @FXML
    private void initialize() {
        Image image = new Image("C:\\Users\\X1 Carbon\\Documents\\tangram\\tangram\\src\\main\\resources\\images\\puzzle.png");
        background.setImage(image);

        makeDraggable(rectangle);
        makeDraggable(large1);
        makeDraggable(large2);
        makeDraggable(small1);
        makeDraggable(small2);
        makeDraggable(mid);
        makeDraggable(parallelogram);

        secondsElapsed = 0;
        timerLabel.setText(" Time: 00:00 ");
        setupTimer();
        startTimer();
    }

    private void startTimer() {
        timeline.play();
    }

    private void setupTimer() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    secondsElapsed++;
                    updateTimerLabel();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateTimerLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timerLabel.setText(String.format(" Time: %02d:%02d ", minutes, seconds));
    }

    @FXML
    private void nextButtonClicked() throws IOException {
        double totalIntersectionArea = 0;
        List<Shape> intersections = new ArrayList<>();

        Node[] shapes = {rectangle, large1, large2, small1, small2, mid, parallelogram};

        for (Node shape : shapes) {
            if (shape != puzzle) {
                Shape intersect = Shape.intersect((Shape) shape, puzzle);
                intersections.add(intersect);
            }
        }


        totalIntersectionArea = calculateAdjustedIntersectionArea(intersections);
        System.out.println(totalIntersectionArea);

        if (isPuzzleSolved(totalIntersectionArea)) {
            System.out.println("Puzzle Solved!");
            stopTimer();
            saveScore(currentLevel, secondsElapsed);
            showResultScreen(currentLevel, secondsElapsed);
            saveProgress(++currentLevel);
        } else {
            System.out.println("Puzzle Not Solved.");
        }
    }

    private double calculateAdjustedIntersectionArea(List<Shape> intersections) {
        double totalArea = 0;

        for (int i = 0; i < intersections.size(); i++) {
            Shape shapeA = intersections.get(i);
            double areaA = calculateArea(shapeA);

            for (int j = i + 1; j < intersections.size(); j++) {
                Shape shapeB = intersections.get(j);
                Shape intersect = Shape.intersect(shapeA, shapeB);
                double overlapArea = calculateArea(intersect);
                areaA -= overlapArea;
            }

            totalArea += areaA;
        }

        return totalArea;
    }

    private double calculateArea(Shape shape) {
        Path path = (Path) shape;
        Polygon polygon = convertPathToPolygon(path);
        return shoelaceArea(polygon.getPoints());
    }

    private Polygon convertPathToPolygon(Path path) {
        Polygon polygon = new Polygon();
        ObservableList<PathElement> elements = path.getElements();

        for (PathElement element : elements) {
            if (element instanceof MoveTo) {
                MoveTo moveTo = (MoveTo) element;
                polygon.getPoints().addAll(moveTo.getX(), moveTo.getY());
            } else if (element instanceof LineTo) {
                LineTo lineTo = (LineTo) element;
                polygon.getPoints().addAll(lineTo.getX(), lineTo.getY());
            }
        }

        return polygon;
    }

    private double shoelaceArea(ObservableList<Double> points) {
        int numPoints = points.size() / 2;
        if (numPoints < 3) {
            return 0;
        }
        double area = 0;

        for (int i = 0; i < numPoints - 1; i++) {
            double x1 = points.get(i * 2);
            double y1 = points.get(i * 2 + 1);
            double x2 = points.get((i + 1) * 2);
            double y2 = points.get((i + 1) * 2 + 1);

            area += x1 * y2 - x2 * y1;
        }

        double x1 = points.get((numPoints - 1) * 2);
        double y1 = points.get((numPoints - 1) * 2 + 1);
        double x2 = points.get(0);
        double y2 = points.get(1);

        area += x1 * y2 - x2 * y1;

        return Math.abs(area) / 2;
    }

    private boolean isPuzzleSolved(double totalIntersectionArea) {
        return Math.abs(totalIntersectionArea - TARGET_AREA) <= TOLERANCE;
    }



    private void makeDraggable(Node node) {
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();

            if (e.getButton() == MouseButton.SECONDARY) {
                node.setRotate((node.getRotate() + 45) % 360);
            }
        });

        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
        });
    }

    private void saveProgress(int currentLevel) {
        DbController dbController = DbController.getInstance();
        dbController.saveProgress(currentLevel);
    }

    private void saveScore(int level, int score) {
        DbController dbController = DbController.getInstance();
        dbController.saveScore(level, score);
    }

    private void stopTimer() {
        timeline.stop();
    }

    private void showResultScreen(int level, int time) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("result.fxml"));
        Parent root = loader.load();

        ResultController resultController = loader.getController();
        resultController.setLevel(level);
        resultController.setTime(time);

        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
