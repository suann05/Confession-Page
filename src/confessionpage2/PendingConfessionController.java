/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package confessionpage2;

import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author ngsua
 */
public class PendingConfessionController implements Initializable{
    
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
    Confession confession = null ;
    
    ObservableList<Confession>  confessionList = FXCollections.observableArrayList();
    
    /*private void initialize() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File("C:\\Users\\ngsua\\OneDrive\\Documents\\NetBeansProjects\\confessionPage2\\confession.json")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (br != null) {
            String st;
            while ((st = br.readLine()) != null) {
                addConfessionToList(new GsonBuilder().create().fromJson(st, Confession.class));
            }
        }
        setTable();
    }
    
    private void addConfessionToList(Confession confession) {
        confessionList.add(confession);
    }
    
     private void setTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        confessionCol.setCellValueFactory(new PropertyValueFactory<>("confession"));
        replyidCol.setCellValueFactory(new PropertyValueFactory<>("replyid"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.setItems(confessionList);
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
}
    
    
    
    
    


