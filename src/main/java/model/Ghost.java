package model;

import javafx.scene.image.ImageView;

public class Ghost {
    private ImageView imageView;
    private double xCoordinate;
    private double baseXCoordinate;
    private double yCoordinate;
    private double baseYCoordinate;
    private int xIndex;
    private int yIndex;

    public Ghost(ImageView imageView, double xCoordinate, double yCoordinate, int xIndex, int yIndex) {
        this.imageView = imageView;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.baseXCoordinate = xCoordinate;
        this.baseYCoordinate = yCoordinate;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getxIndex() {
        return xIndex;
    }

    public double getBaseXCoordinate() {
        return baseXCoordinate;
    }

    public double getBaseYCoordinate() {
        return baseYCoordinate;
    }

    public void setxIndex(int xIndex) {
        this.xIndex = xIndex;
    }

    public int getyIndex() {
        return yIndex;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setyIndex(int yIndex) {
        this.yIndex = yIndex;
    }
}
