package com.example.flipper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainBoard mainBoard = new MainBoard();

        Scene scene = new Scene(mainBoard.getBoard(), 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Nested Tic Tac Toe");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
