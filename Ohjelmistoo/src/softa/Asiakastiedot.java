package softa;

import javafx.scene.control.TextField;
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
import javafx.scene.control.PasswordField;

public class Asiakastiedot extends Menu {
	@FXML
    TextField hae;
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
    		   lista.getItems().clear();
    		  
    		   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
    			System.out.println("Connected With the database successfully");
    			PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas");
    	      
    	        ResultSet resultSet=preparedStatement.executeQuery();
    	        
    	        while(resultSet.next()){
    	             String id=resultSet.getString("asiakas_id");
    	             String etu=resultSet.getString("etunimi");
    	             String suku=resultSet.getString("sukunimi");
    	          
    	             Button x=new Button(id+" "+etu+" "+suku);
    	             
    	             lista.getItems().add(x);
    	             
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
    	            
    	           
    	        }
    	        
    			} catch (SQLException e) {
    			System.out.println("Error while connecting to the database");
    			}
    	   
    	   
	}
    public void listaHaku(){            // t�ss� haetaan id:n ja nimen perusteella asiakkaita
    	String hakutext=hae.getText();
    	System.out.println(hakutext);
    	lista.getItems().clear();
 	   try {
 		   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
 			System.out.println("Connected With the database successfully");
 			PreparedStatement preparedStatement=connection.prepareStatement(
 					"select * from asiakas where etunimi="+"'"+hakutext+"'"
 							+ "or asiakas_id="+"'"+hakutext+"'"
 							+"or sukunimi="+"'"+hakutext+"'"
 					
 					
 					);
 	        
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	       
 	        while(resultSet.next()){
 	             String id=resultSet.getString("asiakas_id");
 	             String etu=resultSet.getString("etunimi");
 	             String suku=resultSet.getString("sukunimi");
 	        
 	             Button x=new Button(id+" "+etu+" "+suku);
 	             
 	
 	            lista.getItems().add(x);
 	           
 	        }
 	        
 			} catch (SQLException e) {
 			System.out.println("Error while connecting to the database");
 			}
 	   
 	   
	}
    
    
	
}
