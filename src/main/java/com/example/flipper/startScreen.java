package com.example.flipper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


import java.awt.*;
import java.util.Collection;
import java.util.Objects;

public class startScreen {
    public Scene scene;
    public VBox vbox;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double width = screenSize.getWidth();
    private double height = screenSize.getHeight();

    private Button startBtn;
    private Button exitBtn;

    public startScreen() {
        vbox = new VBox();
        scene = new Scene(vbox, (int)width, (int)height);

        vbox.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        startBtn = new Button("Start");
        exitBtn = new Button("Exit");

        startBtn.setId("startBtn");
        exitBtn.setId("exitBtn");

        vbox.setId("vbox");

        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        vbox.getChildren().addAll(startBtn, exitBtn);
    }

    public Button getStartBtn() {
        return startBtn;
    }
}
