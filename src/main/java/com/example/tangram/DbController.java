package com.example.tangram;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbController {
    private static DbController instance;
    private static Connection databaseLink;

    private DbController() {
        databaseLink = getConnection();
    }

    public static DbController getInstance() {
        if (instance == null) {
            instance = new DbController();
        }
        return instance;
    }

    private Connection getConnection() {
        String databaseName = "tangramdb";
        String databaseUser = "root";
        String databasePassword = "1234";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveProgress(int currentLevel) {
        String query = "UPDATE progress SET currentLevel = ? WHERE id = 1";
        try (PreparedStatement preparedStatement = databaseLink.prepareStatement(query)) {
            preparedStatement.setInt(1, currentLevel);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) { // No row was updated, so insert instead
                query = "INSERT INTO progress (id, currentLevel) VALUES (1, ?)";
                try (PreparedStatement insertStatement = databaseLink.prepareStatement(query)) {
                    insertStatement.setInt(1, currentLevel);
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getProgress() {
        String query = "SELECT currentLevel FROM progress WHERE id = 1";
        try (PreparedStatement preparedStatement = databaseLink.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("currentLevel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to level 1 if no progress is found
    }

    public void resetProgress() {
        String query = "TRUNCATE TABLE progress";
        try (PreparedStatement preparedStatement = databaseLink.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveScore(int level, int score) {
        String query = "INSERT INTO scores (level, score) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = databaseLink.prepareStatement(query)) {
            preparedStatement.setInt(1, level);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Score> getScores() {
        List<Score> scores = new ArrayList<>();
        String query = "SELECT level, score FROM scores ORDER BY score ASC";

        try (PreparedStatement preparedStatement = databaseLink.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int level = resultSet.getInt("level");
                int score = resultSet.getInt("score");
                scores.add(new Score(level, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }

}
