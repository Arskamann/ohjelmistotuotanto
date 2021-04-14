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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Asiakastiedot extends Menu {
	@FXML
    private TextField hae;
	@FXML
    private Button takas;
	@FXML
	private ListView<Button> lista;
	@FXML
    private Button päivitä;
	
	 String etunimi="öpö";
     String  sukunimi;
     String puhelinnumero;
     String  sähköposti;
     String  osoite;
     String postinumero;

    public void menu(ActionEvent event) throws IOException { //tÃ¤llÃ¤  toiminnolla pÃ¤Ã¤see takasin pÃ¤Ã¤valikkoon. TÃ¤tÃ¤ kutsutaan uusivaraus.xml tiedostossa
        changeScene("Menu.fxml");
        
    }
   public void listapäivitys(){
    	   try {
    		   lista.getItems().clear();
    		  
    		   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
    			System.out.println("Tiedot saatu!");
    			PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas");
    	      
    	        ResultSet resultSet=preparedStatement.executeQuery();
    	        
    	        while(resultSet.next()){
    	             int id=resultSet.getInt("asiakas_id");
    	             String etu=resultSet.getString("etunimi");
    	             String suku=resultSet.getString("sukunimi");
    	        
    	             Button x=new Button(id+" "+etu+" "+suku);
    	            x.setOnAction(new EventHandler<ActionEvent>() {
   	                 @Override
   	                 public void handle(ActionEvent event) {   // kun henkilöstä klikkaa...
   	                     System.out.println(x.getText());
   	                     String sisältö=x.getText();
   	                     String[] sisältöosissa= sisältö.split(" ");
   	                     int idd = Integer.parseInt(sisältöosissa[0]);
   	                     System.out.println(idd); // tällä id:llä sitten etsitään asiakas-taulusta kyseisen henkilön tiedot!
   	                     try {
   							asiakas(idd);
   						} catch (IOException e) {
   							// TODO Auto-generated catch block
   							e.printStackTrace();
   						}
   	                     
   	                     
   	                 }
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
 	            x.setOnAction(new EventHandler<ActionEvent>() {
	                 @Override
	                 public void handle(ActionEvent event) {   // kun henkilöstä klikkaa...
	                     System.out.println(x.getText());
	                     String sisältö=x.getText();
	                     String[] sisältöosissa= sisältö.split(" ");
	                     int idd = Integer.parseInt(sisältöosissa[0]);
	                     System.out.println(idd); // tällä id:llä sitten etsitään asiakas-taulusta kyseisen henkilön tiedot!
	                     try {
							asiakas(idd);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                     
	                     
	                 }
	             });
 	
 	            lista.getItems().add(x);
 	           
 	        }
 	        
 			} catch (SQLException e) {
 			System.out.println("Error while connecting to the database");
 			}
 	   
 	   
	}
    
    
   // ----------------------------------------------------------------
    
	//yksittäisen asiakkaan asiat...
  
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
    Button tallenna;
    @FXML
    Label et;
    @FXML
    Label su;
    @FXML
    Label pu;
    @FXML
    Label sä;
    @FXML
    Label os;
    @FXML
    Label po;
    
   
    public void asiakas(int id) throws IOException { //tÃ¤llÃ¤  toiminnolla pÃ¤Ã¤see takasin pÃ¤Ã¤valikkoon. TÃ¤tÃ¤ kutsutaan uusivaraus.xml tiedostossa
        changeScene("asiakas.fxml");
       
        try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
			System.out.println("Tiedot saatu!");
		
        PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas where asiakas_id="+id);
	      
        ResultSet resultSet=preparedStatement.executeQuery();
        
        while(resultSet.next()){
        	System.out.println(resultSet.getString("etunimi"));
            et.setText(resultSet.getString("etunimi"));
            su.setText(resultSet.getString("sukunimi"));
            pu.setText(resultSet.getString("puhelinnro"));
            sä.setText(resultSet.getString("email"));
            os.setText(resultSet.getString("lahiosoite"));
            po.setText(resultSet.getString("postinro"));
            
        }
        
        
       
        
        } catch (SQLException e) {
			System.out.println("Error while connecting to the database");
			e.printStackTrace();
		}
     
    }
    
    
}
