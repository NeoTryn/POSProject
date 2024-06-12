package com.example.flipper;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BrickBreakerGame extends Application {
    private Ball ball;
    private Paddle paddle;
    private Brick[][] bricks;
    private Rectangle bottom;
    private boolean gameOver;
    private int score;
    private Text scoreText;
    private Set<KeyCode> keysPressed;
    private SoundPlayer hitSoundPlayer;
    private BackgroundMusicPlayer backgroundMusicPlayer;

    private static final int BRICK_ROWS = 5;
    private static final int BRICK_COLUMNS = 10;
    private static final String HIT_SOUND_FILE_PATH = "music/bara.wav";
    private static final String BACKGROUND_MUSIC_FILE_PATH = "music/yanomeno.wav";
    private static final float VOLUME = -10.0f;

    // Adjust the volume here (in decibels)

    public startScreen StartScreen;
    public MainBoard mainBoard;

    //public boolean isRunning = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);
        StartScreen = new startScreen();
        mainBoard = new MainBoard();

        Scene ticScene = new Scene(mainBoard.getBoard(), 600, 600);

        // Load the CSS file
        try {
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Could not load CSS file. Make sure the file is in the correct directory.");
            e.printStackTrace();
        }

        primaryStage.setTitle("JavaFX Brick Breaker Game");
        primaryStage.setScene(StartScreen.scene);

        StartScreen.getStartBrickBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(scene);
                initializeGame(root);
                hitSoundPlayer = new SoundPlayer(HIT_SOUND_FILE_PATH, VOLUME);
                backgroundMusicPlayer = new BackgroundMusicPlayer(BACKGROUND_MUSIC_FILE_PATH, VOLUME);
                backgroundMusicPlayer.playLooping();

                keysPressed = new HashSet<>();

                // Animation timer to update the game state
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        if (!gameOver) {
                            updateGame(root);
                        }
                    }
                };
                timer.start();

                // Handle keyboard input for paddle control
                scene.setOnKeyPressed(event -> keysPressed.add(event.getCode()));
                scene.setOnKeyReleased(event -> keysPressed.remove(event.getCode()));
            }
        });

        StartScreen.getStartTicButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(ticScene);
            }
        });

        primaryStage.show();
    }



    private void initializeGame(Pane root) {
        root.getChildren().clear();

        score = 0;
        scoreText = new Text("Score: " + score);
        scoreText.setFont(Font.font(20));
        scoreText.setLayoutX(10);
        scoreText.setLayoutY(20);
        root.getChildren().add(scoreText);

        // Initialize the ball, paddle, and bottom
        ball = new Ball(400, 300, 8);
        paddle = new Paddle(350, 550, 100, 10);
        bottom = new Rectangle(0, 600, 800, 1); // bottom border
        bottom.setFill(Color.TRANSPARENT);

        // Initialize the bricks
        bricks = new Brick[BRICK_ROWS][BRICK_COLUMNS];
        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICK_COLUMNS; col++) {
                bricks[row][col] = new Brick(80 * col + 10, 30 * row + 30, 70, 20);
                root.getChildren().add(bricks[row][col].getRectangle());
            }
        }

        // Add ball, paddle, and bottom to the scene
        root.getChildren().addAll(ball.getCircle(), paddle.getRectangle(), bottom);

        gameOver = false;
    }

    private void updateGame(Pane root) {
        ball.update();
        paddle.update();
        ball.checkCollision(paddle);

        boolean hitBrick = false;
        for (int row = 0; row < BRICK_ROWS; row++) {
            for (int col = 0; col < BRICK_COLUMNS; col++) {
                if (bricks[row][col] != null && ball.checkCollision(bricks[row][col])) {
                    root.getChildren().remove(bricks[row][col].getRectangle());
                    bricks[row][col] = null;
                    score++;
                    scoreText.setText("Score: " + score);
                    hitBrick = true;
                }
            }
        }

        if (hitBrick) {
            backgroundMusicPlayer.pause();
            hitSoundPlayer.play();
        } else {
            backgroundMusicPlayer.resume();
        }

        if (ball.getCircle().getCenterY() + ball.getCircle().getRadius() >= bottom.getY() || allBricksBroken()) {
            gameOver = true;
            endGame(root);
        }

        // Update paddle movement based on keys pressed
        if (keysPressed.contains(KeyCode.LEFT)) {
            paddle.moveLeft();
        } else if (keysPressed.contains(KeyCode.RIGHT)) {
            paddle.moveRight();
        } else {
            paddle.stop();
        }
    }

    private void endGame(Pane root) {
        Text gameOverText = new Text("Game Over!");
        gameOverText.getStyleClass().add("game-over-text");
        gameOverText.setFont(Font.font(100));
        gameOverText.setLayoutX(150);
        gameOverText.setLayoutY(250);
        root.getChildren().add(gameOverText);

        Button restartButton = new Button("Restart");
        restartButton.setLayoutX(350);
        restartButton.setLayoutY(350);
        restartButton.setOnAction(e -> initializeGame(root));
        restartButton.getStyleClass().add("restart-button"); // Apply the CSS class
        root.getChildren().add(restartButton);

        ball.getCircle().setVisible(false); // hide the ball
        backgroundMusicPlayer.stop(); // stop the background music when game ends
    }

    private boolean allBricksBroken() {
        for (Brick[] brickRow : bricks) {
            for (Brick brick : brickRow) {
                if (brick != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
