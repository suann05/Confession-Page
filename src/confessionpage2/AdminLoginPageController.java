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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    @FXML
    private Button backButton;
    
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void loginAdmin(ActionEvent event) throws SQLException, IOException{
        
        if(usernameTextField.getText().isEmpty()==false && passwordField.getText().isEmpty()==false){ //if admin entered both username and password
              
                    database.loginAdmin(event, usernameTextField.getText(), passwordField.getText()); //connect to database to check whether admin entered correct username or password
                   
            }else{ //if admin didnt entered either username or password or both
                
                Alert alert = new Alert(Alert.AlertType.ERROR); //pop out the alert
                alert.setContentText("Please enter your email and password");
                alert.show();
            }
        
    }
    
    public void backToWelPage(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("welcomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
