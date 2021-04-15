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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class Uusivaraus extends Menu {
	
	@FXML
    private TextField hae;
	@FXML
	private Button takas;
	@FXML
	private ListView<Button> lista;
	@FXML
    private Button päivitä;
	static int iddd=1;
	
	@FXML
    TextField etu;
    @FXML
    TextField suk;
    @FXML
    TextField puh;
    @FXML
    TextField säh;
    @FXML
    TextField oso;
    @FXML
    TextField pos;
    @FXML
    Label d;
	
	public void menu(ActionEvent event) throws IOException { 
		changeScene("Menu.fxml");

	}
	
	public void listapäivitys(){
 	   try {
 		   lista.getItems().clear();
 		  
 		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas");
 	      
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	        
 	        while(resultSet.next()){
 	             int id=resultSet.getInt("asiakas_id");
 	             String etu=resultSet.getString("etunimi");
 	             String suku=resultSet.getString("sukunimi");
 	        
 	             Button x=new Button(id+" "+etu+" "+suku);
 	            
 	            x.setOnAction((event) -> {
 	                System.out.println(x.getText());
	                     String sisältö=x.getText();
	                     String[] sisältöosissa= sisältö.split(" ");
	                    iddd = Integer.parseInt(sisältöosissa[0]);
	                 
 	            });
 	            
 	            lista.getItems().add(x);
 	           
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
 			System.out.println("Tiedot saatu!");
 			PreparedStatement preparedStatement=connection.prepareStatement(
 					"select * from asiakas where etunimi="+"'"+hakutext+"'"
 							+ "or asiakas_id="+"'"+hakutext+"'"
 							+"or sukunimi="+"'"+hakutext+"'"
 					
 					
 					);
 	        
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	       
 	        while(resultSet.next()){
 	             int id=resultSet.getInt("asiakas_id");
 	             String etu=resultSet.getString("etunimi");
 	             String suku=resultSet.getString("sukunimi");
 	        
 	             Button x=new Button(id+" "+etu+" "+suku);
 	            x.setOnAction((event) -> {
	                System.out.println(x.getText());
	                     String sisältö=x.getText();
	                     String[] sisältöosissa= sisältö.split(" ");
	                    iddd = Integer.parseInt(sisältöosissa[0]);
	                   
	            });
 	
 	            lista.getItems().add(x);
 	           
 	        }
 	        
 			} catch (SQLException e) {
 			System.out.println("Error while connecting to the database");
 			}
 	   
 	   
	}
		
	 public void päivitä() throws SQLException{
	    	
	    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
		
	    PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas where asiakas_id="+iddd);
	      
	    ResultSet resultSet=preparedStatement.executeQuery();
	    while(resultSet.next()){
	    	System.out.println(resultSet.getString("etunimi"));
	    	d.setText(resultSet.getString("asiakas_id"));
	    	etu.setText(resultSet.getString("etunimi"));
	        suk.setText(resultSet.getString("sukunimi"));
	        puh.setText(resultSet.getString("puhelinnro"));
	        säh.setText(resultSet.getString("email"));
	        oso.setText(resultSet.getString("lahiosoite"));
	        pos.setText(resultSet.getString("postinro"));
	        
	    }
	    
	    }
}
