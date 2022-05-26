/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package confessionpage2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Date;
/**
 * FXML Controller class
 *
 * @author ngsua
 */
public class UserConfessionPageController{

    @FXML
    private TextField confessionTextField;
    @FXML
    private TextField idTextField;
    @FXML
    private Button submitButton;
    @FXML
    private Button backButton;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    Date date = new Date();  
    DateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
    String strDate = formatter.format(date);  
    
    
    public void submitConfession(ActionEvent event) throws SQLException{ //a method to store the confession to the database
        Random rand = new Random();
        int num = rand.nextInt(50000);
        String id = "UM"+String.valueOf(num);
        if(confessionTextField.getText().isEmpty()==false && idTextField.getText().isEmpty()==true){
              
            database.insertConfession(event,id, confessionTextField.getText(),strDate);
                   
            }else if(confessionTextField.getText().isEmpty()==false && idTextField.getText().isEmpty()==false){
                
                database.insertConfessionWithReply(event, id, confessionTextField.getText(), idTextField.getText(), strDate);
                
            }else{
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter your confession");
                alert.show();
            }

    }
    
    public void confessionPage(ActionEvent event) throws IOException{ //a method to back to displayConfessionPage.fxml
        root = FXMLLoader.load(getClass().getResource("displayConfessionPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
