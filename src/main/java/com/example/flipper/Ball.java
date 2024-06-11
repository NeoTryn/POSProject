package com.example.flipper;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Bounds;

public class Ball {
    private Circle circle;
    private double velocityX;
    private double velocityY;
    private double paddleLastDirection; // 0 for no movement, -1 for left, 1 for right

    public Ball(double centerX, double centerY, double radius) {
        circle = new Circle(centerX, centerY, radius, Color.RED);
        velocityX = 1; // No initial horizontal velocity
        velocityY = -3; // Initial velocity directed upwards
        paddleLastDirection = velocityX; // No initial movement
    }

    public Circle getCircle() {
        return circle;
    }

    public void update() {
        circle.setCenterX(circle.getCenterX() + velocityX);
        circle.setCenterY(circle.getCenterY() + velocityY);

        // Handle collision with the walls
        if (circle.getCenterX() - circle.getRadius() <= 10 || circle.getCenterX() + circle.getRadius() >= 790) {
            velocityX *= -1;
        }
        if (circle.getCenterY() - circle.getRadius() <= 0) {
            velocityY *= -1;
        }
    }

    public void checkCollision(Paddle paddle) {
        Bounds bounds = paddle.getRectangle().getBoundsInParent();
        if (circle.getBoundsInParent().intersects(bounds)) {
            // Determine the direction of the paddle movement
            double currentPaddleDirection = paddle.getVelocity();

            // Update the ball's horizontal velocity based on the paddle's direction
            if (currentPaddleDirection != 0) {
                paddleLastDirection = currentPaddleDirection;
                velocityX = currentPaddleDirection ;
            } else {
                velocityX = velocityX;
            }

            if(velocityX > 100){
                velocityX = 100;
            }
            if(velocityX < -100){
                velocityX = -100;
            }

            // Reverse the vertical velocity
            velocityY *= -1;
        }
    }

    public boolean checkCollision(Brick brick) {
        Bounds bounds = brick.getRectangle().getBoundsInParent();
        if (circle.getBoundsInParent().intersects(bounds)) {
            double overlapLeft = circle.getCenterX() - bounds.getMinX();
            double overlapRight = bounds.getMaxX() - circle.getCenterX();
            double overlapTop = circle.getCenterY() - bounds.getMinY();
            double overlapBottom = bounds.getMaxY() - circle.getCenterY();

            boolean ballFromLeft = overlapLeft < overlapRight;
            boolean ballFromTop = overlapTop < overlapBottom;

            if (Math.min(overlapLeft, overlapRight) < Math.min(overlapTop, overlapBottom)) {
                // Collision was horizontal
                velocityX *= -1;
            } else {
                // Collision was vertical
                velocityY *= -1;
            }
            return true;
        }
        return false;
    }

}