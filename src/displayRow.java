
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ngsua
 */
public class displayRow {
    
    public static void main(String[] args) throws SQLException {
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/confessionpage", "root", "root");
                 Statement stmt = con.createStatement();
                 String query = "select count(*) from pendingconf";
                 ResultSet rs = stmt.executeQuery(query);
                 rs.next();
                 int count = rs.getInt(1);
                 System.out.println("Number of records in the cricketers_data table: "+count);
    }
    
}
