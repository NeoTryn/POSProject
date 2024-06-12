package com.example.flipper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class startScreen {
    public static Scene scene;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double width = screenSize.getWidth();
    private double height = screenSize.getHeight();

    private Button startBrickBtn;
    private Button startTicBtn;
    private Button exitBtn;

    public startScreen() throws IOException {
        AnchorPane root = new AnchorPane();
        scene = new Scene(root, width, height);

        startBrickBtn = new Button("Brick Breaker");
        startTicBtn = new Button("Mega Tic-Tac-Toe");
        exitBtn = new Button("Exit");

        startBrickBtn.setTranslateY(300);
        startBrickBtn.setTranslateX(550);
        startTicBtn.setTranslateY(300);
        startTicBtn.setTranslateX(800);
        exitBtn.setTranslateY(500);
        exitBtn.setTranslateX(675);

        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        startBrickBtn.setPrefHeight(100);
        startBrickBtn.setPrefWidth(200);
        startTicBtn.setPrefHeight(100);
        startTicBtn.setPrefWidth(200);

        exitBtn.setPrefHeight(100);
        exitBtn.setPrefWidth(200);

        exitBtn.getStyleClass().add("restart-button");

        exitBtn.setId("exitBtn");
        startBrickBtn.setId("startBrickBtn");
        startTicBtn.setId("startTicBtn");

        root.getChildren().addAll(startBrickBtn,startTicBtn, exitBtn);

        exitBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }

    public Button getStartBrickBtn() {
        return startBrickBtn;
    }

    public Button getStartTicButton() {
        return startTicBtn;
    }
}
