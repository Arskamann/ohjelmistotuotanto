package softa;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private Button p�ivit�;
	static int iddd=1;
	


	
	  	@FXML
	    TextField etu;
	    @FXML
	    TextField suk;
	    @FXML
	    TextField puh;
	    @FXML
	    TextField s�h;
	    @FXML
	    TextField oso;
	    @FXML
	    TextField pos;
	    @FXML
	    Button tallenna;
	@FXML
	Pane henk;
	@FXML
	Pane list;
	
	
	
	
	
	
	
	public void menu(ActionEvent event) throws IOException {
        changeScene("Menu.fxml");
        
    }
	public void takas(ActionEvent event) throws IOException {
       henk.setVisible(false);
       list.setVisible(true);
       listap�ivitys();
       tallenna.setStyle("-fx-background-color: #FFFFFF");
       tallenna.setText("Tallenna");
       
        
    }
	public void uusiAsiakas(ActionEvent event) throws IOException {
        changeScene("Uusiasiakas.fxml");
        
    }
   public void listap�ivitys(){
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
    	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
    	            
    	            x.setOnAction((event) -> {
    	                System.out.println(x.getText());
  	                     String sis�lt�=x.getText();
  	                     String[] sis�lt�osissa= sis�lt�.split(" ");
  	                   
  	                    iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
  	                   
  	                  list.setVisible(false);
					henk.setVisible(true);
					try {
						p�ivit�();
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
    public void listaHaku(){            // t�ss� haetaan id:n ja nimen perusteella asiakkaita
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
	             String id=resultSet.getString("asiakas_id");
	             String etu=resultSet.getString("etunimi");
	             String suku=resultSet.getString("sukunimi");
	        
	             Button x=new Button(etu+" "+suku);
	             x.setAccessibleText(id);               //  n�in saahaan se napin ID talteen ilman ett� sit� n�ytet��n siin�
	            
	            x.setOnAction((event) -> {
	                System.out.println(x.getText());
                     String sis�lt�=x.getText();
                     String[] sis�lt�osissa= sis�lt�.split(" ");
                   
                    iddd=Integer.parseInt(x.getAccessibleText()); // t�lleen saahaan se id sielt� sit poimittua
                   
                    list.setVisible(false);
					henk.setVisible(true);
					try {
						p�ivit�();
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
    
	//yksitt�isen asiakkaan asiat...
  
  
    
    @FXML
    Button p�iv;
    @FXML
    Label d;
    @FXML
    Button ua;
    @FXML
    Button poista;
    
   
    
    public void tallenna() throws SQLException {
    	
    	System.out.println(iddd);
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
		
		String etunimi=etu.getText();
		String sukunimi=suk.getText();
		String numero=puh.getText();
		String s�hk�posti=s�h.getText();
		String osoite=oso.getText();
		String posti=pos.getText();
		
		
	
    PreparedStatement preparedStatement=connection.prepareStatement(
    		"update asiakas set etunimi ='"+etunimi+"', sukunimi='"+sukunimi+"',"+"puhelinnro='"+numero+"'"
    				+ ", email='"+s�hk�posti+"', lahiosoite='"+osoite+"', postinro='"+posti+"' where asiakas_id="+iddd);
   preparedStatement.executeUpdate();
    tallenna.setText("Tallennettu");
    tallenna.setStyle("-fx-background-color: #00ff00");
    }
    public void p�ivit�() throws SQLException{
    	
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
        s�h.setText(resultSet.getString("email"));
        oso.setText(resultSet.getString("lahiosoite"));
        pos.setText(resultSet.getString("postinro"));
        
    }
    
    }
    
    @FXML
    TextField uusietu;
    @FXML
    TextField uusisuk;
    @FXML
    TextField uusipuh;
    @FXML
    TextField uusis�h;
    @FXML
    TextField uusioso;
    @FXML
    TextField uusipos;
    @FXML
    Button tallennauusi;
 public void tallennaUusi() {
	 try {
    	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+kanta, nimi, salis);
		System.out.println("Tiedot saatu!");
		
		String etunimi=uusietu.getText();
		String sukunimi=uusisuk.getText();
		String numero=uusipuh.getText();
		String s�hk�posti=uusis�h.getText();
		String osoite=uusioso.getText();
		String posti=uusipos.getText();
		
		
	
    PreparedStatement preparedStatement=connection.prepareStatement(
    		"insert into asiakas set etunimi ='"+etunimi+"', sukunimi='"+sukunimi+"',"+"puhelinnro='"+numero+"'"
    				+ ", email='"+s�hk�posti+"', lahiosoite='"+osoite+"', postinro='"+posti+"'");
   preparedStatement.executeUpdate();
   Alert a = new Alert(AlertType.INFORMATION);
	 a.setContentText("Uusi asiakas luotu!");
	 a.setTitle("Huomio");
	 a.show();
	 changeScene("Asiakastiedot.fxml");
	 }catch (Exception e) {
		 Alert a = new Alert(AlertType.INFORMATION);
		 a.setContentText("T�yt� kaikki kent�t!");
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
listap�ivitys();
	}catch(Exception e) {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("Asiakkaalla on aktiivinen varaus! Poista varaus ensin.");
		a.setTitle("Huomio");
		a.show();
	}
}
}
