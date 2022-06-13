/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package confessionpage2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ngsua
 */
public class DisplayConfessionPageController implements Initializable{

    @FXML
    private Button submitButton;
    @FXML
    private TableView<Confession> tableView;
    @FXML
    private TableColumn<Confession, String> idCol;
    @FXML
    private TableColumn<Confession, String> confessionCol;
    @FXML
    private TableColumn<Confession, String> replyIDCol;
    @FXML
    private TableColumn<Confession, String> dateCol;
    @FXML
    private TextField searchTextField;
    @FXML
    private ImageView displayImage;
    @FXML
    private Button button;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Confession confession = null ;
    
    ObservableList<Confession>  confessionList = FXCollections.observableArrayList();
    
    public void confessionPage(ActionEvent event) throws IOException{ //a method to go to userConfessionPage.fxml
        root = FXMLLoader.load(getClass().getResource("userConfessionPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = database.getConnect();
        query = "SELECT * FROM confession2 ORDER BY DATE DESC";
        
        try{
            
            Statement statement = connection.createStatement();
            ResultSet queryOutput = statement.executeQuery(query);
            
            while(queryOutput.next()){
                
                String queryID = queryOutput.getString("idconfession");
                String queryConfession = queryOutput.getString("confession");
                String queryReplyID = queryOutput.getString("replyid");
                String queryDate = queryOutput.getString("date");
                
                //System.out.println(queryReplyID);
                confessionList.add(new Confession(queryID,queryConfession,queryReplyID,queryDate));
                
            }
            
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            confessionCol.setCellValueFactory(new PropertyValueFactory<>("confession"));
            replyIDCol.setCellValueFactory(new PropertyValueFactory<>("replyid"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            
            tableView.setItems(confessionList);
            
            FilteredList<Confession> filteredData = new FilteredList<>(confessionList, b -> true);
            
            searchTextField.textProperty().addListener((observable,oldValue,newValue) -> {
                filteredData.setPredicate(Confession -> {
                    
                    if(newValue.isEmpty()||newValue==null){
                       return true;
                    }
                    
                    String searchKeyword = newValue.toLowerCase();
                    
                    if(Confession.getConfession().toLowerCase().indexOf(searchKeyword) > -1){
                       return true;
                    }else if(Confession.getDate().toLowerCase().indexOf(searchKeyword) > -1){
                       return true;
                    }else if(Confession.getId().toLowerCase().indexOf(searchKeyword) > -1){
                       return true;
                    }else{
                       return false;
                    }
                    
                });
            });
            
            SortedList<Confession> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
            
        }catch(SQLException e){
            Logger.getLogger(DisplayConfessionPageController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        
    }
    
    public void showImage(MouseEvent event) throws IOException{
        tableView.setOnMouseClicked(e ->{
            try {
                confession = tableView.getSelectionModel().getSelectedItem();
                displayImage.setImage(database.getImageById(event, confession.getId()));
            } catch (IOException ex) {
                Logger.getLogger(DisplayConfessionPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
}
