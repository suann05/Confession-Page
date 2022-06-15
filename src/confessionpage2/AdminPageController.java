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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ngsua
 */
public class AdminPageController implements Initializable {

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
    private TableColumn<Confession, String> editCol1;
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
    
    ObservableList<Confession>  confessionList = FXCollections.observableArrayList();
    
    public void refreshTable(){ //get data from database and refresh everytime when the button is clicked
         try {
            confessionList.clear();
            
            query = "SELECT * FROM confession2 ORDER BY date DESC";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                confessionList.add(new Confession(
                        
                        resultSet.getString("idconfession"),
                        resultSet.getString("confession"),
                        resultSet.getString("replyid"),
                        resultSet.getString("date")
                
                ));
                      
                tableView.setItems(confessionList);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
    }
    
    public void loadDate(){ //display the database to the tableview
        
        connection = database.getConnect();
        refreshTable();
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        confessionCol.setCellValueFactory(new PropertyValueFactory<>("confession"));
        replyIDCol.setCellValueFactory(new PropertyValueFactory<>("replyid"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        Callback<TableColumn<Confession, String>, TableCell<Confession, String>> cellFactory1 = (TableColumn<Confession, String> param) -> {
            // make cell containing buttons
            final TableCell<Confession, String> cell = new TableCell<Confession, String>() {
                
                private final Button btn = new Button("Remove");
                
                {
                        btn.setOnAction((ActionEvent event) -> {
                            
                            try {
                                confession = tableView.getSelectionModel().getSelectedItem();
                                //database connection
                                connection = database.getConnect();
                                query = "DELETE FROM `confession2` WHERE idconfession=?"; 
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, confession.getId());
                                preparedStatement.executeUpdate();
                                refreshTable();
                                
                                ObservableList<Confession> allConfession,singleConfession;
                                allConfession=tableView.getItems();
                                singleConfession = tableView.getSelectionModel().getSelectedItems();
                                singleConfession.forEach(allConfession::remove);
                            } catch (SQLException ex) {
                                Logger.getLogger(AdminPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            
                        });
                    }
                
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        setGraphic(btn);
                    }
                }

            };

            return cell;
        };
        
         editCol1.setCellFactory(cellFactory1);
         tableView.setItems(confessionList);
         
         
    }
    
    public void showImage(MouseEvent event) throws IOException{ //a method to show the image posted by user
        tableView.setOnMouseClicked(e ->{
            try {
                confession = tableView.getSelectionModel().getSelectedItem();
                displayImage.setImage(database.getImageById(event, confession.getId()));
                Alert alert = new Alert(Alert.AlertType.NONE,"Image submitted by user", ButtonType.CLOSE);
                alert.setGraphic(displayImage);
                alert.showAndWait();
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
    

