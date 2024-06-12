package com.example.flipper;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SubBoard {
    private GridPane board;
    private char[][] state;
    private MainBoard mainBoard;
    private int mainRow;
    private int mainCol;
    private boolean isCompleted;

    public SubBoard(int mainRow, int mainCol, MainBoard mainBoard) {
        this.mainRow = mainRow;
        this.mainCol = mainCol;
        this.mainBoard = mainBoard;
        state = new char[3][3];
        board = new GridPane();
        isCompleted = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button("");
                button.setPrefSize(100, 100);
                int row = i;
                int col = j;
                button.setOnAction(e -> handleButtonClick(row, col));
                board.add(button, j, i);
                state[i][j] = ' ';
            }
        }
    }

    private void handleButtonClick(int row, int col) {
        if (state[row][col] == ' ' && !isCompleted) {
            state[row][col] = mainBoard.getCurrentPlayer();
            Button button = (Button) board.getChildren().get(row * 3 + col);
            button.setText(String.valueOf(mainBoard.getCurrentPlayer()));
            button.setDisable(true);

            mainBoard.handleMove(mainRow, mainCol, row, col);
        }
    }

    public void handleMove(int subRow, int subCol) {
        // No additional logic needed, handled in handleButtonClick
    }

    public char getWinner() {
        for (int i = 0; i < 3; i++) {
            if (state[i][0] != ' ' && state[i][0] == state[i][1] && state[i][1] == state[i][2]) {
                disableRemainingButtons();
                return state[i][0];
            }
            if (state[0][i] != ' ' && state[0][i] == state[1][i] && state[1][i] == state[2][i]) {
                disableRemainingButtons();
                return state[0][i];
            }
        }
        if (state[0][0] != ' ' && state[0][0] == state[1][1] && state[1][1] == state[2][2]) {
            disableRemainingButtons();
            return state[0][0];
        }
        if (state[0][2] != ' ' && state[0][2] == state[1][1] && state[1][1] == state[2][0]) {
            disableRemainingButtons();
            return state[0][2];
        }
        return ' ';
    }

    private void disableRemainingButtons() {
        isCompleted = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == ' ') {
                    Button button = (Button) board.getChildren().get(i * 3 + j);
                    button.setDisable(true);
                }
            }
        }
    }

    public void reset() {
        isCompleted = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = ' ';
                Button button = (Button) board.getChildren().get(i * 3 + j);
                button.setText("");
                button.setDisable(false);
            }
        }
    }

    public GridPane getBoard() {
        return board;
    }
}
