package com.kodilla.game;

public class Player {

    String player;
    char symbol;
    int wonRounds;

    public Player(String player, char symbol, int wonRounds) {
        this.symbol = symbol;
        this.player = player;
        this.wonRounds = wonRounds;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getWonRounds() {
        return wonRounds;
    }

    public void setWonRounds(int wonRounds) {
        this.wonRounds = wonRounds;
    }

    @Override
    public String toString() {
        return "Player{" +
                "player='" + player + '\'' +
                ", symbol=" + symbol +
                ", wonRounds=" + wonRounds +
                '}';
    }
}
