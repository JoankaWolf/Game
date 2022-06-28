package com.kodilla.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;


    @FXML
    private Text menuText;
    @FXML
    private Text endText;

    ArrayList<Button> buttons;

    ComputerMoves randomComputerMoves = new ComputerMoves();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));

        buttons.forEach(button -> {
            buttonOrganization(button);
            button.setFocusTraversable(false);
        });
    }

    @FXML
    public void restartGame(ActionEvent actionEvent) {
        buttons.forEach(this::resetButton);
        menuText.setText("Tic Tac Toe");
        endText.setText("");
    }

    public void resetButton(Button button) {
        button.setDisable(false);
        button.setText("");
    }
    public void pickButton(int index){
        buttons.get(index).setText("O");
        buttons.get(index).setDisable(true);
    }

       public void makeComputerMoves(){
        int move = randomComputerMoves.computerDecision(getBoardPosition());
        pickButton(move);
    }

    public Position getBoardPosition(){
        String[] board = new String[9];
        for (int a = 0; a < buttons.size(); a++){
            board[a] = buttons.get(a).getText();
        }
        return new Position(0,board);

    }

    private void buttonOrganization(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            button.setDisable(true);
            button.setText("X");
            makeComputerMoves();
            logicOfTheGame();
        });
    }

    private void noSymbol(Button button) {
        button.setDisable(true);
        endText.setText("THE END");
    }

    public void logicOfTheGame() {
        String line;
        for (int a = 0; a < 8; a++) {

            switch (a) {
                case 0:
                    line = button1.getText() + button2.getText() + button3.getText();
                    break;
                case 1:
                    line = button4.getText() + button5.getText() + button6.getText();
                    break;
                case 2:
                    line = button7.getText() + button8.getText() + button9.getText();
                    break;
                case 3:
                    line = button1.getText() + button5.getText() + button9.getText();
                    break;
                case 4:
                    line = button3.getText() + button5.getText() + button7.getText();
                    break;
                case 5:
                    line = button1.getText() + button4.getText() + button7.getText();
                    break;
                case 6:
                    line = button2.getText() + button5.getText() + button8.getText();
                    break;
                case 7:
                    line = button3.getText() + button6.getText() + button9.getText();
                    break;
                default:
                    line = null;
                    break;
            }
            if (line.equals("XXX")) {
                menuText.setText("YOU ARE WINNER!");
                buttons.forEach(this::noSymbol);
            } else if (line.equals("OOO")) {
                menuText.setText("Computer Won!");
                buttons.forEach(this::noSymbol);
            }
        }
    }
}

