package com.kodilla.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Button button3;
    @FXML private Button button4;
    @FXML private Button button5;
    @FXML private Button button6;
    @FXML private Button button7;
    @FXML private Button button8;
    @FXML private Button button9;
    @FXML private Button newGameButton;
    @FXML private Button nextRound;
    @FXML private Button startPlayGame;
    @FXML private Label statisticLabel;
    @FXML private Text endText;
    @FXML private TextField playerOneTF;
    @FXML private TextField playerTwoTF;
    @FXML private Text winnerText;
    @FXML private CheckBox isComputerCheckBox;
    @FXML private ChoiceBox<Integer> choiceLevelBox;
    @FXML private Label numberOfRoundsLabel;
    private final Integer[] roundsChoiceLevel = {1,2,3,4,5};

    List<List<Character>> board = Arrays.asList(Arrays.asList(' ', ' ', ' '), Arrays.asList(' ', ' ',' '), Arrays.asList(' ', ' ', ' '));

    ArrayList<Button> buttons;
    private Game game;
    public Player currentPlayer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));

        buttons.forEach(button -> {
            button.setDisable(true);
            buttonOrganization(button);
            button.setFocusTraversable(false);
        });

        choiceLevelBox.getItems().addAll(roundsChoiceLevel);
        choiceLevelBox.setOnAction(this::setChoiceLevelBox);

        newGameButton.setDisable(true);
        nextRound.setDisable(true);

    }

    public void setNextRound(ActionEvent actionEvent){
        try {
            buttons.forEach(this::nextRound);
        }
        catch (Exception exception) {
            setAlert();
        }
    }

    @FXML
    public void restartGame(ActionEvent actionEvent) {
        buttons.forEach(this::nextRound);
        numberOfRoundsLabel.setText("");
        playerTwoTF.clear();
        playerOneTF.clear();
        choiceLevelBox.setDisable(false);
        playerOneTF.setEditable(true);
        playerTwoTF.setEditable(true);
        statisticLabel.setText("");
    }

    public void setChoiceLevelBox(ActionEvent actionEvent){
        Integer numberOfRoundsSet = (choiceLevelBox.getValue() + 2);
        String numberOfRoundsAsString = numberOfRoundsSet.toString();
        numberOfRoundsLabel.setText(numberOfRoundsAsString);
    }


    public void nextRound(Button button) {
            game.cleanBoard(game);
            button.setDisable(false);
            button.setText("");
            winnerText.setText("");
            endText.setText("");
    }

    private void buttonOrganization(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            try {

                button.setDisable(true);
                button.setText(currentPlayer.symbol + "");

                if (button.getId().equals("button1")) {
                    makeMove(game, currentPlayer, 0, 0);
                } else if (button.getId().equals("button2")) {
                    makeMove(game, currentPlayer, 0, 1);
                } else if (button.getId().equals("button3")) {
                    makeMove(game, currentPlayer, 0, 2);
                } else if (button.getId().equals("button4")) {
                    makeMove(game, currentPlayer, 1, 0);
                } else if (button.getId().equals("button5")) {
                    makeMove(game, currentPlayer, 1, 1);
                } else if (button.getId().equals("button6")) {
                    makeMove(game, currentPlayer, 1, 2);
                } else if (button.getId().equals("button7")) {
                    makeMove(game, currentPlayer, 2, 0);
                } else if (button.getId().equals("button8")) {
                    makeMove(game, currentPlayer, 2, 1);
                } else if (button.getId().equals("button9")) {
                    makeMove(game, currentPlayer, 2, 2);
                }
            }
            catch (Exception exception){
                setAlert();
            }
        });
    }

    public void fakeClickingOnButton(String buttonName) {
        buttons.forEach(button -> {
            if (button.getId().equals(buttonName)) {
                button.setDisable(true);
                button.setText('O' + "");
            }
        });
    }

    public void buttonOrganizationComputerPlay(List<Integer> points){
        int x = points.get(0);
        int y = points.get(1);

        if (x == 0 && y == 0) {
            fakeClickingOnButton("button1");
        } else if (x == 0 && y == 1) {
            fakeClickingOnButton("button2");
        } else if (x == 0 && y == 2) {
            fakeClickingOnButton("button3");
        } else if (x == 1 && y == 0) {
            fakeClickingOnButton("button4");
        } else if (x == 1 && y == 1) {
            fakeClickingOnButton("button5");
        } else if (x == 1 && y == 2) {
            fakeClickingOnButton("button6");
        } else if (x == 2 && y == 0) {
            fakeClickingOnButton("button7");
        } else if (x == 2 && y == 1) {
            fakeClickingOnButton("button8");
        } else if (x == 2 && y == 2) {
            fakeClickingOnButton("button9");
        }
    }

    public void makeMove(Game game, Player player, int x, int y){
        char requestedMove = game.board.get(x).get(y);

        if(requestedMove == ' ') {
            game.board.get(x).set(y,player.getSymbol());
            Player possibleWinner = game.checkIfThereIsAWinnerForRound(game);

            if (possibleWinner != null){
                buttons.forEach(button -> button.setDisable(true));
                endOfTheRound(possibleWinner);
                showStatistics();

            } else {
                if(game.isBoardPlayable(game)){

                    if(currentPlayer.equals(game.players.get(0))) {
                        currentPlayer = game.players.get(1);
                         if (isComputerCheckBox.isSelected()) {
                         computerMove(game);
                         }

                    } else {
                        currentPlayer = game.players.get(0);
                    }
                }
                else {

                    endText.setText("");
                    endOfTheRound(null);
                    game.cleanBoard(game);

                    showStatistics();
                }
            }
        }
    }

    public void computerMove(Game game) {
        List<Integer> randomPoint;
        int x;
        int y;
        char requestedMove;

        do {
            randomPoint = getRandomPoint();
            x = randomPoint.get(0);
            y = randomPoint.get(1);
            requestedMove = game.board.get(x).get(y);
        } while (requestedMove != ' ');

        makeMove(game, currentPlayer, x, y);
        buttonOrganizationComputerPlay(randomPoint);

    }

    public List<Integer> getRandomPoint(){

        Random random = new Random();
        int x = random.nextInt(3);
        int y = random.nextInt(3);

        return Arrays.asList(x,y);

    }

    public void startPlayGame(ActionEvent actionEvent) {

       try {
            buttons.forEach(button -> button.setDisable(false));
            nextRound.setDisable(false);

            int rounds = Integer.parseInt(numberOfRoundsLabel.getText());
            Player playerOne = new Player(playerOneTF.getText(), 'X', 0);
            Player playerTwo = new Player(playerTwoTF.getText(), 'O', 0);
            game = new Game(Arrays.asList(playerOne, playerTwo), rounds, 0, board);

            currentPlayer = playerOne;

            choiceLevelBox.setDisable(true);
            playerOneTF.setEditable(false);
            playerTwoTF.setEditable(false);
        }
       catch (Exception exception){
               setAlert();
       }
    }

    public void showStatistics(){

        String statisticsText = "Rounds won: \n" +
                "Player 1: " + game.players.get(0).wonRounds + "\n" +
                "Player 2: " + game.players.get(1).wonRounds + "\n" +
                "\n" +
                game.players.get(0).player + "\n" + " Your wins to go: "
                + (game.numbersOfRoundsToWin - game.players.get(0).wonRounds) +
                "\n" +
                game.players.get(1).player + "\n" + " Your wins to go: "
                + (game.numbersOfRoundsToWin - game.players.get(1).wonRounds);

        statisticLabel.setText(statisticsText);
    }

    public void endOfTheRound(Player winningPlayer){

        if (winningPlayer != null) {
            int numbersOfWins = winningPlayer.getWonRounds();
            winningPlayer.setWonRounds(numbersOfWins+1);
            endText.setText("The Winner of the round is: " + winningPlayer.player);


            if (winningPlayer.getWonRounds() >= game.numbersOfRoundsToWin){
                winnerText.setText("End Game, The Winner is: " + winningPlayer.player);
                nextRound.setDisable(true);
                newGameButton.setDisable(false);

            }
            else {
                winnerText.setText("Next Round");
                game.roundCounter++;
            }
        }
        else {
            endText.setText("There is no Winner!");
            game.roundCounter++;
        }
    }
    @FXML
    public Alert setAlert() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error - Chose Level");
        alert.setContentText("Choose Level and enter PLAY");
        Optional<ButtonType> result = alert.showAndWait();
        result.isEmpty();

        return alert;
    }
}

