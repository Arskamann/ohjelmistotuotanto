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
    Button päivitä;
	
	

    public void menu(ActionEvent event) throws IOException { //tÃ¤llÃ¤  toiminnolla pÃ¤Ã¤see takasin pÃ¤Ã¤valikkoon. TÃ¤tÃ¤ kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
        
    }
    public void listapäivitys(){
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
    	                 public void handle(ActionEvent event) {   // kun henkilöstä klikkaa...
    	                     System.out.println(x.getText());
    	                     String sisältö=x.getText();
    	                     String[] sisältöosissa= sisältö.split(" ");
    	                     String idd = sisältöosissa[0];
    	                     System.out.println(idd); // tällä id:llä sitten etsitään asiakas-taulusta kyseisen henkilön tiedot!
    	                 }
    	             });
    	            lista.getItems().add(x);
    	           
    	        }
    			} catch (SQLException e) {
    			System.out.println("Error while connecting to the database");
    			}
    	   
    	   
	}
    public void henkilönappi(ActionEvent event,String id){
 	 
 	   
 	   
	}
    
	
	
}
