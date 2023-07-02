package com.example.myride.models;

public class Car {
    private int carImage;
    private String carName;

    public Car(int carImage, String carName) {
        this.carImage = carImage;
        this.carName = carName;
    }

    public int getCarImage() {
        return carImage;
    }

    public void setCarImage(int carImage) {
        this.carImage = carImage;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carImage=" + carImage +
                ", carName='" + carName + '\'' +
                '}';
    }
}
