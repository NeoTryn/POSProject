package com.example.flipper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;

public class startScreen {
    public Scene scene;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double width = screenSize.getWidth();
    private double height = screenSize.getHeight();

    @FXML
    private Button startBtn;

    @FXML
    private Button exitBtn;

    public startScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./POSProjekt/src/main/resources/startScreen.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        scene = new Scene(root, width, height);

        initialize();
    }

    @FXML
    private void initialize() {
        exitBtn.setOnAction(this::handleExitButtonAction);
    }

    private void handleExitButtonAction(ActionEvent event) {
        System.exit(0);
    }

    public Button getStartBtn() {
        return startBtn;
    }
}
