package softa;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
public class Laskutus extends Menu {
	@FXML
    private TextField hae;
	@FXML
    private Button takas;
	@FXML
	private ListView<Text> lista;
	@FXML
    private Button p‰ivit‰;
	static int iddd=1;
	static boolean uusiposti=true;


	
	
	

	
	
	
	
	
	
	
	public void menu(ActionEvent event) throws IOException {
        changeScene("Menu.fxml");
        
    }
	
	
   public void listap‰ivitys(){
    	   try {
    		   lista.getItems().clear();
    		  
    		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
    			System.out.println("Tiedot saatu!");
    			PreparedStatement preparedStatement=connection.prepareStatement("select distinct * from lasku,varaus,asiakas where varaus.varaus_id=lasku.varaus_id and asiakas.asiakas_id=varaus.asiakas_id and maksettu='ei' order by varattu_loppupvm");
    	      
    	        ResultSet resultSet=preparedStatement.executeQuery();
    	        
    	        while(resultSet.next()){
    	        	String id=resultSet.getString("varaus_id");
    	        	String nimi =resultSet.getString("etunimi");
    	        	String snimi =resultSet.getString("sukunimi");
    	        	String summa=resultSet.getString("summa");
    	        	String aika=resultSet.getString("varattu_loppupvm").substring(0,9);
    	        	Text x = new Text("Laskutettava: "+nimi+" "+snimi+" Summa: "+summa+"Ä"+" | Varaus p‰‰ttyi: "+aika);
    	            x.setAccessibleText(id);
    	          
    	            
    	                    
    	                     
    	                     
    	             
    	
    	            lista.getItems().add(x);
    	           
    	        }
    	        
    			} catch (SQLException e) {
    			System.out.println("Error while connecting to the database");
    			}
    	   
    	   
	}
   public void listap‰ivitys2(){
	   try {
		   lista.getItems().clear();
		  
		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
			PreparedStatement preparedStatement=connection.prepareStatement("select distinct * from lasku,varaus,asiakas where varaus.varaus_id=lasku.varaus_id and asiakas.asiakas_id=varaus.asiakas_id and maksettu='kyll‰' order by varattu_loppupvm");
	      
	        ResultSet resultSet=preparedStatement.executeQuery();
	        
	        while(resultSet.next()){
	        	String id=resultSet.getString("varaus_id");
	        	String nimi =resultSet.getString("etunimi");
	        	String snimi =resultSet.getString("sukunimi");
	        	String summa=resultSet.getString("summa");
	        	String aika=resultSet.getString("varattu_loppupvm").substring(0,9);
	        	Text x = new Text("Laskutettava: "+nimi+" "+snimi+" Summa: "+summa+"Ä"+" Varaus p‰‰ttyi: "+aika);
	            x.setAccessibleText(id);
	          
	            
	                    
	                     
	                     
	             
	
	            lista.getItems().add(x);
	           
	        }
	        
			} catch (SQLException e) {
			System.out.println("Error while connecting to the database");
			}
	   
	   
}
    public void listaHaku(){            // t‰ss‰ haetaan id:n ja nimen perusteella asiakkaita
    	String hakutext=hae.getText();
    	System.out.println(hakutext);
    	lista.getItems().clear();
    	 try {
  		   lista.getItems().clear();
  		  
  		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
  			System.out.println("Tiedot saatu!");
  			PreparedStatement preparedStatement=connection.prepareStatement("select distinct * from lasku,varaus where varaus.varaus_id=lasku.varaus_id and maksettu='ei' sort by varattu_loppupvm");
  	      
  	        ResultSet resultSet=preparedStatement.executeQuery();
  	        
  	        while(resultSet.next()){
  	        	String id=resultSet.getString("varaus_id");
  	        	String nimi =resultSet.getString("etunimi");
  	        	String snimi =resultSet.getString("sukunimi");
  	        	String summa=resultSet.getString("summa");
  	        	Text x = new Text("Laskutettava: "+nimi+" "+snimi+" "+summa);
  	            x.setAccessibleText(id);
  	          
 	            
 	                    
 	                     
 	                     
 	             
  	
  	            lista.getItems().add(x);
  	           
  	        }
  	        
  			} catch (SQLException e) {
  			System.out.println("Error while connecting to the database");
  			}
 	   
 	   
	}
    
    

  
    
  
 
    
   
    

    
 

 

}
