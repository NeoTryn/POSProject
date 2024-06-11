package com.example.flipper;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick {
    private Rectangle rectangle;

    public Brick(double x, double y, double width, double height) {
        rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(Color.GREEN);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
