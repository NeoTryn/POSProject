package com.example.flipper;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle {
    private Rectangle rectangle;
    private double velocity;
    private static final double SPEED = 5;

    public Paddle(double x, double y, double width, double height) {
        rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.BLUE);
        velocity = 0;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void moveLeft() {
        velocity = -SPEED;
    }

    public void moveRight() {
        velocity = SPEED;
    }

    public void stop() {
        velocity = 0;
    }

    public void update() {
        rectangle.setX(rectangle.getX() + velocity);
        if (rectangle.getX() < 0) {
            rectangle.setX(0);
        } else if (rectangle.getX() + rectangle.getWidth() > 800) {
            rectangle.setX(800 - rectangle.getWidth());
        }
    }

    public double getVelocity() {
        return velocity;
    }
}
