package com.kodilla.game;

import java.util.ArrayList;

public class ComputerMoves {


    public int computerDecision(Position position) {

        ArrayList<Position> computerMoves = nextMove(position);
        ArrayList<Integer> listOfMoves = new ArrayList<>();

        for (Position positions : computerMoves) {
            listOfMoves.add(minValue(position));
        }
        int max = listOfMoves.get(0);
        int bestIndex = 0;

        for (int a = 1; a < listOfMoves.size(); a++) {
            if (listOfMoves.get(a) > max) {
                max = listOfMoves.get(a);
                bestIndex = a;
            }
        }
        System.out.println(computerMoves);
        System.out.println(listOfMoves);

        int action = computerMoves.get(bestIndex).getPlace();
        System.out.println("Action: " + action);
        return action;
    }
    private void maxValue(Position position){
        int v = -Integer.MAX_VALUE;

        for (Position possibleMove: nextMove(position)) {
            v = Math.max(v, minValue(possibleMove));
        }
    }


    private int minValue(Position position){
        int v = Integer.MIN_VALUE;
        for (Position possibleMove: nextMove(position)) {

            maxValue(possibleMove);
        }
        return v;
    }

    private ArrayList<Position> nextMove(Position position) {
        ArrayList<Position> movesPossible = new ArrayList<>();
        int xMoves = 0;
        int yMoves = 0;
        String player;

        for (String s : position.getPosition()) {
            if (s.equals("X")) {
                xMoves++;
            }
        }
        if (xMoves <= yMoves) {
            player = "X";
        } else {
            player = "O";
        }
        for (int a = 0; a < 9; a++) {
            String[] newPosition = position.getPosition().clone();

            if (!newPosition[a].equals("X") && !newPosition[a].equals("O")) {
                newPosition[a] = player;
                movesPossible.add(new Position(a, newPosition));
            }
        }
        return movesPossible;
    }
}
