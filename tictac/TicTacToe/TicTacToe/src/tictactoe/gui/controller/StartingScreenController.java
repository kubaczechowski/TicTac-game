package tictactoe.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartingScreenController {
    @FXML
    private Label lblPlayer;
   public static int chosenMode =0; // it must be static to be changed within method below
    // gets value 1 for single Player
    //gets value two for multi Player

    public int chosenMode(){ return chosenMode;}

    /**
     * method is called when the user pressed button called sinlge Player
     * @param event
     */

    @FXML
    private void SinglePlayer(ActionEvent event) throws IOException { // we need to throw the exception
        chosenMode=2; // thats the main objective of this method

       loadNewScreen();


    }
    @FXML
    private void MultiPlayer(ActionEvent event) throws IOException{ chosenMode=1; loadNewScreen(); // i changed the modes on revesre
    }

    private void loadNewScreen() throws IOException
    {
        // we need this to send data between windows
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tictactoe/gui/views/TicTacView.fxml"));
        //we need to copy from the other window and just initialize a new stage
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Tic tac toe");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
