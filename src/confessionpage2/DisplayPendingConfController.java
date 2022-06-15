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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private Button refreshButton;
    @FXML
    private ImageView displayImage;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Confession confession = null ;
    
    ObservableList<Confession> confessionList = FXCollections.observableArrayList();
    
    public void refreshTable(){//get data from database and refresh everytime when the button is clicked
         try {
            confessionList.clear();
            
            query = "SELECT * FROM pendingconf ORDER BY date DESC";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                confessionList.add(new Confession(
                        
                        resultSet.getString("idconfession"),
                        resultSet.getString("confession"),
                        resultSet.getString("replyid"),
                        resultSet.getString("date")));
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
    
    public void loadDate(){ //connect to database and get the value
        
        connection = database.getConnect();
        refreshTable();
        
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        confessionCol.setCellValueFactory(new PropertyValueFactory<>("confession"));
        replyidCol.setCellValueFactory(new PropertyValueFactory<>("replyid"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        
    }
    
    public void showImage(MouseEvent event) throws IOException{ //show the image where posted by user
        tableView.setOnMouseClicked(e ->{
            try {
                confession = tableView.getSelectionModel().getSelectedItem();
                displayImage.setImage(database.getImageById(event, confession.getId()));
            } catch (IOException ex) {
                Logger.getLogger(DisplayConfessionPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void backButton(ActionEvent event) throws IOException{ //a method to go to adminChoosePage.fxml
        root = FXMLLoader.load(getClass().getResource("adminChoosePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
