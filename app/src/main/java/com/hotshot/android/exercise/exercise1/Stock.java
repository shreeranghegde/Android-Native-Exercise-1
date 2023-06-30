package com.hotshot.android.exercise.exercise1;

public class Stock {
    String name;
    String type;

    public Stock(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override public String toString() {
        return "Stock{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
