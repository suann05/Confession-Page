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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ngsua
 */
public class DisplayPendingConfController implements Initializable {

    @FXML
    private TableView<Confession> tableView;
    @FXML
    private TableColumn<Confession, String> idCol;
    @FXML
    private TableColumn<Confession, String> confessionCol;
    @FXML
    private TableColumn<Confession, String> replyidCol;
    @FXML
    private TableColumn<Confession, String> dateCol;
    @FXML
    private Button backButton;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Confession confession = null ;
    
    ObservableList<Confession> confessionList = FXCollections.observableArrayList();
    
    public void refreshTable(){
         try {
            confessionList.clear();
            
            query = "SELECT * FROM pendingconf";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                confessionList.add(new Confession(
                        
                        resultSet.getString("idconfession"),
                        resultSet.getString("confession"),
                        resultSet.getString("date"),
                        resultSet.getString("replyid")));
                tableView.setItems(confessionList);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DisplayPendingConfController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
    }
    
    public void loadDate(){
        
        connection = database.getConnect();
        refreshTable();
        
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("idconfession"));
        confessionCol.setCellValueFactory(new PropertyValueFactory<>("confession"));
        replyidCol.setCellValueFactory(new PropertyValueFactory<>("replyid"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        
    }   
    
    public void backButton(ActionEvent event) throws IOException{ //a method to go to userConfessionPage.fxml
        root = FXMLLoader.load(getClass().getResource("adminChoosePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
