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
import javafx.scene.input.MouseEvent;
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
    
    public static boolean checkBadWords(ActionEvent event,String confession){ //to check whether the confession submit by user include any inappropriate wordings
        String word = "fuck";
        String[] array = confession.split(" ");
        for(int i=0;i<array.length;i++){ //if rude words included, return true
            if(array[i].equalsIgnoreCase(word)){
                return true;
            }
        }
        return false; //if no, return false
    }
    
    public static void insertConfession(ActionEvent event,String id,String confession,String date)throws SQLException, FileNotFoundException{ //a method to insert the confession from user into database(without reply id)
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement spamCheck = null;
        ResultSet resultSet = null;
        
        try{
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
            spamCheck = connection.prepareStatement("SELECT * FROM pendingconf WHERE confession = ?"); //select all the confession post in pendingconf database
            spamCheck.setString(1, confession);
            resultSet = spamCheck.executeQuery();
            
            
            if(resultSet.isBeforeFirst()){ //if there is a same content(spam checking)
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please submit a different content");
                alert.show();
                
            }else{ //if there is no similar content
            
            if(checkBadWords(event,confession)==false){ //check is there any rude words, if false(no rude words)
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
                    preparedStatement = connection.prepareStatement("INSERT INTO confessionpage.pendingconf(idconfession,confession,replyid,date) VALUES (?,?,?,?)"); //insert to pendingconf
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, confession);
                    preparedStatement.setString(3, null);
                    preparedStatement.setString(4, date);
                    
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
                    
            }else if(checkBadWords(event,confession)==true){ //if there is including bad words
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please check your content.");
                alert.show();
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
    
    public static void insertConfessionWithImage(ActionEvent event,String id,String confession,String date)throws SQLException, FileNotFoundException{ //same method same the previous one, just including image submitted by user
        
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
                if(checkBadWords(event,confession)==false){
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
                }else if(checkBadWords(event,confession)==true){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please check your content.");
                    alert.show();
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
        stage.setScene(new Scene(root,1080,700));
        stage.show();   
        
    }
    
    public static void loginAdmin(ActionEvent event,String username,String password)throws SQLException, IOException{ //check whether the username and password entered by admin is correct
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adminlogin", "root", "root");  
            preparedStatement = connection.prepareStatement("SELECT password FROM admin WHERE username = ?"); 
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            
            if(!(resultSet.isBeforeFirst())){ //if username entered is not found in database
                System.out.println("User not found");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            }
            else{ //if username entered is found
                while(resultSet.next()){
                    String retrivedPassword = resultSet.getString("password");
                    if(retrivedPassword.equals(password)){ //check whether the password entered is correct
                         changeScene(event,username,"adminChoosePage.fxml"); //if correct, change the scene to adminChoosePage.fxml
                        
                    }
                    else{ //else, pop out the alert
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
    
    public static void insertPendingConf(ActionEvent event,String id,String confession,String replyid,String date)throws SQLException, FileNotFoundException{ //a method to insert the confession from user into database(with reply id)
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement spamCheck = null;
        PreparedStatement psCheckIDExists = null;
        ResultSet resultSet = null;
        
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
                if(checkBadWords(event,confession)==false){
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
                    psCheckIDExists = connection.prepareStatement("SELECT * FROM confession2 WHERE idconfession=?");
                    psCheckIDExists.setString(1, replyid);
                    resultSet = psCheckIDExists.executeQuery();
            
                    if(resultSet.isBeforeFirst()){
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
                    preparedStatement = connection.prepareStatement("INSERT INTO confessionpage.pendingconf(idconfession,confession,replyid,date) VALUES (?,?,?,?)");
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, confession);
                    preparedStatement.setString(3,replyid);
                    preparedStatement.setString(4, date);
                    
               
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
                }else if(checkBadWords(event,confession)==true){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please check your content.");
                    alert.show();
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
    
    public static void insertPendingConfWithImage(ActionEvent event,String id,String confession,String replyid,String date)throws SQLException, FileNotFoundException{ //a method to insert the confession from user into database(with reply id and image)
        
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
                if(checkBadWords(event,confession)==false){
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
                }else if(checkBadWords(event,confession)==true){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please check your content.");
                    alert.show();
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
    
    public static void timeScheduling() throws SQLException{ //timer task, the confession from pendingconf will upload automatically to confession2 based on different number of pending confession
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
        Statement stmt = con.createStatement();
        String query = "select count(*) from pendingconf"; //count how many pending confession in table
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt(1);
        System.out.println("Number of records in the cricketers_data table: "+count);
        
        if(count<=5){ //if less thn or equal 5
        
        Timer timer = new Timer();
        
        TimerTask task = new TimerTask(){

            @Override
            public void run() {
                
                    try {
                        insertConfession2(); //insert to confession2 table
                        removeConfession2(); //remove in the pendingconf table
                        System.out.println("done");
                    } catch (SQLException ex) {
                        Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("error");
                    }
                
            }
        
        };
        
        timer.schedule(task, 900000); //pop out every 15mins
            
        }else if(count<=10){ //less than or equal to 10
            
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
            
            timer.schedule(task, 600000); //pop out every 10 mins
            
        }else if(count>10){ //more than 10
            
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
            
            timer.schedule(task, 300000); //pop out every 5mins
            
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
            
            confID.dequeue();
            confConf.dequeue();
            confReplyID.dequeue();
            confDate.dequeue();
            
            System.out.println(confID.toString());
            System.out.println(confConf.toString());
            System.out.println(confReplyID.toString());
            System.out.println(confDate.toString());
               
            
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
    
    public static Image getImageById(MouseEvent event,String id) throws IOException { //for displaying image from confession2 database
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Image image = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root"); //connect to your databases, javafx-loginsigup is my scheme name, root is my user and root is my password 
            preparedStatement = connection.prepareStatement("SELECT image FROM confession2 WHERE idconfession = ? and image IS NOT NULL"); 
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
    
    public static Image getImageById2(MouseEvent event,String id) throws IOException { //for displaying image from pendingconf database
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Image image = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root"); //connect to your databases
            preparedStatement = connection.prepareStatement("SELECT image FROM pendingconf WHERE idconfession = ? and image IS NOT NULL"); 
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
    

