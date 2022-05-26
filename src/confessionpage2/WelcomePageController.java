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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ngsua
 */
public class WelcomePageController {
    
    @FXML
    private Button getStartedButton;
    @FXML
    private Button adminLoginButton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void adminLogin(ActionEvent event) throws IOException{ //a method to go to adminPage.fxml
        root = FXMLLoader.load(getClass().getResource("adminLoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void userLogin(ActionEvent event) throws IOException{ //a method to go to displayConfessionPage.fxml
        root = FXMLLoader.load(getClass().getResource("displayConfessionPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
    
    
}
