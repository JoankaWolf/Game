package com.kodilla.game;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Game {

    List<Player> players;
    int numbersOfRoundsToWin;
    int roundCounter;
    List<List<Character>> board;


    public Game(List<Player> players, int numbersOfMaxRounds, int roundCounter, List<List<Character>> board) {
        this.players = players;
        this.numbersOfRoundsToWin = numbersOfMaxRounds;
        this.roundCounter = roundCounter;
        this.board = board;
    }

    public boolean isBoardPlayable(Game game) {

        String rows = game.board.stream().map(l -> "" +l.get(0) + l.get(1) + l.get(2))
                .collect(Collectors.joining());
        if (rows.contains(" ")) {
            return true;
        } else {
            return false;
        }
    }

    public Player checkIfThereIsAWinnerForRound(Game game) {
        List<String> rows = game.board.stream().map(l -> "" +l.get(0) + l.get(1) + l.get(2)).collect(toList());

        List<String> columns = Stream.of(0,1,2)
                .map(i -> "" + game.board.get(0).get(i) + game.board.get(1).get(i) + game.board.get(2).get(i))
                .collect(toList());

        List<String> cross = Arrays.asList(
                "" + game.board.get(0).get(0) + game.board.get(1).get(1) + game.board.get(2).get(2),
                "" + game.board.get(0).get(2) + game.board.get(1).get(1) + game.board.get(2).get(0)
        );

        List<String> combined = Stream.of(rows,columns,cross).flatMap(Collection::stream).collect(toList());
        List<String> listOfPotentialWinners = combined
                .stream().filter(row -> row.equals("OOO") || row.equals("XXX")).collect(Collectors.toList());

        Player winner = null;
        if (listOfPotentialWinners.isEmpty()) {
            winner = null;
        }
        else {
            if (listOfPotentialWinners.get(0).equals("OOO")) {
                winner = game.players.get(1);
            }
            else if (listOfPotentialWinners.get(0).equals("XXX")){
                winner = game.players.get(0);
            }
        }
        return winner;
    }

    public void cleanBoard(Game game) {
        game.board.forEach(listCharacter -> Collections.fill(listCharacter, ' '));
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", numbersOfMaxRounds=" + numbersOfRoundsToWin +
                ", roundCounter=" + roundCounter +
                ", board=" + board +
                '}';
    }
}
