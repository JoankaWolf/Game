package com.kodilla.game;
import java.util.Arrays;

public class Position {
    private int place;
    private String[] position;

    public Position(int place, String[] position) {
        this.place = place;
        this.position = position;
    }

    public int getPlace() {
        return place;
    }

    public String[] getPosition() {
        return position;
    }


    @Override
    public String toString() {
        return "Position{" +
                "place=" + place +
                ", position=" + Arrays.toString(position) +
                '}';
    }
}
