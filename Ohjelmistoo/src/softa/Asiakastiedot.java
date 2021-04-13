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
    Button päivitä;
	
	

    public void menu(ActionEvent event) throws IOException { //tÃ¤llÃ¤  toiminnolla pÃ¤Ã¤see takasin pÃ¤Ã¤valikkoon. TÃ¤tÃ¤ kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
        
    }
   public void listapäivitys(){
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
    	                 public void handle(ActionEvent event) {   // kun henkilöstä klikkaa...
    	                     System.out.println(x.getText());
    	                     String sisältö=x.getText();
    	                     String[] sisältöosissa= sisältö.split(" ");
    	                     String idd = sisältöosissa[0];
    	                     System.out.println(idd); // tällä id:llä sitten etsitään asiakas-taulusta kyseisen henkilön tiedot!
    	                 }
    	             });
    	            
    	           
    	        }
    	        
    			} catch (SQLException e) {
    			System.out.println("Error while connecting to the database");
    			}
    	   
    	   
	}
    public void listaHaku(){            // tässä haetaan id:n ja nimen perusteella asiakkaita
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
