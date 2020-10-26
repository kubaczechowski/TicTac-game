/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.gui.controller;

import java.util.ArrayList;
import java.util.Random;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;
import tictactoe.gui.controller.StartingScreenController;

/**
 *
 * @author Stegger
 */
public class TicTacViewController implements Initializable
{

    @FXML
    private Label lblPlayer;

    @FXML
    private Button btn0;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;


    Image blankTileImg = new Image("tictactoe/gui/images/BlankTile.png");
    Background blankTile = new Background(new BackgroundFill(new ImagePattern(blankTileImg), CornerRadii.EMPTY, Insets.EMPTY));
    Image XTileImg = new Image("tictactoe/gui/images/XTile.png");
    Background XTile = new Background(new BackgroundFill(new ImagePattern(XTileImg), CornerRadii.EMPTY, Insets.EMPTY));
    Image OTileImg = new Image("tictactoe/gui/images/OTile.png");
    Background OTile = new Background(new BackgroundFill(new ImagePattern(OTileImg), CornerRadii.EMPTY, Insets.EMPTY));
   private ArrayList<Button> buttons = new ArrayList();
    private void addButtons(){
        buttons.add(btn0);
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8); // i think it can be written simplier
    }


    @FXML
    private Button btnNewGame;

    @FXML
    private GridPane gridPane;
    
    private static final String TXT_PLAYER = "Player: ";
    private IGameModel game;
    boolean stopGame =false; // if its true you cant add new signs
    // and then you have to refresh it when making a new game

    @FXML
    private void handleButtonAction(ActionEvent event)
    { StartingScreenController startingScreen = new StartingScreenController();

    if(startingScreen.chosenMode() == 1) {
        try {
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;
            int player = game.getNextPlayer();
            if (game.play(c, r) && !stopGame) {
                Button btn = (Button) event.getSource();
                if(player == 1) {
                    btn.setBackground(XTile);
                }
                if(player == 2) {
                    btn.setBackground(OTile);
                }
                if (game.isGameOver()) {
                    int winner = game.getWinner();
                    displayWinner(winner);
                    stopGame = true;
                } else {
                    game.setPlayer(game.getNextPlayer());
                    setPlayer(); // it shows a label with a current player
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }
        if(startingScreen.chosenMode==2)
        {
            try
            {
                Integer row = GridPane.getRowIndex((Node) event.getSource());
                Integer col = GridPane.getColumnIndex((Node) event.getSource());
                int r = (row == null) ? 0 : row;
                int c = (col == null) ? 0 : col;
                Button btn =(Button) event.getSource();
              // if(game.play(c,r) && !stopGame && game.currentPlayerInfo() == 1){
               if(game.play(c,r) && !stopGame ){
                   game.setPlayer(1);
                    //String xOrO =  "X";// computers is O;
                    //btn.setText(xOrO);
                   btn.setBackground(XTile);
                    if (game.isGameOver()) {
                        int winner = game.getWinner();
                        displayWinnerAI(winner); // other method for AI
                        stopGame = true;

                        // I don't know how to display info that
                        // computer won or that there is a draw
                    }
                    else{
                        //if(game.getNextPlayer() == 2)AImove();
                        //game.setPlayer(game.getNextPlayer());
                       // game.setPlayer(2);
                        AImove();
                    }
                }
//sejfs
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }
    private void AImove(){

        ArrayList<Button> availableMoves = new ArrayList();
       for(int index =0; index<9; index++) {
          if (!buttons.get(index).getBackground().equals(OTile) && !buttons.get(index).getBackground().equals(XTile))
           //if (buttons.get(index).getBackground()==null)
               availableMoves.add(buttons.get(index));

       }//availableMoves.get(availableMoves.size()-1).setBackground(OTile);
       Random random = new Random();
      int randomElement = random.nextInt(availableMoves.size()-1);

      availableMoves.get(randomElement).setBackground(OTile);

    }




    @FXML
    private void handleNewGame(ActionEvent event)
    {
        game.newGame();
        setPlayer();
        clearBoard();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        game = new GameBoard();
        addButtons();
        StartingScreenController startingScreen = new StartingScreenController();
        clearBoard();
        if(startingScreen.chosenMode==1)
            setPlayer();
        if(startingScreen.chosenMode==2)
        {lblPlayer.setText("Player vs AI");
        game.setPlayer(1); // user has to start the game
        }
        stopGame=false;
    }

    private void setPlayer()
    {
        lblPlayer.setText(TXT_PLAYER + game.getNextPlayer());
        if(game.getNextPlayer() == 2) {
            lblPlayer.setTextFill(Color.BLUE);
        }
        if(game.getNextPlayer() == 1) {
            lblPlayer.setTextFill(Color.RED);
        }
    }

    private void displayWinner(int winner)
    {
        String message = switch (winner) {
            case -1 -> "It's a draw :-(";
            default -> "Player " + winner + " wins!!!";
        };
        lblPlayer.setTextFill(Color.WHITE);
        lblPlayer.setText(message);
    }

    private void displayWinnerAI(int winner)
    {
        String message = switch (winner) {
            case -1 -> "It's a draw :-(";
            case 1 -> "You won!";
            case 2 -> "Computer won!";
            default -> "There is some error.";
        };
        lblPlayer.setText(message);
    }


    private void clearBoard()
    {
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setBackground(blankTile);
        }
        stopGame=false;

        GameBoard gameBoard = new GameBoard();
        gameBoard.player =1; // its needed for AI
    }

}
