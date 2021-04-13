package softa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
    	             Button x=new Button(id+" "+etu+" "+suku);
    	             x.setOnAction(new EventHandler<ActionEvent>() {
    	                 @Override
    	                 public void handle(ActionEvent event) {   // kun henkil�st� klikkaa...
    	                     System.out.println(x.getText());
    	                     String sis�lt�=x.getText();
    	                     String[] sis�lt�osissa= sis�lt�.split(" ");
    	                     String idd = sis�lt�osissa[0];
    	                     System.out.println(idd); // t�ll� id:ll� sitten etsit��n asiakas-taulusta kyseisen henkil�n tiedot!
    	                 }
    	             });
    	            lista.getItems().add(x);
    	           
    	        }
    			} catch (SQLException e) {
    			System.out.println("Error while connecting to the database");
    			}
    	   
    	   
	}
    public void henkil�nappi(ActionEvent event,String id){
 	 
 	   
 	   
	}
    
	
	
}
