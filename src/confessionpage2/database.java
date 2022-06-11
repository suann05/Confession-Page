/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package confessionpage2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ngsua
 */
public class database {
    
    static Stage stage;
    static Scene scene;
    static Parent root;
    
    static Queue<String> confID = new Queue<>();
    static Queue<String> confConf = new Queue<>();
    static Queue<String> confReplyID = new Queue<>();
    static Queue<String> confDate = new Queue<>();
    
    static File file;
    static FileChooser fileChooser;
    
    public static void insertConfession(ActionEvent event,String id,String confession,String date)throws SQLException, FileNotFoundException{ //a method to insert the confession from user into database
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement spamCheck = null;
        ResultSet resultSet = null;
        FileInputStream fs =null;
        
        try{
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
            spamCheck = connection.prepareStatement("SELECT * FROM pendingconf WHERE confession = ?");
            spamCheck.setString(1, confession);
            resultSet = spamCheck.executeQuery();
            
            
            if(resultSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please submit a different content");
                alert.show();
                
            }else{
                String word = "fuck";
                String[] array = confession.split(" ");
                for(int i=0;i<array.length;i++){
                if(array[i].equalsIgnoreCase(word)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please check your content.");
                        alert.show();
                }else{
                    
                    
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
                    preparedStatement = connection.prepareStatement("INSERT INTO confessionpage.pendingconf(idconfession,confession,replyid,date,image) VALUES (?,?,?,?,?)");
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, confession);
                    preparedStatement.setString(3,null);
                    preparedStatement.setString(4, date);
                    fs = new FileInputStream(file);
                    preparedStatement.setBinaryStream(5, fs, (int)file.length());
               
                    preparedStatement.executeUpdate();
                    
                    confID.enqueue(id);
                    confConf.enqueue(confession);
                    confReplyID.enqueue(null);
                    confDate.enqueue(date); 
                    
                    System.out.println(confID.toString());
                    System.out.println(confConf.toString());
                    System.out.println(confReplyID.toString());
                    System.out.println(confDate.toString());
                    
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Submitted at "+date+"\nConfession post ID : "+id+"\nYour confession will be published soon.");
                    alert.show(); 
                }
                }
                }
   
        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            
        }
    }
    
    public static Connection getConnect (){ //a method to get connection from database
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return  connection;
        }
    
    public static void changeScene(ActionEvent e,String username,String fxmlfile) throws IOException{ // change the scene to the other fxml file
        Parent root = null;
        
        if(username != null){
            try{
                FXMLLoader loader = new FXMLLoader(database.class.getResource(fxmlfile));
                root = loader.load();
            }catch(IOException event){
                event.printStackTrace();
            }
        }
        else{
            try{
                root = FXMLLoader.load(database.class.getResource(fxmlfile));
            }catch(IOException event){
                event.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,600,500));
        stage.show();   
        
    }
    
    public static void loginAdmin(ActionEvent event,String username,String password)throws SQLException, IOException{
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adminlogin", "root", "root"); //connect to your databases, javafx-loginsigup is my scheme name, root is my user and root is my password 
            preparedStatement = connection.prepareStatement("SELECT password FROM admin WHERE username = ?"); 
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            
            if(!(resultSet.isBeforeFirst())){
                System.out.println("User not found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            }
            else{
                while(resultSet.next()){
                    String retrivedPassword = resultSet.getString("password");
                    if(retrivedPassword.equals(password)){
                         changeScene(event,username,"adminChoosePage.fxml"); 
                        
                    }
                    else{
                        System.out.println("Password is incorrect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect");
                        alert.show();
                        
                    }
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            
        }
    }
    
    public static void insertPendingConf(ActionEvent event,String id,String confession,String replyid,String date)throws SQLException, FileNotFoundException{ //a method to insert the confession from user into database
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement spamCheck = null;
        PreparedStatement psCheckIDExists = null;
        ResultSet resultSet = null;
        FileInputStream fs =null;
        
        try{
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
            spamCheck = connection.prepareStatement("SELECT * FROM pendingconf WHERE confession = ?");
            spamCheck.setString(1, confession);
            resultSet = spamCheck.executeQuery();
            
            
            if(resultSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please submit a different content");
                alert.show();
                
            }else{
                String word = "fuck";
                String[] array = confession.split(" ");
                for(int i=0;i<array.length;i++){
                if(array[i].equalsIgnoreCase(word)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please check your content.");
                        alert.show();
                }else{
                    
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
                    psCheckIDExists = connection.prepareStatement("SELECT * FROM confession2 WHERE idconfession=?");
                    psCheckIDExists.setString(1, replyid);
                    resultSet = psCheckIDExists.executeQuery();
            
                    if(resultSet.isBeforeFirst()){
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
                    preparedStatement = connection.prepareStatement("INSERT INTO confessionpage.pendingconf(idconfession,confession,replyid,date,image) VALUES (?,?,?,?,?)");
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, confession);
                    preparedStatement.setString(3,replyid);
                    preparedStatement.setString(4, date);
                    fs = new FileInputStream(file);
                    preparedStatement.setBinaryStream(5, fs, (int)file.length());
               
                    preparedStatement.executeUpdate();
                    
                    confID.enqueue(id);
                    confConf.enqueue(confession);
                    confReplyID.enqueue(replyid);
                    confDate.enqueue(date); 
                    
                    System.out.println(confID.toString());
                    System.out.println(confConf.toString());
                    System.out.println(confReplyID.toString());
                    System.out.println(confDate.toString());
                    
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Submitted at "+date+"\nConfession post ID : "+id+"\nYour confession will be published soon.");
                    alert.show(); 
                }else{
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setContentText("Reply ID doesn't exists.");
                      alert.show();  
                    }
                }
                }

            
            
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            
        }
    }
    
    public static void timeScheduling() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
        Statement stmt = con.createStatement();
        String query = "select count(*) from pendingconf";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt(1);
        System.out.println("Number of records in the cricketers_data table: "+count);
        
        if(count<=5){
        
        Timer timer = new Timer();
        
        TimerTask task = new TimerTask(){

            @Override
            public void run() {
                
                    try {
                        insertConfession2();
                        removeConfession2();
                        System.out.println("done");
                    } catch (SQLException ex) {
                        Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("error");
                    }
                
            }
        
        };
        
        timer.schedule(task, 10000);
            
        }else if(count<=10){
            
            Timer timer = new Timer();
        
            TimerTask task = new TimerTask(){
                @Override
                public void run() {
                    try {
                        insertConfession2();
                        removeConfession2();
                        System.out.println("done");
                    } catch (SQLException ex) {
                        Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("error");
                    }
                }
                
            };
            
            timer.schedule(task, 600000);
            
        }else if(count>10){
            
            Timer timer = new Timer();
        
            TimerTask task = new TimerTask(){
                @Override
                public void run() {
                    try {
                        insertConfession2();
                        removeConfession2();
                        System.out.println("done");
                    } catch (SQLException ex) {
                        Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("error");
                    }
                }
                
            };
            
            timer.schedule(task, 300000);
            
        }else{
            System.out.println("oops");
        }
        
        
    }
    
    public static void insertConfession2()throws SQLException{ //a method to insert the confession from user into database
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
            preparedStatement = connection.prepareStatement("INSERT INTO confession2 (idconfession,confession,replyid,date,image) SELECT idconfession,confession,replyid,date,image FROM pendingconf");
               
            preparedStatement.executeUpdate();
               
            
        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            
        }
    }
    
    
        public static void removeConfession2()throws SQLException{ //a method to insert the confession from user into database
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
            preparedStatement = connection.prepareStatement("DELETE FROM pendingconf");
               
            preparedStatement.executeUpdate();
               
            
        }catch(SQLException e){
            e.printStackTrace();
        } finally{
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            
        }
    }
    
    public static void fileBrowser(ActionEvent event){
        fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(stage);
    }
    
    public static Image getImageById(ActionEvent event,String id) throws IOException { //for displaying image from confession2 database
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Image image = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root"); //connect to your databases, javafx-loginsigup is my scheme name, root is my user and root is my password 
            preparedStatement = connection.prepareStatement("SELECT image FROM confession2 WHERE idconfession = ?"); 
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                Blob blobImage = resultSet.getBlob("image");
                InputStream is = blobImage.getBinaryStream();
                image = new Image(is);
                is.close();
            }
            resultSet.close();
        
        }catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
    
    public static Image getImageById2(ActionEvent event,String id) throws IOException { //for displaying image from pendingconf database
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Image image = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root"); //connect to your databases, javafx-loginsigup is my scheme name, root is my user and root is my password 
            preparedStatement = connection.prepareStatement("SELECT image FROM pendingconf WHERE idconfession = ?"); 
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                Blob blobImage = resultSet.getBlob("image");
                InputStream is = blobImage.getBinaryStream();
                image = new Image(is);
                is.close();
            }
            resultSet.close();
        
        }catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
    
    
    
    
    
}    
    

