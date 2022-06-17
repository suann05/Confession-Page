/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package confessionpage2;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author ngsua
 */
public class ConfessionPage2 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
        Parent root = FXMLLoader.load(getClass().getResource("welcomePage.fxml")); //first scene is welcomePage.fxml file
        Scene scene = new Scene(root);
     
        primaryStage.setTitle("CONFESS TIME!"); //set the window title
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Image logo = new Image(getClass().getResourceAsStream("icon.png")); //set the window icon
        primaryStage.getIcons().add(logo);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(event -> { //call out the logout method
          event.consume();
          logout(primaryStage);
                    
        });
        
        }catch(IOException e){
        }
        
        
    }
    
    public void logout(Stage stage){ //pop out the alert when the "x" button on the window is clicked
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure you want to quit?");
        
        
        if(alert.showAndWait().get()==ButtonType.OK){
          stage.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
