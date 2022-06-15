/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package confessionpage2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ngsua
 */
public class AdminChoosePageController {
    
    @FXML
    private Button pendingButton;
    @FXML
    private Button postedButton;
    @FXML
    private Button logoutButton;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void pendingPage(ActionEvent event) throws IOException{ //a method to change the scene to displayPendingConf.fxml file
        root = FXMLLoader.load(getClass().getResource("displayPendingConf.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void postedPage(ActionEvent event) throws IOException{ //a method to change the scene to adminPage.fxml file
        root = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void logout(ActionEvent event) throws IOException{ //pop out an alert message when the logout button is clicked
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Are you sure you want to log out?");
        
        
        if(alert.showAndWait().get()==ButtonType.OK){ //change the scene to welcomePage.fxml file when the user click "ok"
          root = FXMLLoader.load(getClass().getResource("welcomePage.fxml"));
          stage = (Stage)((Node)event.getSource()).getScene().getWindow();
          scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
        }
    }
    
}
