/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package confessionpage2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ngsua
 */
public class AdminLoginPageController{

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void loginAdmin(ActionEvent event) throws SQLException, IOException{
        
        if(usernameTextField.getText().isEmpty()==false && passwordField.getText().isEmpty()==false){
              
                    database.loginAdmin(event, usernameTextField.getText(), passwordField.getText());
                   
            }else{
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter your email and password");
                alert.show();
            }
        
    }
    
}
