package tictactoe.bll;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;
import tictactoe.gui.controller.StartingScreenController;

import java.util.ArrayList;
import java.util.Random;

public class GameBoard implements IGameModel {
    public int[][] board = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    // each smaller array is a row

    public int player = 1; // this same for AI

    public GameBoard() {
    }

    public void setPlayer(int id) {
        player = id;
    }

    public int currentPlayerInfo(){
        return player;
    }



    public int getNextPlayer() {
        if (player == 1 ) return 2;
        return 1;
    }

    /**
     * Attempts to let the current player play at the given coordinates. It the
     * attempt is successful the current player has ended his turn and it is the
     * next players turn.
     *
     * @param col column to place a marker in.
     * @param row row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean play(int col, int row) {
        if (board[row][col] != 0) {
            return false;
        }
        if (board[row][col] == 0) {
            board[row][col] = player; // for AI its 2. it works in this same way
            // as it works for multiplayer
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        if(checkCol(0) || checkCol(1) || checkCol(2) || checkRow(0) ||checkRow(1) ||checkRow(2) || checkDiagonal()) return true;
        if(checkDraw()) return true;
        return false;
    }

    /**
     * Gets the id of the winner, -1 if its a draw.
     *
     * @return int id of winner, or -1 if draw.
     */
    public int getWinner() {
        if(checkCol(0) || checkCol(1) || checkCol(2) || checkRow(0) ||checkRow(1) ||checkRow(2) || checkDiagonal()) return player;
        return -1;
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame() {
        StartingScreenController startingScreen = new StartingScreenController();
       if(startingScreen.chosenMode()==1) setPlayer(getNextPlayer());
        initBoard();
    }

    private void initBoard() {
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 2; c++) {
                board[r][c] = 0;
            }
        }
    }

    private boolean checkRow(int row){
        return board[row][1] != 0 && board[row][1] == board[row][0] && board[row][1] == board[row][2];
    }

    private boolean checkCol(int col){
        return board[1][col] != 0 && board[1][col] == board[0][col] && board[1][col] == board[2][col];
    }
    private boolean checkDiagonal(){
        return board[1][1] != 0
                && (board[1][1] == board[0][0] && board[1][1] == board[2][2]
                || board[1][1] == board[0][2] && board[1][1] == board[2][0]);
    }

    private int checkEmptySpots() {
        int empty = 0;
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 2; c++) {
                if (board[r][c] == 0) empty++;
            }
        }
        return empty;
    }
    private boolean checkDraw(){
        if(checkEmptySpots() == 2 || checkEmptySpots() == 1){

            for (int r = 0; r <= 2; r++) {
                for (int c = 0; c <= 2; c++) {

                    if (board[r][c] == 0){
                        return !checkPotentialWin(r,c);
                    }
                }
            }
        }
        if (checkEmptySpots() == 0) return true;
        return false;
    }

    private boolean checkPotentialWin(int row,int col) {
        if(checkPotentialRow(row) || checkPotentialCol(col) || checkPotentialDiagonal()) return true;
        return false;
    }
    private boolean checkPotentialRow(int row){
        return board[row][1] == board[row][0] && board[row][2] == 0
                || board[row][1] == board[row][2] && board[row][0] == 0
                || board[row][0] == board[row][2] && board[row][1] == 0;
    }
    private boolean checkPotentialCol(int col){
        return board[1][col] == board[0][col] && board[2][col] == 0
                || board[1][col] == board[2][col] && board[0][col] == 0
                || board[0][col] == board[2][col] && board[1][col] == 0;
    }
    private boolean checkPotentialDiagonal(){
        return board[1][1] == board[0][0] && board[2][2] == 0
                || board[2][2] == board[1][1] && board[0][0] == 0
                || board[2][0] == board[1][1] && board[0][2] == 0
                || board[0][2] == board[1][1] && board[2][0] == 0
                || (board[0][0] == board[2][2] || board[0][2] == board[2][0]) && board[1][1] == 0;
    }
}
