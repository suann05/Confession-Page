/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package confessionpage2;

import static confessionpage2.database.confConf;
import static confessionpage2.database.confDate;
import static confessionpage2.database.confID;
import static confessionpage2.database.confReplyID;
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
import java.io.FileWriter;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
 
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
    @FXML
    private TextArea imageFilePath;
    @FXML
    private ImageView imageView;
    @FXML
    private Button browseButton;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    Date date = new Date();  
    DateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
    String strDate = formatter.format(date);
    
    //JSONArray jrr = new JSONArray();
    
    Queue<String> confID = new Queue<>();
    Queue<String> confConf = new Queue<>();
    Queue<String> confReplyID = new Queue<>();
    Queue<String> confDate = new Queue<>();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    //String word = "fuck";
    
    public void submitConfession(ActionEvent event) throws SQLException, IOException{ //a method to store the confession to the database
        Random rand = new Random();
        int num = rand.nextInt(50000);
        String id = "UM"+String.valueOf(num);
        
        confID.enqueue(id);
        confConf.enqueue(confessionTextField.getText());
        confReplyID.enqueue(idTextField.getText());
        confDate.enqueue(strDate);
        
        if(idTextField.getText().isEmpty()==true&&confessionTextField.getText().isEmpty()==false){ //no reply id and image
            database.insertConfession(event, confID.getElement(), confConf.getElement(),confDate.getElement());
        }else if(idTextField.getText().isEmpty()==true&&confessionTextField.getText().isEmpty()==false&&imageFilePath.getText().isEmpty()==false){ //no reply id but with image
            database.insertConfessionWithImage(event, confID.getElement(), confConf.getElement(),confDate.getElement());
        }else if(idTextField.getText().isEmpty()==false&&confessionTextField.getText().isEmpty()==false&&imageFilePath.getText().isEmpty()==false){ //with replyid and image
            database.insertPendingConfWithImage(event, confID.getElement(), confConf.getElement(), confReplyID.getElement(), confDate.getElement());
        }else if(idTextField.getText().isEmpty()==false&&confessionTextField.getText().isEmpty()==false&&imageFilePath.getText().isEmpty()==true){ //with reply id but no image
            database.insertPendingConf(event, confID.getElement(), confConf.getElement(), confReplyID.getElement(), confDate.getElement());
        }else if(confessionTextField.getText().isEmpty()==true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter your confession.");
            alert.show();
        }
        database.timeScheduling();
         
    }
    
    public void browseFiles(ActionEvent event){
        database.fileBrowser(event);
        if(database.file!=null){
            imageFilePath.setText(database.file.getAbsolutePath());
            Image image = new Image(database.file.toURI().toString(),1080,1080,true,true);
            imageView.setImage(image);
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
