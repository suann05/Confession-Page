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
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileWriter;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import org.json.simple.parser.JSONParser;
 
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
        
        /*JSONObject objDetails = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONParser jp = new JSONParser();
        
        try{
            
            FileReader file = new FileReader("confession.json");
            jrr = (JSONArray)jp.parse(file);
            
        }catch(Exception e){
            System.out.println("error");
        }
        
        objDetails.put("id", id);
        objDetails.put("confession", confessionTextField.getText());
        objDetails.put("replyid", idTextField.getText());
        objDetails.put("date", strDate);
        obj.put("Confession", objDetails);
        jrr.add(objDetails);
        
        try{
            FileWriter file = new FileWriter("confession.json");
            file.write(jrr.toJSONString());
            file.flush();
            file.close();
        }catch(Exception e){
            System.out.println("Error");
        }        */
        
        
        //System.out.println(jrr);
        
        confID.enqueue(id);
        confConf.enqueue(confessionTextField.getText());
        confReplyID.enqueue(idTextField.getText());
        confDate.enqueue(strDate);
        
        if(idTextField.getText().isEmpty()==true&&confessionTextField.getText().isEmpty()==false)
            database.insertConfession(event, confID.getElement(), confConf.getElement(),confDate.getElement());
        else if(confessionTextField.getText().isEmpty()==true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter your confession.");
            alert.show();
        }else
            database.insertPendingConf(event, confID.getElement(), confConf.getElement(), confReplyID.getElement(), confDate.getElement());
        
        database.timeScheduling();
        
            
           
        
        /*confID.enqueue(id);
        confConf.enqueue(confessionTextField.getText());
        confReplyID.enqueue(idTextField.getText());
        confDate.enqueue(strDate);
        
        
        
        System.out.println(confID.toString());
        System.out.println(confConf.toString());
        System.out.println(confReplyID.toString());
        System.out.println(confDate.toString());
        
        
        database.insertPendingConf(event, confID.getElement(), confConf.getElement(), confReplyID.getElement(), confDate.getElement());*/
        
        
        
        /*Timer timer = new Timer();
        
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                
                /*try{
                 DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
                 Statement stmt = con.createStatement();
                 String query = "select count(*) from pendingconf";
                 ResultSet rs = stmt.executeQuery(query);
                 rs.next();
                 int count = rs.getInt(1);
                 System.out.println("Number of records in the cricketers_data table: "+count);
                 
                }catch(Exception e){
                    System.out.println("Error");
                }*/
                
                //System.out.println("hello world");
                
                
                
            //}
            
        //};
        
        //timer.schedule(task, 10);
        
        /*database.timeScheduling();
        System.out.println("hello");*/
        
        /*if(confessionTextField.getText().isEmpty()==false && idTextField.getText().isEmpty()==true){
              
            database.insertConfession(event,id, confessionTextField.getText(),strDate);
                   
            }else if(confessionTextField.getText().isEmpty()==false && idTextField.getText().isEmpty()==false){
                
                database.insertConfessionWithReply(event, id, confessionTextField.getText(), idTextField.getText(), strDate);
                
            }else{
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter your confession");
                alert.show();
            }*/
         
    }
    
    public void confessionPage(ActionEvent event) throws IOException{ //a method to back to displayConfessionPage.fxml
        root = FXMLLoader.load(getClass().getResource("displayConfessionPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
