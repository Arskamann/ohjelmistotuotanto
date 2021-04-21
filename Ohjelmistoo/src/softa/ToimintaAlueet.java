package softa;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class ToimintaAlueet extends Menu {
	
	@FXML
	private Button takas;
	@FXML
	private ListView<Button> lista;
	@FXML
    private Button p�ivit�;
	static int iddd=1;
	
	@FXML
	Pane alue;
	@FXML
	Pane list;
	
	
	public void menu(ActionEvent event) throws IOException { 
		changeScene("Menu.fxml");

	}
	
	public void listap�ivitys(){
 	   try {
 		   lista.getItems().clear();
 		  
 		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement("select * from toimintaalue");
 	      
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	        
 	        while(resultSet.next()){
 	             String id=resultSet.getString("toimintaalue_id");
 	             String nimi=resultSet.getString("nimi");
 	        
 	             Button x=new Button(id+" "+nimi);
 	             x.setMinWidth(150);
 	             x.setAlignment(Pos.CENTER_LEFT);
 	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
 	            
 	            x.setOnAction((event) -> {
 	                System.out.println(x.getText());
	                     String sis�lt�=x.getText();
	                     String[] sis�lt�osissa= sis�lt�.split(" ");
	                   
	                    iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
	                   
	                  list.setVisible(false);
					alue.setVisible(true);
 	            });
	            
	                    
	                     
	                     
	             
 	
 	            lista.getItems().add(x);
 	           
 	        }
 	        
 			} catch (SQLException e) {
 			System.out.println("Error while connecting to the database");
 			}
 	   
 	   
	}
	
}

