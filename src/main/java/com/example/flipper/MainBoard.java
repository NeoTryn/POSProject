package com.example.flipper;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MainBoard {

    private GridPane board;
    private SubBoard[][] subBoards;
    private char[][] mainBoardState;
    private char currentPlayer;

    public MainBoard() {
        board = new GridPane();
        subBoards = new SubBoard[3][3];
        mainBoardState = new char[3][3];
        currentPlayer = 'X';

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                subBoards[i][j] = new SubBoard(i, j, this);
                GridPane subBoardGrid = subBoards[i][j].getBoard();

                // Set a thick border around each sub-board
                BorderStroke stroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3));
                Border border = new Border(stroke);
                subBoardGrid.setBorder(border);

                board.add(subBoardGrid, j, i);

                mainBoardState[i][j] = ' ';
            }
        }

        // Adding spacing between sub-boards for visual clarity
        board.setHgap(10);
        board.setVgap(10);

        // Setting the padding around the main board
        board.setPadding(new Insets(10));
    }

    public GridPane getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public void updateMainBoard(int row, int col, char winner) {
        mainBoardState[row][col] = winner;
        if (checkWin(winner)) {
            showWinner(winner);
        } else if (checkDraw()) {
            showWinner(' ');
        }
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (mainBoardState[i][0] == player && mainBoardState[i][1] == player && mainBoardState[i][2] == player) return true;
            if (mainBoardState[0][i] == player && mainBoardState[1][i] == player && mainBoardState[2][i] == player) return true;
        }
        if (mainBoardState[0][0] == player && mainBoardState[1][1] == player && mainBoardState[2][2] == player) return true;
        if (mainBoardState[0][2] == player && mainBoardState[1][1] == player && mainBoardState[2][0] == player) return true;
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mainBoardState[i][j] == ' ') return false;
            }
        }
        return true;
    }

    private void showWinner(char winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (winner == ' ') {
            alert.setTitle("Game Over");
            alert.setHeaderText("It's a Draw!");
        } else {
            alert.setTitle("Game Over");
            alert.setHeaderText("Player " + winner + " Wins!");
        }
        alert.showAndWait();
        resetGame();
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
}
