package softa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class Asiakastiedot extends Menu {
	
	@FXML
    Button takas;
	@FXML
	private ListView<Button> lista;
	@FXML
    Button p�ivit�;
	
	

    public void menu(ActionEvent event) throws IOException { //tällä  toiminnolla pääsee takasin päävalikkoon. Tätä kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
        
    }
    public void listap�ivitys(){
    	   try {
    		   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
    			System.out.println("Connected With the database successfully");
    			PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas");
    	        //Creating Java ResultSet object
    	        ResultSet resultSet=preparedStatement.executeQuery();
    	        while(resultSet.next()){
    	             String id=resultSet.getString("asiakas_id");
    	             String etu=resultSet.getString("etunimi");
    	             String suku=resultSet.getString("sukunimi");
    	             //Printing Results
    	            lista.getItems().add(new Button(id+" "+etu+" "+suku));
    	           
    	        }
    			} catch (SQLException e) {
    			System.out.println("Error while connecting to the database");
    			}
    	   
    	   
	}
    
	
	
}
