package softa;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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

public class Raportointi extends Menu {
	@FXML
    private TextField hae;
	@FXML
    private Button takas;
	@FXML
	private ListView<Button> lista;
	@FXML
    private Button päivitä;
	static int iddd=1;
	static boolean uusiposti=true;


	
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
	    TextField paik;
	    @FXML
	    TextField toim;
	    @FXML
	    Button tallenna;
	@FXML
	Pane henk;
	@FXML
	Pane list;
	
	
	
	   @FXML
	    TextField uusietu;
	    @FXML
	    TextField uusisuk;
	    @FXML
	    TextField uusipuh;
	    @FXML
	    TextField uusisäh;
	    @FXML
	    TextField uusioso;
	    @FXML
	    TextField uusipos;
	    @FXML
	    Button tallennauusi;
	
	
	
	public void menu(ActionEvent event) throws IOException {
        changeScene("Menu.fxml");
        
    }
	public void takas() throws IOException {
       henk.setVisible(false);
       list.setVisible(true);
       listapäivitys();
       tallenna.setStyle("-fx-background-color: #FFFFFF");
       tallenna.setText("Tallenna");
       
        
    }
	public void uusiAsiakas(ActionEvent event) throws IOException {
        changeScene("Uusiasiakas.fxml");
        
        
        
        
    }
   public void listapäivitys(){
    	   try {
    		   lista.getItems().clear();
    		  
    		  Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
    			System.out.println("Tiedot saatu!");
    			PreparedStatement preparedStatement=connection.prepareStatement("select * from asiakas");
    	      
    	        ResultSet resultSet=preparedStatement.executeQuery();
    	        
    	        while(resultSet.next()){
    	             String id=resultSet.getString("asiakas_id");
    	             String etu=resultSet.getString("etunimi");
    	             String suku=resultSet.getString("sukunimi");
    	        
    	             Button x=new Button(etu+" "+suku);
    	             x.setMinWidth(150);
    	             x.setAlignment(Pos.CENTER_LEFT);
    	             x.setAccessibleText(id);               //  näin saahaan se napin ID talteen ilman että sitä näytetään siinä
    	            
    	            x.setOnAction((event) -> {
    	                System.out.println(x.getText());
  	                     String sisältö=x.getText();
  	                     String[] sisältöosissa= sisältö.split(" ");
  	                   
  	                    iddd=Integer.parseInt(x.getAccessibleText()); // tälleen saahaan se id sieltä sit poimittua
  	                   
  	                  list.setVisible(false);
					henk.setVisible(true);
					try {
						päivitä();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
 					"select distinct * from asiakas,posti where etunimi="+"'"+hakutext+"'"
 						+" and asiakas.postinro=posti.postinro "	+ "or asiakas_id="+"'"+hakutext+"'"
 						+" and asiakas.postinro=posti.postinro "+"or sukunimi="+"'"+hakutext+"'"
 						+" and asiakas.postinro=posti.postinro "+"or toimipaikka='"+hakutext+"'"+" and asiakas.postinro=posti.postinro "
 					
 					
 					);
 	        
 	        ResultSet resultSet=preparedStatement.executeQuery();
 	       
 	       while(resultSet.next()){
	             String id=resultSet.getString("asiakas_id");
	             String etu=resultSet.getString("etunimi");
	             String suku=resultSet.getString("sukunimi");
	        
	             Button x=new Button(etu+" "+suku);
	             x.setMinWidth(150);
	             x.setAlignment(Pos.CENTER_LEFT);
	             x.setAccessibleText(id);               //  näin saahaan se napin ID talteen ilman että sitä näytetään siinä
	            
	            x.setOnAction((event) -> {
	                System.out.println(x.getText());
                     String sisältö=x.getText();
                     String[] sisältöosissa= sisältö.split(" ");
                   
                    iddd=Integer.parseInt(x.getAccessibleText()); // tälleen saahaan se id sieltä sit poimittua
                   
                    list.setVisible(false);
					henk.setVisible(true);
					try {
						päivitä();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
    Button päiv;
    @FXML
    Button ua;
    @FXML
    Button poista;
    
   
    
    public void tallenna() throws SQLException {
    	uusiposti=true;
    	System.out.println(iddd);
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
		
		String etunimi=etu.getText();
		String sukunimi=suk.getText();
		String numero=puh.getText();
		String sähköposti=säh.getText();
		String osoite=oso.getText();
		String posti=pos.getText();
		String toi=toim.getText();
		if(etunimi!=""&&sukunimi!=""&&posti!=""&&toi!=""&&osoite!="") {
		PreparedStatement preparedStatement2=connection.prepareStatement("SELECT * FROM posti");
		preparedStatement2.executeQuery();
		
		 ResultSet resultSet=preparedStatement2.executeQuery();
		    while(resultSet.next()){
		    	String num =resultSet.getString("postinro");
		    	
		        if(num.equals(posti)) {
		        	uusiposti=false;
		        }
		    }
		    if(uusiposti==true) {
				
				
				PreparedStatement preparedStatement3=connection.prepareStatement(
			    		"insert into posti set postinro ='"+posti+"',toimipaikka= '"+toi+"'");
			   preparedStatement3.executeUpdate();
			   Alert b = new Alert(AlertType.INFORMATION);
				 b.setContentText("postitietoja lisätty tietokantaan!");
				 b.setTitle("Huomio");
				 b.show();
			}
		
	
    PreparedStatement preparedStatement=connection.prepareStatement(
    		"update asiakas set etunimi ='"+etunimi+"', sukunimi='"+sukunimi+"',"+"puhelinnro='"+numero+"'"
    				+ ", email='"+sähköposti+"', lahiosoite='"+osoite+"', postinro='"+posti+"' where asiakas_id="+iddd);
   preparedStatement.executeUpdate();
    tallenna.setText("Tallennettu");
    tallenna.setStyle("-fx-background-color: #00ff00");
		}
		else {
			Alert a = new Alert(AlertType.INFORMATION);
			 a.setContentText("Täytä kaikki pakolliset kentät!");
			 a.setTitle("Huomio");
			 a.show();
		}
    }
    public void päivitä() throws SQLException{
    	
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
		 PreparedStatement preparedStatement2=connection.prepareStatement("select * from asiakas where asiakas_id="+iddd);
	      
		    ResultSet resultSet2=preparedStatement2.executeQuery();
		    String postinumero = null;
		    while(resultSet2.next()){
		    	postinumero=resultSet2.getString("postinro");
		    
		
		    }
		    
		    
	
    PreparedStatement preparedStatement=connection.prepareStatement("select * from vn.asiakas,vn.posti where asiakas_id="+iddd+" and posti.postinro= '"+postinumero+"'");
      
    ResultSet resultSet=preparedStatement.executeQuery();
    while(resultSet.next()){
    	System.out.println(resultSet.getString("etunimi"));
    	etu.setText(resultSet.getString("etunimi"));
        suk.setText(resultSet.getString("sukunimi"));
        puh.setText(resultSet.getString("puhelinnro"));
        säh.setText(resultSet.getString("email"));
        oso.setText(resultSet.getString("lahiosoite"));
        pos.setText(resultSet.getString("postinro"));
        toim.setText(resultSet.getString("toimipaikka"));
    }
    
    }
    
 
 public void tallennaUusi() {
	 
	 try {
		 
		 uusiposti=true;
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
		
		String etunimi=uusietu.getText();
		String sukunimi=uusisuk.getText();
		String numero=uusipuh.getText();
		String sähköposti=uusisäh.getText();
		String osoite=uusioso.getText();
		String posti=uusipos.getText();
		String toimip = toim.getText();
		if(etunimi!=""&&sukunimi!=""&&posti!=""&&toimip!=""&&osoite!="") {
			
		
		PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM posti");
		preparedStatement.executeQuery();
		
		 ResultSet resultSet=preparedStatement.executeQuery();
		    while(resultSet.next()){
		    	String num =resultSet.getString("postinro");
		    	
		        if(num.equals(posti)) {
		        	uusiposti=false;
		        }
		    }
		    if(uusiposti==true) {
				
				
				PreparedStatement preparedStatement3=connection.prepareStatement(
			    		"insert into posti set postinro ='"+posti+"',toimipaikka= '"+toimip+"'");
			   preparedStatement3.executeUpdate();
			   Alert b = new Alert(AlertType.INFORMATION);
				 b.setContentText("Uusi asiakas luotu ja postitietoja lisätty tietokantaan!");
				 b.setTitle("Huomio");
				 b.show();
			}
		    
		    
		    
    PreparedStatement preparedStatement2=connection.prepareStatement(
    		"insert into asiakas set etunimi ='"+etunimi+"', sukunimi='"+sukunimi+"',"+"puhelinnro='"+numero+"'"
    				+ ", email='"+sähköposti+"', lahiosoite='"+osoite+"', postinro='"+posti+"'");
   preparedStatement2.executeUpdate();
   Alert a = new Alert(AlertType.INFORMATION);
	 a.setContentText("Uusi asiakas luotu!");
	 a.setTitle("Huomio");
	 a.show();
	 changeScene("Asiakastiedot.fxml");
		}
		else {
			Alert a = new Alert(AlertType.INFORMATION);
			 a.setContentText("Täytä kaikki pakolliset kentät!");
			 a.setTitle("Huomio");
			 a.show();
		}
	 }catch (Exception e) {
		 Alert a = new Alert(AlertType.INFORMATION);
		 a.setContentText("Virhe!");
		 a.setTitle("Huomio");
		 a.show();
	 }
	 
    }
 
public void poista() throws IOException {
	try {
	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
	System.out.println("Tiedot saatu!");
	
	

PreparedStatement preparedStatement=connection.prepareStatement("delete from asiakas where asiakas_id="+iddd);
preparedStatement.executeUpdate();
Alert a = new Alert(AlertType.INFORMATION);
a.setContentText("Asiakas poistettu");
a.setTitle("Huomio");
a.show();
takas();
listapäivitys();
	}catch(Exception e) {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Asiakkaalla on aktiivinen varaus! Poista varaus ensin.");
		a.setTitle("Huomio");
		a.show();
	}
}
}
