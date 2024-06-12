package com.example.flipper;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainBoard {
    private GridPane board;
    private SubBoard[][] subBoards;
    private char[][] mainBoardState;
    private char currentPlayer;
    private SoundPlayer celebrationSoundPlayer;

    public MainBoard() {
        board = new GridPane();
        subBoards = new SubBoard[3][3];
        mainBoardState = new char[3][3];
        currentPlayer = 'X';
        celebrationSoundPlayer = new SoundPlayer("music/Celebration.wav", -10.0f);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                subBoards[i][j] = new SubBoard(i, j, this);
                GridPane subBoardGrid = subBoards[i][j].getBoard();

                board.add(subBoardGrid, j, i);
                mainBoardState[i][j] = ' ';
            }
        }

        board.setHgap(10);
        board.setVgap(10);
    }

    public void handleMove(int mainRow, int mainCol, int subRow, int subCol) {
        if (mainBoardState[mainRow][mainCol] == ' ') {
            subBoards[mainRow][mainCol].handleMove(subRow, subCol);
            char subWinner = subBoards[mainRow][mainCol].getWinner();
            if (subWinner != ' ') {
                mainBoardState[mainRow][mainCol] = subWinner;
                if (checkWin(mainBoardState)) {
                    celebrationSoundPlayer.play();
                    showWinnerAlert();
                }
            }
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private boolean checkWin(char[][] boardState) {
        for (int i = 0; i < 3; i++) {
            if (boardState[i][0] != ' ' && boardState[i][0] == boardState[i][1] && boardState[i][1] == boardState[i][2]) {
                return true;
            }
            if (boardState[0][i] != ' ' && boardState[0][i] == boardState[1][i] && boardState[1][i] == boardState[2][i]) {
                return true;
            }
        }
        if (boardState[0][0] != ' ' && boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2]) {
            return true;
        }
        if (boardState[0][2] != ' ' && boardState[0][2] == boardState[1][1] && boardState[1][1] == boardState[2][0]) {
            return true;
        }
        return false;
    }

    private void showWinnerAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Player " + currentPlayer + " wins!");

        ButtonType resetButton = new ButtonType("Reset");
        ButtonType mainMenuButton = new ButtonType("Main Menu");
        alert.getButtonTypes().setAll(resetButton, mainMenuButton);

        alert.showAndWait().ifPresent(response -> {
            celebrationSoundPlayer.stop();
            if (response == resetButton) {
                resetGame();
            } else if (response == mainMenuButton) {
                goToMainMenu();
            }
        });
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                subBoards[i][j].reset();
                mainBoardState[i][j] = ' ';
            }
        }
        currentPlayer = 'X';
    }

    private void goToMainMenu() {
        Stage stage = (Stage) board.getScene().getWindow();
        try {
            startScreen mainMenu = new startScreen();
            stage.setScene(startScreen.scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GridPane getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }
}
