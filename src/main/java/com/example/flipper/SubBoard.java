package com.example.flipper;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SubBoard {

    private GridPane board;
    private Button[][] cells;
    private char[][] state;
    private int row, col;
    private MainBoard mainBoard;
    private boolean gameOver;

    public SubBoard(int row, int col, MainBoard mainBoard) {
        this.row = row;
        this.col = col;
        this.mainBoard = mainBoard;
        this.board = new GridPane();
        this.cells = new Button[3][3];
        this.state = new char[3][3];
        this.gameOver = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = ' ';
                cells[i][j] = new Button();
                cells[i][j].setMinSize(60, 60);
                int finalI = i;
                int finalJ = j;
                cells[i][j].setOnAction(e -> handleMove(finalI, finalJ));
                board.add(cells[i][j], j, i);
            }
        }
    }

    public GridPane getBoard() {
        return board;
    }

    private void handleMove(int i, int j) {
        if (gameOver || state[i][j] != ' ') {
            return;
        }

        char player = mainBoard.getCurrentPlayer();
        cells[i][j].setText(String.valueOf(player));
        state[i][j] = player;

        if (checkWin(player)) {
            mainBoard.updateMainBoard(row, col, player);
            gameOver = true;
        } else if (checkDraw()) {
            mainBoard.updateMainBoard(row, col, ' ');
            gameOver = true;
        }

        mainBoard.switchPlayer();
    }

    private boolean checkWin(char player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (state[i][0] == player && state[i][1] == player && state[i][2] == player) return true;
            if (state[0][i] == player && state[1][i] == player && state[2][i] == player) return true;
        }
        if (state[0][0] == player && state[1][1] == player && state[2][2] == player) return true;
        if (state[0][2] == player && state[1][1] == player && state[2][0] == player) return true;
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == ' ') return false;
            }
        }
        return true;
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = ' ';
                cells[i][j].setText("");
            }
        }
        gameOver = false;
    }
}
